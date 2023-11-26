package com.example.demo.controllers;

import com.example.demo.models.Bill;
import com.example.demo.models.BillDetail;
import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.services.BillDetailService;
import com.example.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminHomeController {

    @Autowired
    BillDetailService billDetailService;

    @Autowired
    CategoryService categoryService;
    
    // // Trang đăng nhập - GET
    @RequestMapping(value = {"/login"},method = RequestMethod.GET)
    public String LoginPage(){

        return "admin/login2";
    }
    
    // // Trang đăng nhập 2 - GET
    @RequestMapping(value = {"/login2"},method = RequestMethod.GET)
    public String LoginPage2(){

        return "admin/login2";
    }
    // Trang thống kê - GET
    @RequestMapping(value ="/statistics")
    public String StatisticsPage(HttpServletRequest rq,
                                 RedirectAttributes redirectAttributes,
                                 Model model){
    	// Kiểm tra đăng nhập
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if(user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        }
        // Kiểm tra quyền admin
        if(user.getRole() != 3) {
            session.removeAttribute("account");
            return "redirect:/admin/login";
        }

        List<BillDetail> billDetailList = billDetailService.findAll();
     // Lấy danh sách chi tiết hóa đơn và danh sách danh mục sản phẩm
        List<Category> categories = categoryService.findAll();
        int[] countList = new int[categories.size()];// Tạo mảng đếm số lượng sản phẩm theo danh mục
        for(BillDetail p : billDetailList){

            for(Category c : categories){
                if(p.getProductId().getCategory().getId() == c.getId()){
                    countList[c.getId() - 1] += 1;
                }
            }

        }
        //// Tạo mảng tên danh mục
        String[] nameList = new String[categories.size()];
        for (int i = 0; i<categories.size(); i++){
            nameList[i] = categories.get(i).getName();
        }
        // Thêm vào model để truyền sang view
        model.addAttribute("nameList",nameList);
        model.addAttribute("countList",countList);
        return "admin/revenue-statistics";
    }

}
