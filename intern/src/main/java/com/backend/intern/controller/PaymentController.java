package com.backend.intern.controller;


import com.backend.intern.model.Transaction;
import com.backend.intern.service.PaymentService;
import com.backend.intern.service.impl.PaymenyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@ControllerAdvice
@RequestMapping(value = "/transactions")
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/payment")
    public String payment(){
        return paymentService.doPayment();
    }

}
