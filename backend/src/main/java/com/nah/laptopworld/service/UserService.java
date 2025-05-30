package com.nah.laptopworld.service;

import com.nah.laptopworld.model.*;
import com.nah.laptopworld.repository.*;
import com.nah.laptopworld.dto.response.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final OrderRepository orderRepository;
    private final LaptopModelRepository laptopModelRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;


    public Page<User> getAllUser(String fullName, String roleDes, Pageable pageable) {
        return this.userRepository.filterUserByNameAndEmailAndRoleDes(fullName, roleDes, pageable);
    }

    public List<User> getListUser() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUserByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User getUserById(long id) {
        return this.userRepository.findFirstById(id);
    }

    public void deleteAUser(long id){
        this.forgotPasswordRepository.deleteByUserId(id);

        Cart cart = this.cartRepository.findByUser(this.userRepository.findFirstById(id));
        List<CartDetail> cartDetails = cart.getCartDetails();
        if(cartDetails != null){
            for (CartDetail cartDetail : cartDetails){
                this.cartDetailRepository.deleteById(cartDetail.getId());
            }
        }
        this.cartRepository.deleteById(cart.getId());


        List<Order> orders = this.orderRepository.findByUser(this.userRepository.findFirstById((id)));
        for(Order order : orders){
                this.orderDetailRepository.deleteAll(order.getOrderDetails());

                this.orderRepository.deleteById(order.getId());
            }

        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User handleSaveUser(User user) {
        User savedUser = this.userRepository.save(user);
        return savedUser;
    }

    public User registerDTOtoUser(RegisterResponse registerResponse) {
        User user = new User();
        user.setFullName(registerResponse.getFirstName() + " " + registerResponse.getLastName());
        user.setEmail(registerResponse.getEmail());
        user.setPassword(registerResponse.getPassword());
        return user;
    }

    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUsers() {
        return this.userRepository.count();
    }
    public long countOrders() {
        return this.orderRepository.count();
    }

    public long countProducts() {
        return this.laptopModelRepository.count();
    }

    public void updatePassword(String email, String password) {
        this.userRepository.updatePassword(email, password);
    }
}
