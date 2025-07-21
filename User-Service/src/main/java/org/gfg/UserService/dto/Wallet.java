package org.gfg.UserService.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    private Integer id;

    private String contact;

    private Double balance;

    private Integer userId;

    @CreationTimestamp
    protected Date createdOn;

    @UpdateTimestamp
    protected Date updatedOn;
}
