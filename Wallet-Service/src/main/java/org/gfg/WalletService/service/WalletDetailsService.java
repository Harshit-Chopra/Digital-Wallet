package org.gfg.WalletService.service;

import org.gfg.WalletService.dto.WalletResponseDTO;
import org.gfg.WalletService.model.Wallet;
import org.gfg.WalletService.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletDetailsService {
    @Autowired
    private WalletRepo walletRepo;

    public WalletResponseDTO walletDetails(String contact, String type, Double amount) {

        Wallet wallet = walletRepo.findByContact(contact);
        if(wallet == null){
           return WalletResponseDTO
                    .builder()
                    .error("wallet is not present for sender")
                    .message("failed")
                    .wallet(null)
                    .build();
        }
        // sender or receiver
        if(type == "sender"){ // type as enum
            if(amount != null && wallet.getBalance() < amount){
                return WalletResponseDTO
                        .builder()
                        .error("wallet amount is less than the  txn amount")
                        .message("failed")
                        .wallet(null)
                        .build();
            }
        }
        return WalletResponseDTO
                .builder()
                .error(null)
                .message("success")
                .wallet(wallet)
                .build();
    }
}
