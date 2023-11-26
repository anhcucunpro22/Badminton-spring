package com.example.demo.repositories;

import com.example.demo.models.Card;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//interface CardRepository, là một JpaRepository của Spring Data JPA. JpaRepository cung cấp các phương thức 
//cơ bản để thao tác với cơ sở dữ liệu, bao gồm tạo, đọc, cập nhật và xóa (CRUD).

public interface CardRepository extends JpaRepository<Card, Integer> {
	//Card là entity mà repository này sẽ quản lý.
	//Integer là kiểu dữ liệu của khóa chính của entity.

    List<Card> findAllByUser(User user);
    //được định nghĩa trong interface này để tìm kiếm tất cả các thẻ (Card) mà thuộc về một người dùng cụ thể (User). 
    //Phương thức này sẽ trả về một danh sách (List) các thẻ.
}
