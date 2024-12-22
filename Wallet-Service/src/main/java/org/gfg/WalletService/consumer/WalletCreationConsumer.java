package org.gfg.WalletService.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.Utils.CommonConstants;
import org.gfg.WalletService.model.Wallet;
import org.gfg.WalletService.repository.WalletRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class WalletCreationConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${user.creation.time.balance}")
    private double balance;

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = {CommonConstants.USER_CREATION_TOPIC},
            groupId = "${Wallet.group.id}",
            containerFactory = "factory1")
    public void walletCreation(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        try{
            JSONObject jsonObject = objectMapper.readValue(message, JSONObject.class);
            Integer userId = (Integer) jsonObject.get(CommonConstants.USER_ID);
            String contact = (String) jsonObject.get(CommonConstants.USER_CONTACT);

            Wallet wallet = Wallet.
                    builder().
                    contact(contact).
                    userId(userId).
                    balance(balance).
                    build();

            wallet =  walletRepo.save(wallet);
            JSONObject o = new JSONObject();
            o.put(CommonConstants.USER_ID, userId);
            o.put(CommonConstants.USER_EMAIl, jsonObject.get(CommonConstants.USER_EMAIl));
            // notification , txn part ?
            kafkaTemplate.send(CommonConstants.WALLET_CREATION_TOPIC, objectMapper.writeValueAsString(o));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        acknowledgment.acknowledge();
    }
}
