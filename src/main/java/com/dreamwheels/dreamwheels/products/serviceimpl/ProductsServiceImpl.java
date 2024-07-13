package com.dreamwheels.dreamwheels.products.serviceimpl;

import com.dreamwheels.dreamwheels.products.entity.Product;
import com.dreamwheels.dreamwheels.products.service.ProductsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Override
    public List<Product> getProducts() {
        return List.of();
    }
}
