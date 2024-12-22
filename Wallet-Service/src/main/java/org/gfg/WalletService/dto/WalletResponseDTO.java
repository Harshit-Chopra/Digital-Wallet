package org.gfg.WalletService.dto;

import lombok.*;
import org.gfg.WalletService.model.Wallet;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponseDTO implements Serializable {

    private String message;
    private String error;
    private Wallet wallet;

}
