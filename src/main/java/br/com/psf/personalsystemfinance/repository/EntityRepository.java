package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Entities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<Entities, Integer> {


}
