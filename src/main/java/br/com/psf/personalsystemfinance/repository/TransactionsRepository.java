package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {



    @Query("SELECT t.id, " +
            "t.name ,  " +
            "t.type, " +
            "t.categoryType, " +
            "CASE t.categoryType " +
            "   WHEN 'Fixed' THEN f.value " +
            "   WHEN 'Eventual' THEN e.value " +
            "END, " +
            " en.name " +
            "FROM Transactions t " +
            "LEFT JOIN FixedTransactions f ON " +
            "   f.id = t.idFixedTransaction " +
            "LEFT JOIN EventualTransaction e ON " +
            "   e.id = t.idEventualTransaction " +
            "LEFT JOIN Entities en ON " +
            "   en.id = t.idEntity " +
            "WHERE (:name IS NULL " +
            "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<List<Object[]>> getListTransactionsScreenRegisters(@Param("name") String name, Pageable pageable);
}
