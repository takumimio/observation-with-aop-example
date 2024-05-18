package com.example.demo.controller;

import com.example.demo.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @Autowired
    FooService fooService;

    @GetMapping("/")
    public void test() {
        fooService.foo();

    }
}
