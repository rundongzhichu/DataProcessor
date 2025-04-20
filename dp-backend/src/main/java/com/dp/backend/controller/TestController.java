package com.dp.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name="TestsController", description = "This Controller is just for function test")
@Controller
public class TestController {

    @Operation(summary = "get hello string")
    @GetMapping(value = "/hello/{name}")
    @ResponseBody
    public String hello(@PathVariable(value = "name") String name){
        return "Hello " + name;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }


}
