package com.example.demo.services;

import com.example.demo.models.Bill;
import com.example.demo.models.BillDetail;
import com.example.demo.models.Product;
import com.example.demo.models.keys.BillDetailKey;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BillDetailService {
    Optional<BillDetail> findBillDetailsByProductIdAndBillId(Product productId, Bill billId);
    List<BillDetail> findAllByBillId(Bill billId);

    void saveBillDetail(BillDetail billDetail);

    void removeBillDetail(BillDetailKey billDetailKey);

    List<BillDetail> findAll();

    Page<BillDetail> PagingAllBillDetail(int offset, int pageSize);

    void deleteBillDetail(Bill bill, Product product);
}
