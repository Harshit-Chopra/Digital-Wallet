package org.gfg.WalletService.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.WalletService.repository.WalletRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxnUpdationConsumer {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = {"TXN_UPDATION_TOPIC"},
            groupId = "${Wallet.group.id}",
            containerFactory = "factory1")
    @Transactional
    public void walletCreation(String message, Acknowledgment acknowledgment) throws JsonProcessingException {

        JSONObject object = objectMapper.readValue(message, JSONObject.class);
        String sender = (String) object.get("sender");
        String receiver = (String) object.get("receiver");
        String status = (String) object.get("status");
        String txnid = (String) object.get("txnId");
        Double amount = (Double) object.get("amount");
        walletRepo.updateWallet(sender, -amount);
        walletRepo.updateWallet(receiver, amount);
        // queue : txn can know that wallet is done
        JSONObject object1 = new JSONObject();
        object1.put("message", "wallet updated");
        object1.put("status", "success");
        object1.put("txnId", txnid);

        kafkaTemplate.send("WALLET_UPDATED_TOPIC", objectMapper.writeValueAsString(object1));
        acknowledgment.acknowledge();

    }
}
