package com.example.demo.services.implement;

import com.example.demo.models.Address;
import com.example.demo.models.User;
import com.example.demo.repositories.AddresRepository;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// lớp AddressServiceImplement là một implementation của interface AddressService. Lớp này được sử dụng để thực hiện các phương 
// thức đã được định nghĩa trong interface AddressService.

@Transactional //được sử dụng để đảm bảo rằng các phương thức trong lớp này sẽ được thực thi trong một giao dịch (transaction).
@Service //  được sử dụng để đánh dấu lớp này là một bean dịch vụ trong Spring Framework.
public class AddressServiceImplement implements AddressService {

    @Autowired //để tiêm (inject) một đối tượng AddresRepository vào lớp. Điều này cho phép lớp AddressServiceImplement sử dụng các 
    //phương thức được cung cấp bởi AddresRepository để tương tác với cơ sở dữ liệu.
    
    AddresRepository addresRepository;
    
    //AddresRepository để thực hiện các thao tác cơ bản như lấy dữ liệu, lưu dữ liệu, xóa dữ liệu và phân trang dữ 
    //liệu liên quan đến đối tượng Address.
    @Override
    public List<Address> findAllByUser(User user) {
        return addresRepository.findAllByUser(user);
    }

    @Override
    public List<Address> findAll() {
        return addresRepository.findAll();
    }

    @Override
    public Address getById(Integer id) {
        return addresRepository.getById(id);
    }

    @Override
    public void deleteAddressById(Integer id) {
        addresRepository.deleteById(id);
    }

    @Override
    public void saveAddress(Address address) {
        addresRepository.save(address);
    }

    @Override
    public Page<Address> pagingAddress(int offset, int pagesize) {
        return addresRepository.findAll(PageRequest.of(offset, pagesize, Sort.by("id").descending()));
        
    //pagingAddress sử dụng addresRepository để thực hiện phân trang dữ liệu bằng cách sử dụng PageRequest và Sort từ Spring Data.    
    }
    
    
    //Lớp AddressServiceImplement cung cấp triển khai cụ thể cho các phương thức được định nghĩa trong interface AddressService, 
    //và sử dụng AddresRepository để tương tác với cơ sở dữ liệu.
}
