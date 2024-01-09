package br.com.psf.personalsystemfinance.service;

import br.com.psf.personalsystemfinance.dto.ProductDTO;
import br.com.psf.personalsystemfinance.entity.Product;
import br.com.psf.personalsystemfinance.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    /**
     * @param id Product id
     * @throws Exception The id is null
     */
    public void deleteProduct(Integer id) throws Exception {
        if(id!=null && this.productRepository.existsById(id)){
            this.productRepository.deleteById(id);
        }else {
            throw new Exception("The id is null");
        }
    }
    /**
     * @param id Product id
     * @return Product
     * @throws Exception NOT FOUND
     */
    public ProductDTO getProduct(Integer id) throws Exception {
        if( id != null && this.productRepository.existsById(id)){
            return this.toDTO(this.productRepository.getReferenceById(id));
        }else {
            throw new Exception("NOT FOUND");
        }
    }

    /**
     * @param productDTO Product
     * @throws Exception The id should be null
     */
    public ProductDTO addProduct(ProductDTO productDTO) throws Exception {
        if(productDTO.getId() == null){
            Product product = this.toProduct(productDTO);
            return this.toDTO(this.productRepository.saveAndFlush(product));
        }else{
            throw new Exception("The id should be null");
        }
    }
    private ProductDTO toDTO(Product p){
        ProductDTO dto = new ProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setUnitValue(p.getUnitValue());
        return dto;
    }
    private Product toProduct(ProductDTO dto){
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setUnitValue(dto.getUnitValue());
        return p;
    }


}
