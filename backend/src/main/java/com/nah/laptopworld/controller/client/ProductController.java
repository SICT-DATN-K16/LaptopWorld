package com.nah.laptopworld.controller.client;

import com.nah.laptopworld.dto.response.LaptopDetailResponse;
import com.nah.laptopworld.model.*;
import com.nah.laptopworld.dto.request.CartRequest;
import com.nah.laptopworld.dto.response.ProductCriteriaResponse;
import com.nah.laptopworld.repository.CartRepository;
import com.nah.laptopworld.service.ProductService;
import com.nah.laptopworld.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController("clientProductController")
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final CartRepository cartRepository;

    @GetMapping("/laptop/{variantId}")
    public ResponseEntity<LaptopDetailResponse> getLaptopDetails(@PathVariable Long variantId) {
        LaptopDetailResponse response = productService.getLaptopDetails(variantId);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable long id) {
        Optional<LaptopModel> laptopModelOptional = this.productService.fetchProductById(id);
        
        if (!laptopModelOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        LaptopModel laptopModel = laptopModelOptional.get();
        List<Map<String, Object>> productCountByBrand = this.productService.countProductsByBrand();
        List<Comment> comments = laptopModel.getComments();
        
        // Tính trung bình đánh giá
        double totalRate = 0;
        for(Comment comment : comments){
            totalRate += comment.getRate();
        }
        double avgRate = totalRate != 0 ? totalRate / comments.size() : 0;
        
        // Kiểm tra giỏ hàng
        Integer cartQuantity = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String email = authentication.getName();
            User user = this.userService.getUserByEmail(email);
            if (user != null) {
                Cart cart = this.cartRepository.findByUser(user);
                if (cart != null) {
                    List<CartDetail> cartDetails = cart.getCartDetails();
                    for (CartDetail cd : cartDetails) {
                        if (cd.getLaptopVariant().getLaptopModel().getId() == id) {
                            cartQuantity = (int) cd.getQuantity();
                            break;
                        }
                    }
                }
            }
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("product", laptopModel);
        response.put("productImages", laptopModel.getProductImages());
        response.put("productCountByBrand", productCountByBrand);
        response.put("comments", comments);
        response.put("avgRate", avgRate);
        if (cartQuantity != null) {
            response.put("cartQuantity", cartQuantity);
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/add/{id}")
    public ResponseEntity<?> addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        this.productService.handleAddProductToCart(email, id, session, 1);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sản phẩm đã được thêm vào giỏ hàng");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart")
    public ResponseEntity<Map<String, Object>> getCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        long id = (long) session.getAttribute("id");
        User currentUser = new User();
        currentUser.setId(id);
        
        Cart cart = this.productService.fetchCartByUser(currentUser);
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("cartDetails", cartDetails);
        response.put("totalPrice", totalPrice);
        response.put("cart", cart);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> deleteCartProduct(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        this.productService.handleRemoveCartDetail(id, session);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sản phẩm đã được xóa khỏi giỏ hàng");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkout")
    public ResponseEntity<Map<String, Object>> getCheckout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        long id = (long) session.getAttribute("id");
        User currentUser = new User();
        currentUser.setId(id);
        
        Cart cart = this.productService.fetchCartByUser(currentUser);
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        
        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("cartDetails", cartDetails);
        response.put("totalPrice", totalPrice);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cart/update")
    public ResponseEntity<?> updateCart(@RequestBody Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Giỏ hàng đã được cập nhật");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(
            @RequestBody Map<String, String> orderRequest,
            HttpServletRequest request) {
            
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        if (session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        long id = (long) session.getAttribute("id");
        User currentUser = new User();
        currentUser.setId(id);
        
        String receiverName = orderRequest.get("receiverName");
        String receiverAddress = orderRequest.get("receiverAddress");
        String receiverPhone = orderRequest.get("receiverPhone");
        
        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Đặt hàng thành công");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/add-with-quantity")
    public ResponseEntity<?> addProductWithQuantity(
            @RequestBody CartRequest cartRequest,
            HttpServletRequest request) {
            
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        this.productService.handleAddProductToCart(email, cartRequest.getProductId(), session, cartRequest.getQuantity());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sản phẩm đã được thêm vào giỏ hàng");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getProducts(
            ProductCriteriaResponse productCriteriaResponse) {
            
        int page = 1;
        if (productCriteriaResponse.getPage() != null && productCriteriaResponse.getPage().isPresent()) {
            try {
                page = Integer.parseInt(productCriteriaResponse.getPage().get());
            } catch (NumberFormatException e) {
                // Giữ page = 1 nếu có lỗi
            }
        }
        
        // Xử lý sắp xếp
        Pageable pageable = PageRequest.of(page - 1, 6);
        if (productCriteriaResponse.getSort() != null && productCriteriaResponse.getSort().isPresent()) {
            String sort = productCriteriaResponse.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 6, Sort.by("price").ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 6, Sort.by("price").descending());
            }
        }
        
        Page<LaptopModel> prs = this.productService.fetchProductsWithSpec(pageable, productCriteriaResponse);
        List<LaptopModel> laptopModels = prs.getContent().size() > 0 ? prs.getContent() : new ArrayList<LaptopModel>();
        
        Map<String, Object> response = new HashMap<>();
        response.put("products", laptopModels);
        response.put("currentPage", page);
        response.put("totalPages", prs.getTotalPages());
        response.put("totalItems", prs.getTotalElements());
        response.put("criteria", productCriteriaResponse);
        
        return ResponseEntity.ok(response);
    }
}

