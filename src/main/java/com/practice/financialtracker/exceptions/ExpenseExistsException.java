package com.practice.financialtracker.exceptions;

public class ExpenseExistsException extends Exception{
    public ExpenseExistsException(String message) {
        super(message);
    }
}
