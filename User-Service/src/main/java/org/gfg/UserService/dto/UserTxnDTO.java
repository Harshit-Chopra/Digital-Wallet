package org.gfg.UserService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTxnDTO {

    private double txnAmount;

    private String message;

    private String receiverContact;

}
