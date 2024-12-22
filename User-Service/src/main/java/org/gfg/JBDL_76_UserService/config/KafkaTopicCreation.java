package org.gfg.JBDL_76_UserService.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.gfg.Utils.CommonConstants;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaTopicCreation {
    private AdminClient adminClient;


    public KafkaTopicCreation(AdminClient adminClient){
        this.adminClient = adminClient;
    }

    @PostConstruct
    public void createTopic(){
        createTopicIfNotExists(CommonConstants.TXN_CREATION_TOPIC, 3, (short)2);
    }

    public void createTopicIfNotExists(String  topicName, int partitions, short rFactor){
        NewTopic topic = new NewTopic(topicName, partitions, rFactor);
        try{
            adminClient.createTopics(Collections.singleton(topic)).all().get();
            System.out.println("topic has been created");
        } catch (TopicExistsException e){
            System.out.println("topic already exists");
        } catch(ExecutionException e) {
            System.out.println("failed to create topic ");
        } catch (InterruptedException e) {
            System.out.println("failed to create topic ");
        }

    }


}
