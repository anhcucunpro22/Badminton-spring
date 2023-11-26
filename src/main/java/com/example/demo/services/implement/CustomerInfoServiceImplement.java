package com.example.demo.services.implement;

import com.example.demo.models.CustomerInfo;
import com.example.demo.models.User;
import com.example.demo.repositories.CustomerInfoRepository;
import com.example.demo.services.CustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional //được sử dụng để đảm bảo rằng mỗi phương thức trong service sẽ chạy trong một transaction.
@Service //được sử dụng để đánh dấu class này là một Spring service.
public class CustomerInfoServiceImplement implements CustomerInfoService {

    @Autowired // để inject CustomerInfoRepository vào class này.
    CustomerInfoRepository customerInfoRepository;

    @Override //được sử dụng để ghi đè (override) một phương thức từ lớp cha trong một lớp con, 
    //đảm bảo rằng phương thức trong lớp con thực sự đang ghi đè phương thức tương ứng trong lớp cha.
    public CustomerInfo findByUser(User user) {
        return customerInfoRepository.findByUser(user);
        
        //sử dụng CustomerInfoRepository để tìm kiếm thông tin CustomerInfo dựa trên đối tượng User.

    }

    @Override
    public CustomerInfo findByEmail(String email) {
        return customerInfoRepository.findByEmail(email);
        
        //sử dụng CustomerInfoRepository để tìm kiếm thông tin CustomerInfo dựa trên địa chỉ email.
    }

    @Override
    public List<CustomerInfo> findAll() {
        return customerInfoRepository.findAll();
        
        //sử dụng CustomerInfoRepository để trả về tất cả các thông tin CustomerInfo.
    }

    @Override
    public CustomerInfo findCustomerInfoByUserID(Integer id) {
        return customerInfoRepository.getById(id);
        
        //sử dụng CustomerInfoRepository để tìm kiếm thông tin CustomerInfo dựa trên ID của người dùng.
    }

    @Override
    public void saveInfo(CustomerInfo info) {
        customerInfoRepository.save(info);
        
        // sử dụng CustomerInfoRepository để lưu thông tin CustomerInfo.
    }

    @Override
    public void deletInfoById(Integer id) {
        customerInfoRepository.deleteById(id);
        
        //sử dụng CustomerInfoRepository để xóa thông tin CustomerInfo dựa trên ID.
    }

    @Override
    public Page<CustomerInfo> pagingCustomerInfo(int offset, int pagesize) {
        return customerInfoRepository.findAll(PageRequest.of(offset, pagesize, Sort.by("id").descending()));
        
        //sử dụng CustomerInfoRepository để phân trang thông tin CustomerInfo.
    }

    @Override
    public CustomerInfo getByID(Integer id) {
        return customerInfoRepository.getById(id);
        
        //sử dụng CustomerInfoRepository để trả về thông tin CustomerInfo dựa trên ID.
    }
    
    //Class này cung cấp các phương thức cần thiết để thực hiện các thao tác CRUD và 
    //phân trang liên quan đến CustomerInfo bằng cách sử dụng CustomerInfoRepository.
    
}
