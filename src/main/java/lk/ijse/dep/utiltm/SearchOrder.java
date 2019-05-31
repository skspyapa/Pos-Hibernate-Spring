package lk.ijse.dep.utiltm;

public class SearchOrder {
    private String orderId;
    private String orderDate;
    private String customerId;
    private String customerName;
    private double total;

    public SearchOrder() {
    }

    public SearchOrder(String orderId, String orderDate, String customerId, String customerName, double total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.customerName = customerName;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SearchOrder{" +
                "orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", total=" + total +
                '}';
    }
}
