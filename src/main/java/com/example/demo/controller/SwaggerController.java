package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

	@RequestMapping(value = "/")
	public String index() {
		return "redirect:/api/1.0/swagger-ui";
	}
}
