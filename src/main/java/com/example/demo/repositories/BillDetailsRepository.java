package com.example.demo.repositories;
import com.example.demo.models.Bill;
import com.example.demo.models.BillDetail;
import com.example.demo.models.Product;
import com.example.demo.models.keys.BillDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillDetailsRepository extends JpaRepository<BillDetail, BillDetailKey> {
	// interface này kế thừa từ JpaRepository và sử dụng BillDetail làm entity và BillDetailKey làm kiểu dữ liệu của khóa chính. 
	// Điều này cho phép interface này sử dụng các phương thức cơ bản của JpaRepository để thao tác với cơ sở dữ liệu liên quan đến đối tượng BillDetail.
	
    Optional<BillDetail> findBillDetailsByProductIdAndBillId(Product productId, Bill billId);
    //phương thức này sẽ tìm kiếm và trả về một đối tượng BillDetail dựa trên productId và billId. 
    // Phương thức trả về một Optional để xử lý trường hợp không tìm thấy kết quả.
    
    
    List<BillDetail> findAllByBillId(Bill billId);
    //Đây là khai báo phương thức findAllByBillId, phương thức này sẽ tìm kiếm và trả về một danh sách các đối tượng BillDetail dựa trên billId.
    
    List<BillDetail> findAll();
    //Đây là khai báo phương thức findAll, phương thức này sẽ trả về tất cả các đối tượng BillDetail có trong cơ sở dữ liệu.

    void deleteBillDetailByBillIdAndProductId(Bill bill, Product product);
    //Đây là khai báo phương thức deleteBillDetailByBillIdAndProductId, phương thức này sẽ xóa một đối tượng BillDetail dựa trên billId và productId.
}