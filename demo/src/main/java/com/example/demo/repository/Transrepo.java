package com.example.demo.repository;

import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionDTO;
import com.example.demo.service.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Transrepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAcNoOrderByTimestamp(String accountnumber);
}
