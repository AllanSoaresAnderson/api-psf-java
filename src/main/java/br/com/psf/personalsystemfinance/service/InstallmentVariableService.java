package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.FixedTransactionDTO;
import br.com.psf.personalsystemfinance.dto.InstallmentVariableDTO;
import br.com.psf.personalsystemfinance.entity.InstallmentVariable;
import br.com.psf.personalsystemfinance.repository.InstallmentVariableRepository;
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
public class InstallmentVariableService {

    @Autowired
    private InstallmentVariableRepository installmentVariableRepository;


    /**
     * @param idFixedTransaction FixedTransaction id
     */
    public void deleteInstallments(Integer idFixedTransaction){
        long amountInstallments = this.installmentVariableRepository.countInstallmentsByTransaction(idFixedTransaction);
        if(amountInstallments > 0){
            this.installmentVariableRepository.deleteInstallmentsByTransaction(idFixedTransaction);
        }
    }

    /**
     * @param ft The fixed transaction from which the installment list will be created.
     * @return the installment list
     * @throws Exception Installment creation rules violated
     */
    public List<InstallmentVariableDTO> createInstallments(FixedTransactionDTO ft) throws Exception {
        List<InstallmentVariableDTO> listInstallment;
        if(ft.getId() != null
                && ((ft.isInstallment() && ft.getTypeInstallment().equals("variable"))
                && (ft.getAmountInstallment() != null
                && ft.getAmountInstallment() > 2 && ft.getAmountInstallment() <= 24))){
            Double valueInstallmentDefault = ft.getValue() / ft.getAmountInstallment();
            listInstallment = new ArrayList<>();
            LocalDate timeInstallment = ft.getStartDate();
            for (int i = 0; i < ft.getAmountInstallment(); i++) {
                InstallmentVariableDTO installment = new InstallmentVariableDTO();
                if(i>0){
                    timeInstallment = this.calculateDateInstallment(ft.getType(), ft.getAmountTime(), timeInstallment);
                }
                installment.setIdFixedTransaction(ft.getId());
                installment.setValue(valueInstallmentDefault);
                installment.setDate(timeInstallment);
                listInstallment.add(installment);
            }
            listInstallment = this.addListInstallment(listInstallment);
        } else {
            throw new Exception("Installment creation rules violated");
        }
        return listInstallment;
    }
    private LocalDate calculateDateInstallment(String type, Integer amount, LocalDate startDate){
        switch (type){
            case "perYear":
                startDate = startDate.plusYears(amount);
                break;
            case "perMonth":
                startDate = startDate.plusMonths(amount);
                break;
            case "perWeek":
                startDate = startDate.plusWeeks(amount);
                break;
            case "perDay":
                startDate = startDate.plusDays(amount);
                break;
        }
        return startDate;
    }

    /**
     * @param newInstallment The new Installment Registry
     * @throws Exception The id should be null
     */
    public InstallmentVariableDTO addInstallment(InstallmentVariableDTO newInstallment) throws Exception{
        if (newInstallment.getId() != null){
            throw new Exception("The id should be null");
        }
        InstallmentVariable i = this.toInstallmentVariable(newInstallment);
        return this.toDTO(this.installmentVariableRepository.saveAndFlush(i));
    }

    /**
     * @param listNewInstallment A list of variable installments
     * @return The list of entered variable installments
     * @throws Exception The id should be null and the list must not be empty
     */
    private List<InstallmentVariableDTO> addListInstallment(List<InstallmentVariableDTO> listNewInstallment) throws Exception {
        if (listNewInstallment.isEmpty()){
            throw new Exception("The list must not be empty");
        }
        List<InstallmentVariable> listInstallment = new ArrayList<>();
        for(InstallmentVariableDTO i : listNewInstallment){
            if (i.getId() != null){
                throw new Exception("The id should be null");
            }else{
                listInstallment.add(this.toInstallmentVariable(i));
            }
        }
        listInstallment = this.installmentVariableRepository.saveAll(listInstallment);
        listNewInstallment = new ArrayList<>();
        for (InstallmentVariable i : listInstallment){
            listNewInstallment.add(this.toDTO(i));
        }
        return listNewInstallment;
    }

    private InstallmentVariableDTO toDTO(InstallmentVariable i){
        InstallmentVariableDTO dto = new InstallmentVariableDTO();
        dto.setId(i.getId());
        dto.setDate(i.getDate());
        dto.setValue(i.getValue());
        dto.setIdFixedTransaction(i.getIdFixedTransaction());
        return dto;
    }

    private InstallmentVariable toInstallmentVariable(InstallmentVariableDTO dto){
        InstallmentVariable i =
                new InstallmentVariable(
                dto.getIdFixedTransaction(),
                dto.getValue(),
                dto.getDate());
        i.setId(dto.getId());
        return i;
    }


}
