package lk.ijse.dep.entity;

import javax.persistence.*;

@Entity
@Table(name = "item_detail")
public class ItemDetail extends SuperEntity{
    @EmbeddedId
    private ItemDetailPK itemdetailPK;
    private int qty;
    private double unitPrice;
    @ManyToOne
    @JoinColumn(name = "orderId",referencedColumnName = "id",updatable = false,insertable = false)
    private Orders orders;
    @ManyToOne
    @JoinColumn(name = "itemCode",referencedColumnName = "code",updatable = false,insertable = false)
    private Item item;
    public ItemDetail() {
    }

    public ItemDetail(ItemDetailPK itemdetailPK, int qty, double unitPrice) {
        this.itemdetailPK = itemdetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public ItemDetail(String orderId, String itemCode, int qty, double unitPrice) {
        this.itemdetailPK = new ItemDetailPK(orderId, itemCode);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public ItemDetailPK getItemdetailPK() {
        return itemdetailPK;
    }

    public void setItemdetailPK(ItemDetailPK itemdetailPK) {
        this.itemdetailPK = itemdetailPK;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "ItemDetail{" +
                "ItemDetailPK=" + itemdetailPK +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
