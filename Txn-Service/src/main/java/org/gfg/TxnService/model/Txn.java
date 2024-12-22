package org.gfg.TxnService.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @CreationTimestamp
    protected Date createdOn;

    @UpdateTimestamp
    protected Date updatedOn;
    private String txnId;
    private double amount;
    private String msg;
    private String senderContact;
    private String receiverContact;
    @Enumerated
    private TxnStatus txnStatus;


}
