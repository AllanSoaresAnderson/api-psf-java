package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.EventualTransactionDTO;
import br.com.psf.personalsystemfinance.entity.EventualTransaction;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
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
     * @throws EntityNotFoundException NOT FOUND
     */
    public void deleteEventualTransaction(Integer id) throws EntityNotFoundException {
        if(id!=null && this.eventualTransactionRepository.existsById(id)){
            this.eventualTransactionRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("NOT FOUND");
        }
    }
    /**
     * @param id Eventual Transaction id
     * @return Eventual Transaction
     * @throws EntityNotFoundException NOT FOUND
     */
    public EventualTransactionDTO getEventualTransaction(Integer id) throws EntityNotFoundException {
        if(id != null && this.eventualTransactionRepository.existsById(id)){
            return this.toDTO(this.eventualTransactionRepository.getReferenceById(id));
        }else{
            throw new EntityNotFoundException("NOT FOUND");
        }
    }
    /**
     * @param etDTO EventualTransaction
     * @return  EventualTransaction
     * @throws NullException The id should be null
     */
    public EventualTransactionDTO addEventualTransaction(EventualTransactionDTO etDTO) throws NullException {
        if(etDTO.getId() == null){
            EventualTransaction et = this.toEventualTransaction(etDTO);
            return this.toDTO(this.eventualTransactionRepository.saveAndFlush(et));
        }else{
            throw new NullException("The id should be null");
        }
    }
    public EventualTransactionDTO toDTO(EventualTransaction e){
        EventualTransactionDTO dto = new EventualTransactionDTO();
        dto.setId(e.getId());
        dto.setDate(e.getDate());
        dto.setType(e.getType());
        dto.setValue(e.getValue());
        return dto;
    }

    public EventualTransaction toEventualTransaction(EventualTransactionDTO dto){
        EventualTransaction e = new EventualTransaction(
                dto.getValue(),
                dto.getDate());
        e.setId(dto.getId());
        e.setType(dto.getType());
        return e;
    }
}
