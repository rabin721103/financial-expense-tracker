package com.practice.financialtracker.expensecategory;

import com.practice.financialtracker.incomecategory.IncomeCategoryResponse;
import com.practice.financialtracker.model.User;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping()
    public ResponseEntity<ResponseWrapper<List<ExpenseCategoryResponse>>> getAllExpenseCategoriesByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        ResponseWrapper<List<ExpenseCategoryResponse>> response = new ResponseWrapper<>();
        try {
            // Assuming you have a method in your service to get income categories by user ID
            List<ExpenseCategoryResponse> expenseCategories = expenseCategoryService.getAllExpenseCategoriesByUserId(userId);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Expense Categories retrieved successfully");
            response.setResponse(expenseCategories);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
//    @PostMapping()
//    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> addExpenseCategory(HttpServletRequest request, @RequestBody ExpenseCategoryDto expenseCategoryDto){
//        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();
//        try{
//            Long decodedUserId = (Long) request.getAttribute("userId");
//            User user = new User();
//            user.setUserId(decodedUserId);
//            ExpenseCategory newExpenseCategory = new ExpenseCategory(user, expenseCategoryDto);
//            response.setStatusCode(HttpStatus.OK.value());
//            response.setResponse(expenseCategoryService.addExpenseCategory(newExpenseCategory));
//            response.setSuccess(true);
//            response.setMessage("Expense Category added successfully");
//            return ResponseEntity.ok(response);
//        }
//        catch(Exception e) {
//            response.setStatusCode(HttpStatus.NOT_FOUND.value());
//            response.setMessage("Internal Server Error");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//        }
//
//    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<ExpenseCategoryDto>> addExpenseCategory(HttpServletRequest request, @RequestBody ExpenseCategoryDto expenseCategoryDto) {
        ResponseWrapper<ExpenseCategoryDto> response = new ResponseWrapper<>();

        try {
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);

            // Check if the expense category with the given name already exists
            ExpenseCategory existingCategory = expenseCategoryService.findExpenseCategoryByName(expenseCategoryDto.getName());
            if (existingCategory != null) {
                // Category with the same name already exists, handle accordingly
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setSuccess(false);
                response.setMessage("Expense Category with this name already exists");
                return ResponseEntity.badRequest().body(response);
            }
            // If the category doesn't exist, proceed to create and save it
            ExpenseCategory newExpenseCategory = new ExpenseCategory(user, expenseCategoryDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setResponse(expenseCategoryService.addExpenseCategory(newExpenseCategory));
            response.setSuccess(true);
            response.setMessage("Expense Category added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
