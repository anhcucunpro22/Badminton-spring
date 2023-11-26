package com.example.demo.controllers;


import com.example.demo.models.*;

import com.example.demo.repositories.BillRepository;
import com.example.demo.repositories.ProductDetailRepository;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class WebHomeController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    BillService billService;

    @Autowired
    BillDetailService billDetailService;

    public static final int PAGE_SITE = 12;

    @RequestMapping(value = {"/", "/home", "/index"}) //Phương thức HomePage:
    public String HomePage(Model model, HttpServletRequest rq){ //Được gọi khi người dùng truy cập vào trang chủ.

       // GetData getData = new GetData();
        //		getData.getDataForDatabase();

        List<Product> listTop8Product = productService.getTop8Product();
        List<Product> listLast4Product = productService.getLast4Product();
        //Lấy danh sách 8 sản phẩm hàng đầu và 4 sản phẩm mới nhất để hiển thị trên trang chủ.
        HttpSession session = rq.getSession();

        User user = (User) session.getAttribute("account");

        if(user != null){
            Optional<Bill> billCheck = billService.findByUserAndStatus(user, -1);
            if (billCheck.isPresent()){
                Bill bill = billCheck.get();
                //Kiểm tra xem người dùng đã đăng nhập chưa. Nếu đã đăng nhập, kiểm tra xem có hóa đơn 
                //chưa được xử lý (-1) hay không và hiển thị số lượng sản phẩm trong hóa đơn đó.
                List<BillDetail> billDetailList = billDetailService.findAllByBillId(bill);
                if(billDetailList.size() > 0){
                    int count = 0;
                    for(BillDetail p : billDetailList){
                        count += p.getQuantity();
                    }
                    model.addAttribute("count", count);
                }
            }
        }

        model.addAttribute("listtop8product", listTop8Product);
        model.addAttribute("listlast4product", listLast4Product);
        return "web/home";
    }

    @RequestMapping(value =  "/products/{category}/page/{pagenumber}")
    public String AllProductPage(@PathVariable("category") Integer category,
                                 @PathVariable("pagenumber") Integer pagenumber,
                                 Model model,
                                 HttpServletRequest rq){
    	//Được gọi khi người dùng truy cập vào trang hiển thị tất cả sản phẩm theo danh mục.
        List<Category> listAllCategory = categoryService.findAll();
        int amount = productService.findAll().size();

        List<Product> productList =  productService.PagingAllProduct(pagenumber-1, PAGE_SITE).getContent();
        if(category !=0){
            Category categoryModel = categoryService.findCategoryById(category);
            productList = productService.PagingProductByCategory(pagenumber-1, PAGE_SITE, categoryModel).getContent();
            amount = productService.findAllByCategory(categoryModel).size();
        }
        //Lấy danh sách tất cả danh mục và sản phẩm theo trang và danh mục được chọn để hiển thị.
        int endPage = amount / PAGE_SITE;

        if(amount % PAGE_SITE != 0){
            endPage += 1;
        }

        if(pagenumber == null){
            pagenumber = 1;
        }

        HttpSession session = rq.getSession();

        User user = (User) session.getAttribute("account");

        if(user != null){
            Optional<Bill> billCheck = billService.findByUserAndStatus(user, -1);
            if (billCheck.isPresent()){
                Bill bill = billCheck.get();

                List<BillDetail> billDetailList = billDetailService.findAllByBillId(bill);
                if(billDetailList.size() > 0){
                    int count = 0;
                    for(BillDetail p : billDetailList){
                        count += p.getQuantity();
                    }
                    model.addAttribute("count", count);
                }
            }
        }
        //Tính toán số trang kết quả và hiển thị số lượng sản phẩm trong giỏ hàng nếu người dùng đã đăng nhập.

        model.addAttribute("listAllProduct", productList);
        model.addAttribute("listAllCategory", listAllCategory);
        model.addAttribute("tag", pagenumber);
        model.addAttribute("endPage", endPage);
        model.addAttribute("targetactive", category);
        model.addAttribute("isSearch", false);

        return "web/product";
    }

    @RequestMapping(value = {"/product-detail/{product}/{category}"}) //Được gọi khi người dùng xem chi tiết sản phẩm.
    public String ProductDetailPage(@PathVariable("product") Integer product,
                                    @PathVariable("category") Integer category,
                                    Model model,
                                    HttpServletRequest rq){

        Product productDetail = productService.getProductById(product);
        System.out.println(categoryService.findCategoryById(category).getName());
        System.out.println(productService.getProductById(product).getName());
        List<Product> listProductSame = productService.getProductSameCategory(categoryService.findCategoryById(category), product);

        List<ProductImage> listImageOfProduct = productImageService.getAllImageOfProduct(productDetail);

        ProductDetail detailOfProduct = productDetailRepository.findByProduct(productDetail);
        //Lấy thông tin chi tiết của sản phẩm, danh sách sản phẩm cùng danh mục, danh sách hình ảnh của sản phẩm.
        if(detailOfProduct == null){
            ProductDetail temp = new ProductDetail();
            temp.setBrand("Không");
            temp.setColor("Không");
            temp.setDescription("Không");
            temp.setMaterial("Không");
            detailOfProduct = temp;
        }

        HttpSession session = rq.getSession();

        User user = (User) session.getAttribute("account");

        if(user != null){
            Optional<Bill> billCheck = billService.findByUserAndStatus(user, -1);
            if (billCheck.isPresent()){
                Bill bill = billCheck.get();

                List<BillDetail> billDetailList = billDetailService.findAllByBillId(bill);
                if(billDetailList.size() > 0){
                    int count = 0;
                    for(BillDetail p : billDetailList){
                        count += p.getQuantity();
                    }
                    model.addAttribute("count", count);
                } //Hiển thị số lượng sản phẩm trong giỏ hàng nếu người dùng đã đăng nhập.
            }
        }

        model.addAttribute("product", productDetail);
        model.addAttribute("detail", detailOfProduct);
        model.addAttribute("listProductSame", listProductSame);
        model.addAttribute("listImageOfProduct", listImageOfProduct);

        return "web/product-detail";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET) //Được gọi khi người dùng tìm kiếm sản phẩm.
    public String SearchProductPage(@RequestParam("text") String text,
                                    @RequestParam("pagenumber") Integer pagenumber,
                                    Model model,
                                    HttpServletRequest rq){

        List<Product> listAllProduct = productService.findProductByName(text, pagenumber-1, 8);
        List<Category> listAllCategory = categoryService.findAll();
        //Tìm kiếm sản phẩm theo từ khóa và hiển thị kết quả theo trang.
        int amount = 0;
        int endPage = 0;
        List<Product> list = productService.findAllProductByName(text);

        if(list.size() > 0) {
            amount = list.size();
            endPage  = amount / 8;
            if(amount % 8 != 0){
                endPage += 1;
            }
        }else {
            System.out.println("NUlllll");
        }


        if(pagenumber == null){
            pagenumber = 1;
        }

        HttpSession session = rq.getSession();
        //Hiển thị số lượng sản phẩm trong giỏ hàng nếu người dùng đã đăng nhập.
        User user = (User) session.getAttribute("account");

        if(user != null){
            Optional<Bill> billCheck = billService.findByUserAndStatus(user, -1);
            if (billCheck.isPresent()){
                Bill bill = billCheck.get();

                List<BillDetail> billDetailList = billDetailService.findAllByBillId(bill);
                if(billDetailList.size() > 0){
                    int count = 0;
                    for(BillDetail p : billDetailList){
                        count += p.getQuantity();
                    }
                    model.addAttribute("count", count);
                }
            }
        }

        model.addAttribute("listAllProduct", listAllProduct);
        model.addAttribute("listAllCategory", listAllCategory);
        model.addAttribute("tag", pagenumber);
        model.addAttribute("endPage", endPage);
        model.addAttribute("targetactive", 0);
        model.addAttribute("isSearch", true);
        model.addAttribute("txtSearch", text);

        return "web/product";
    }
    
    @RequestMapping(value = {"/news"} , method = RequestMethod.GET)
    public String NewsPage(){
        return "web/news";
    }
    @RequestMapping(value = {"/news1"} , method = RequestMethod.GET) //Phương thức NewsPage và News1Page:
    public String News1Page(){ //Được gọi khi người dùng truy cập vào trang tin tức.
        return "web/news1"; //Hiển thị trang tin tức tương ứng.
    }

    @RequestMapping(value = {"/login"} , method = RequestMethod.GET)
    public String LoginPage(){
        return "web/login";
    }

    @RequestMapping(value = {"/recover-password"} , method = RequestMethod.GET) //Phương thức LoginPage và RecoverPasswordPage:
    public String RecoverPasswordPage(){ //Được gọi khi người dùng truy cập vào trang đăng nhập và khôi phục mật khẩu.
        return "web/forgot-password"; //Hiển thị trang đăng nhập và khôi phục mật khẩu tương ứng.
    }

    @RequestMapping(value = {"/cart"}) //Được gọi khi người dùng truy cập vào trang giỏ hàng.
    public String CartPage(HttpServletRequest rq, Model model){
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if(user == null)
            return "redirect:/login";
        double total = 0;
        Optional<Bill> billCheck = billService.findByUserAndStatus(user,-1);
        if(billCheck.isPresent()){
            Bill bill = billCheck.get();
            List<BillDetail> billDetailList = billDetailService.findAllByBillId(bill);
            if(billDetailList.size() > 0) {
                int count = 0;
                for(BillDetail p : billDetailList){
                    count += p.getQuantity();
                } //Kiểm tra xem người dùng đã đăng nhập chưa. Nếu đã đăng nhập, hiển thị danh sách sản phẩm trong giỏ hàng và tổng số tiền.
                model.addAttribute("count", count);
                model.addAttribute("listBill", billDetailList);
                total = bill.getTotal();
            }
        }
        model.addAttribute("total", total);

        return "web/cart";
    }

}
