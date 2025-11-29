package com.example.demo.model;

public class TransactionDTO {
    private String type;
    private double amount;
    private String date;

    public TransactionDTO(String type, double amount, String date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    // getters only (no setters if not needed)
    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
