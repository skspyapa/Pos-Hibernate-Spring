package lk.ijse.dep.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ItemDetailPK implements Serializable {
    private String orderId;
    private String itemCode;
    public ItemDetailPK() {
    }

    public ItemDetailPK(String orderId, String itemCode) {
        this.orderId = orderId;
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return "ItemDetailPK{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }
}
