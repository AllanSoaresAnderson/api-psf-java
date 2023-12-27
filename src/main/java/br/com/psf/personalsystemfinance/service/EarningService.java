package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.EarningDTO;
import br.com.psf.personalsystemfinance.dto.EntityDTO;
import br.com.psf.personalsystemfinance.entity.Earnings;
import br.com.psf.personalsystemfinance.entity.Entities;
import br.com.psf.personalsystemfinance.repository.EarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    public EarningDTO updateEarning(Integer id, EarningDTO earningDTO) throws Exception {
        if (id == null || !this.earningRepository.existsById(id)){
            throw new Exception("Essa entrada não existe");
        }
        earningDTO.setId(id);
        Earnings earnings = this.toEarning(this.mergeEarnings(id, earningDTO));
        return toEarningDTO(this.earningRepository.saveAndFlush(earnings));
    }

    private EarningDTO mergeEarnings(Integer id,EarningDTO earningDTO){
        EarningDTO earningBD = this.getEarningById(id);
        if (earningDTO.getIdEntity() == null){
            earningDTO.setIdEntity(earningBD.getIdEntity());
        }
        if (earningDTO.getValue() == null){
            earningDTO.setValue(earningBD.getValue());
        }
        if (earningDTO.getName() == null){
            earningDTO.setName(earningBD.getName());
        }
        if (earningDTO.getEstimateValue() == null){
            earningDTO.setEstimateValue(earningBD.getEstimateValue());
        }
        return earningDTO;
    }

    public void delEarning(Integer id) throws Exception {
        if(!this.earningRepository.existsById(id)){
            throw new Exception("Esta entrada não existe");
        }
        this.earningRepository.deleteById(id);
    }

    public EarningDTO addEarning(EarningDTO earningDTO){
        Earnings newEarning = this.toEarning(earningDTO);
        return toEarningDTO(this.earningRepository.saveAndFlush(newEarning));
    }
    public List<EarningDTO> getListEarnings(){
        return this.earningRepository.findAll().stream()
                .map(this::toEarningDTO).collect(Collectors.toList());

    }

    public EarningDTO getEarningById(Integer id){
        return toEarningDTO(this.earningRepository.getReferenceById(id));
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
