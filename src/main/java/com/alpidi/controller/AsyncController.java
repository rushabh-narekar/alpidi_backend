package com.alpidi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpidi.security.sevices.AsyncService;

@RestController
@RequestMapping("/api/auth")
@EnableAsync
public class AsyncController {
	 @Autowired
	 AsyncService asyncService;
	 @GetMapping("/async")
	 public String asyncCallerMethod() throws InterruptedException 
	 {
	     long start = System.currentTimeMillis();
	     asyncService.asyncMethod();
	     String response = "task completes in :" + (start -   System.currentTimeMillis()) + "milliseconds";
	     return response;
	 }
}
