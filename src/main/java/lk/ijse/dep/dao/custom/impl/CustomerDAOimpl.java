package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.custom.CustomerDAO;
import lk.ijse.dep.entity.Customer;
import org.hibernate.Session;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerDAOimpl implements CustomerDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
this.session=session;
    }

    public void save(Customer customer) throws Exception {
        session.save(customer);
            }

    public void update(Customer customer) throws Exception {
        session.merge(customer);
    }
    public void delete(String id) throws Exception {
        session.delete(session.load(Customer.class,id));
    }

    public Customer find(String id) throws Exception {
       return session.find(Customer.class,id);
    }

    public List<Customer> findAll() throws Exception {
        return session.createNativeQuery("SELECT * FROM customer",Customer.class).list();
    }
    public String findMaxCustId() throws Exception {
        Object objects = session.createNativeQuery("SELECT id from customer ORDER BY id DESC LIMIT 1").uniqueResult();
        return objects.toString();
    }


}
