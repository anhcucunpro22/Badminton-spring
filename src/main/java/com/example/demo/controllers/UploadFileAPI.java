package com.example.demo.controllers;

import com.example.demo.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Đây là một RESTful API Controller trong ứng dụng Spring Boot. Nó chịu trách nhiệm xử lý các yêu cầu liên quan đến việc tải lên và đọc file.

@RestController
//đánh dấu lớp này là một RESTful Controller, nó sẽ xử lý các yêu cầu HTTP và trả về kết quả dưới dạng JSON hoặc XML.

@RequestMapping(value = "/api/uploadfile")
// xác định đường dẫn cơ sở cho tất cả các yêu cầu được xử lý bởi Controller này.



public class UploadFileAPI {

    @Autowired
    //được sử dụng để chú thích trường UploadFileService uploadFileService, đảm bảo rằng Spring sẽ tự động tiêm (inject) một đối tượng 
    //của lớp UploadFileService vào trường này.
    
    UploadFileService uploadFileService;

    @GetMapping(value = "/upload/{file}")
    // chỉ định rằng phương thức này sẽ xử lý yêu cầu GET tới đường dẫn "/api/uploadfile/upload/{file}", trong đó {file} là một tham số đường dẫn.
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String file){
    // phương thức readDetailFile, đầu tiên nó gọi phương thức readFileContent của uploadFileService để đọc nội dung của file được chỉ định. 
    	//Sau đó, nó trả về nội dung của file dưới
    	
        try {
            byte[] bytes = uploadFileService.readFileContent(file);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
