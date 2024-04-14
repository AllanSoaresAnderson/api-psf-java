package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.EventualTransactionDTO;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.service.EventualTransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/eventualTransaction")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class EventualTransactionsController {

    @Autowired
    private EventualTransactionService eventualTransactionService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEventualTransaction(@PathVariable Integer id){
        try {
            this.eventualTransactionService.deleteEventualTransaction(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<EventualTransactionDTO> getEventualTransaction(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.eventualTransactionService.getEventualTransaction(id),HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
