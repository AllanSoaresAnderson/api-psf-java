package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
