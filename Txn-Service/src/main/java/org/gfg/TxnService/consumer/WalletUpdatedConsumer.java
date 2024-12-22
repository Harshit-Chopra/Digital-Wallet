package org.gfg.TxnService.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class WalletUpdatedConsumer {

    @Autowired
    private ObjectMapper mapper;


    @KafkaListener(topics = {"TXN_UPDATION_TOPIC"},
            groupId = "${txn.kafka.groupId}",
            containerFactory = "factory1")
    public void txnCreation(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        // json object coming
        // txn id, update the txn status in db basis on message coming
    }
}
