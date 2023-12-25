package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.EarningDTO;
import br.com.psf.personalsystemfinance.entity.Earnings;
import br.com.psf.personalsystemfinance.repository.EarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    public EarningDTO addEarning(EarningDTO earningDTO){
        Earnings newEarning = this.toEarning(earningDTO);
        return toEarningDTO(this.earningRepository.saveAndFlush(newEarning));
    }
    public List<EarningDTO> getListEarnings(){
        return this.earningRepository.findAll().stream()
                .map(this::toEarningDTO).collect(Collectors.toList());

    }

    public EarningDTO getEarningById(Integer id){
        return toEarningDTO(this.earningRepository.findById(id).get());
    }


    private Earnings toEarning(EarningDTO earningDTO){
        Earnings earnings = new Earnings();
        earnings.setId(earningDTO.getId());
        earnings.setName(earningDTO.getName());
        earnings.setValue(earningDTO.getValue());
        earnings.setIdEntity(earningDTO.getIdEntity());
        earnings.setEstimateValue(earningDTO.getEstimateValue());
        return earnings;

    }

    private EarningDTO toEarningDTO(Earnings earnings){
        EarningDTO earningDTO = new EarningDTO();
        earningDTO.setId(earnings.getId());
        earningDTO.setName(earnings.getName());
        earningDTO.setValue(earnings.getValue());
        earningDTO.setIdEntity(earnings.getIdEntity());
        earningDTO.setEstimateValue(earnings.getEstimateValue());
        return  earningDTO;
    }


}
