package com.example.demo.services;

import com.example.demo.models.Address;
import com.example.demo.models.Card;
import com.example.demo.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CardService {
	//interface CardService, đây là một interface dùng để quản lý các thao tác liên quan đến entity Card. 
	//Interface này cung cấp các phương thức để thực hiện các thao tác cơ bản như tìm kiếm, lấy thông tin, xóa và lưu trữ thẻ vào cơ sở dữ liệu.
	
    List<Card> findAllByUser(User user);//Được sử dụng để tìm kiếm tất cả các thẻ (Card) mà thuộc về một người dùng cụ thể (User).

    List<Card> findAll();//Được sử dụng để lấy tất cả các thẻ từ cơ sở dữ liệu.

    Card getById(Integer id);//Được sử dụng để lấy thông tin của một thẻ dựa trên ID.

    void deleteCardById(Integer id);//Được sử dụng để xóa một thẻ dựa trên ID.

    void saveCard(Card card);//Được sử dụng để lưu trữ hoặc cập nhật thông tin của một thẻ vào cơ sở dữ liệu.

    Page<Card> pagingCard(int offset, int pagesize); 
    //Được sử dụng để phân trang danh sách các thẻ, trả về một trang dữ liệu dựa trên offset (vị trí bắt đầu) 
    //và pagesize (số lượng phần tử trên mỗi trang).
    
    
    //Interface CardService cung cấp một cách tiếp cận trừu tượng và linh hoạt để thực hiện các thao tác 
    //liên quan đến entity Card, giúp tách biệt logic kinh doanh và logic truy cập cơ sở dữ liệu.
}
