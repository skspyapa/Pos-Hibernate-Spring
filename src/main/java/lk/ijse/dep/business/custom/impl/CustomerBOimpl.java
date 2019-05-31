package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.dao.custom.CustomerDAO;
import lk.ijse.dep.dbpos.HibernateUtil;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.entity.Customer;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CustomerBOimpl implements CustomerBO {
    @Autowired
    private CustomerDAO customerDAO;

    public List<CustomerDTO> getAll() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            customerDAO.setSession(session);
            List<CustomerDTO> collect = customerDAO.findAll().stream().map(customer -> new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary())).collect(Collectors.toList());
            session.getTransaction().commit();
            return collect;
        }
}
            public boolean save(CustomerDTO dto) throws Exception {
                try(Session session = HibernateUtil.getSessionFactory().openSession()){
                    session.beginTransaction();
                    customerDAO.setSession(session);
                    customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
                    session.getTransaction().commit();
                    return true;
                }catch (Exception ex){
                    throw ex;
                }
            }

            public boolean remove(String  id) throws Exception {
                try(Session session = HibernateUtil.getSessionFactory().openSession()){
                    session.beginTransaction();
                    customerDAO.setSession(session);
                    customerDAO.delete(id);
                    session.getTransaction().commit();
                    return true;
                }catch (Exception ex){
                    throw ex;
                }
            }

            public boolean update(CustomerDTO dto) throws Exception {
                try(Session session = HibernateUtil.getSessionFactory().openSession()){
                    session.beginTransaction();
                    customerDAO.setSession(session);
                    customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
                    session.getTransaction().commit();
                    return true;
                }catch (Exception ex){
                    throw ex;
                }
            }
            public CustomerDTO get(String id) throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    session.beginTransaction();
                    customerDAO.setSession(session);
                    Customer customer = customerDAO.find(id);
                    session.getTransaction().commit();
                    return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
                }
            }
            public List<CustomerDTO> getCustomerId() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    session.beginTransaction();
                    customerDAO.setSession(session);
                    List<CustomerDTO> collect = customerDAO.findAll().stream().map(customer -> new CustomerDTO(customer.getId())).collect(Collectors.toList());
                    session.getTransaction().commit();
                    return collect;
                }
            }
            public String getMaxCustId() throws Exception {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    session.beginTransaction();
                    customerDAO.setSession(session);
                    String maxCustId = customerDAO.findMaxCustId();
                    session.getTransaction().commit();
                    return maxCustId;
                }
            }
}
