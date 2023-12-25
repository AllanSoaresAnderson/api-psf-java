package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Earnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarningRepository extends JpaRepository<Earnings, Integer> {


}
