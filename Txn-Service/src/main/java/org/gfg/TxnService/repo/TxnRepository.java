package org.gfg.TxnService.repo;

import jakarta.transaction.Transactional;
import org.gfg.TxnService.model.Txn;
import org.gfg.TxnService.model.TxnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Txn t SET t.txnStatus = :status WHERE t.id = :txnId")
    void updateStatusById(String txnId, TxnStatus status);
}
