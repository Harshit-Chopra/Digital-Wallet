package org.gfg.JBDL_76_UserService.dto;

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
