package com.example.demo.repositories;

import com.sun.mail.imap.protocol.UIDSet;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

	// interface là UserRepository, là một interface kế thừa từ JpaRepository, 
	//được sử dụng để thực hiện các thao tác truy vấn dữ liệu liên quan đến entity User trong cơ sở dữ liệu.
	
	//Các phương thức này sẽ được JpaRepository cung cấp các phương thức cơ bản để 
	//thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên cơ sở dữ liệu.


    User getUserByUserNameAndPassWord(String username, String password); //Trả về một đối tượng User dựa trên tên người dùng và mật khẩu.

    User getUserByUserName(String username); // Trả về một đối tượng User dựa trên tên người dùng.

    User getUserByUserNameAndPassWordAndRole(String username,String password, int role); //Trả về một đối tượng User dựa trên tên người dùng, mật khẩu và vai trò.

    Optional<User> findUserByUserNameAndId(String username, Integer id); //Trả về một Optional chứa đối tượng User dựa trên tên người dùng và id.

    List<User> findAllByRoleAndStatus(int role, int status); //Trả về một danh sách các User dựa trên vai trò và trạng thái.

    List<User> findUserByRole(Integer role); //Trả về một danh sách các User dựa trên vai trò.

}
