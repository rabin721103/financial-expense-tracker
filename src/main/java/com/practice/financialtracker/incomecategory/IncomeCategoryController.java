package com.practice.financialtracker.incomecategory;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
import com.practice.financialtracker.expensecategory.ExpenseCategoryDto;
import com.practice.financialtracker.expensecategory.ExpenseCategoryResponse;
import com.practice.financialtracker.model.Income;
import com.practice.financialtracker.model.User;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes/category")
public class IncomeCategoryController {
    private final IncomeCategoryService incomeCategoryService;

    public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }
    @GetMapping("/allCategories")
    public ResponseEntity<ResponseWrapper<List<IncomeCategoryResponse>>> getAllIncomeCategories() {
        ResponseWrapper<List<IncomeCategoryResponse>> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income Categories retrieved successfully");
            response.setResponse( incomeCategoryService.getAllIncomeCategory());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<List<IncomeCategoryResponse>>> getAllIncomeCategoriesByUserId(@PathVariable Long userId) {
        ResponseWrapper<List<IncomeCategoryResponse>> response = new ResponseWrapper<>();
        try {
            // Assuming you have a method in your service to get income categories by user ID
            List<IncomeCategoryResponse> incomeCategories = incomeCategoryService.getAllIncomeCategoriesByUserId(userId);

            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income Categories retrieved successfully");
            response.setResponse(incomeCategories);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping()
    public ResponseEntity<ResponseWrapper<IncomeCategoryDto>> addIncomeCategory(HttpServletRequest request, @RequestBody IncomeCategoryDto incomeCategoryDto){
        ResponseWrapper<IncomeCategoryDto> response = new ResponseWrapper<>();

        try {
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);

            // Check if the expense category with the given name already exists
            IncomeCategory existingCategory = incomeCategoryService.findIncomeCategoryByName(incomeCategoryDto.getCategoryName());
            if (existingCategory != null) {
                // Category with the same name already exists, handle accordingly
                response.setStatusCode(HttpStatus.BAD_REQUEST.value());
                response.setSuccess(false);
                response.setMessage("Income Category with this name already exists");
                return ResponseEntity.badRequest().body(response);
            }
            // If the category doesn't exist, proceed to create and save it
            IncomeCategory newIncomeCategory = new IncomeCategory(user, incomeCategoryDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setResponse(incomeCategoryService.addIncomeCategory(newIncomeCategory));
            response.setSuccess(true);
            response.setMessage("Income Category added successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
}
