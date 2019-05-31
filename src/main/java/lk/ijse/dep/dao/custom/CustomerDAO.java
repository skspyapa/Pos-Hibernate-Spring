package lk.ijse.dep.dao.custom;

import lk.ijse.dep.dao.CrudDAO;
import lk.ijse.dep.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String findMaxCustId() throws Exception;
}
