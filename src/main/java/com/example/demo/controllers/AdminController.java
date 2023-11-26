package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.ListView;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

//Bộ điều khiển chính cho các chức năng liên quan đến quản trị viên
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	//Injecting Tiêm các dịch vụ cần thiết
    @Autowired
    UserService userService;
    @Autowired
    BillService billService;
    @Autowired
    ProductService productService;

    @Autowired
    BillDetailService billDetailService;
    @Autowired
    CustomerInfoService customerInfoService;
    
    // Mapping cho trang chủ
    @RequestMapping(value = {"/", "/home", "/index"})
    public String HomePage(HttpServletRequest rq, Model model){

    	//Checking session for xác thực người dùng
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if(user == null)
            return "redirect:/admin/login";
        
        //Chuyển hướng người dùng trái phép
        if(user.getRole() != 3){
            session.removeAttribute("account");
            return "redirect:/admin/login";
        }
        
        //Tìm nạp nhiều dữ liệu khác nhau để hiển thị trên trang chủ
        List<Bill> billList=billService.findAllByStatus(1);
        List<BillDetail> billDetailList=billDetailService.findAll();
        List<CustomerInfo> customerInfoList=customerInfoService.findAll();
        Date date= Date.valueOf( LocalDate.now());
        String dateStart = String.valueOf(LocalDate.now().getYear() + "/"+ LocalDate.now().getMonthValue() + "/1" );
        List<Bill> billList1=billService.findAllByDate(dateStart,date.toString());
        List<Product> productList=productService.getTop4ProductBestSeller();
        double total=0;
        for (int i=0;i<billList.size();i++) {
            total+=billList.get(i).getTotal();//Tính tổng số tiền hóa đơn
        }
        
        //Tính toán và tổng hợp tổng doanh số bán hàng trong một khoảng thời gian cụ thể
        double total1=0;
        for (int j=0;j<billList1.size();j++) {
            if(billList1.get(j).getStatus() == 1) {
                total1 += billList1.get(j).getTotal();
            }
        }

        int totalcustomer=customerInfoList.size(); //Đếm tổng số khách hàng
        int totalbill=billList.size(); // Đếm tổng hóa đơn

        int totalproduct= 0;
        for(BillDetail billDetail : billDetailList){
            if(billDetail.getBillId().getStatus() == 1) {
                totalproduct += billDetail.getQuantity(); // Đếm tổng số sản phẩm đã bán
            }
        }
        //// Thêm dữ liệu vào mô hình để hiển thị trong dạng xem
        model.addAttribute("Total",total);
        model.addAttribute("Total1",total1);
        model.addAttribute("Totalbill",totalbill);
        model.addAttribute("totalproduct",totalproduct);
        model.addAttribute("totalcustomer",totalcustomer);
        model.addAttribute("customerInfoList",customerInfoList);
        model.addAttribute("productList",productList);
        return "admin/home"; // Hiển thị trang chủ quản trị
    }
 // Xử lý quá trình đăng nhập
    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    public String LoginPage(@RequestParam(required = false, value = "username") String username,
                            @RequestParam(required = false, value = "password") String password,
                            RedirectAttributes redirectAttributes,
                            HttpServletRequest rq){
        User user = userService.getUserByUserNameAndPassWordAndRole(username,password,3);
        if(user == null){
            redirectAttributes.addFlashAttribute("message1","Sai thông đăng nhập!!!");
            return "redirect:/admin/login";// Chuyển hướng sang đăng nhập nếu xác thực thất bại
        }else {
            redirectAttributes.addFlashAttribute("message1", "Đăng nhập thành công!!!");
            CustomerInfo customerInfo = customerInfoService.findByUser(user);
            if (customerInfo == null) {
                CustomerInfo temp = new CustomerInfo();
                temp.setFullname(username);
                customerInfo = temp;
            }
            HttpSession session = rq.getSession();

            session.setAttribute("account", user); // Đặt thuộc tính phiên khi đăng nhập thành công
            session.setAttribute("info", customerInfo);
            return "redirect:/admin/home";// Chuyển hướng về trang chủ quản trị sau khi đăng nhập thành công
        }
    }
    
    //Xử lý quá trình đăng xuất
    @RequestMapping(value = "/logout")
    public String LogoutPage(HttpServletRequest rq){
        HttpSession session = rq.getSession();
        session.removeAttribute("account");//Xóa thuộc tính phiên khi đăng xuất
        session.removeAttribute("info");
        return "admin/login2";//Chuyển hướng đến trang đăng nhập
    }


    @RequestMapping(value = {"/register"}) 
    public String Register(){
        return "admin/register";
    }

    @RequestMapping(value = {"/profile"})//Chuyển hướng đến trang hồ sơ
    public String Profile(){
        return "admin/profile";//Hiển thị trang hồ sơ
    }

    @RequestMapping(value = {"/revenue-statistics"}) //Chuyển hướng đến trang thống kê doanh thu
    public String RevenueStatisticsPage(){
        return "admin/revenue-statistics"; //Hiển thị trang thống kê doanh thu
    }
}
