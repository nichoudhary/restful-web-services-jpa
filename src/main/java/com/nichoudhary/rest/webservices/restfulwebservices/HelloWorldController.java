package com.nichoudhary.rest.webservices.restfulwebservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // HTTP Method ->GET
    // URI -> /hello-world
    // method
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    /*@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }*/
}
