package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.TransactionsDTO;
import br.com.psf.personalsystemfinance.entity.Transactions;
import br.com.psf.personalsystemfinance.repository.EventualTransactionRepository;
import br.com.psf.personalsystemfinance.repository.FixedTransactionsRepository;
import br.com.psf.personalsystemfinance.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private FixedTransactionsRepository fixedTransactionsRepository;

    @Autowired
    private EventualTransactionRepository eventualTransactionRepository;


    /**
     * @param id Transaction id
     * @throws Exception NOT FOUND
     */
    public void deleteTransaction(Integer id) throws Exception {
        if(id != null && this.transactionsRepository.existsById(id)) {
            this.transactionsRepository.deleteById(id);
        }else{
            throw new Exception("NOT FOUND");
        }
    }
    /**
     * @param id Transactions id
     * @return TransactionsDTO
     * @throws Exception NOT FOUND
     */
    public TransactionsDTO getTransaction(Integer id) throws Exception {
        if(id != null && this.transactionsRepository.existsById(id)){
            return this.toDTO(this.transactionsRepository.getReferenceById(id));
        }else{
            throw new Exception("NOT FOUND");
        }
    }
    /**
     * @param transactionsDTO Transaction Registry
     * @return TransactionsDTO
     * @throws Exception The id should be null, Eventual Transaction Not Found,
     * Fixed Transaction Not Found
     */
    public TransactionsDTO addTransaction(TransactionsDTO transactionsDTO) throws Exception {
        if(transactionsDTO.getId() == null){
            if (transactionsDTO.getCategoryType().equals("fixed")
                    && !this.fixedTransactionsRepository.existsById(transactionsDTO.getIdCategory())){
                throw new Exception("Fixed Transaction Not Found");
            } else if (transactionsDTO.getCategoryType().equals("eventual")
                    && !this.eventualTransactionRepository.existsById(transactionsDTO.getIdCategory())){
                throw new Exception("Eventual Transaction Not Found");
            }
            if (transactionsDTO.getType().equals("expense")
                || transactionsDTO.getType().equals("earning")){
                Transactions transactions = this.toTransactions(transactionsDTO);
                return this.toDTO(this.transactionsRepository.saveAndFlush(transactions));
            }else {
                throw new Exception("Unrecognized Type");
            }
        }else{
            throw new Exception("The id should be null");
        }


    }

    private TransactionsDTO toDTO(Transactions t){
        TransactionsDTO dto = new TransactionsDTO();
        dto.setId(t.getId());
        dto.setType(t.getType());
        dto.setIdCategory(t.getIdCategory());
        dto.setCategoryType(t.getCategoryType());
        dto.setIdEntity(t.getIdEntity());
        return dto;
    }
    private Transactions toTransactions(TransactionsDTO dto){
        Transactions t = new Transactions(
                dto.getType(),
                dto.getCategoryType(),
                dto.getIdCategory(),
                dto.getIdEntity()
                );
        t.setId(dto.getId());
        return t;
    }
}
