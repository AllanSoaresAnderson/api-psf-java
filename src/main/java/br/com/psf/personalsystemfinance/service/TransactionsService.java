package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.TransactionsDTO;
import br.com.psf.personalsystemfinance.entity.Transactions;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.exceptions.FieldEmptyException;
import br.com.psf.personalsystemfinance.exceptions.LogicException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
import br.com.psf.personalsystemfinance.repository.EventualTransactionRepository;
import br.com.psf.personalsystemfinance.repository.FixedTransactionsRepository;
import br.com.psf.personalsystemfinance.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private FixedTransactionsRepository fixedTransactionsRepository;

    @Autowired
    private EventualTransactionRepository eventualTransactionRepository;


    @SuppressWarnings("unchecked")
    public PageImpl<Map<String, Object>> getListTransactionsScreenRegisters(Pageable pageable, String name){
        Page<List<Object[]>> page = this.transactionsRepository.getListTransactionsScreenRegisters(name, pageable);
        List<Map<String, Object>> result = new ArrayList<>();
        Object content = page.getContent();
        List<Object[]> list = (List<Object[]>) content;
        for (Object[] row : list) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", row[0]);
            obj.put("name", row[1]);
            obj.put("type", row[2]);
            obj.put("categoryType", row[3]);
            obj.put("value", row[4]);
            obj.put("entityName", row[5]);
            result.add(obj);
        }

        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    /**
     * @param id Transaction id
     * @throws EntityNotFoundException NOT FOUND
     */
    public void deleteTransaction(Integer id) throws EntityNotFoundException {
        if(id != null && this.transactionsRepository.existsById(id)) {
            this.transactionsRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("NOT FOUND");
        }
    }
    /**
     * @param id Transactions id
     * @return TransactionsDTO
     * @throws EntityNotFoundException NOT FOUND
     */
    public TransactionsDTO getTransaction(Integer id) throws EntityNotFoundException {
        if(id != null && this.transactionsRepository.existsById(id)){
            return this.toDTO(this.transactionsRepository.getReferenceById(id));
        }else{
            throw new EntityNotFoundException("NOT FOUND");
        }
    }

    /**
     * @param transactionsDTO The new Transaction
     * @return The new Transaction
     * @throws NullException The id should be null
     * @throws EntityNotFoundException Fixed Transaction or Eventual Transaction Not Found
     * @throws LogicException Unrecognized Type, The name must not be less than 3
     * @throws FieldEmptyException If the name is empty
     */
    public TransactionsDTO addTransaction(TransactionsDTO transactionsDTO) throws NullException, EntityNotFoundException, LogicException, FieldEmptyException {
        if(transactionsDTO.getId() == null){
            transactionsDTO.setIdEntity(transactionsDTO.getEntity().getId());
            switch (transactionsDTO.getCategoryType()){
                case "Fixed":
                    transactionsDTO.setIdFixedTransaction(transactionsDTO.getFixedTransaction().getId());
                    if(!this.fixedTransactionsRepository.existsById(transactionsDTO.getIdFixedTransaction())) {
                        throw new EntityNotFoundException("Fixed Transaction Not Found");
                    }
                    break;
                case "Eventual":
                    transactionsDTO.setIdEventualTransaction(transactionsDTO.getEventualTransaction().getId());
                    if(!this.eventualTransactionRepository.existsById(transactionsDTO.getIdEventualTransaction())){
                        throw new EntityNotFoundException("Eventual Transaction Not Found");
                    }
                    break;
                default:
                    throw new LogicException("Unrecognized Type");
            }
            FieldEmptyException.checkStringField(transactionsDTO.getName(), "name");
            transactionsDTO.setName(transactionsDTO.getName().trim());
            if(transactionsDTO.getName().length() < 3){
                throw new LogicException("The name must not be less than 3");
            }
            if (transactionsDTO.getType().equals("Expense")
                || transactionsDTO.getType().equals("Earning")){
                Transactions transactions = this.toTransactions(transactionsDTO);
                return this.toDTO(this.transactionsRepository.saveAndFlush(transactions));
            }else {
                throw new LogicException("Unrecognized Type");
            }
        }else{
            throw new NullException("The id should be null");
        }
    }

    private TransactionsDTO toDTO(Transactions t){
        TransactionsDTO dto = new TransactionsDTO();
        dto.setId(t.getId());
        dto.setType(t.getType());
        dto.setIdFixedTransaction(t.getIdFixedTransaction());
        dto.setIdEventualTransaction(t.getIdEventualTransaction());
        dto.setCategoryType(t.getCategoryType());
        dto.setIdEntity(t.getIdEntity());
        dto.setName(t.getName());
        return dto;
    }
    private Transactions toTransactions(TransactionsDTO dto){
        Transactions t = new Transactions(
                dto.getName(),
                dto.getType(),
                dto.getCategoryType(),
                dto.getIdEntity()
                );
        t.setId(dto.getId());
        t.setIdEventualTransaction(dto.getIdEventualTransaction());
        t.setIdFixedTransaction(dto.getIdFixedTransaction());
        return t;
    }
}
