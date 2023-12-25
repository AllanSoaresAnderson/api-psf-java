package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository  extends JpaRepository<Expenses,Integer> {
}
