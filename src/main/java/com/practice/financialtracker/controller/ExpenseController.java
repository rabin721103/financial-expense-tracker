package com.practice.financialtracker.controller;

import com.practice.financialtracker.model.Expense;
import com.practice.financialtracker.service.ExpenseService;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<Expense>>> getAllExpenses() {
        ResponseWrapper<List<Expense>> response = new ResponseWrapper<>();
        try {
            List<Expense> expenses = expenseService.getAllExpense();
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Expense retrieved successfully");
            response.setResponse(expenses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/{expenseId}")
    public ResponseEntity<ResponseWrapper<Expense>> getExpenseById(@PathVariable("expenseId") long expenseId) {
        ResponseWrapper<Expense> response = new ResponseWrapper<>();
        try {
            Expense expense = expenseService.getExpenseById(expenseId);
            if (expense != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("expense retrieved successfully");
                response.setResponse(expense);
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("expense not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/name/{expenseName}")
    public ResponseEntity<ResponseWrapper<List<Expense>>> getExpenseByName(@PathVariable("expenseName")  String expenseName) {
        ResponseWrapper<List<Expense>> response = new ResponseWrapper<>();
        try {
            List<Expense> expense = expenseService.getExpenseByName(expenseName);
            if (expense != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("expense retrieved successfully");
                response.setResponse(expense);
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("expense not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
//    @GetMapping()
//    public List<Expense> getExpenseByIdOrName( @RequestParam(required = false) Long id,
//                                               @RequestParam(required = false) String expenseName) {
//        List<Expense> result = new ArrayList<>();
//        try {
//            if ((id == null) && (expenseName == null)) {
//                result = expenseService.getAllExpense();
//            } else if (expenseName == null) {
//                result.add(expenseService.getExpenseById(id));
//            } else {
//                result.add(expenseService.getExpenseByName(expenseName));
//            }
//        } catch (ExpenseNotFoundException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
//        }
//        return result;
//    }


    @PostMapping("/")
    public ResponseEntity<ResponseWrapper<Expense>> addExpense(@Valid @RequestBody Expense expense) {
        ResponseWrapper<Expense> response = new ResponseWrapper<>();
        try {
            Expense newExpense = expenseService.addExpense(expense);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Expense added successfully");
            response.setResponse(newExpense);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(path = "{expenseId}")
    public void deleteExpenseById(@PathVariable("expenseId") Long expenseId) {
        try {
            expenseService.deleteExpenseById(expenseId);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
    @PutMapping(path = "{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable("expenseId") Long oldExpenseId, @RequestBody Expense expense) {
        ResponseWrapper<Expense> response = new ResponseWrapper<>();
        try {
            Expense newExpense = expenseService.updateExpense(oldExpenseId, expense).getBody();
            if (newExpense != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("User updated successfully");
                response.setResponse(newExpense);
                return ResponseEntity.ok(response.getResponse());
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("User not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getResponse());
            }

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getResponse());
        }
    }

}
