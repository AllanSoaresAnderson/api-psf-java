package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.entity.FixedTransactions;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.exceptions.LogicException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
import br.com.psf.personalsystemfinance.repository.FixedTransactionsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author allan
 */
@Service
@Transactional
public class FixedTransactionService {

    @Autowired
    public FixedTransactionsRepository fixedTransactionsRepository;


    /**
     * @param id FixedTransactions id
     * @throws EntityNotFoundException NOT FOUND
     */
    public void deleteFixedTransaction(Integer id) throws EntityNotFoundException {
        if(this.fixedTransactionsRepository.existsById(id)){
            this.fixedTransactionsRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("NOT FOUND");
        }

    }
    /**
     * @param id FixedTransaction id
     * @return FixedTransactionDTO
     * @throws EntityNotFoundException NOT FOUND
     */
    public FixedTransactionDTO getFixedTransaction(Integer id) throws EntityNotFoundException {
        if (this.fixedTransactionsRepository.existsById(id)){
            return this.toDTO(this.fixedTransactionsRepository.getReferenceById(id));
        }else {
            throw new EntityNotFoundException("NOT FOUND");
        }
    }
    /**
     * @param newFT The new Fixed Transaction Registry
     * @throws NullException The id should be null
     * @throws LogicException  Invalid installment quantity and Invalid type installment
     */
    public FixedTransactionDTO addFixedTransaction(FixedTransactionDTO newFT) throws NullException, LogicException {
        NullException.checkFieldIsNotNull(newFT.getId(), "id");
        FixedTransactionService.invalidInstallmentQuantityLock(newFT);
        FixedTransactions f = this.toFixedTransaction(newFT);
        newFT = this.toDTO(this.fixedTransactionsRepository.save(f));
        return newFT;
    }
    private FixedTransactionDTO toDTO(FixedTransactions fixedTransactions){
        FixedTransactionDTO DTO = new FixedTransactionDTO();
        DTO.setId(fixedTransactions.getId());
        DTO.setType(fixedTransactions.getType());
        DTO.setIsInstallment(fixedTransactions.getIsInstallment());
        DTO.setEndDate(fixedTransactions.getEndDate());
        DTO.setAmountTime(fixedTransactions.getAmountTime());
        DTO.setStartDate(fixedTransactions.getStartDate());
        DTO.setAmountInstallment(fixedTransactions.getAmountInstallment());
        DTO.setValue(fixedTransactions.getValue());
        return DTO;
    }
    private FixedTransactions toFixedTransaction(FixedTransactionDTO dto){
        FixedTransactions f = new FixedTransactions(dto.getType(), dto.getStartDate(), dto.getValue());
        f.setId(dto.getId());
        f.setIsInstallment(dto.getIsInstallment());
        f.setEndDate(dto.getEndDate());
        f.setAmountTime(dto.getAmountTime());
        f.setAmountInstallment(dto.getAmountInstallment());
        f.setStartDate(dto.getStartDate());
        f.setValue(dto.getValue());
        f.setType(dto.getType());
        return f;
    }

    /**
     * @param amountInstallment The amount installment is between 2 and 24
     * @return true if the
     */
    public static Boolean AmountInstallmentRule(Integer amountInstallment) throws NullException {
        NullException.checkFieldIsNull(amountInstallment, "Amount Installment");
        return amountInstallment > 2 && amountInstallment <= 24;
    }

    /**
     * @param ft The Fixed Transaction
     * @throws
     * LogicException If the Transaction is installments and the amount time is null and violated Amount Installment Rule
     */
    public static void invalidInstallmentQuantityLock(FixedTransactionDTO ft) throws LogicException, NullException {
        if(ft.getIsInstallment()
                && ft.getAmountTime() == null
                && !FixedTransactionService.AmountInstallmentRule(
                        ft.getAmountInstallment())){
            throw new LogicException("Invalid installment quantity");
        }
    }


}
