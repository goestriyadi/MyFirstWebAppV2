package com.goesmodul.MyFirstWebbApp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //Tell the Java this one is Controller
public class SayHelloController {
	//"say-hello" => "Hello! What are you learning today?"
	// http://localhost:8080/say-hello
	@RequestMapping("say-hello") //Define the URL 
	@ResponseBody //Show the return in web browser as what returned from the code
	public String sayHello() {
		return "Hello! What are you learning today?";
	}
	
	
		@RequestMapping("say-hello-jsp") //Define the URL 
		public String sayHelloJsp() {
			return "sayHello";
		}
		
		
}
