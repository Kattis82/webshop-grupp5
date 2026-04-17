package se.iths.kattis.webshopgrupp5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.kattis.webshopgrupp5.exception.OrderNotFoundException;
import se.iths.kattis.webshopgrupp5.model.Order;
import se.iths.kattis.webshopgrupp5.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // metod för att visa orderbekräftelse
    @GetMapping("/confirmation/{orderId}")
    public String showConfirmation(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order hittades inte: " + orderId);
        }
        model.addAttribute("order", order);
        return "confirmation";
    }


}
