package lk.ijse.dep.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`Orders`")
public class Orders extends SuperEntity{
    @Id
    private String id;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "customer_Id",referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy ="orders",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ItemDetail> itemDetails=new ArrayList<>();
    public Orders() {
    }

    public Orders(String id, LocalDate date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer=customer;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customer=" + customer +
                '}';
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }
    public void addItemDetail(ItemDetail itemDetail){
        itemDetail.setOrders(this);
        this.getItemDetails().add(itemDetail);
    }
}
