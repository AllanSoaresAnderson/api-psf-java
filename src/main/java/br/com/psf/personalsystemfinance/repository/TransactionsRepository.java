package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}
