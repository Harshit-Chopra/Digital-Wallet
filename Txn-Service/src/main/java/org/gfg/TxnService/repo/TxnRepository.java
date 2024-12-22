package org.gfg.TxnService.repo;

import org.gfg.TxnService.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {
}
