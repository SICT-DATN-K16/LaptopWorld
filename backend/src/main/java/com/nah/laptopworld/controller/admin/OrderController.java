package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.model.Order;
import com.nah.laptopworld.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(name = "status", required = false) String status) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("createdAt").descending());
        Page<Order> orders = this.orderService.getAllOrders(status, pageable);

        double totalAmount = this.orderService.getTotalAmountByMonth();

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orders.getContent());
        response.put("currentPage", page);
        response.put("totalPages", orders.getTotalPages());
        response.put("totalItems", orders.getTotalElements());
        response.put("totalAmount", totalAmount);
        response.put("status", status);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable("id") long id) {
        Optional<Order> orderOptional = this.orderService.fetchOrderById(id);

        if (!orderOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();

        Map<String, Object> response = new HashMap<>();
        response.put("order", order);
        response.put("orderDetails", order.getOrderDetails());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable long id, @RequestBody Order order) {
        order.setId(id);
        this.orderService.updateOrder(order);

        Optional<Order> updatedOrderOptional = this.orderService.fetchOrderById(id);
        if (!updatedOrderOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedOrderOptional.get());
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        Optional<Order> orderOptional = this.orderService.fetchOrderById(id);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        this.orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics/sales")
    public ResponseEntity<List<Map<String, Object>>> getSalesStatistics(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Map<String, Object>> statistics = orderService.getSalesStatistics(startDate, endDate);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/statistics/sales-by-brand")
    public ResponseEntity<List<Map<String, Object>>> getSalesStatisticsByBrand(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Map<String, Object>> statistics = orderService.getSalesStatisticsByBrand(startDate, endDate);
        return ResponseEntity.ok(statistics);
    }
}
