package br.com.psf.personalsystemfinance.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/earnings")
public class EarningController {


//    @Autowired
//    private EarningService earningService;
    @ApiOperation(value = "Obtém a lista de Entradas")
    @GetMapping("/listEarnings")
    @ResponseBody
    public ResponseEntity<String[]> getListEarnings() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        String[] listEarnings = {"Salário", "Décimo Terceiro", "Causa na Justiça"};
        return new ResponseEntity<>(listEarnings, headers, HttpStatus.OK);
    }

//    @ApiOperation(value = "Obtém a lista de Entradas")
//    @GetMapping
//    public ResponseEntity<List<EarningDTO>> getListEarnings(){
//        System.out.println("chegou na api");
//        return ResponseEntity.ok().body(this.earningService.getListEarnings());
//    }

//    @ApiOperation(value = "Obtém uma Entrada por um ID")
//    @GetMapping(value = "{id}")
//    public ResponseEntity<EarningDTO> getEarningById(@PathVariable Integer id){
//        System.out.println("chegou na api");
//        return ResponseEntity.ok().body(this.earningService.getEarningById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<EarningDTO> addEarning(@Validated @RequestBody EarningDTO newEarningDTO ){
//        return ResponseEntity.ok().body(this.earningService.addEarning(newEarningDTO));
//    }

}
