package com.nah.laptopworld.service;

import com.nah.laptopworld.dto.response.LaptopDetailResponse;
import com.nah.laptopworld.model.*;
import com.nah.laptopworld.repository.*;
import com.nah.laptopworld.dto.response.ProductCriteriaResponse;
import com.nah.laptopworld.service.specification.ProductSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final LaptopModelRepository laptopModelRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    private LaptopVariantRepository laptopVariantRepository;

    public LaptopDetailResponse getLaptopDetails(Long variantId) {
        Optional<LaptopVariant> variantOptional = laptopVariantRepository.findById(variantId);
        if (variantOptional.isPresent()) {
            LaptopVariant variant = variantOptional.get();
            LaptopModel model = variant.getLaptopModel();
            
            LaptopDetailResponse response = new LaptopDetailResponse();
            
            // Set model attributes
            response.setModelId(model.getId());
            response.setModelName(model.getName());
            response.setDetailDesc(model.getDetailDesc());
            response.setShortDesc(model.getShortDesc());
            response.setTarget(model.getTarget());
            response.setStatus(model.isStatus());
            response.setBrandName(model.getBrand().getName());
            response.setProductImages(model.getProductImages().stream()
                .map(ProductImage::getImage)
                .collect(Collectors.toList()));
            response.setComments(model.getComments());
            
            // Set variant attributes
            response.setVariantId(variant.getId());
            response.setVariantName(variant.getVariantName());
            response.setQuantity(variant.getQuantity());
            response.setSold(variant.getSold());
            response.setPrice(variant.getPrice());
            response.setProcessorBrand(variant.getProcessorBrand());
            response.setProcessor(variant.getProcessor());
            response.setRam(variant.getRam());
            response.setGraphicCardBrand(variant.getGraphicCardBrand());
            response.setGraphicCard(variant.getGraphicCard());
            response.setStorage(variant.getStorage());
            response.setDisplay(variant.getDisplay());
            response.setWeight(variant.getWeight());
            
            return response;
        }
        return null;
    }

    public ProductService(LaptopModelRepository laptopModelRepository,
                          CartRepository cartRepository,
                          CartDetailRepository cartDetailRepository,
                          UserService userservice, 
                          LaptopVariantRepository laptopVariantRepository,
                          UserService userService,
                          OrderRepository orderRepository,
                          OrderDetailRepository orderDetailRepository) {
        this.laptopModelRepository = laptopModelRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.laptopVariantRepository = laptopVariantRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public LaptopModel handleSaveProduct(LaptopModel laptopModel){
        LaptopModel savedLaptopModel = this.laptopModelRepository.save(laptopModel);
        return savedLaptopModel;
    }

    public Optional<LaptopModel> fetchProductById(long id) {
        return this.laptopModelRepository.findById(id);
    }

    public void deleteProductById(long id) {
        this.laptopModelRepository.deleteById(id);
    }




    public Page<LaptopModel> fetchProductsWithSpec(Pageable page, ProductCriteriaResponse productCriteriaResponse) {
        if(productCriteriaResponse.getTarget() == null
                && productCriteriaResponse.getBrand() == null
                && productCriteriaResponse.getPrice() == null){
            return this.laptopModelRepository.findAll(page);
        }

        Specification<LaptopModel> combinedSpec = Specification.where(null);
        if (productCriteriaResponse.getTarget() != null && productCriteriaResponse.getTarget().isPresent()) {
            Specification<LaptopModel> currentSpecs = ProductSpecs.matchListTarget(productCriteriaResponse.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        if (productCriteriaResponse.getBrand() != null && productCriteriaResponse.getBrand().isPresent()) {
            Specification<LaptopModel> currentSpecs = ProductSpecs.matchListBrand(productCriteriaResponse.getBrand().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        if(productCriteriaResponse.getPrice() != null && productCriteriaResponse.getPrice().isPresent()){
            Specification<LaptopModel> currentSpecs = this.buildPriceSpecification(productCriteriaResponse.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        return this.laptopModelRepository.findAll(combinedSpec, page);
    }


    public Specification<LaptopModel>  buildPriceSpecification(List<String> price) {
        Specification<LaptopModel> combinedSpec = Specification.where(null);
        for (String p : price){
            double min = 0;
            double max = 0;
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-toi-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-toi-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 500000000;
                    break;
            }
            if(min != 0 && max != 0){
                Specification<LaptopModel> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }

        return combinedSpec;
    }

    public Page<LaptopModel> fetchProducts(Pageable page) {
        return this.laptopModelRepository.findAll(page);
    }


    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {

        User user = this.userService.getUserByEmail(email);

        if(user != null){
            //check user đâ có Cart chưa? nếu chưa thì tạo mới
            Cart cart = this.cartRepository.findByUser(user);

            if(cart == null){
                // tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            //lưu cart-detail

            Optional<LaptopVariant> laptopVariantOptional = this.laptopVariantRepository.findById(productId);
            if(laptopVariantOptional.isPresent()){
                LaptopVariant realLaptopVariant = laptopVariantOptional.get();
                //check xem product đã có trong cart chưa

                CartDetail oldDetail = this.cartDetailRepository.findByCartAndLaptopVariant(cart, realLaptopVariant);

                if(oldDetail == null){
                    CartDetail cd =  new CartDetail();
                    cd.setCart(cart);
                    cd.setLaptopVariant(realLaptopVariant);
                    cd.setPrice(realLaptopVariant.getPrice());
                    cd.setQuantity(quantity);
                    this.cartDetailRepository.save(cd);

                    //update cart sum

                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                }else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }


            }
        }


    }

    public Cart fetchCartByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());

            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session,
                                 String receiverName, String receiverAddress, String receiverPhone){

        // step1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null){
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null){

                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double totalPrice = 0;
                for(CartDetail cartDetail : cartDetails){
                    totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
                }
                order.setTotalPrice(totalPrice);
                order = this.orderRepository.save(order);
                // create order detail
                for (CartDetail cartDetail : cartDetails){
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setLaptopVariant(cartDetail.getLaptopVariant());
                    orderDetail.setPrice(cartDetail.getPrice());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    this.orderDetailRepository.save(orderDetail);

                    LaptopVariant laptopVariant = cartDetail.getLaptopVariant();
                    laptopVariant.setQuantity(laptopVariant.getQuantity() - cartDetail.getQuantity());
                    this.laptopVariantRepository.save(laptopVariant);
                }

                //step 2: delete cart_detail and cart
                for (CartDetail cartDetail : cartDetails){
                    this.cartDetailRepository.deleteById(cartDetail.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                //step 3: update session sum

                session.setAttribute("sum", 0);
            }
        }

    }

    public List<LaptopModel> searchProduct(String keyword) {
        return laptopModelRepository.findByNameContaining(keyword);
    }

    public List<Map<String, Object>> countProductsByBrand() {
        return laptopModelRepository.countProductsByBrand();
    }

    public Page<LaptopModel> getAllProducts(String name, String brand, Pageable pageable) {
        return laptopModelRepository.filterProductByNameAndBrand(name, brand, pageable);
    }

    public double getAvgRate(LaptopModel laptopModel) {
        List<Comment> comments = laptopModel.getComments();
        double totalRate = 0;
        int count = 0;
        for(Comment comment : comments){
            totalRate += comment.getRate();
            count++;
        }
        double avgRate = totalRate != 0 ? totalRate / count : 0;
        return avgRate;
    }




}
