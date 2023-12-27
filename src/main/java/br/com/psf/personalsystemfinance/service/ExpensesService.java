package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.EarningDTO;
import br.com.psf.personalsystemfinance.dto.ExpensesDTO;
import br.com.psf.personalsystemfinance.entity.Earnings;
import br.com.psf.personalsystemfinance.entity.Expenses;
import br.com.psf.personalsystemfinance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    @Autowired
    private ExpenseRepository expenseRepository;


    public ExpensesDTO updateExpense(Integer id, ExpensesDTO expensesDTO) throws Exception {
        if (id == null || !this.expenseRepository.existsById(id)){
            throw new Exception("Essa entrada não existe");
        }
        expensesDTO.setId(id);
        Expenses expenses = this.toExpenses(this.mergeExpenses(id, expensesDTO));
        return this.toExpensesDTO(this.expenseRepository.saveAndFlush(expenses));
    }

    private ExpensesDTO mergeExpenses(Integer id, ExpensesDTO expensesDTO){
        ExpensesDTO expenseBD = this.getExpenseById(id);
        if (expensesDTO.getIdEntity() == null){
            expensesDTO.setIdEntity(expenseBD.getIdEntity());
        }
        if(expensesDTO.getCurrentValue() == null){
            expensesDTO.setCurrentValue(expenseBD.getCurrentValue());
        }
        if(expensesDTO.getName() == null){
            expensesDTO.setName(expenseBD.getName());
        }
        if(expensesDTO.getEstimateValue() == null){
            expensesDTO.setEstimateValue(expenseBD.getEstimateValue());
        }
        return expensesDTO;
    }

    public  void delExpense(Integer id) throws Exception {
        if(!this.expenseRepository.existsById(id)){
            throw new Exception("Esta Despesa não existe");
        }
        this.expenseRepository.deleteById(id);
    }


    public ExpensesDTO addExpense(ExpensesDTO expensesDTO){
        Expenses expense = this.toExpenses(expensesDTO);
        return this.toExpensesDTO(this.expenseRepository.saveAndFlush(expense));
    }

    public ExpensesDTO getExpenseById(Integer id){
        return this.toExpensesDTO(this.expenseRepository.getReferenceById(id));
    }

    public List<ExpensesDTO> getListExpenses(){
        return this.expenseRepository.findAll().stream()
                .map(this::toExpensesDTO).collect(Collectors.toList());
    }


    public Expenses toExpenses(ExpensesDTO expensesDTO){
        Expenses expense = new Expenses();
        expense.setId(expensesDTO.getId());
        expense.setName(expensesDTO.getName());
        expense.setCurrentValue(expensesDTO.getCurrentValue());
        expense.setIdEntity(expensesDTO.getIdEntity());
        expense.setEstimateValue(expensesDTO.getEstimateValue());
        return expense;
    }

    public ExpensesDTO toExpensesDTO(Expenses expenses){
        ExpensesDTO expensesDTO = new ExpensesDTO();
        expensesDTO.setId(expenses.getId());
        expensesDTO.setName(expenses.getName());
        expensesDTO.setCurrentValue(expenses.getCurrentValue());
        expensesDTO.setIdEntity(expenses.getIdEntity());
        expensesDTO.setEstimateValue(expenses.getEstimateValue());
        return expensesDTO;
    }
}
