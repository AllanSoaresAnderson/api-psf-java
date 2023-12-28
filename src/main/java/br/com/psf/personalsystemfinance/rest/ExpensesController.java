package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.ExpensesDTO;
import br.com.psf.personalsystemfinance.service.EntityService;
import br.com.psf.personalsystemfinance.service.ExpensesService;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/expenses")
@RestController
@Transactional
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;
    @Autowired
    private EntityService entityService;

    @PutMapping(value = "/updateExpense/{id}")
    @ResponseBody
    public ResponseEntity<ExpensesDTO> updateExpense(@PathVariable Integer id, @RequestBody ExpensesDTO expensesDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            return new ResponseEntity<>( this.expensesService.updateExpense(id, expensesDTO), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(value = "/delExpense/{id}")
    public ResponseEntity<Void> delExpense(@PathVariable Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            this.expensesService.delExpense(id);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping(value = "/addExpense")
    @ResponseBody
    public ResponseEntity<ExpensesDTO> addExpense(@Validated @RequestBody ExpensesDTO expenseDTO ){
        if(expenseDTO.getIdEntity() != null
                && !this.entityService.entityExists(expenseDTO.getIdEntity())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.expensesService.addExpense(expenseDTO), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtém uma Despesa por um ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpensesDTO> getExpenseById(@PathVariable Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        return new ResponseEntity<>(this.expensesService.getExpenseById(id), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtém a lista de Despesas")
    @GetMapping("/listExpenses")
    @ResponseBody
    public ResponseEntity<List<ExpensesDTO>> getListExpenses() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        return new ResponseEntity<>(this.expensesService.getListExpenses(), headers, HttpStatus.OK);
    }
}
