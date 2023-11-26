package com.example.demo.models.keys;

import java.io.Serializable;

public class BillDetailKey implements Serializable {
	//Đây là khai báo class BillDetailKey, class này implements Serializable để đảm bảo rằng đối tượng của nó có thể 
	//được chuyển đổi thành dạng byte để lưu trữ hoặc truyền đi qua mạng.


	
    private Integer productId;
    private Integer billId;

    public BillDetailKey(){} //Đây là constructor mặc định của class BillDetailKey.

    public Integer getProductId() { //Đây là phương thức getter để lấy giá trị của thuộc tính productId.

        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }
}
