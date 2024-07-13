package com.dreamwheels.dreamwheels.products.repository;

import com.dreamwheels.dreamwheels.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
