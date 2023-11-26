package com.example.demo.repositories;

import com.example.demo.models.CustomerInfo;
import com.example.demo.models.User;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Integer> {
	//interface là CustomerInfoRepository, là một JpaRepository của Spring Data JPA để thực hiện các thao tác liên quan đến CustomerInfo.
	 //Interface này kế thừa từ JpaRepository<CustomerInfo, Integer>, cho phép thực hiện các thao tác 
    //cơ bản như lưu, cập nhật, xóa và truy vấn dữ liệu liên quan đến đối tượng CustomerInfo.
	
    CustomerInfo findByUser(User user);
    //được định nghĩa để tìm kiếm thông tin CustomerInfo dựa trên đối tượng User. Điều này cho phép tìm kiếm thông tin 
    //khách hàng dựa trên thông tin người dùng liên quan đến họ.
    
    CustomerInfo findByEmail(String email);
    //được định nghĩa để tìm kiếm thông tin CustomerInfo dựa trên địa chỉ email. 
    //Điều này cho phép tìm kiếm thông tin khách hàng dựa trên địa chỉ email của họ.
    
    
    //Cả hai phương thức này sẽ được Spring Data JPA tự động triển khai khi được sử dụng, giúp việc thao tác với dữ liệu trở nên 
    //dễ dàng và linh hoạt.
}
