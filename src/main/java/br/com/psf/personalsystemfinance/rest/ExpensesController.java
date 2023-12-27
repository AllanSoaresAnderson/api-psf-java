package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.service.ExpensesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/expenses")
@RestController
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;


    @ApiOperation(value = "Obtém a lista de Despesas")
    @GetMapping("/listExpenses")
    @ResponseBody
    public ResponseEntity<String[]> getListExpenses() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        String[] listExpenses = {"Faculdade", "Carro", "Luz", "Água", "Internet", "Aluguel", "Cartão"};
        return new ResponseEntity<>(listExpenses, headers, HttpStatus.OK);
    }



}
