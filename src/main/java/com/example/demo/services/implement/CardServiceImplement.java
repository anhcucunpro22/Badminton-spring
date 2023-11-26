package com.example.demo.services.implement;

import com.example.demo.models.Address;
import com.example.demo.models.Card;
import com.example.demo.models.User;
import com.example.demo.repositories.CardRepository;
import com.example.demo.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional // Đánh dấu rằng tất cả các phương thức trong service này sẽ được quản lý bởi transaction.
@Service //Đánh dấu đây là một service trong Spring Framework.
public class CardServiceImplement implements CardService {
    @Autowired //để inject(tiêm) CardRepository vào service.
    CardRepository cardRepository;
    @Override//được sử dụng để ghi đè (override) một phương thức từ lớp cha trong một lớp con, 
    //đảm bảo rằng phương thức trong lớp con thực sự đang ghi đè phương thức tương ứng trong lớp cha.
    public List<Card> findAllByUser(User user) {
        return cardRepository.findAllByUser(user);
        
        //Trả về danh sách các thẻ của một người dùng cụ thể.
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
        
        //Trả về danh sách tất cả các thẻ.
    }

    @Override
    public Card getById(Integer id) {
        return cardRepository.getById(id);
        //Trả về thông tin của thẻ dựa trên id.
    }

    @Override
    public void deleteCardById(Integer id) {
        cardRepository.deleteById(id);
        //Xóa thẻ dựa trên id.
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
        // Lưu thông tin của thẻ vào cơ sở dữ liệu.
    }

    @Override
    public Page<Card> pagingCard(int offset, int pagesize) {
        return cardRepository.findAll(PageRequest.of(offset, pagesize, Sort.by("id").descending()));
        //Phân trang danh sách thẻ dựa trên offset và kích thước trang.
    }
    
    // implementation này thực hiện các thao tác cơ bản như lấy thông tin, xóa, lưu thông tin thẻ và 
    //phân trang danh sách thẻ. Điều này giúp quản lý thông tin thẻ một cách dễ dàng và linh hoạt.
}
