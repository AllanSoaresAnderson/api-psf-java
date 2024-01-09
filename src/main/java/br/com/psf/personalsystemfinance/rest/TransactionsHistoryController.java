package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.TransactionHistoryDTO;
import br.com.psf.personalsystemfinance.service.TransactionsHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactionHistory")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionsHistoryController {

    @Autowired
    private TransactionsHistoryService transactionsHistoryService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTransactionHistory(@PathVariable Integer id){
        try {
            this.transactionsHistoryService.deleteTransactionHistory(id);
            return  new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionHistoryDTO> getTransactionHistory(@PathVariable Integer id){
        try {
            return  new ResponseEntity<>(this.transactionsHistoryService.getTransactionHistory(id), HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TransactionHistoryDTO> addTransactionHistory(@Validated @RequestBody TransactionHistoryDTO transactionHistoryDTO){
        try {
            transactionHistoryDTO = this.transactionsHistoryService.addTransactionHistory(transactionHistoryDTO);
            return new ResponseEntity<>(transactionHistoryDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }



}
