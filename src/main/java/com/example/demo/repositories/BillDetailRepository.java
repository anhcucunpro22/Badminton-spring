package com.example.demo.repositories;

import com.example.demo.models.BillDetail;
import com.example.demo.models.keys.BillDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetail, BillDetailKey> {
}
// Đây là khai báo interface BillDetailRepository, interface này kế thừa từ JpaRepository 
// và sử dụng BillDetail làm entity và BillDetailKey làm kiểu dữ liệu của khóa chính. Điều này cho phép interface 
// này sử dụng các phương thức cơ bản của JpaRepository để thao tác với cơ sở dữ liệu liên quan đến đối tượng BillDetail.