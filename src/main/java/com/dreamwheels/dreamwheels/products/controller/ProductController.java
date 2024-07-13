package com.dreamwheels.dreamwheels.products.controller;

import com.dreamwheels.dreamwheels.products.entity.Product;
import com.dreamwheels.dreamwheels.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/products")
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductsService productsService;

    // get all products page
    @GetMapping({"", "/"})
    public String showProductsList(Model model){
        List<Product> products = productsService.getProducts();
        System.out.println(products);
        model.addAttribute("products", products);
        return "products/index";
    }

    // render add product page
    public String addProduct(){
        return "products/add";
    }

    // method to add product

    // delete product


    // render edit product
}
