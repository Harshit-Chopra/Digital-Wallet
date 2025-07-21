package org.gfg.UserService.dto;


import lombok.*;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDTO {

    private String message;
    private String error;
    private Wallet wallet;

}

