package com.example.demo.repositories;

import com.example.demo.models.Product;
import com.example.demo.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    ProductDetail findByProduct(Product product);

    void deleteProductDetailById(int id);
}
