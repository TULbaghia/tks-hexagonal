package pl.lodz.p.it.tks.user.rest.user;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.json.JSONObject;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;
import pl.lodz.p.it.tks.user.rest.dto.UserDto;
import pl.lodz.p.it.tks.user.rest.exception.RestException;
import pl.lodz.p.it.tks.user.rest.validation.user.ActivateUserValid;
import pl.lodz.p.it.tks.user.rest.validation.user.AddUserValid;
import pl.lodz.p.it.tks.user.rest.validation.user.UpdateUserValid;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@ApplicationScoped
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("user")
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

    @POST
    @Metered(name = "addUserMetered")
    @Counted(name = "addUserCounted", absolute = true)
    @Timed(name = "addUserTimed", unit = MetricUnits.MICROSECONDS, tags = {"type=timer"})
    public String addUser(@AddUserValid UserDto userDto) throws RepositoryAdapterException {
        User newUser = UserDto.fromDto(userDto);
        newUser.setId(UUID.randomUUID());

        TemporaryQueue addTemporaryQueue = null;
        try {
            addTemporaryQueue = sendPayload("user.create", JSONObject.wrap(newUser).toString());
            List<Message> messages = receive(addTemporaryQueue);

            boolean allSucceed = messages.stream().map(x -> {
                try {
                    return x.getStringProperty("result");
                } catch (JMSException e) {
                    throw new RestException(e.getMessage());
                }
            }).allMatch(x -> x.equals("success"));

            if (!allSucceed) {
                TemporaryQueue deleteTemporaryQueue = sendPayload("user.delete", JSONObject.wrap(newUser).toString());
            }
        } catch (JMSException e) {
            throw new RestException(e.getMessage());
        }
        return null;
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

    @GET
    @Metered(name = "getAllUserMetered")
    @Counted(name = "getAllUserCounted", absolute = true)
    @Timed(name = "getAllUserTimed", unit = MetricUnits.MICROSECONDS, tags = {"type=timer"})
    public String getAllUsers() {
        return JSONObject.valueToString(userUseCase.getAll().stream().map(UserDto::toDto).collect(Collectors.toList()));
    }

    @Path("/{uuid}")
    @GET
    @Metered(name = "getUserMetered")
    @Counted(name = "getUserCounted", absolute = true)
    @Timed(name = "getUserTimed", unit = MetricUnits.MICROSECONDS, tags = {"type=timer"})
    public String getUser(@PathParam("uuid") String id) throws RepositoryAdapterException {
        User user;
        try {
            user = userUseCase.get(UUID.fromString(id));
        } catch (IllegalArgumentException | RepositoryAdapterException e) {
            user = userUseCase.get(id);
        }
        return JSONObject.wrap(UserDto.toDto(userUseCase.get(user.getId()))).toString();
    }

    @PUT
    @Metered(name = "updateUserMetered")
    @Counted(name = "updateUserCounted", absolute = true)
    @Timed(name = "updateUserTimed", unit = MetricUnits.MICROSECONDS, tags = {"type=timer"})
    public String updateUser(@UpdateUserValid UserDto userDto) throws RepositoryAdapterException {
        User oldUser = userUseCase.get(UUID.fromString(userDto.getId()));

        User editingUser = UserDto.fromDto(userDto);
        editingUser.setActive(oldUser.isActive());

        TemporaryQueue addTemporaryQueue = null;
        try {
            addTemporaryQueue = sendPayload("user.update", JSONObject.wrap(editingUser).toString());
            List<Message> messages = receive(addTemporaryQueue);

            boolean allSucceed = messages.stream().map(x -> {
                try {
                    return x.getStringProperty("result");
                } catch (JMSException e) {
                    throw new RestException(e.getMessage());
                }
            }).allMatch(x -> x.equals("success"));

            if (!allSucceed) {
                TemporaryQueue deleteTemporaryQueue = sendPayload("user.update", JSONObject.wrap(oldUser).toString());
            }
        } catch (JMSException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(UserDto.toDto(userUseCase.get(editingUser.getLogin()))).toString();
    }

    @PATCH
    @Metered(name = "activateUserMetered")
    @Counted(name = "activateUserCounted", absolute = true)
    @Timed(name = "activateUserTimed", unit = MetricUnits.MICROSECONDS, tags = {"type=timer"})
    public String activateUser(@ActivateUserValid UserDto userDto) throws RepositoryAdapterException {
        User oldUser = userUseCase.get(UUID.fromString(userDto.getId()));

        User activatedUser = UserDto.fromDto(UserDto.toDto(oldUser));
        activatedUser.setActive(userDto.getActive());

        TemporaryQueue addTemporaryQueue = null;
        try {
            addTemporaryQueue = sendPayload("user.update", JSONObject.wrap(activatedUser).toString());
            List<Message> messages = receive(addTemporaryQueue);

            boolean allSucceed = messages.stream().map(x -> {
                try {
                    return x.getStringProperty("result");
                } catch (JMSException e) {
                    throw new RestException(e.getMessage());
                }
            }).allMatch(x -> x.equals("success"));

            if (!allSucceed) {
                TemporaryQueue deleteTemporaryQueue = sendPayload("user.update", JSONObject.wrap(oldUser).toString());
            }
        } catch (JMSException e) {
            throw new RestException(e.getMessage());
        }
        return JSONObject.wrap(UserDto.toDto(userUseCase.get(activatedUser.getLogin()))).toString();
    }
}
