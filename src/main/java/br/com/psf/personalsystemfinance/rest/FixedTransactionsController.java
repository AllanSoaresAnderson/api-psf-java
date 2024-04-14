package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.service.FixedTransactionService;
import br.com.psf.personalsystemfinance.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fixedTransactions")
@CrossOrigin(origins = "http://localhost:4200")
public class FixedTransactionsController {

    @Autowired
    FixedTransactionService fixedTransactionService;

    @Autowired
    InstallmentService installmentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FixedTransactionDTO> getFixedTransaction(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.fixedTransactionService.getFixedTransaction(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFixedTransaction(@PathVariable Integer id){
        try {
            this.installmentService.deleteInstallments(id);
            this.fixedTransactionService.deleteFixedTransaction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
