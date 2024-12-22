package org.gfg.JBDL_76_UserService.dto;


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

