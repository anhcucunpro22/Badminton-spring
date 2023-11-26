package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.models.ProductImage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> findAll();

    List<ProductImage> getAllImageOfProduct(Product product);

    Page<ProductImage> PagingAllProductImg(int offset, int pageSize);

    void save(ProductImage productImage);

    ProductImage getProductImgById(Integer id);

    void deleteProductImg(int id);
}
