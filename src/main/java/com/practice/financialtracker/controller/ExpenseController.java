package com.practice.financialtracker.controller;

import com.practice.financialtracker.model.Expense;
import com.practice.financialtracker.model.ExpenseDto;
import com.practice.financialtracker.model.ExpenseResponse;
import com.practice.financialtracker.model.User;
import com.practice.financialtracker.service.ExpenseService;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/allExpenses")
    public ResponseEntity<ResponseWrapper<List<ExpenseResponse>>> getAllExpenses() {
        ResponseWrapper<List<ExpenseResponse>> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Expense retrieved successfully");
            response.setResponse( expenseService.getAllExpense());
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

    @PostMapping()
    public ResponseEntity<ResponseWrapper<ExpenseDto>> addExpense(HttpServletRequest request, @RequestBody ExpenseDto expenseDto){
        ResponseWrapper<ExpenseDto> response = new ResponseWrapper<>();
        try{
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);
            Expense newExpense = new Expense(user, expenseDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("expense added successfully");
            response.setResponse(expenseService.addExpense(newExpense));
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping(path = "{expenseId}")
    public ResponseEntity<ResponseWrapper<Expense>> deleteExpenseById(@PathVariable("expenseId") Long expenseId) {
        try {
            expenseService.deleteExpenseById(expenseId);
            ResponseWrapper<Expense> response = new ResponseWrapper<>();
            response.setSuccess(true);
            response.setStatusCode(200);
            response.setMessage("Expense deleted successfully");
            response.setResponse(response.getResponse());
            return ResponseEntity.ok(response);
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
