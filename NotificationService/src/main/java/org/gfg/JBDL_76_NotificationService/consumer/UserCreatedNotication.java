package org.gfg.JBDL_76_NotificationService.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.JBDL_76_NotificationService.utils.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedNotication {

    @Autowired
    private SimpleMailMessage simpleMailMessage;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper objectMapper;


    @KafkaListener(topics = {Constants.USER_CREATION_TOPIC}, groupId = "notification-service")
    public void sendNotification(String message) throws JsonProcessingException {
        JSONObject object = objectMapper.readValue(message, JSONObject.class);
        String name = (String) object.get(Constants.USER_NAME);
        String email = (String) object.get(Constants.USER_EMAIl);

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("EWallet User Created !!");
        simpleMailMessage.setFrom("ewallet@jbdl-76.com");
        simpleMailMessage.setText("Welcome "+ name+ " to Ewallet. User has been Created, wallet will created in while.");
        mailSender.send(simpleMailMessage);

    }

}
