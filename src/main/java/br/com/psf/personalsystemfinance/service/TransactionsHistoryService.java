package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.TransactionHistoryDTO;
import br.com.psf.personalsystemfinance.entity.TransactionHistory;
import br.com.psf.personalsystemfinance.repository.EventualTransactionRepository;
import br.com.psf.personalsystemfinance.repository.FixedTransactionsRepository;
import br.com.psf.personalsystemfinance.repository.InstallmentVariableRepository;
import br.com.psf.personalsystemfinance.repository.TransactionHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionsHistoryService {


    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private FixedTransactionsRepository fixedTransactionsRepository;

    @Autowired
    private EventualTransactionRepository eventualTransactionRepository;

    @Autowired
    private InstallmentVariableRepository installmentVariableRepository;

    /**
     * @param id Transaction History id
     * @throws Exception NOT FOUND
     */
    public void deleteTransactionHistory(Integer id) throws Exception {
        if(id != null && this.transactionHistoryRepository.existsById(id)){
            this.transactionHistoryRepository.deleteById(id);
        }else{
            throw new Exception("NOT FOUND");
        }
    }

    /**
     * @param id Transaction History id
     * @return Transaction History
     * @throws Exception NOT FOUND
     */
    public TransactionHistoryDTO getTransactionHistory(Integer id) throws Exception {
        if(id != null && this.transactionHistoryRepository.existsById(id)){
            return this.toDTO(this.transactionHistoryRepository.getReferenceById(id));
        }else{
            throw new Exception("NOT FOUND");
        }
    }

    /**
     * @param newTH New Transaction History
     * @return Transaction History
     * @throws Exception The id should be null and Not Found
     */
    public TransactionHistoryDTO addTransactionHistory(TransactionHistoryDTO newTH) throws Exception {
        if(newTH.getId() == null){
            if(newTH.getType().equals("fixed")
                    && !this.fixedTransactionsRepository.existsById(newTH.getIdTransaction())){
                throw new Exception("NOT FOUND");
            }
            else if(newTH.getType().equals("eventual")
                    && !this.eventualTransactionRepository.existsById(newTH.getIdTransaction())){
                throw new Exception("NOT FOUND");
            }
            else if(newTH.getType().equals("installment")
                    && !this.installmentVariableRepository.existsById(newTH.getIdTransaction())){
                throw new Exception("NOT FOUND");
            }
            TransactionHistory transactionHistory = this.toTransactionHistory(newTH);
            return this.toDTO(this.transactionHistoryRepository.saveAndFlush(transactionHistory));
        }else{
            throw  new Exception("The id should be null");
        }
    }

    private TransactionHistoryDTO toDTO(TransactionHistory t){
        TransactionHistoryDTO dto = new TransactionHistoryDTO();
        dto.setIdTransaction(t.getIdTransaction());
        dto.setId(t.getId());
        dto.setDate(t.getDate());
        dto.setType(t.getType());
        dto.setValue(t.getValue());
        dto.setItsDone(t.isItsDone());
        return dto;
    }
    private TransactionHistory toTransactionHistory(TransactionHistoryDTO dto){
        TransactionHistory t = new TransactionHistory(
                dto.getType(),
                dto.getIdTransaction(),
                dto.getDate(),
                dto.getValue()
        );
        t.setId(dto.getId());
        t.setItsDone(dto.isItsDone());
        return t;
    }
}
