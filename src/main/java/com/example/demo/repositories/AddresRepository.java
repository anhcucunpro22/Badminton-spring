package com.example.demo.repositories;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//interface là AddresRepository trong ứng dụng Spring Boot, được sử dụng để tương tác với cơ sở dữ liệu để thực hiện 
//các thao tác liên quan đến đối tượng Address.

public interface AddresRepository extends JpaRepository<Address, Integer> {
	//điều này có nghĩa là nó sử dụng JpaRepository để thực hiện các thao tác cơ bản như lưu, cập nhật, xóa và truy vấn dữ liệu liên quan 
	//đến đối tượng Address. Address là kiểu dữ liệu của đối tượng và Integer là kiểu dữ liệu của khóa chính của đối tượng.
	
	
    List<Address> findAllByUser(User user);
    //được định nghĩa để truy vấn tất cả các địa chỉ của một người dùng cụ thể. Phương thức này sẽ trả về một danh sách (List) các đối tượng 
    //Address liên kết với đối tượng User được chỉ định.
    
    
    
    //Với việc sử dụng JpaRepository, các phương thức cơ bản như lưu, cập nhật, xóa và truy vấn dữ liệu đã được 
    //tự động triển khai, và phương thức tùy chỉnh như findAllByUser cũng có thể được định nghĩa một cách dễ dàng. 
    //Điều này giúp giảm thiểu việc viết mã và tăng tính tái sử dụng trong ứng dụng.
}
