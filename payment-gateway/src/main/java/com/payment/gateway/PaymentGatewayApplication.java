package com.payment.gateway;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@SpringBootApplication
@RestController
public class PaymentGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentGatewayApplication.class, args);
	}

//	@CrossOrigin(origins="http://localhost:8080/")
	@PostMapping("/createOrder")
	public String createOrder(@RequestParam Integer amount) throws RazorpayException {
		
		
		RazorpayClient razorpay = new RazorpayClient("rzp_test_67rbte7z6mFF63", "qdHTHWTbo9YEP7eRcTSQxQv0");

		int amt = (int)amount * 100;
		
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",amt);
		orderRequest.put("currency","INR");
		orderRequest.put("receipt", "receipt#1");
		JSONObject notes = new JSONObject();
		notes.put("notes_key_1","Tea, Earl Grey, Hot");
		orderRequest.put("notes",notes);

		Order order = razorpay.Orders.create(orderRequest);
		
		return order.toString();
	}
	
	
	// for cross origin request allowance
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry cors) {
				cors.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
