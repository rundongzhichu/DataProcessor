package com.shichi.history.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

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
