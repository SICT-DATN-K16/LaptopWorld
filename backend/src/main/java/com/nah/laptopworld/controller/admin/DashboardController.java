package com.nah.laptopworld.controller.admin;

import com.nah.laptopworld.service.OrderService;
import com.nah.laptopworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("countUsers", this.userService.countUsers());
        // Giả sử orderService.countOrders() tồn tại hoặc bạn cần tạo nó
        // stats.put("countOrders", this.orderService.countOrders()); // Tạm comment out
        // do lỗi linter
        // Giả sử productService.countProducts() tồn tại hoặc bạn cần tạo và inject
        // ProductService
        // stats.put("countProducts", this.productService.countProducts()); // Cần
        // inject ProductService
        stats.put("totalAmountCurrentMonth", this.orderService.getTotalAmountByMonth()); // Rõ ràng hơn

        // Có thể thêm các thống kê khác nếu cần

        return ResponseEntity.ok(stats); // Trả về Map trong ResponseEntity
    }
}