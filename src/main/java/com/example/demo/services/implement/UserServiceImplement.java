package com.example.demo.services.implement;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserServiceImplement implements UserService {

	// implement của interface UserService. Lớp này triển khai các phương thức được định nghĩa trong UserService 
	//bằng cách sử dụng các phương thức từ UserRepository để thực hiện các thao tác liên quan đến cơ sở dữ liệu.
	
    @Autowired //được sử dụng để ghi đè (override) một phương thức từ lớp cha trong một lớp con, 
    //đảm bảo rằng phương thức trong lớp con thực sự đang ghi đè phương thức tương ứng trong lớp cha.
    UserRepository userRepository;

    @Override
    public User getUserByUserNameAndPassWord(String username, String password) { 
    	//Sử dụng phương thức của UserRepository để trả về một đối tượng User dựa trên tên người dùng và mật khẩu.

        return userRepository.getUserByUserNameAndPassWord(username, password);
    }

    @Override
    public User getUserByUserName(String username) {
    	//Sử dụng phương thức của UserRepository để trả về một đối tượng User dựa trên tên người dùng.
        return userRepository.getUserByUserName(username);
    }

    @Override
    public void saveUser(User user) {
    	//Sử dụng phương thức của UserRepository để lưu thông tin của một đối tượng User vào cơ sở dữ liệu.
        userRepository.save(user);
    }
    @Override
    public User getUserByUserNameAndPassWordAndRole(String username, String password, int role) {
    	//Sử dụng phương thức của UserRepository để trả về một đối tượng User dựa trên tên người dùng, mật khẩu và vai trò.
        return userRepository.getUserByUserNameAndPassWordAndRole(username, password, role);
    }

    @Override
    public User getUserById(Integer id) {
    	//Sử dụng phương thức của UserRepository để trả về một đối tượng User dựa trên id.
        return userRepository.getById(id);
    }

    @Override
    public boolean checkEdiUsername(String username, int id) {
    	//Sử dụng phương thức của UserRepository để kiểm tra xem tên người dùng đã tồn tại hay chưa.
        return userRepository.findUserByUserNameAndId(username, id).isPresent();
    }

    @Override
    public List<User> findAll() {
    	//Sử dụng phương thức của UserRepository để trả về một danh sách tất cả các User.
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Integer id) {
    	//Sử dụng phương thức của UserRepository để xóa một User dựa trên id.
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> pagingUser(int offset, int pagesize) {
    	//Sử dụng phương thức của UserRepository để trả về một trang dữ liệu User dựa trên offset và kích thước trang.
        return userRepository.findAll(PageRequest.of(offset, pagesize,  Sort.by("id").descending()));
    }

    @Override
    public List<User> findAllByRoleAndStatus(int role, int status) {
    	//Sử dụng phương thức của UserRepository để trả về một danh sách các User dựa trên vai trò và trạng thái.
        return userRepository.findAllByRoleAndStatus(role, status);
    }

    @Override
    public List<User> findUserByRole(int id) {
    	//Sử dụng phương thức của UserRepository để trả về một danh sách các User dựa trên vai trò.
        return userRepository.findUserByRole(id);
    }

}
