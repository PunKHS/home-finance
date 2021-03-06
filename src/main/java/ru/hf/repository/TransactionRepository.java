package ru.hf.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hf.model.Transaction;
import ru.hf.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT t FROM Transaction t WHERE t.id = :id")
    Transaction findById(@Param("id") Long id);

    @Query(value = "SELECT t FROM Transaction t WHERE t.user = :user AND t.status = 'ACTIVE' ORDER BY t.timestamp")
    List<Transaction> findByUser(@Param("user") User user);

    @Query(value = "SELECT t FROM Transaction t WHERE t.user = :user AND t.status = 'ACTIVE' ORDER BY t.timestamp")
    Page<Transaction> findByUser(Pageable pageable, @Param("user") User user);
}
