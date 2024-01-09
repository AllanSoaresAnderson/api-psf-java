package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.EventualTransactionDTO;
import br.com.psf.personalsystemfinance.entity.EventualTransaction;
import br.com.psf.personalsystemfinance.repository.EventualTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class EventualTransactionService {

    @Autowired
    private EventualTransactionRepository eventualTransactionRepository;


    /**
     * @param id Eventual Transaction id
     * @throws Exception NOT FOUND
     */
    public void deleteEventualTransaction(Integer id) throws Exception {
        if(id!=null && this.eventualTransactionRepository.existsById(id)){
            this.eventualTransactionRepository.deleteById(id);
        }else{
            throw new Exception("NOT FOUND");
        }
    }
    /**
     * @param id Eventual Transaction id
     * @return Eventual Transaction
     * @throws Exception NOT FOUND
     */
    public EventualTransactionDTO getEventualTransaction(Integer id) throws Exception {
        if(id != null && this.eventualTransactionRepository.existsById(id)){
            return this.toDTO(this.eventualTransactionRepository.getReferenceById(id));
        }else{
            throw new Exception("NOT FOUND");
        }
    }
    /**
     * @param etDTO EventualTransaction
     * @return  EventualTransaction
     * @throws Exception The id should be null
     */
    public EventualTransactionDTO addEventualTransaction(EventualTransactionDTO etDTO) throws Exception {
        if(etDTO.getId() != null){
            EventualTransaction et = this.toEventualTransaction(etDTO);
            return this.toDTO(this.eventualTransactionRepository.saveAndFlush(et));
        }else{
            throw new Exception("The id should be null");
        }
    }
    public EventualTransactionDTO toDTO(EventualTransaction e){
        EventualTransactionDTO dto = new EventualTransactionDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDate(e.getDate());
        dto.setType(e.getType());
        dto.setValue(e.getValue());
        return dto;
    }

    public EventualTransaction toEventualTransaction(EventualTransactionDTO dto){
        EventualTransaction e = new EventualTransaction(dto.getName(),
                dto.getValue(),
                dto.getDate());
        e.setId(dto.getId());
        e.setType(dto.getType());
        return e;
    }
}
