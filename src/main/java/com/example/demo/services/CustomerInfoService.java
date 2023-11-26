package com.example.demo.services;

import com.example.demo.models.CustomerInfo;
import com.example.demo.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerInfoService {
	//Interface CustomerInfoService định nghĩa các phương thức mà một service 
	//hoặc business logic class cần triển khai để thực hiện các thao tác liên quan đến CustomerInfo.
	
    CustomerInfo findByUser(User user); //được sử dụng để tìm kiếm thông tin CustomerInfo dựa trên đối tượng User.

    CustomerInfo findByEmail(String email);// được sử dụng để tìm kiếm thông tin CustomerInfo dựa trên địa chỉ email.

    List<CustomerInfo> findAll();//trả về tất cả các thông tin CustomerInfo.

    CustomerInfo findCustomerInfoByUserID(Integer id);//tìm kiếm thông tin CustomerInfo dựa trên ID của người dùng.

    void saveInfo(CustomerInfo info);//được sử dụng để lưu thông tin CustomerInfo.

    void deletInfoById(Integer id);// được sử dụng để xóa thông tin CustomerInfo dựa trên ID.

    Page<CustomerInfo> pagingCustomerInfo(int offset, int pagesize);//được sử dụng để phân trang thông tin CustomerInfo.

    CustomerInfo getByID(Integer id);//trả về thông tin CustomerInfo dựa trên ID.
    
    //Các phương thức này định nghĩa các chức năng cơ bản mà một service cần triển khai để thực hiện các thao tác liên quan đến CustomerInfo.
}
