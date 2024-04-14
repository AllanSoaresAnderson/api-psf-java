package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.InstallmentDTO;
import br.com.psf.personalsystemfinance.entity.FixedTransactions;
import br.com.psf.personalsystemfinance.entity.Installment;
import br.com.psf.personalsystemfinance.exceptions.LogicException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
import br.com.psf.personalsystemfinance.repository.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allan
 */
@Service
@Transactional
public class InstallmentService {

    @Autowired
    private InstallmentRepository installmentRepository;

    /**
     * @param idFixedTransaction FixedTransaction id
     */
    public void deleteInstallments(Integer idFixedTransaction){
        long amountInstallments = this.installmentRepository.countInstallmentsByTransaction(idFixedTransaction);
        if(amountInstallments > 0){
            this.installmentRepository.deleteInstallmentsByTransaction(idFixedTransaction);
        }
    }

    /**
     * @param ft The fixed transaction from which the installment list will be created.
     * @throws LogicException Installment creation rules violated
     * @throws NullException The id should be null
     */
    public void createInstallments(FixedTransactionDTO ft) throws LogicException, NullException {
        List<InstallmentDTO> listInstallment;
        if(InstallmentService.isPeriodicDate(ft.getType())){
            ft.setAmountTime(1);
        }
        InstallmentService.lockToCreateInstallments(ft);
        Double valueInstallmentDefault = ft.getValue() / ft.getAmountInstallment();
        listInstallment = new ArrayList<>();
        LocalDate timeInstallment = ft.getStartDate();
        for (int i = 0; i < ft.getAmountInstallment(); i++) {
            InstallmentDTO installment = new InstallmentDTO();
            if(i>0){
                timeInstallment = this.calculateDateInstallment(ft.getType(), ft.getAmountTime(), timeInstallment);
            }
            installment.setIdFixedTransaction(ft.getId());
            installment.setValue(valueInstallmentDefault);
            installment.setDate(timeInstallment);
            listInstallment.add(installment);
        }
        this.addListInstallment(listInstallment);
    }
    private LocalDate calculateDateInstallment(String type, Integer amount, LocalDate startDate) {
        switch (type){
            case "Annual":
            case "Amount of Years":
                startDate = startDate.plusYears(amount);
                break;
            case"Monthly":
            case "Amount of Months":
                startDate = startDate.plusMonths(amount);
                break;
            case"Weekly":
            case "Amount of Weeks":
                startDate = startDate.plusWeeks(amount);
                break;
            case "Amount of Days":
                startDate = startDate.plusDays(amount);
                break;
        }
        return startDate;
    }

    /**
     * @param newInstallment The new Installment Registry
     * @throws NullException The id should be null
     */
    public InstallmentDTO addInstallment(InstallmentDTO newInstallment) throws NullException{
        NullException.checkFieldIsNotNull(newInstallment.getId(), "id");
        Installment i = this.toInstallmentVariable(newInstallment);
        return this.toDTO(this.installmentRepository.saveAndFlush(i));
    }

    /**
     * @param listNewInstallment A list of variable installments
     * @return The list of entered variable installments
     * @throws NullException The id should be null
     * @throws LogicException The list must not be empty
     */
    private List<InstallmentDTO> addListInstallment(List<InstallmentDTO> listNewInstallment) throws LogicException, NullException {
        if (listNewInstallment.isEmpty()){
            throw new LogicException("The list must not be empty");
        }
        List<Installment> listInstallment = new ArrayList<>();
        for(InstallmentDTO i : listNewInstallment){
            NullException.checkFieldIsNotNull(i.getId(), "id");
            listInstallment.add(this.toInstallmentVariable(i));
        }
        listInstallment = this.installmentRepository.saveAll(listInstallment);
        listNewInstallment = new ArrayList<>();
        for (Installment i : listInstallment){
            listNewInstallment.add(this.toDTO(i));
        }
        return listNewInstallment;
    }

    private InstallmentDTO toDTO(Installment i){
        InstallmentDTO dto = new InstallmentDTO();
        dto.setId(i.getId());
        dto.setDate(i.getDate());
        dto.setValue(i.getValue());
        dto.setIdFixedTransaction(i.getIdFixedTransaction());
        return dto;
    }

    private Installment toInstallmentVariable(InstallmentDTO dto){
        Installment i =
                new Installment(
                dto.getIdFixedTransaction(),
                dto.getValue(),
                dto.getDate());
        i.setId(dto.getId());
        return i;
    }

    /**
     * @param type The type of the installment
     * @return True if the type equals Monthly, Annual or Weekly. Else false
     * @throws NullException if the type is null
     */
    public static Boolean isPeriodicDate(String type) throws NullException {
        NullException.checkFieldIsNull(type,"Type Installment");
        return type.equals("Monthly") || type.equals("Annual") || type.equals("Weekly");
    }

    /**
     * @param ft The Fixed Transaction
     * @throws NullException If the number of installments and the type of the transaction are null
     * @throws LogicException Installment creation rules violated
     */
    public static void lockToCreateInstallments(FixedTransactionDTO ft) throws NullException, LogicException {
        if(!InstallmentService.checkRuleToCreateInstallments(ft)){
            throw new LogicException("Installment creation rules violated");
        }
    }

    /**
     * @param ft The Fixed Transaction
     * @return True if it is able to create installments
     * @throws NullException If the number of installments and the type of the transaction are null
     */
    public static Boolean checkRuleToCreateInstallments(FixedTransactionDTO ft) throws NullException {
        return ft.getId() != null
                && ft.getIsInstallment()
                && ft.getAmountInstallment() != null
                && FixedTransactionService.AmountInstallmentRule(ft.getAmountInstallment());
    }


}
