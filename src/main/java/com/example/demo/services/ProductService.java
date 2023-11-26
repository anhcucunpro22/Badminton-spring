package com.example.demo.services;

import com.example.demo.models.Category;
import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List<Product> findAllByCategory(Category category);

    Page<Product> PagingAllProduct(int offset, int pageSize);

    Slice<Product> PagingProductByCategory(int offset, int pageSize, Category category);

    Product getProductById(Integer id);

    List<Product> getProductSameCategory(Category category, Integer id);

    List<Product> findProductByName(String name, int offset, int pagesize);

    List<Product> findAllProductByName(String name);

    List<Product> getTop8Product();

    List<Product> getLast4Product();

    List<Product> getTop4ProductBestSeller();

    void saveProduct(Product product);

    void deleteProduct(int id);
}
