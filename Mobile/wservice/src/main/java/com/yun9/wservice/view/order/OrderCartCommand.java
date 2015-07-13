package com.yun9.wservice.view.order;

import com.yun9.jupiter.command.JupiterCommand;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderCartCommand  extends JupiterCommand{

    private List<OrderProductView> orderProductViews;

    public List<OrderProductView> getOrderProductViews() {
        return orderProductViews;
    }

    public void setOrderProductViews(List<OrderProductView> orderProductViews) {
        this.orderProductViews = orderProductViews;
    }

    public static class OrderProductView implements Serializable{
        private double price;
        private String productid;
        private String classifyid;

        public OrderProductView() {
        }

        public OrderProductView(double price, String productid, String classifyid) {
            this.price = price;
            this.productid = productid;
            this.classifyid = classifyid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getClassifyid() {
            return classifyid;
        }

        public void setClassifyid(String classifyid) {
            this.classifyid = classifyid;
        }
    }
}
