package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.EntityDTO;
import br.com.psf.personalsystemfinance.entity.Entities;
import br.com.psf.personalsystemfinance.repository.EntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntityService {

    @Autowired
    private EntityRepository entityRepository;

    public Boolean entityExists(Integer id){
       if(id != null){
           return  this.entityRepository.existsById(id);
       }
        return false;
    }


    public List<EntityDTO> getListEntities(){
        return this.entityRepository.findAll().stream()
                .map(this::toEntityDTO).collect(Collectors.toList());
    }

    public EntityDTO getEntityById(Integer id){
        return this.toEntityDTO(this.entityRepository.getReferenceById(id));
    }


    public EntityDTO updateFieldEntity(Integer id, String field, Object value) throws Exception {
        if(id != null && this.entityRepository.existsById(id)){
            Entities entity = this.entityRepository.getReferenceById(id);
            switch (field){
                case "name":
                    entity.setName((String) value);
                    break;
                case "isPerson":
                    entity.setIsPerson((Boolean) value);
                    break;
                default:
                    throw new Exception("Campo não existe");
            }
            return toEntityDTO(this.entityRepository.saveAndFlush(entity));
        }
        else{
            throw new Exception("Entidade não existe");
        }
    }

    public void delEntity(Integer id) throws Exception {
        if(id != null && this.entityRepository.existsById(id)){
            this.entityRepository.deleteById(id);
        }else {
            throw new Exception("Entidade não existe");
        }
    }

    public EntityDTO updateEntity(Integer id, EntityDTO entityDTO) throws Exception {
        if(id != null && this.entityRepository.existsById(id)){
            entityDTO.setId(id);
            Entities entity = this.toEntity(entityDTO);
            return toEntityDTO(this.entityRepository.saveAndFlush(entity));
        }else {
            throw new Exception("Entidade não existe");
        }
    }


    public EntityDTO addEntity(EntityDTO entityDTO) throws Exception {
        Entities newEntity = this.toEntity(entityDTO);
        if (entityDTO.getId() == null){
           return this.toEntityDTO(this.entityRepository.saveAndFlush(newEntity));
        }
        else {
            throw new Exception("Entidade já existe");
        }
    }

    public Entities toEntity(EntityDTO entityDTO){
        Entities entity = new Entities();
        entity.setId(entityDTO.getId());
        entity.setName(entityDTO.getName());
        entity.setIsPerson(entityDTO.getIsPerson());
        return entity;
    }

    private EntityDTO toEntityDTO(Entities entity){
        EntityDTO newEntityDTO = new EntityDTO();
        newEntityDTO.setId(entity.getId());
        newEntityDTO.setName(entity.getName());
        newEntityDTO.setIsPerson(entity.getIsPerson());
        return newEntityDTO;
    }


}
