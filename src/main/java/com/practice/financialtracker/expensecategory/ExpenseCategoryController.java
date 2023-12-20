package com.practice.financialtracker.expensecategory;

import com.practice.financialtracker.model.User;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses/category")
public class ExpenseCategoryController {
    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }
    @GetMapping("/allCategories")
    public ResponseEntity<ResponseWrapper<List<ExpenseCategoryResponse>>> getAllExpenseCategories() {
        ResponseWrapper<List<ExpenseCategoryResponse>> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Expense Categories retrieved successfully");
            response.setResponse( expenseCategoryService.getAllExpenseCategory());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping()
    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> addExpenseCategory(HttpServletRequest request, @RequestBody ExpenseCategoryDto expenseCategoryDto){
        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();
        try{
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);
            ExpenseCategory newExpenseCategory = new ExpenseCategory(user, expenseCategoryDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Expense Category added successfully");
            response.setResponse(expenseCategoryService.addExpenseCategory(newExpenseCategory));
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}
