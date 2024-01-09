package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.TransactionsDTO;
import br.com.psf.personalsystemfinance.service.TransactionsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactions")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id){
        try {
            this.transactionsService.deleteTransaction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionsDTO> getTransaction(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.transactionsService.getTransaction(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TransactionsDTO> addTransaction(@Validated @RequestBody TransactionsDTO transactionsDTO){
        try {
            return new ResponseEntity<>(this.transactionsService.addTransaction(transactionsDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
