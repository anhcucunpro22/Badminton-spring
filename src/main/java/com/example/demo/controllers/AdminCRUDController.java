package com.example.demo.controllers;

import com.example.demo.models.Address;
import com.example.demo.models.Card;
import com.example.demo.models.CustomerInfo;
import com.example.demo.models.User;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminCRUDController {
	//Controller chứa các phương thức xử lý các yêu cầu từ phía người dùng, được ánh xạ theo các URL bắt đầu bằng /admin.
	//Controller chứa các phương thức xử lý các yêu cầu từ phía người dùng, được ánh xạ theo các URL bắt đầu bằng /admin.

    @Autowired
    UserService userService;

    @Autowired
    CustomerInfoService customerInfoService;
    @Autowired
    AddressService addressService;
    @Autowired
    CardService cardService;

    @Autowired
    UploadFileService uploadFileService;

    private static final int PAGE_SIZE = 5;

    @RequestMapping(value = "/list-user/{page}")
    public String ListUserPage(@PathVariable Integer page,
                               HttpServletRequest rq,
                               RedirectAttributes redirectAttributes,
                               Model model) {
    	//Xử lý hiển thị danh sách người dùng theo trang.
    	//Kiểm tra thông tin phiên người dùng để đảm bảo xác thực và quyền admin trước khi hiển thị danh sách người dùng.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                Page<User> userList = userService.pagingUser(page - 1, PAGE_SIZE);
                int amount = userService.findAll().size();

                int endPage = amount / PAGE_SIZE;

                if (amount % PAGE_SIZE != 0) {
                    endPage += 1;
                }

                if (page == null) {
                    page = 1;
                }

                model.addAttribute("userList", userList);
                model.addAttribute("tag", page);
                model.addAttribute("endPage", endPage);

                return "admin/list-user";
            }
        }
    }

    @RequestMapping(value = "/edit-user")
    public String saveUser(@RequestParam(required = false, name = "editID") Integer id,
                           @RequestParam(required = false, name = "editUserName") String username,
                           @RequestParam(required = false, name = "editPW") String password,
                           @RequestParam(required = false, name = "editStt") Integer status,
                           HttpServletRequest rq,
                           RedirectAttributes redirectAttributes) {
    	//Xử lý cập nhật thông tin người dùng.
    	//Kiểm tra thông tin phiên người dùng và quyền admin trước khi cho phép chỉnh sửa.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                User newUser = userService.getUserById(id);
                newUser.setUserName(username);
                newUser.setStatus(status);
                newUser.setPassWord(password);
                userService.saveUser(newUser);
                redirectAttributes.addFlashAttribute("message", "Đã sửa thành công!!!");
                return "redirect:/admin/list-user/1";
            }
        }
    }

    @RequestMapping(value = "/delete-user")
    public String deleteUser(@RequestParam(required = false, name = "deleteID") Integer id,
                             @RequestParam(required = false, name = "resultDelete") String listID,
                             HttpServletRequest rq,
                             RedirectAttributes redirectAttributes) {
    	//Xử lý xóa người dùng dựa trên ID cung cấp hoặc danh sách ID.
    	//Kiểm tra thông tin phiên người dùng và quyền admin trước khi cho phép xóa.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                if (id != 0) {
                    userService.deleteUserById(id);
                } else {
                    String[] splitListId = listID.split(",");

                    for (String x : splitListId) {
                        userService.deleteUserById(Integer.parseInt(x));
                    }
                }
                redirectAttributes.addFlashAttribute("message", "Đã xoá thành công!!!");
                return "redirect:/admin/list-user/1";
            }
        }
    }

    @RequestMapping(value = "/add-user")
    public String insertUser(@RequestParam(required = false, name = "addUserName") String username,
                             @RequestParam(required = false, name = "addPW") String password,
                             @RequestParam(required = false, name = "addStt") Integer status,
                             HttpServletRequest rq,
                             RedirectAttributes redirectAttributes) {
    	//Xử lý thêm người dùng mới vào hệ thống.
    	//Kiểm tra thông tin phiên người dùng và quyền admin trước khi thêm người dùng.
        HttpSession session = rq.getSession();
        //getSession là phương thức được sử dụng để tạo hoặc lấy phiên làm việc hiện tại của người dùng từ yêu cầu HTTP.
        //rq là HttpServletRequest, đây là đối tượng chứa thông tin về yêu cầu HTTP được gửi đến máy chủ từ phía người dùng.
        
        User user = (User) session.getAttribute("account");//được sử dụng để truy xuất giá trị của thuộc tính được lưu trữ trong đối tượng HttpSession trong môi trường Java web.
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                User newUser = userService.getUserByUserName(username);
                if (newUser == null) {
                    newUser = new User();
                    newUser.setUserName(username);
                    newUser.setPassWord(password);
                    newUser.setRole(1);
                    newUser.setStatus(status);
                    userService.saveUser(newUser);
                    redirectAttributes.addFlashAttribute("message", "Đã thêm thành công!!!");
                } else {
                    redirectAttributes.addFlashAttribute("messageError", "Đã tồn tại tên tài khoản!!!");

                }
                return "redirect:/admin/list-user/1";
            }
        }
    }

    //----------------
    @RequestMapping(value = "/list-customerinfo/{page}")
    public String ListCustomerInfoPage(@PathVariable Integer page, HttpServletRequest rq,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
    	//Xử lý hiển thị thông tin khách hàng theo trang.
    	//Kiểm tra thông tin phiên người dùng để đảm bảo xác thực và quyền admin trước khi hiển thị.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                Page<CustomerInfo> customerInfoList = customerInfoService.pagingCustomerInfo(page - 1, PAGE_SIZE);

                int amount = customerInfoService.findAll().size();

                int endPage = amount / PAGE_SIZE;

                if (amount % PAGE_SIZE != 0) {
                    endPage += 1;
                }

                if (page == null) {
                    page = 1;
                }

                List<User> userList = userService.findAllByRoleAndStatus(1, 1);
                List<CustomerInfo> customerInfoListTemp = customerInfoService.findAll();
                boolean flag = true;
                for (int i = 0; i < userList.size(); ) {
                    for (int j = 0; j < customerInfoListTemp.size(); j++) {
                        if (userList.get(i).getId() == customerInfoListTemp.get(j).getUser().getId()) {
                            userList.remove(i);
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        i++;
                    } else {
                        flag = true;
                    }
                }

                model.addAttribute("tag", page);
                model.addAttribute("endPage", endPage);
                model.addAttribute("customerInfoList", customerInfoList);
                model.addAttribute("listUser", userList);
                return "admin/list-customerinfo";
            }
        }
    }

    @RequestMapping(value = "/add-customerinfo")
    public String AddCustomerInfo(@RequestParam(required = false, name = "addUserID") Integer addUserID,
                                  @RequestParam(required = false, name = "addFullName") String addFullName,
                                  @RequestParam(required = false, name = "addBirthday") Date addBirthday,
                                  @RequestParam(required = false, name = "addEmail") String addEmail,
                                  @RequestParam(required = false, name = "addPhone") String addPhone,
                                  @RequestParam(required = false, name = "addAVT") MultipartFile addAVT,
                                  HttpServletRequest rq,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
    	//Xử lý thêm thông tin khách hàng mới.
    	//Yêu cầu xác thực phiên người dùng và quyền admin cho thao tác này.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                CustomerInfo customerInfo = new CustomerInfo();
                customerInfo.setUser(userService.getUserById(addUserID));
                customerInfo.setFullname(addFullName);
                customerInfo.setBithday(addBirthday);
                customerInfo.setPhone(addPhone);
                customerInfo.setEmail(addEmail);

                if (addAVT != null) {
                    customerInfo.setLinkAVT(uploadFileService.storeFile(addAVT));
                }

                customerInfoService.saveInfo(customerInfo);
                redirectAttributes.addFlashAttribute("message", "Đã thêm thành công!!!");
                return "redirect:/admin/list-customerinfo/1";
            }
        }
    }

    @RequestMapping(value = "/edit-customerinfo")
    public String EditCustomerInfo(@RequestParam(required = false, name = "editID") Integer editID,
                                   @RequestParam(required = false, name = "editFullName") String editFullName,
                                   @RequestParam(required = false, name = "editBirthday") Date editBirthday,
                                   @RequestParam(required = false, name = "editEmail") String editEmail,
                                   @RequestParam(required = false, name = "editPhone") String editPhone,
                                   @RequestParam(required = false, name = "editAVT") MultipartFile editAVT,
                                   HttpServletRequest rq,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
    	//Cho phép chỉnh sửa thông tin khách hàng.
    	//Yêu cầu xác thực phiên người dùng và quyền admin.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                System.out.println(editID);
                CustomerInfo customerInfo = customerInfoService.getByID(editID);
                customerInfo.setFullname(editFullName);
                customerInfo.setBithday(editBirthday);
                customerInfo.setPhone(editPhone);
                customerInfo.setEmail(editEmail);

                if (editAVT != null) {
                    customerInfo.setLinkAVT(uploadFileService.storeFile(editAVT));
                }

                customerInfoService.saveInfo(customerInfo);
                redirectAttributes.addFlashAttribute("message", "Đã sửa thành công!!!");
                return "redirect:/admin/list-customerinfo/1";
            }
        }
    }

    @RequestMapping(value = "/delete-customerinfo")
    public String deleteCustomerInfo(@RequestParam(required = false, name = "deleteCustomerInfo") Integer deleteID,
                                     @RequestParam(required = false, name = "resultCustomerInfo") String listID,
                                     HttpServletRequest rq,
                                     RedirectAttributes redirectAttributes) {
    	//Xử lý xóa thông tin khách hàng dựa trên ID cung cấp.
    	//Kiểm tra thông tin phiên người dùng và quyền admin trước khi xóa.
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                if (deleteID != 0) {
                    customerInfoService.deletInfoById(deleteID);
                } else {
                    String[] listIDInt = listID.split(",");
                    for (String idTemp : listIDInt) {
                        customerInfoService.deletInfoById(Integer.parseInt(idTemp));
                    }
                }
                redirectAttributes.addFlashAttribute("message", "Đã xoá thành công!!!");
                return "redirect:/admin/list-customerinfo/1";
            }
        }
    }


    @RequestMapping(value = "/list-address/{page}")
    public String ListAddressPage(@PathVariable Integer page,
                                  HttpServletRequest rq,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                Page<Address> addressList = addressService.pagingAddress(page - 1, PAGE_SIZE);
                int amount = addressService.findAll().size();

                int endPage = amount / PAGE_SIZE;

                if (amount % PAGE_SIZE != 0) {
                    endPage += 1;
                }

                if (page == null) {
                    page = 1;
                }

                List<User> userList = userService.findAll();
                model.addAttribute("addressList", addressList);
                model.addAttribute("tag", page);
                model.addAttribute("endPage", endPage);
                model.addAttribute("listUser", userList);
                return "admin/list-address";
            }
        }
    }


    @RequestMapping(value = "/add-address")
    public String AddAddress(@RequestParam(required = false, name = "addUserID") Integer addUserID,
                             @RequestParam(required = false, name = "addDescription") String addDescription,
                             HttpServletRequest rq,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                Address address = new Address();
                address.setUser(userService.getUserById(addUserID));
                address.setDescription(addDescription);
                addressService.saveAddress(address);
                redirectAttributes.addFlashAttribute("message", "Đã thêm thành công!!!");
                return "redirect:list-address/1";
            }
        }
    }


    @RequestMapping(value = "/edit-address")
    public String EditAddress(@RequestParam(required = false, name = "editID") Integer editID,
                              @RequestParam(required = false, name = "editDescription") String editDescription,
                              HttpServletRequest rq,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                Address address = addressService.getById(editID);
                address.setDescription(editDescription);
                addressService.saveAddress(address);
                redirectAttributes.addFlashAttribute("message", "Đã sửa thành công!!!");
                return "redirect:list-address/1";
            }
        }
    }


    @RequestMapping(value = "/delete-address")
    public String DeleteAddress(@RequestParam(required = false, name = "deleteAddress") Integer deleteAddress,
                                @RequestParam(required = false, name = "resultAddress") String resultAddress,
                                HttpServletRequest rq,
                                RedirectAttributes redirectAttributes,
                                Model model) {

        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng đăng nhập!!!");
            return "redirect:/admin/login";
        } else {
            if (user.getRole() != 3) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản không có quyền admin!!!");
                return "redirect:/admin/login";
            } else {
                //do some thing
                if (deleteAddress != 0) {
                    addressService.deleteAddressById(deleteAddress);
                } else {
                    String[] listID = resultAddress.split(",");
                    for (String idTemp : listID) {
                        addressService.deleteAddressById(Integer.parseInt(idTemp));
                    }
                }
                redirectAttributes.addFlashAttribute("message", "Đã xoá thành công!!!");
                return "redirect:list-address/1";
            }
        }
    }
}