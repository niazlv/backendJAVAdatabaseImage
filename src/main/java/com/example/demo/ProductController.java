package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/speculation")
public class ProductController {
    @GetMapping("/simple")
    public String simple(){
        return "Легкий GET запросик";
    }
}
