package org.gfg.TxnService.dto;

import lombok.*;
import org.gfg.TxnService.model.TxnStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TxnUpdate {
    private double amount;
    private String senderContact;
    private String receiverContact;
    private TxnStatus status;
    private String message;
    private String txnId;
    }
