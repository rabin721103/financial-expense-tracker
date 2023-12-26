package com.practice.financialtracker.controller;

import com.practice.financialtracker.exceptions.CustomException;
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
    public ResponseEntity<ResponseWrapper<List<IncomeResponse>>> getAllIncomes(HttpServletRequest request) {
        ResponseWrapper<List<IncomeResponse>> response = new ResponseWrapper<>();
        try {
            Long id = (Long) request.getAttribute("userId");
            if (incomeService.getAllIncomes(id) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Incomes retrieved successfully");
                response.setSuccess(true);
                response.setResponse(incomeService.getAllIncomes(id));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Incomes not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{incomeId}")
    public ResponseEntity<ResponseWrapper<IncomeDto>> getIncomeById(@PathVariable("incomeId") long incomeId, HttpServletRequest request) {

        ResponseWrapper<IncomeDto> response = new ResponseWrapper<>();

        Long userId = (Long) request.getAttribute("userId");
        IncomeDto income = incomeService.getIncomeById(incomeId, userId);
        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setMessage("income retrieved successfully");
        response.setResponse(income);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ResponseWrapper<IncomeResponse>> addIncome(HttpServletRequest request, @RequestBody IncomeDto incomeDto) {
        ResponseWrapper<IncomeResponse> response = new ResponseWrapper<>();
        try {
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);
            Income newIncome = new Income(user, incomeDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income added successfully");
            response.setResponse(incomeService.addIncome(newIncome));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

//    @PutMapping( "/{incomeId}")
//    public ResponseEntity<Income> updateIncome(@PathVariable("incomeId") Long oldIncomeId, @RequestBody Income income) {
//        ResponseWrapper<Income> response = new ResponseWrapper<>();
//        try {
//            Income newIncome = incomeService.updateIncome(oldIncomeId, income).getBody();
//            if (newIncome != null) {
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setSuccess(true);
//                response.setMessage("Income updated successfully");
//                response.setResponse(newIncome);
//                return ResponseEntity.ok(response.getResponse());
//            } else {
//                response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                response.setMessage("User not Found");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getResponse());
//            }
//
//        } catch (Exception e) {
//            response.setStatusCode(HttpStatus.NOT_FOUND.value());
//            response.setMessage("Internal Server Error");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getResponse());
//        }
//    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<ResponseWrapper<IncomeResponse>> updateIncome(@PathVariable("incomeId") Long oldIncomeId, @RequestBody Income income) {
        ResponseWrapper<IncomeResponse> response = new ResponseWrapper<>();
        System.out.println("sdafdah");
        try {
            IncomeResponse updatedIncome = incomeService.updateIncome(oldIncomeId, income);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Income updated successfully");
            response.setResponse(updatedIncome);
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
