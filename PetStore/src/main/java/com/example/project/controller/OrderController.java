package com.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.AddressDto;
import com.example.project.exception.UserNotExistException;
import com.example.project.model.Order;
import com.example.project.model.UserModel;
import com.example.project.service.OrderService;
import com.example.project.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	@PostMapping("/placeorder/{id}")
	public ResponseEntity<Order> placeOrder(@RequestBody AddressDto address, @PathVariable(value = "id") long id)
			throws UserNotExistException {
		UserModel user = userService.getOneUser(id);
		return new ResponseEntity<>(orderService.placeOrder(user, address), HttpStatus.OK);
		

	}

	@GetMapping("/listorder/{id}")
	public ResponseEntity<List<Order>> orderList(@PathVariable(value = "id") long id) throws UserNotExistException {
		UserModel user = userService.getOneUser(id);
		List<Order> orders = orderService.listOrder(user);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/oneorder/{id}")
	public ResponseEntity<Order> oneOrder(@PathVariable(value = "id") long id) {
			return new ResponseEntity<>(orderService.getOneOrder(id), HttpStatus.OK);
	}

	@GetMapping("/allorders")
	public ResponseEntity<List<Order>> allOrders() {
		return new ResponseEntity<>(orderService.allOrders(), HttpStatus.OK);
	}

	@GetMapping("/cancelorder/{id}")
	public void cancelOrder(@PathVariable(value = "id") long id) {
		orderService.deleteOrder(id);
	}
}
