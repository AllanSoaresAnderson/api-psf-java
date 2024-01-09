package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.FixedTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedTransactionsRepository extends JpaRepository<FixedTransactions, Integer> {
}
