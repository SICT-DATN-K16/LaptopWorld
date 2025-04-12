package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.Cart;
import com.nah.laptopworld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByUser(User user);
}
