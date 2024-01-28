package com.example.project.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.AddressDto;
import com.example.project.exception.OrderNotExistException;
import com.example.project.model.Cart;
import com.example.project.model.CartItem;
import com.example.project.model.Order;
import com.example.project.model.OrderItem;
import com.example.project.model.UserModel;
import com.example.project.repository.OrderItemsRepo;
import com.example.project.repository.OrderRepo;

@Service
public class OrderService {
	Logger logger = LogManager.getLogger(OrderService.class);
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderItemsRepo orderItemsRepo;
	@Autowired
	private ProductService productService;

	public Order placeOrder(UserModel user, AddressDto address) {

		Cart cart = cartService.getCartByUser(user.getId());

		List<CartItem> cartItemList = cart.getItems();

		Order newOrder = new Order();
		newOrder.setCreatedDate(new Date(System.currentTimeMillis()));
		newOrder.setAddress(address.toString());
		newOrder.setUser(user);
		newOrder.setTotalPrice(cart.getTotal());

		orderRepo.save(newOrder);

		for (CartItem cartItem : cartItemList) {

			OrderItem orderItem = new OrderItem();
			orderItem.setCreatedDate(new Date(System.currentTimeMillis()));
			orderItem.setPrice(cartItem.getProduct().getPrice());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setOrder(newOrder);

			orderItemsRepo.save(orderItem);
			newOrder.getOrderItems().add(orderItem);
			orderRepo.save(newOrder);
			productService.changeQuantity(orderItem.getProduct().getProductid(), orderItem.getQuantity());

		}

		cartService.deleteCart(user.getId());
		logger.info("Order Placed id: {} By {}" , newOrder.getId(),newOrder.getUser().getEmail());
		
		return newOrder;
	}

	public List<Order> listOrder(UserModel user) {

		return orderRepo.findAllByUserOrderByCreatedDateDesc(user);
	}

	public Order getOneOrder(long id) {
		Optional<Order> order = orderRepo.findById(id);
		if (!order.isPresent()) {
			throw new OrderNotExistException("Order id is invalid " + id);
		}
		return order.get();
	}

	public List<Order> allOrders() {
		return orderRepo.findAll();
	}

	public void deleteOrder(long id) {
		Optional<Order> order = orderRepo.findById(id);
		if (!order.isPresent()) {
			throw new OrderNotExistException("Order id is invalid " + id);
		}
		logger.info("Order Cancelled id :{} By {}" , id,order.get().getUser().getEmail());
		orderRepo.delete(order.get());

	}

}
