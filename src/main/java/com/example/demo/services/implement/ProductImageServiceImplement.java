package com.example.demo.services.implement;

import com.example.demo.models.Product;
import com.example.demo.models.ProductImage;
import com.example.demo.repositories.ProductImageRepository;
import com.example.demo.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Service
public class ProductImageServiceImplement implements ProductImageService {

    @Autowired
    ProductImageRepository productImageRepository;

    @Override
    public List<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public List<ProductImage> getAllImageOfProduct(Product product) {
        return productImageRepository.findAllByProduct(product);
    }

    @Override
    public Page<ProductImage> PagingAllProductImg(int offset, int pageSize) {
        return productImageRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by("id").descending()));
    }

    @Override
    public void save(ProductImage productImage) {
        productImageRepository.save(productImage);
    }

    @Override
    public ProductImage getProductImgById(Integer id) {
        return productImageRepository.getById(id);
    }

    @Override
    public void deleteProductImg(int id) {
        productImageRepository.deleteProductImageById(id);
    }
}
