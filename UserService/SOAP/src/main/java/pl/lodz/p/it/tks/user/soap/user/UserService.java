package pl.lodz.p.it.tks.user.soap.user;

import lombok.NoArgsConstructor;
import org.json.JSONObject;
import pl.lodz.p.it.tks.user.soap.dtosoap.UserSoap;
import pl.lodz.p.it.tks.user.soap.exception.SoapException;
import pl.lodz.p.it.tks.user.soap.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.user.soap.validation.user.AddUserValid;
import pl.lodz.p.it.tks.user.soap.validation.user.UpdateUserValid;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jms.*;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@WebService(serviceName = "UserAPI")
public class UserService {

    @Inject
    private UserUseCase userUseCase;

    @Inject
    private JMSContext jmsContext;

    private Topic topic;

    @PostConstruct
    private void initTopic() {
        topic = jmsContext.createTopic("UserTopic");
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap addUser(@AddUserValid UserSoap userSoap) throws RepositoryAdapterException {
        User newUser = UserSoap.fromSoap(userSoap);
        newUser.setId(UUID.randomUUID());

        TemporaryQueue addTemporaryQueue = null;
        try {
            addTemporaryQueue = sendPayload("user.create", JSONObject.wrap(newUser).toString());
            List<Message> messages = receive(addTemporaryQueue);

            boolean allSucceed = messages.stream().map(x -> {
                try {
                    return x.getStringProperty("result");
                } catch (JMSException e) {
                    throw new SoapException(e.getMessage());
                }
            }).allMatch(x -> x.equals("success"));

            if (!allSucceed) {
                TemporaryQueue deleteTemporaryQueue = sendPayload("user.delete", JSONObject.wrap(newUser).toString());
            }
        } catch (JMSException e) {
            throw new SoapException(e.getMessage());
        }
        return UserSoap.toSoap(userUseCase.get(newUser.getId()));
    }

    private TemporaryQueue sendPayload(String action, String payload) throws JMSException {
        TemporaryQueue tq = jmsContext.createTemporaryQueue();
        Message msg = jmsContext.createMessage();
        msg.setStringProperty("action", action);
        msg.setStringProperty("payload", payload);
        msg.setJMSReplyTo(tq);
        msg.setJMSCorrelationID(UUID.randomUUID().toString());
        JMSProducer jmsProducer = jmsContext.createProducer();
        jmsProducer.send(topic, msg);
        return tq;
    }

    private List<Message> receive(TemporaryQueue tq) {
        JMSConsumer jmsConsumer = jmsContext.createConsumer(tq);
        Message msg = null;
        List<Message> messageList = new ArrayList<>();
        do {
            msg = jmsConsumer.receive(5000);
            if (msg != null) {
                messageList.add(msg);
            }
        } while (msg != null);

        return messageList;
    }

    @WebMethod
    public List<UserSoap> getAllUsers() {
        List<UserSoap> list = userUseCase.getAll().stream().map(UserSoap::toSoap).collect(Collectors.toList());
        list.forEach(x -> x.setPassword(null));
        return list;
    }

    @WebMethod
    public UserSoap getUser(@WebParam(name="id") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = userUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = userUseCase.get(id);
        }
        UserSoap us = UserSoap.toSoap(userUseCase.get(user.getId()));
        us.setPassword(null);
        return us;
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap updateUser(@UpdateUserValid UserSoap userDto) throws RepositoryAdapterException {
        User oldUser = userUseCase.get(UUID.fromString(userDto.getId()));

        User editingUser = UserSoap.fromSoap(userDto);
        editingUser.setActive(oldUser.isActive());

        TemporaryQueue addTemporaryQueue = null;
        try {
            addTemporaryQueue = sendPayload("user.update", JSONObject.wrap(editingUser).toString());
            List<Message> messages = receive(addTemporaryQueue);

            boolean allSucceed = messages.stream().map(x -> {
                try {
                    return x.getStringProperty("result");
                } catch (JMSException e) {
                    throw new SoapException(e.getMessage());
                }
            }).allMatch(x -> x.equals("success"));

            if (!allSucceed) {
                TemporaryQueue deleteTemporaryQueue = sendPayload("user.update", JSONObject.wrap(oldUser).toString());
            }
        } catch (JMSException e) {
            throw new SoapException(e.getMessage());
        }

        UserSoap userSoap = UserSoap.toSoap(userUseCase.get(editingUser.getId()));
        userSoap.setPassword(null);

        return userSoap;
    }

    @WebMethod
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public UserSoap activateUser(@ActivateUserValid UserSoap userSoap) throws RepositoryAdapterException {
        User oldUser = userUseCase.get(UUID.fromString(userSoap.getId()));

        User editingUser = UserSoap.fromSoap(UserSoap.toSoap(oldUser));
        editingUser.setActive(userSoap.getActive());

        TemporaryQueue addTemporaryQueue = null;
        try {
            addTemporaryQueue = sendPayload("user.update", JSONObject.wrap(editingUser).toString());
            List<Message> messages = receive(addTemporaryQueue);

            boolean allSucceed = messages.stream().map(x -> {
                try {
                    return x.getStringProperty("result");
                } catch (JMSException e) {
                    throw new SoapException(e.getMessage());
                }
            }).allMatch(x -> x.equals("success"));

            if (!allSucceed) {
                TemporaryQueue deleteTemporaryQueue = sendPayload("user.update", JSONObject.wrap(oldUser).toString());
            }
        } catch (JMSException e) {
            throw new SoapException(e.getMessage());
        }

        UserSoap userSoapChanged = UserSoap.toSoap(userUseCase.get(editingUser.getId()));
        userSoapChanged.setPassword(null);

        return userSoapChanged;
    }
}
