package com.practice.financialtracker.controller;

import com.practice.financialtracker.model.*;
import com.practice.financialtracker.service.IncomeService;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }
    @GetMapping("/allIncomes")
    public ResponseEntity<ResponseWrapper<List<IncomeResponse>>> getAllIncomes() {
        ResponseWrapper<List<IncomeResponse>> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income retrieved successfully");
            response.setResponse( incomeService.getAllIncome());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping()
    public ResponseEntity<ResponseWrapper<IncomeDto>> addIncome(HttpServletRequest request, @RequestBody IncomeDto incomeDto){
        ResponseWrapper<IncomeDto> response = new ResponseWrapper<>();
        try{
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);
            Income newIncome = new Income(user, incomeDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income added successfully");
            response.setResponse(incomeService.addIncome(newIncome));
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
    @DeleteMapping(path = "/{incomeId}")
    public ResponseEntity<ResponseWrapper<Income>> deleteIncomeById(@PathVariable("incomeId") Long incomeId) {
        try {
            incomeService.deleteIncome(incomeId);
            ResponseWrapper<Income> response = new ResponseWrapper<>();
            response.setSuccess(true);
            response.setStatusCode(200);
            response.setMessage("Income deleted successfully");
            response.setResponse(response.getResponse());
            return ResponseEntity.ok(response);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
