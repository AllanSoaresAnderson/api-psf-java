package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.InstallmentVariableDTO;
import br.com.psf.personalsystemfinance.service.FixedTransactionService;
import br.com.psf.personalsystemfinance.service.InstallmentVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/fixedTransactions")
@CrossOrigin(origins = "http://localhost:4200")
public class FixedTransactionsController {


    @Autowired
    FixedTransactionService fixedTransactionService;

    @Autowired
    InstallmentVariableService installmentVariableService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostMapping
    @ResponseBody
    public ResponseEntity<FixedTransactionDTO> addFixedTransaction(@Validated @RequestBody FixedTransactionDTO ft){
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            ft = this.fixedTransactionService.addFixedTransaction(ft);
            if(ft.getTypeInstallment().equals("variable")){
                List<InstallmentVariableDTO> installmentList = this.installmentVariableService.createInstallments(ft);
            }
            transactionManager.commit(status);
            return new ResponseEntity<>(ft, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<FixedTransactionDTO> getFixedTransaction(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.fixedTransactionService.getFixedTransaction(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteFixedTransaction(@PathVariable Integer id){
        try {
            this.installmentVariableService.deleteInstallments(id);
            this.fixedTransactionService.deleteFixedTransaction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
