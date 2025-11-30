package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "acNo", nullable = false, unique = true, length = 10)
    private String accountNumber;

    @Column(nullable = false)
    private double balance;

    public Account() {
    }

    public Account(String accountNo, double balance) {
        this.accountNumber = accountNo;
        this.balance = balance;
    }

    public void setAccountNo(String accountNo) {
        this.accountNumber = accountNo;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
