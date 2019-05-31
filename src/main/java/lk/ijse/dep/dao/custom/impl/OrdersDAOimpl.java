package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.custom.OrdersDAO;
import lk.ijse.dep.entity.Orders;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OrdersDAOimpl implements OrdersDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
this.session=session;
    }

    public void save(Orders orders) throws Exception {
       session.save(orders);
    }

    public void update(Orders orders) throws Exception {
    session.merge(orders);
    }

    public void delete(String id) throws Exception {
    session.delete(session.load(Orders.class,id));
    }

    public Orders find(String id) throws Exception{
       return session.find(Orders.class,id);
    }

    public List<Orders> findAll() throws Exception {
return session.createNativeQuery("SELECT * from Orders",Orders.class).list();
    }

    @Override
    public String findMaxId() throws Exception {
        String objects = session.createNativeQuery("SELECT id from `order` ORDER BY id DESC LIMIT 1",String.class).uniqueResult();

        return objects; }
}

