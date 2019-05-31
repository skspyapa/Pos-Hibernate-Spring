package lk.ijse.dep.business.custom;

import lk.ijse.dep.business.SuperBO;
import lk.ijse.dep.dto.OrdersDTO;

public interface OrdersBO extends SuperBO<OrdersDTO,String> {

    String getdMaxId() throws Exception;

    boolean placeOrder(OrdersDTO dto) throws Exception;
}
