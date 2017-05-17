package com.aek56.microservice.auth.apis;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("supervisor")
public class SupervisorController {


    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<?> getProtectedGreeting() {
        return ResponseEntity.ok("只有监管单位(supervisor)可以访问的资源");
    }

}