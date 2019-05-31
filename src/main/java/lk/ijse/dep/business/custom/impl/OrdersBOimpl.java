package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.OrdersBO;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.dao.custom.ItemDetailDAO;
import lk.ijse.dep.dao.custom.OrdersDAO;
import lk.ijse.dep.dbpos.HibernateUtil;
import lk.ijse.dep.dto.ItemDetailDTO;
import lk.ijse.dep.dto.OrdersDTO;
import lk.ijse.dep.entity.Item;
import lk.ijse.dep.entity.ItemDetail;
import lk.ijse.dep.entity.Orders;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class OrdersBOimpl implements OrdersBO {
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ItemDetailDAO itemDetailDAO;

    public List<OrdersDTO> getAll() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            List<Orders> allOrders = ordersDAO.findAll();
            List<OrdersDTO> dtos = new ArrayList<>();
            for (Orders orders : allOrders) {
                OrdersDTO ordersDTO = new OrdersDTO(orders.getId(), orders.getDate(), orders.getCustomer());
                dtos.add(ordersDTO);
            }
            session.getTransaction().commit();
            return dtos;
        }catch (Exception ex){
            throw ex;
        }
    }

    public boolean save(OrdersDTO dto) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Orders orders = new Orders(dto.getId(), dto.getDate(), dto.getCustomerId());
            ordersDAO.save(orders);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            throw ex;
        }


    }

    public boolean remove(String id) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            ordersDAO.delete(id);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    public boolean update(OrdersDTO dto) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Orders orders = new Orders(dto.getId(), dto.getDate(), dto.getCustomerId());
            ordersDAO.update(orders);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public OrdersDTO get(String id) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Orders orders = ordersDAO.find(id);
            session.getTransaction().commit();
            return new OrdersDTO(orders.getId(), orders.getDate(), orders.getCustomer());
        }catch (Exception ex){
            throw ex;
        }
    }

    public String getdMaxId() throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            String maxId = ordersDAO.findMaxId();
            session.getTransaction().commit();
            return maxId;
        }catch (NullPointerException ex){
            return "0";
        }
    }

    public boolean placeOrder(OrdersDTO dto)throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            ordersDAO.save(new Orders(dto.getId(), dto.getDate(), dto.getCustomerId()));
            for (ItemDetailDTO itemDetailDTO : dto.getItemDetailDTOS()) {
                itemDetailDAO.save(new ItemDetail(itemDetailDTO.getOrderId(), itemDetailDTO.getItemCode(), itemDetailDTO.getQty(), itemDetailDTO.getUnitPrice()));
                Item item = itemDAO.find(itemDetailDTO.getItemCode());
                int qty = item.getQtyOnHand() - itemDetailDTO.getQty();
                item.setQtyOnHand(qty);
                itemDAO.update(item);
            }
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
                throw ex;
        }
//        Connection connection = DBConnection.getInstance().getConnection();
//        boolean result = false;
//        try {
//            connection.setAutoCommit(false);
//            result = ordersDAO.save(new Orders(lk.ijse.dep.dto.getId(), lk.ijse.dep.dto.getDate(), lk.ijse.dep.dto.getCustomerId()));
//            if (!result) {
//                connection.rollback();
//                return false;
//            }
//            for (ItemDetailDTO itemDetailDTO : lk.ijse.dep.dto.getItemDetailDTOS()) {
//                result = itemDetailDAO.save(new ItemDetail(itemDetailDTO.getOrderId(), itemDetailDTO.getItemCode(), itemDetailDTO.getQty(), itemDetailDTO.getUnitPrice()));
//                if (!result) {
//                    connection.rollback();
//                    return false;
//                }
//                Item item = itemDAO.find(itemDetailDTO.getItemCode());
//                int qty = item.getQtyOnHand() - itemDetailDTO.getQty();
//                item.setQtyOnHand(qty);
//                result = itemDAO.update(item);
//                if (!result) {
//                    connection.rollback();
//                    return false;
//                }
//            }
//            connection.commit();
//            return true;
//        } catch (SQLException ex) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            ex.printStackTrace();
//            return false;
//        } catch (Throwable t) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            t.printStackTrace();
//            return false;
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return true;
    }
}
