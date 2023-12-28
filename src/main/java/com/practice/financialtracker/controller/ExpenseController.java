package com.practice.financialtracker.controller;

import com.practice.financialtracker.model.*;
import com.practice.financialtracker.service.ExpenseService;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @GetMapping("/allExpenses")
    public ResponseEntity<ResponseWrapper<List<ExpenseResponse>>> getAllExpense(HttpServletRequest request) {
        ResponseWrapper<List<ExpenseResponse>> response = new ResponseWrapper<>();
        try {
            Long id = (Long) request.getAttribute("userId");
            if (expenseService.getAllExpense(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Expense retrieved successfully");
                response.setSuccess(true);
                response.setResponse(expenseService.getAllExpense(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Expenses not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ResponseWrapper<ExpenseDto>> getExpenseById(@PathVariable("expenseId") long expenseId, HttpServletRequest request) {

        ResponseWrapper<ExpenseDto> response = new ResponseWrapper<>();

        Long userId = (Long) request.getAttribute("userId");
        ExpenseDto expense = expenseService.getExpenseById(expenseId, userId);
        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setMessage("expense retrieved successfully");
        response.setResponse(expense);
        return ResponseEntity.ok(response);


    }

    @GetMapping("/name/{expenseName}")
    public ResponseEntity<ResponseWrapper<List<Expense>>> getExpenseByName(@PathVariable("expenseName") String expenseName) {
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
    public ResponseEntity<ResponseWrapper<ExpenseResponse>> addExpense(HttpServletRequest request, @RequestBody ExpenseDto expenseDto) {
        ResponseWrapper<ExpenseResponse> response = new ResponseWrapper<>();

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


    @DeleteMapping( "/{expenseId}")
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

    @PutMapping("/{expenseId}")
    public ResponseEntity<ResponseWrapper<ExpenseResponse>> updateExpense(@PathVariable("expenseId") Long oldExpenseId, @RequestBody ExpenseDto expenseDto) {
        ResponseWrapper<ExpenseResponse> response = new ResponseWrapper<>();
        try {
            ExpenseResponse updateExpense = expenseService.updateExpense(oldExpenseId, expenseDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income updated successfully");
            response.setResponse(updateExpense);
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/expenseData")
    public ResponseEntity<ResponseWrapper<List<ExpenseSummary>>> getData(HttpServletRequest request){
        ResponseWrapper<List<ExpenseSummary>> response = new ResponseWrapper<>();
        try{
            Long id = (Long) request.getAttribute("userId");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Data retrieved Successfully");
            response.setResponse(expenseService.getData(id));
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/totalExpense")
    public ResponseEntity<ResponseWrapper<Double>> getTotalAmount(HttpServletRequest request){
        ResponseWrapper<Double> response = new ResponseWrapper<>();
        try{
            Long id = (Long) request.getAttribute("userId");
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Data retrieved Successfully");
            Double totalAmount = expenseService.getTotalExpenseAmount(id);
            response.setResponse(totalAmount);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("ERROR");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    @GetMapping("/totalExpense/{expenseCategoryId}")
//    public ResponseEntity<ResponseWrapper<Double>> getTotalCategoryAmount(@PathVariable Long expenseCategoryId, HttpServletRequest request){
//        ResponseWrapper<Double> response = new ResponseWrapper<>();
//        try{
//            Long id = (Long) request.getAttribute("userId");
//            if(expenseService.getTotalCategoryAmount(id, expenseCategoryId).isEmpty()) {
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setSuccess(true);
//                response.setMessage("Data retrieved Successfully");
//                Double totalAmount = 0.0;
//                response.setResponse(totalAmount);
//                return ResponseEntity.ok(response);
//            }else {
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setSuccess(true);
//                response.setMessage("Data retrieved Successfully");
//                Optional<Double> totalAmount = expenseService.getTotalCategoryAmount(id, expenseCategoryId);
//                response.setResponse(totalAmount);
//                return ResponseEntity.ok(response);
//            }
//        }catch(Exception e){
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setMessage("ERROR");
//            response.setSuccess(false);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }


}
