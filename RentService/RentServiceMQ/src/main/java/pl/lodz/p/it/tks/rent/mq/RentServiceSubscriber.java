package pl.lodz.p.it.tks.rent.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import pl.lodz.p.it.tks.rent.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.rent.applicationports.ui.AdminUseCase;
import pl.lodz.p.it.tks.rent.applicationports.ui.CustomerUseCase;
import pl.lodz.p.it.tks.rent.applicationports.ui.EmployeeUseCase;
import pl.lodz.p.it.tks.rent.domainmodel.user.Admin;
import pl.lodz.p.it.tks.rent.domainmodel.user.Customer;
import pl.lodz.p.it.tks.rent.domainmodel.user.Employee;
import pl.lodz.p.it.tks.rent.domainmodel.user.User;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.util.UUID;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "UserTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
})
public class RentServiceSubscriber implements MessageListener {

    @Inject
    private JMSContext jmsContext;

    @Inject
    private AdminUseCase adminUseCase;

    @Inject
    private CustomerUseCase customerUseCase;

    @Inject
    private EmployeeUseCase employeeUseCase;

    public void onMessage(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JMSProducer jmsProducer = jmsContext.createProducer().setJMSReplyTo(message.getJMSReplyTo());
            String userPayload = message.getStringProperty("payload");

            JSONObject jsonObject = new JSONObject(userPayload);
            Message reply = jmsContext.createMessage();

            switch (message.getStringProperty("action")) {
                case "user.create": {
                    try {
                        User addUser = null;
                        if ("ADMIN".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            addUser = adminUseCase.add((Admin) UserFactory.createUser(jsonObject));
                        } else if ("CUSTOMER".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            addUser = customerUseCase.add((Customer) UserFactory.createUser(jsonObject));
                        } else if ("EMPLOYEE".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            addUser = employeeUseCase.add((Employee) UserFactory.createUser(jsonObject));
                        }
                        reply.setStringProperty("result", "success");
                        reply.setStringProperty("response", JSONObject.wrap(addUser).toString());
                    } catch (RepositoryAdapterException e) {
                        reply.setStringProperty("result", "failure");
                    }
                    break;
                }
                case "user.update": {
                    try {
                        User updateUser = null;
                        if ("ADMIN".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            updateUser = adminUseCase.update((Admin) UserFactory.createUser(jsonObject));
                        } else if ("CUSTOMER".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            updateUser = customerUseCase.update((Customer) UserFactory.createUser(jsonObject));
                        } else if ("EMPLOYEE".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            updateUser = employeeUseCase.update((Employee) UserFactory.createUser(jsonObject));
                        }
                        reply.setStringProperty("result", "success");
                        reply.setStringProperty("response", JSONObject.wrap(updateUser).toString());
                    } catch (RepositoryAdapterException e) {
                        reply.setStringProperty("result", "failure");
                    }
                    break;
                }
                case "user.delete": {
                    try {
                        if ("ADMIN".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            adminUseCase.delete(UUID.fromString(jsonObject.getString("id")));
                        } else if ("CUSTOMER".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            customerUseCase.delete(UUID.fromString(jsonObject.getString("id")));
                        } else if ("EMPLOYEE".equalsIgnoreCase(jsonObject.getString("userType"))) {
                            employeeUseCase.delete(UUID.fromString(jsonObject.getString("id")));
                        }
                        reply.setStringProperty("result", "success");
                        reply.setStringProperty("response", jsonObject.getString("id"));
                    } catch (RepositoryAdapterException e) {
                        reply.setStringProperty("result", "failure");
                    }
                    break;
                }
                default:
                    break;
            }

            reply.setJMSCorrelationID(message.getJMSCorrelationID());
            jmsProducer.send(message.getJMSReplyTo(), reply);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
