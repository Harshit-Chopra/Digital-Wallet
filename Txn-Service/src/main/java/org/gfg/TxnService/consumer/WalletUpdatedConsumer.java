package org.gfg.TxnService.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.TxnService.dto.TxnUpdate;
import org.gfg.TxnService.repo.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class WalletUpdatedConsumer {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TxnRepository txnRepository;

    @KafkaListener(topics = {"TXN_UPDATION_TOPIC"},
            groupId = "${txn.kafka.groupId}",
            containerFactory = "factory1")
    public void txnCreation(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        try {
            // Deserialize JSON message to TxnUpdateDTO object
            TxnUpdate txnUpdate = mapper.readValue(message, TxnUpdate.class);

            // Update the transaction status in DB based on received update
            txnRepository.updateStatusById(txnUpdate.getTxnId(), txnUpdate.getStatus());

            // Manually acknowledge the message after successful processing
            acknowledgment.acknowledge();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

