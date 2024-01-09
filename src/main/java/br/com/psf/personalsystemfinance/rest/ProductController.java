package br.com.psf.personalsystemfinance.rest;

import br.com.psf.personalsystemfinance.dto.ProductDTO;
import br.com.psf.personalsystemfinance.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductDTO> addProduct(@Validated @RequestBody ProductDTO newProduct){
        try {
            return new ResponseEntity<>(this.productService.addProduct(newProduct), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer id){
        try {
            return  new ResponseEntity<>(this.productService.getProduct(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            this.productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
