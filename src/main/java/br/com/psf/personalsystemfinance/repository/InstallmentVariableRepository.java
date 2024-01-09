package br.com.psf.personalsystemfinance.repository;

import br.com.psf.personalsystemfinance.entity.InstallmentVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentVariableRepository extends JpaRepository<InstallmentVariable, Integer> {

    @Modifying
    @Query("DELETE FROM InstallmentVariable i WHERE i.idFixedTransaction = :idFixedTransaction")
    void deleteInstallmentsByTransaction(@Param("idFixedTransaction") Integer idFixedTransactions);

    @Query("SELECT COUNT(i.id) FROM InstallmentVariable i WHERE i.idFixedTransaction = :idFixedTransaction")
    long countInstallmentsByTransaction(@Param("idFixedTransaction") Integer idFixedTransactions);



}
