package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.TransactionsDTO;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.exceptions.FieldEmptyException;
import br.com.psf.personalsystemfinance.exceptions.LogicException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
import br.com.psf.personalsystemfinance.service.EventualTransactionService;
import br.com.psf.personalsystemfinance.service.FixedTransactionService;
import br.com.psf.personalsystemfinance.service.InstallmentService;
import br.com.psf.personalsystemfinance.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private FixedTransactionService fixedTransactionService;

    @Autowired
    private InstallmentService installmentService;

    @Autowired
    private EventualTransactionService eventualTransactionService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Integer id){
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            TransactionsDTO transactionsDTO = this.transactionsService.getTransaction(id);
            this.transactionsService.deleteTransaction(id);
            switch (transactionsDTO.getCategoryType()){
                case "Fixed":
                    this.installmentService.deleteInstallments(transactionsDTO.getIdFixedTransaction());
                    this.fixedTransactionService.deleteFixedTransaction(transactionsDTO.getIdFixedTransaction());
                    break;
                case "Eventual":
                    this.eventualTransactionService.deleteEventualTransaction(transactionsDTO.getIdEventualTransaction());
                    break;
                default:
                    throw new LogicException("Unrecognized Category Type");
            }
            this.transactionManager.commit(status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            this.transactionManager.rollback(status);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            this.transactionManager.rollback(status);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionsDTO> getTransactionById(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.transactionsService.getTransaction(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TransactionsDTO> addTransaction(@Validated @RequestBody TransactionsDTO transactionsDTO){
        TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            switch (transactionsDTO.getCategoryType()){
                case "Fixed":
                    NullException.checkFieldIsNull(transactionsDTO.getFixedTransaction(), "Fixed Transaction");
                    FixedTransactionDTO ft = this.fixedTransactionService.addFixedTransaction(transactionsDTO.getFixedTransaction());
                    if(ft.getIsInstallment()){
                        this.installmentService.createInstallments(ft);
                    }
                    transactionsDTO.setFixedTransaction(ft);
                    break;
                case "Eventual":
                    NullException.checkFieldIsNull(transactionsDTO.getEventualTransaction(), "Eventual Transaction");
                    transactionsDTO.setEventualTransaction(
                            this.eventualTransactionService.
                                    addEventualTransaction(transactionsDTO.getEventualTransaction())
                    );
                    break;
                default:
                    throw new LogicException("Unrecognized Category Type");
            }
            TransactionsDTO newTransaction = this.transactionsService.addTransaction(transactionsDTO);
            this.transactionManager.commit(status);
            return new ResponseEntity<>(newTransaction , HttpStatus.OK);
        } catch (LogicException | NullException | FieldEmptyException e) {
            this.transactionManager.rollback(status);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            this.transactionManager.rollback(status);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            this.transactionManager.rollback(status);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/registers")
    public ResponseEntity<PageImpl<Map<String, Object>>> getListTransactionsScreenRegisters(
            @RequestParam(required = false)String name,
            Pageable pageable
    ){
        return new ResponseEntity<>(this.transactionsService.getListTransactionsScreenRegisters(pageable, name), HttpStatus.OK);
    }

}
