package bibo.demo.service;
import java.util.List;

import org.springframework.stereotype.Service;

import bibo.demo.Order;
import bibo.demo.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository();
    }

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public Order getOrderById(int orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public void deleteOrder(int orderId) {
        orderRepository.deleteOrder(orderId);
    }
}
