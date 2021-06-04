package pl.lodz.p.it.tks.user.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import pl.lodz.p.it.tks.user.applicationports.exception.RepositoryAdapterException;
import pl.lodz.p.it.tks.user.applicationports.ui.UserUseCase;
import pl.lodz.p.it.tks.user.domainmodel.user.User;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;

@ApplicationScoped
@NoArgsConstructor
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "UserTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
})
public class UserServiceSubscriber implements MessageListener {

    @Inject
    private JMSContext jmsContext;

    @Inject
    private UserUseCase userUseCase;

    public void onMessage(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JMSProducer jmsProducer =  jmsContext.createProducer().setJMSReplyTo(message.getJMSReplyTo());
            String userPayload = message.getStringProperty("payload");
            User user = objectMapper.readValue(userPayload, User.class);
            Message reply = jmsContext.createMessage();

            switch (message.getStringProperty("action")) {
                case "user.create" : {
                    try {
                        User addUser = userUseCase.add(user);
                        reply.setStringProperty("result", "success");
                        reply.setStringProperty("response", JSONObject.wrap(addUser).toString());
                    } catch (RepositoryAdapterException e) {
                        reply.setStringProperty("result", "failure");
                    }
                    break;
                }
                case "user.delete" : {
                    try {
                        userUseCase.delete(user.getId());
                        reply.setStringProperty("result", "success");
                        reply.setStringProperty("response", user.getId().toString());
                    } catch (RepositoryAdapterException e) {
                        reply.setStringProperty("result", "failure");
                    }
                    break;
                }
                case "user.update" : {
                    try {
                        User addUser = userUseCase.update(user);
                        reply.setStringProperty("result", "success");
                        reply.setStringProperty("response", JSONObject.wrap(addUser).toString());
                    } catch (RepositoryAdapterException e) {
                        reply.setStringProperty("result", "failure");
                    }
                    break;
                }
                default : break;
            }

            reply.setJMSCorrelationID(message.getJMSCorrelationID());
            jmsProducer.send(message.getJMSReplyTo(), reply);

        } catch (JsonProcessingException | JMSException e) {
            e.printStackTrace();
        }
    }
}
