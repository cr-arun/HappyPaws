package com.example.project.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.project.dto.AddressDto;
import com.example.project.model.Order;
import com.example.project.model.OrderItem;
import com.example.project.model.Product;
import com.example.project.model.UserModel;
import com.example.project.service.OrderService;
import com.example.project.service.UserService;

@SpringBootTest(classes = { OrderControllerTest.class })
class OrderControllerTest {

	@Mock
	OrderService orderService;
	@Mock
	UserService userService;
	@InjectMocks
	OrderController orderController;

	UserModel user;
	Product product;
	AddressDto address;
	List<OrderItem> listitems;
	Order order;

	@BeforeEach
	void setup() {
		user = new UserModel((long) 1, "arun@gmail.com", "encryped password", "Arun", "9487550114", false, "User");

		product = new Product();
		product.setProductid(1);
		product.setProductName("Labrador");
		product.setDescription("Description");
		product.setImageUrl("url");
		product.setPrice(2000);
		product.setQuantity(30);

		address = new AddressDto("houseno", "street", "city", "state", "pincode");

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setPrice(product.getPrice());
		item.setQuantity(2);

		listitems = new ArrayList<>();
		listitems.add(item);

		order = new Order();
		order.setId(1);
		order.setAddress(address.toString());
		order.setOrderItems(listitems);

	}

	@Test
	void placeOrderTest() {
		when(userService.getOneUser(1)).thenReturn(user);
		when(orderService.placeOrder(user, address)).thenReturn(order);
		assertEquals(order, orderController.placeOrder(address, 1).getBody());
		assertEquals(200, orderController.placeOrder(address, 1).getStatusCodeValue());
	}

	@Test
	void orderList() {
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		when(userService.getOneUser(1)).thenReturn(user);
		when(orderService.listOrder(user)).thenReturn(orders);
		assertEquals(orders, orderController.orderList(1).getBody());
		assertEquals(200, orderController.orderList(1).getStatusCodeValue());
	}

	@Test
	void oneOrderTest() {
		when(orderService.getOneOrder(1)).thenReturn(order);
		assertEquals(order, orderController.oneOrder(1).getBody());
		assertEquals(200, orderController.oneOrder(1).getStatusCodeValue());
	}

	@Test
	void allOrdersTest() {
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		when(orderService.allOrders()).thenReturn(orders);
		assertEquals(orders, orderController.allOrders().getBody());
		assertEquals(200, orderController.allOrders().getStatusCodeValue());
	}

}
