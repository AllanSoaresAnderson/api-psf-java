package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.entity.Expenses;
import br.com.psf.personalsystemfinance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpensesService {

    @Autowired
    private ExpenseRepository expenseRepository;
}
