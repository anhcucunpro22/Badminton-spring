package com.example.demo.services;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

//interface là AddressService trong ứng dụng Spring Boot, được sử dụng để quản lý các thao tác liên quan đến đối tượng Address.

public interface AddressService {

    List<Address> findAllByUser(User user);
    //được định nghĩa để trả về tất cả các địa chỉ của một người dùng cụ thể. Phương thức này sẽ trả về một danh sách (List) 
    //các đối tượng Address liên kết với đối tượng User được chỉ định.

    List<Address> findAll(); //được định nghĩa để trả về tất cả các địa chỉ có sẵn trong hệ thống.

    Address getById(Integer id);//được định nghĩa để trả về đối tượng Address dựa trên khóa chính (id) được chỉ định.

    void deleteAddressById(Integer id);// được định nghĩa để xóa địa chỉ dựa trên khóa chính (id) được chỉ định.

    void saveAddress(Address address);//được định nghĩa để lưu hoặc cập nhật đối tượng Address được chỉ định.

    Page<Address> pagingAddress(int offset, int pagesize);
    //được định nghĩa để thực hiện phân trang cho danh sách địa chỉ, với offset là vị trí bắt đầu và pagesize là kích thước của trang.
    
    
    //Interface này cung cấp các phương thức để thực hiện các thao tác cơ bản như lấy dữ liệu, lưu dữ liệu, 
    //xóa dữ liệu và phân trang dữ liệu liên quan đến đối tượng Address. Các phương thức này có thể được triển khai 
    //trong các lớp dịch vụ cụ thể để tương tác với cơ sở dữ liệu và thực hiện logic kinh doanh.
    
}
