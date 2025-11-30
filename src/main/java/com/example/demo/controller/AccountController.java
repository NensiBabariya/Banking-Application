package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionDTO;
import com.example.demo.repository.Transrepo;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("accounts")

public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    Transrepo transrepo;

    @PostMapping
    public void openAccount(@RequestBody openRequest req) {
        accountService.openAccount(req.initialBalance);
    }

    public static class openRequest {
        public double initialBalance;
    }

    @PostMapping("{acc}/withdraw")
    public Account withdraw(@PathVariable String acc, @RequestBody AmountRequest amt) {
        return accountService.withdraw(acc, amt.amount);
    }

    public static class AmountRequest {
        public double amount;
    }

    @PostMapping("{acc}/deposite")
    public Account Deposite(@PathVariable String acc, @RequestBody AmountRequest amt) {
        return accountService.deposite(acc, amt.amount);
    }

    @PostMapping("transfer")
    public void transfer(@RequestBody transferRequest request) {
        accountService.transfer(request.fromAccount, request.toAccount, request.amount);
    }

    public static class transferRequest {
        public String fromAccount;
        public String toAccount;
        public double amount;
    }

    @GetMapping("viewBalance/{acc}")
    public Account showbalance(@PathVariable String acc) {
        return accountService.getDetailByAcNo(acc);
    }

    @GetMapping("statement/{acc}")
    public List<TransactionDTO> getStatement(@PathVariable String acc) {
        return accountService.getStatement(acc);
    }
}
