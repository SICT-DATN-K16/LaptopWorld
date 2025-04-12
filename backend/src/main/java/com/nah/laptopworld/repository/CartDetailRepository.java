package com.nah.laptopworld.repository;

import com.nah.laptopworld.model.Cart;
import com.nah.laptopworld.model.CartDetail;
import com.nah.laptopworld.model.LaptopVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndLaptopVariant(Cart cart, LaptopVariant laptopVariant);

    CartDetail findByCartAndLaptopVariant(Cart cart, LaptopVariant laptopVariant);
}
