package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.InstallmentVariableDTO;
import br.com.psf.personalsystemfinance.service.FixedTransactionService;
import br.com.psf.personalsystemfinance.service.InstallmentVariableService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/fixedTransactions")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class FixedTransactionsController {


    @Autowired
    FixedTransactionService fixedTransactionService;

    @Autowired
    InstallmentVariableService installmentVariableService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<FixedTransactionDTO> addFixedTransaction(@Validated @RequestBody FixedTransactionDTO ft){
        try {
            ft = this.fixedTransactionService.addFixedTransaction(ft);
            List<InstallmentVariableDTO> installmentList = this.installmentVariableService.createInstallments(ft);
            return new ResponseEntity<>(ft, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<FixedTransactionDTO> getFixedTransaction(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.fixedTransactionService.getFixedTransaction(id), HttpStatus.OK);
        } catch (Exception e) {
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
