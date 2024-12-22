package org.gfg.WalletService.controller;

import org.gfg.WalletService.WalletService;
import org.gfg.WalletService.dto.WalletResponseDTO;
import org.gfg.WalletService.model.Wallet;
import org.gfg.WalletService.service.WalletDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @Autowired
    private WalletDetailsService walletDetailsService;

    @GetMapping("/walletDetails")
    public ResponseEntity<WalletResponseDTO> walletDetails(@RequestParam("contact") String contact ,
                                                           @RequestParam("type") String type,
                                                           @RequestParam(value = "amount", required = false) Double amount){
        WalletResponseDTO walletDTO =  walletDetailsService.walletDetails(contact, type, amount);
        ResponseEntity<WalletResponseDTO> response = new ResponseEntity<>(walletDTO, HttpStatus.OK);
        return response;
    }


}
