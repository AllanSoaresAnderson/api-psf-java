package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.EarningDTO;
import br.com.psf.personalsystemfinance.dto.EntityDTO;
import br.com.psf.personalsystemfinance.service.EarningService;
import br.com.psf.personalsystemfinance.service.EntityService;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/earnings")
@Transactional
public class EarningController {

    @Autowired
    private EarningService earningService;
    @Autowired
    private EntityService entityService;

    @ApiOperation(value = "Obtém a lista de Entradas")
    @GetMapping("/listEarnings")
    @ResponseBody
    public ResponseEntity<List<EarningDTO>> getListEarnings() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        return new ResponseEntity<>(this.earningService.getListEarnings(), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Obtém uma Entrada por um ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EarningDTO> getEarningById(@PathVariable Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        return new ResponseEntity<>(this.earningService.getEarningById(id), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/addEarning")
    @ResponseBody
    public ResponseEntity<EarningDTO> addEarning(@Validated @RequestBody EarningDTO newEarningDTO ){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        if(newEarningDTO.getIdEntity() != null
                && !this.entityService.entityExists(newEarningDTO.getIdEntity())){
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.earningService.addEarning(newEarningDTO), headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delEarning/{id}")
    public ResponseEntity<Void> delEarning(@PathVariable Integer id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            this.earningService.delEarning(id);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(value = "/updateEarning/{id}")
    @ResponseBody
    public ResponseEntity<EarningDTO> updateEarning(@PathVariable Integer id, @RequestBody EarningDTO earningDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        try {
            return new ResponseEntity<>( this.earningService.updateEarning(id, earningDTO), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }


}
