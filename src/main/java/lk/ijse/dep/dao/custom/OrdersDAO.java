package lk.ijse.dep.dao.custom;

import lk.ijse.dep.dao.CrudDAO;
import lk.ijse.dep.entity.Orders;

public interface OrdersDAO extends CrudDAO<Orders,String> {

    String findMaxId() throws Exception;
}
