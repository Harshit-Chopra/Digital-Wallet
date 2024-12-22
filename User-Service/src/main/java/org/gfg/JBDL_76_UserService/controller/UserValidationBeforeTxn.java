package org.gfg.JBDL_76_UserService.controller;

import org.gfg.JBDL_76_UserService.dto.SenderReceiverInfo;
import org.gfg.JBDL_76_UserService.dto.UserTxnDTO;
import org.gfg.JBDL_76_UserService.dto.WalletResponseDTO;
import org.gfg.JBDL_76_UserService.model.Users;
import org.gfg.Utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserValidationBeforeTxn {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("kafkaTemplateJson")
    private KafkaTemplate<String, SenderReceiverInfo> kafkaTemplate;

    @PostMapping("/start-txn")
    public String validateUserBeforeStartTxn(@RequestBody UserTxnDTO dto,
                                             @AuthenticationPrincipal Users user){
        // sender is having an wallet associated with me or not
        // receiver is having an wallet associated with ewallet
        // rest template
        // should i have service first ?-> create your own service
        WalletResponseDTO senderDTO  = restTemplate.exchange("http://localhost:6093/walletDetails?contact=" + user.getPhoneNo() +"&type=sender&amount=" +dto.getTxnAmount(),
                HttpMethod.GET, null,  WalletResponseDTO.class).getBody();

        WalletResponseDTO  receiverDTO  = restTemplate.exchange("http://localhost:6093/walletDetails?contact=" + dto.getReceiverContact() +"&type=receiever",
                HttpMethod.GET, null,  WalletResponseDTO.class).getBody();

        if(!senderDTO.getMessage().equalsIgnoreCase("success")){
            return senderDTO.getError();
        }
        if(!receiverDTO.getMessage().equalsIgnoreCase("success")){
            return receiverDTO.getError();
        }
        SenderReceiverInfo info = SenderReceiverInfo.builder().
                senderContact(user.getPhoneNo()).
                receiverContact(dto.getReceiverContact()).
                msg(dto.getMessage()).
                amount(dto.getTxnAmount()).build();
        kafkaTemplate.send(CommonConstants.TXN_CREATION_TOPIC, info);

        return "txn has been started, you will get notified once your txn is done";
    }


}
