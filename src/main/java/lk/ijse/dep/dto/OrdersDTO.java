package lk.ijse.dep.dto;

import lk.ijse.dep.entity.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersDTO{
private String id;
private LocalDate date;
private Customer customerId;
private String maxId;
private List<ItemDetailDTO> itemDetailDTOS= new ArrayList<>();
    public OrdersDTO() {
    }

    public OrdersDTO(String maxId) {
        this.maxId = maxId;
    }

    public OrdersDTO(String id, LocalDate date, Customer customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }

    public OrdersDTO(String id, LocalDate date, Customer customerId, List<ItemDetailDTO> itemDetailDTOS) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.setItemDetailDTOS(itemDetailDTOS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    public List<ItemDetailDTO> getItemDetailDTOS() {
        return itemDetailDTOS;
    }

    public void setItemDetailDTOS(List<ItemDetailDTO> itemDetailDTOS) {
        this.itemDetailDTOS = itemDetailDTOS;
    }
}
