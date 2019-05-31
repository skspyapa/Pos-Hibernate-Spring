package lk.ijse.dep.dao.custom;

import lk.ijse.dep.dao.CrudDAO;
import lk.ijse.dep.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {

    String findMaxItemCode() throws SQLException;
}
