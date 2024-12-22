package org.gfg.TxnService.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.TopicPartition;
import org.gfg.TxnService.dto.SenderReceiverInfo;
import org.gfg.TxnService.model.Txn;
import org.gfg.TxnService.model.TxnStatus;
import org.gfg.TxnService.repo.TxnRepository;
import org.gfg.Utils.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class TxnInitiatedConsumer implements ConsumerSeekAware {

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = {CommonConstants.TXN_CREATION_TOPIC},
            groupId = "${txn.kafka.groupId}",
            containerFactory = "factory2")
    public void txnCreation(SenderReceiverInfo message, Acknowledgment acknowledgment) throws JsonProcessingException {
        try{
            Txn txn = Txn.builder().txnId(UUID.randomUUID().toString()).amount(message.getAmount()).receiverContact(message.getReceiverContact()).senderContact(message.getSenderContact()).txnStatus(TxnStatus.INITIATED).build();
            txn = txnRepository.save(txn);
            // real time
            // queue: wallet

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", txn.getAmount());
            jsonObject.put("sender", txn.getSenderContact());
            jsonObject.put("receiver", txn.getReceiverContact());
            jsonObject.put("status", txn.getTxnStatus());
            jsonObject.put("message", txn.getMsg());
            jsonObject.put("txnId", txn.getTxnId());
            kafkaTemplate.send("TXN_UPDATION_TOPIC", mapper.writeValueAsString(jsonObject));
            acknowledgment.acknowledge();
        }catch (Exception e){

            System.out.println("going into reties variable txn id ");

//            Integer retryCount = 3;
//            int i = 1; // db
//            if(i<=retryCount){
//                kafkaTemplate.send("retry-topic-"+retryCount, message.toString()); // code, admin console
//            }else{
////                dlq
//                kafkaTemplate.send("dlq-topic", message.toString()); // cod
//            }
        }
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        // Seek to the beginning of all partitions
        assignments.keySet().forEach(tp -> callback.seekToBeginning(Collections.singleton(tp)));
    }
}

// kafka-retry
// @retryable-> time, delay
