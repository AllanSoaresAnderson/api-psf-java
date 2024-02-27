package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.EntitiesDTO;
import br.com.psf.personalsystemfinance.exceptions.EntityNotFoundException;
import br.com.psf.personalsystemfinance.exceptions.FieldEmptyException;
import br.com.psf.personalsystemfinance.exceptions.NullException;
import br.com.psf.personalsystemfinance.service.EntityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/entity")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntitiesDTO> getEntityById(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.entityService.getEntityById(id), HttpStatus.OK);
        } catch (NullException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<EntitiesDTO>> getEntities(){
        return new ResponseEntity<>(this.entityService.getEntities(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Integer id){
        try {
            this.entityService.deleteByID(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Void> updateEntity(@Validated @RequestBody EntitiesDTO entity){
        try {
            this.entityService.updateEntity(entity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullException | FieldEmptyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addEntity(@Validated @RequestBody EntitiesDTO entity){
        try {
            this.entityService.addEntity(entity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullException | FieldEmptyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/registers")
    public ResponseEntity<PageImpl<Map<String, Object>>> getListEntitiesScreenRegisters(
            @RequestParam(required = false)String name,
            @RequestParam(required = false)Boolean isPerson,
            Pageable pageable
            ){
        return new ResponseEntity<>(this.entityService.getListEntitiesScreenRegisters(pageable, name, isPerson), HttpStatus.OK);
    }



}
