package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.EarningDTO;
import br.com.psf.personalsystemfinance.dto.EntityDTO;
import br.com.psf.personalsystemfinance.service.EntityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/entities")
@RestController
public class EntityController {

    @Autowired
    private EntityService entityService;

    @ApiOperation(value = "Obtém a lista de Despesas")
    @GetMapping("/listEntities")
    @ResponseBody
    public ResponseEntity<String[]> getListEntities() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        String[] listExpenses = {"Casa", "Júlia", "Carro"};
        return new ResponseEntity<>(listExpenses, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/addEntity")
    @ResponseBody
    public ResponseEntity<EntityDTO> addEntity(@Validated @RequestBody EntityDTO newEntityDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            return new ResponseEntity<>(this.entityService.addEntity(newEntityDTO), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new EntityDTO(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delEntity/{id}")
    public ResponseEntity<Void> delEntity(@PathVariable Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            this.entityService.delEntity(id);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }


    }

    @PutMapping(value = "/updateEntity/{id}")
    @ResponseBody
    public ResponseEntity<EntityDTO> updateEntity(@PathVariable Integer id, @RequestBody EntityDTO entityDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            return new ResponseEntity<>( this.entityService.updateEntity(id, entityDTO), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PatchMapping(value = "/updateEntity/{id}/name")
    @ResponseBody
    public ResponseEntity<EntityDTO> updateEntityName(@PathVariable Integer id,  @RequestBody EntityDTO entityDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            return new ResponseEntity<>(this.entityService.updateFieldEntity(id, "name", entityDTO.getName()), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(headers, HttpStatus.BAD_GATEWAY);
        }
    }

    @PatchMapping(value = "/updateEntity/{id}/isPerson")
    @ResponseBody
    public ResponseEntity<EntityDTO> updateEntityIsPerson(@PathVariable Integer id, @RequestBody EntityDTO entityDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            return new ResponseEntity<>(this.entityService.updateFieldEntity(id, "isPerson", entityDTO.getIsPerson()), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(headers, HttpStatus.BAD_GATEWAY);
        }
    }




}
