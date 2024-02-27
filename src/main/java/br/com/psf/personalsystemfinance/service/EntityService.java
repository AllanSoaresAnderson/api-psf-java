package br.com.psf.personalsystemfinance.service;


import br.com.psf.personalsystemfinance.dto.EntitiesDTO;
import br.com.psf.personalsystemfinance.entity.Entities;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.exceptions.FieldEmptyException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
import br.com.psf.personalsystemfinance.repository.EntityRepository;
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
public class EntityService {


    @Autowired
    private EntityRepository entityRepository;

    @SuppressWarnings("unchecked")
    public PageImpl<Map<String, Object>> getListEntitiesScreenRegisters(Pageable pageable, String name, Boolean isPerson){
        Page<List<Object[]>> page = this.entityRepository.getListEntitiesScreenRegisters(name, isPerson, pageable);
        List<Map<String, Object>> result = new ArrayList<>();
        Object content = page.getContent();
        List<Object[]> list = (List<Object[]>) content;
        for (Object[] row : list) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", row[0]);
            obj.put("name", row[1]);
            obj.put("isPerson", row[2]);
            obj.put("quantityExpenses", row[3]);
            obj.put("quantityEarnings", row[4]);
            result.add(obj);
        }

        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    /**
     *
     * @param id id should be not null
     * @return true if entity exists and false if entity not exists
     * @throws NullException if id is null
     */
    public boolean entityExists(Integer id) throws NullException {
        NullException.checkFieldIsNull(id, "id");
        return this.entityRepository.existsById(id);
    }

    /**
     *
     * @param newEntity The entity that will be added
     * @throws NullException if id is null or is person is null
     * @throws FieldEmptyException if name is empty
     */
    public void addEntity(EntitiesDTO newEntity) throws NullException, FieldEmptyException {
        NullException.checkFieldIsNotNull(newEntity.getId(), "id");
        this.checkEntityFields(newEntity);
        this.entityRepository.saveAndFlush(this.toEntity(newEntity));
    }

    /**
     *
     * @return All entities
     */
    public List<EntitiesDTO> getEntities(){
        List<Entities> listEntities = this.entityRepository.findAll();
        List<EntitiesDTO> listDTO = new ArrayList<>();
        for(Entities entity : listEntities){
            listDTO.add(this.toDTO(entity));
        }
        return listDTO;
    }

    /**
     *
     * @param id should not be null
     * @return The entity
     * @throws NullException if id is null
     * @throws EntityNotFoundException if the entity is not found
     */
    public EntitiesDTO getEntityById(Integer id) throws NullException, EntityNotFoundException {
        NullException.checkFieldIsNull(id, "id");
        if(this.entityRepository.existsById(id)){
            return this.toDTO(this.entityRepository.getReferenceById(id));
        }else {
            throw new EntityNotFoundException();
        }
    }

    /**
     *
     * @param id should not be null
     * @throws NullException if id is null
     * @throws EntityNotFoundException if the entity is not found
     */
    public void deleteByID(Integer id) throws NullException, EntityNotFoundException{
        NullException.checkFieldIsNull(id, "id");
        if(this.entityRepository.existsById(id)){
            this.entityRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException();
        }

    }

    /**
     *
     * @param entity the entity that will be updated
     * @throws NullException if id or is person is null
     * @throws EntityNotFoundException if the entity is not found
     * @throws FieldEmptyException if the name is empty
     */
    public void updateEntity(EntitiesDTO entity) throws NullException, EntityNotFoundException, FieldEmptyException {
        NullException.checkFieldIsNull(entity.getId(), "id");
        if(this.entityRepository.existsById(entity.getId())){
            this.checkEntityFields(entity);
            this.entityRepository.saveAndFlush(this.toEntity(entity));
        }else {
            throw new EntityNotFoundException();
        }

    }

    /**
     *
     * @param entity the entity that will be checked
     * @throws FieldEmptyException if name is empty
     * @throws NullException if is person is null
     */
    private void checkEntityFields(EntitiesDTO entity) throws FieldEmptyException, NullException {
        NullException.checkFieldIsNull(entity.getIsPerson(), "is person");
        FieldEmptyException.checkStringField(entity.getName(), "Entity Name");
    }


    /**
     *
     * @param entity the entity
     * @return your Data Transfer Object
     */
    public EntitiesDTO toDTO(Entities entity){
        EntitiesDTO dto = new EntitiesDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setIsPerson(entity.getIsPerson());
        dto.setIdChildren(entity.getIdChildren());
        return dto;
    }

    /**
     *
     * @param dto his Data Transfer Object
     * @return the entity
     */
    public Entities toEntity(EntitiesDTO dto){
        Entities entity = new Entities();
        entity.setName(dto.getName());
        entity.setId(dto.getId());
        entity.setIsPerson(dto.getIsPerson());
        entity.setIdChildren(dto.getIdChildren());
        return entity;
    }

}
