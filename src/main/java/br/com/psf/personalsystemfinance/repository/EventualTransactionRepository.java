package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.EventualTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventualTransactionRepository extends JpaRepository<EventualTransaction, Integer> {
}
