package com.example.spring;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
@RequestMapping("/")
    public String hello() {
        return "Hello World!";
    }
    @RequestMapping("/{name}")
    public String helloPerson(@PathVariable String name) {
        return "hello " + name;
    }

    @RequestMapping("/hello/")
    public String helloPersonAge(@RequestParam String name, @RequestParam Integer age) {
        return "hello " + name + " your age is " + age;
    }
}
