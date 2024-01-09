package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.InstallmentVariableDTO;
import br.com.psf.personalsystemfinance.entity.FixedTransactions;
import br.com.psf.personalsystemfinance.repository.FixedTransactionsRepository;
import br.com.psf.personalsystemfinance.repository.InstallmentVariableRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allan
 */
@Service
@Transactional
public class FixedTransactionService {

    @Autowired
    private FixedTransactionsRepository fixedTransactionsRepository;


    /**
     * @param id FixedTransactions id
     * @throws Exception NOT FOUND
     */
    public void deleteFixedTransaction(Integer id) throws Exception {
        if(this.fixedTransactionsRepository.existsById(id)){
            this.fixedTransactionsRepository.deleteById(id);
        }else{
            throw new Exception("NOT FOUND");
        }

    }
    /**
     * @param id FixedTransaction id
     * @return FixedTransactionDTO
     * @throws Exception NOT FOUND
     */
    public FixedTransactionDTO getFixedTransaction(Integer id) throws Exception {
        if (this.fixedTransactionsRepository.existsById(id)){
            return this.toDTO(this.fixedTransactionsRepository.getReferenceById(id));
        }else {
            throw new Exception("NOT FOUND");
        }
    }
    /**
     * @param newFT The new Fixed Transaction Registry
     * @throws Exception The id should be null, Invalid installment quantity
     */
    public FixedTransactionDTO addFixedTransaction(FixedTransactionDTO newFT) throws Exception {
        if(newFT.getId() != null){
            throw new Exception("The id should be null");
        }
        if((newFT.isInstallment() && newFT.getTypeInstallment().equals("variable"))
            && (newFT.getAmountInstallment() < 2 || newFT.getAmountInstallment() > 24)){
            throw new Exception("Invalid installment quantity");
        }
        FixedTransactions f = this.toFixedTransaction(newFT);
        newFT = this.toDTO(this.fixedTransactionsRepository.saveAndFlush(f));
        return newFT;
    }
    private FixedTransactionDTO toDTO(FixedTransactions fixedTransactions){
        FixedTransactionDTO DTO = new FixedTransactionDTO();
        DTO.setId(fixedTransactions.getId());
        DTO.setType(fixedTransactions.getType());
        DTO.setInstallment(fixedTransactions.isInstallment());
        DTO.setEndDate(fixedTransactions.getEndDate());
        DTO.setAmountTime(fixedTransactions.getAmountTime());
        DTO.setStartDate(fixedTransactions.getStartDate());
        DTO.setAmountInstallment(fixedTransactions.getAmountInstallment());
        DTO.setTypeInstallment(fixedTransactions.getTypeInstallment());
        DTO.setValue(fixedTransactions.getValue());
        return DTO;
    }
    private FixedTransactions toFixedTransaction(FixedTransactionDTO dto){
        FixedTransactions f = new FixedTransactions(dto.getType(), dto.getStartDate(), dto.getValue());
        f.setId(dto.getId());
        f.setInstallment(dto.isInstallment());
        f.setEndDate(dto.getEndDate());
        f.setAmountTime(dto.getAmountTime());
        f.setAmountInstallment(dto.getAmountInstallment());
        f.setTypeInstallment(dto.getTypeInstallment());
        return f;
    }





}
