package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
	
	 private static final String PATH = "/error"; // Default error path

	    @RequestMapping(PATH)
	    public String handleError(Model model) {
			Object errorMessage = model.getAttribute("javax.servlet.error.message");
	        return "error";
	    }

	    public String getErrorPath() {
	        return PATH;
	    }
}