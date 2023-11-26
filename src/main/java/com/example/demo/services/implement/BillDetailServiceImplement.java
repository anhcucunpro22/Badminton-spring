package com.example.demo.services.implement;

import com.example.demo.models.Bill;
import com.example.demo.models.BillDetail;
import com.example.demo.models.Product;
import com.example.demo.models.keys.BillDetailKey;
import com.example.demo.repositories.BillDetailsRepository;
import com.example.demo.services.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BillDetailServiceImplement implements BillDetailService {
	//implement của interface BillDetailService. Lớp này triển khai các phương thức được định nghĩa trong BillDetailService 
	//bằng cách sử dụng các phương thức từ UserRepository để thực hiện các thao tác liên quan đến cơ sở dữ liệu.
	
    @Autowired //được sử dụng để ghi đè (override) một phương thức từ lớp cha trong một lớp con, 
    //đảm bảo rằng phương thức trong lớp con thực sự đang ghi đè phương thức tương ứng trong lớp cha.
    BillDetailsRepository billDetailsRepository;
    @Override
    public Optional<BillDetail> findBillDetailsByProductIdAndBillId(Product productId, Bill billId) {
        return billDetailsRepository.findBillDetailsByProductIdAndBillId(productId, billId);
    }

    @Override
    public List<BillDetail> findAllByBillId(Bill billId) {
        return billDetailsRepository.findAllByBillId(billId);
    }

    @Override
    public void saveBillDetail(BillDetail billDetail) {
        billDetailsRepository.save(billDetail);
    }

    @Override
    public void removeBillDetail(BillDetailKey billDetailKey) {
        billDetailsRepository.deleteById(billDetailKey);
    }

    @Override
    public List<BillDetail> findAll() {
        return billDetailsRepository.findAll();
    }

    @Override
    public Page<BillDetail> PagingAllBillDetail(int offset, int pageSize) {
        return billDetailsRepository.findAll(PageRequest.of(offset,pageSize, Sort.by("billId").descending()));
    }

    @Override
    public void deleteBillDetail(Bill bill, Product product) {
        billDetailsRepository.deleteBillDetailByBillIdAndProductId(bill, product);
    }
}
