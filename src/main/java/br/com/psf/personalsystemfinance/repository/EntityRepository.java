package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Entities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface EntityRepository extends JpaRepository<Entities, Integer> {

    @Query("SELECT e.id ,e.name, e.isPerson, " +
            "(SELECT COUNT(t) FROM Transactions t WHERE t.idEntity = e.id AND t.type = 'Earning'), " +
            "(SELECT COUNT(t) FROM Transactions t WHERE t.idEntity = e.id AND t.type = 'Expense') " +
            "FROM Entities e " +
            "WHERE ( :name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) )" +
            "AND ( :isPerson IS NULL OR e.isPerson = :isPerson )")
    Page<List<Object[]>> getListEntitiesScreenRegisters(@Param("name") String name, @Param("isPerson") Boolean isPerson, Pageable pageable);
}
