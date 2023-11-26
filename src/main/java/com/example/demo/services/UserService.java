package com.example.demo.services;

import com.example.demo.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
	// interface là UserService, định nghĩa các phương thức mà một service hoặc business logic layer 
	//cần triển khai để thực hiện các thao tác liên quan đến entity User.


    User getUserByUserNameAndPassWord(String username, String password);//Trả về một đối tượng User dựa trên tên người dùng và mật khẩu.

    User getUserByUserName(String username); //Trả về một đối tượng User dựa trên tên người dùng.

    void saveUser(User user); //Lưu thông tin của một đối tượng User vào cơ sở dữ liệu.
    
    User getUserByUserNameAndPassWordAndRole(String username,String password, int role); //Trả về một đối tượng User dựa trên tên người dùng, mật khẩu và vai trò.

    User getUserById(Integer id); //Trả về một đối tượng User dựa trên id.

    boolean checkEdiUsername(String username, int id); //Kiểm tra xem tên người dùng đã tồn tại hay chưa.

    List<User> findAll(); //Trả về một danh sách tất cả các User.

    void deleteUserById(Integer id); //Xóa một User dựa trên id.

    Page<User> pagingUser(int offset, int pagesize); //Trả về một trang dữ liệu User dựa trên offset và kích thước trang.

    List<User> findAllByRoleAndStatus(int role, int status); //Trả về một danh sách các User dựa trên vai trò và trạng thái.

    List<User> findUserByRole(int id);//Trả về một danh sách các User dựa trên vai trò.
    
    
    //Các phương thức này định nghĩa các chức năng cần thiết để quản lý thông tin người dùng trong hệ thống 
    //và sẽ được triển khai trong lớp service cụ thể.

}
