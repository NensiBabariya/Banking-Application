package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionDTO;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.Transrepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    AccountRepo accountRepo;

    @Autowired
    Transrepo transrepo;

    private final Random random = new Random();


    public Account openAccount(double initialbal) {
        String acNo;
        do {
            acNo = generate10Digit();
        } while (accountRepo.existsByAccountNumber(acNo));

        Account account = new Account(acNo, initialbal);
        Account openedAcc = accountRepo.save(account);

        Transaction transaction = new Transaction(acNo, "deposite", initialbal, LocalDateTime.now());
        transrepo.save(transaction);

        return openedAcc;
    }


    private String generate10Digit() {
        long number = 1_000_000_000L + (long) (random.nextDouble() * 9_000_000_000L);
        return Long.toString(number);
    }


    @Transactional
    public Account withdraw(String acc, double amount) {
        Account account = accountRepo.findByAccountNumber(acc).orElseThrow();
        if (amount > account.getBalance()) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepo.save(account);
        transrepo.save(new Transaction(acc, "withdraw", amount, LocalDateTime.now()));
        return account;
    }

    public Account deposite(String acc, double amount) {
        Account account = accountRepo.findByAccountNumber(acc).orElseThrow();

        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);
        transrepo.save(new Transaction(acc, "deposit", amount, LocalDateTime.now()));
        return account;
    }

    public void transfer(String fromAccount, String toAccount, double amount) {

        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Cannot transfer to same Account");
        }

        Account srcAc = accountRepo.findByAccountNumber(fromAccount).orElseThrow();
        Account dstAc = accountRepo.findByAccountNumber(toAccount).orElseThrow();

        if (srcAc.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        srcAc.setBalance(srcAc.getBalance() - amount);
        dstAc.setBalance(dstAc.getBalance() + amount);

        accountRepo.save(srcAc);
        accountRepo.save(dstAc);

        transrepo.save(new Transaction(fromAccount, "Transfer - out", amount, LocalDateTime.now()));
        transrepo.save(new Transaction(toAccount, "Transfer - in", amount, LocalDateTime.now()));
    }

    public Account getDetailByAcNo(String acc) {
        Account detail = accountRepo.findByAccountNumber(acc).orElseThrow();
        return detail;
    }

    public List<TransactionDTO> getStatement(String acc) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return transrepo.findByAcNoOrderByTimestamp(acc).stream()
                .map(t -> new TransactionDTO(t.getType(), t.getAmount(), t.getTimestamp().format(formatter))).toList();
    }

}


