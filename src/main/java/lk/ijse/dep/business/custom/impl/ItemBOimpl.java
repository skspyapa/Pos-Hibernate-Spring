package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.ItemBO;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.dbpos.HibernateUtil;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.entity.Item;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ItemBOimpl implements ItemBO {
    @Autowired
    private ItemDAO itemDAO;

public List<ItemDTO> getAll() throws Exception {

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        session.beginTransaction();
        itemDAO.setSession(session);
        List<ItemDTO> collect = itemDAO.findAll().stream().map(item -> new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand())).collect(Collectors.toList());
        session.getTransaction().commit();
        return collect;
    }
}
    public boolean save(ItemDTO dto) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            itemDAO.setSession(session);
            itemDAO.save(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    public boolean remove(String  code) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            itemDAO.setSession(session);
            itemDAO.delete(code);
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }

    public boolean update(ItemDTO dto) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            itemDAO.setSession(session);
            itemDAO.update(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
            session.getTransaction().commit();
            return true;
        }catch (Exception ex){
            throw ex;
        }
    }
    public ItemDTO get(String code) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            itemDAO.setSession(session);
            Item item = itemDAO.find(code);
            session.getTransaction().commit();
            return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
        }

    }

    public String getMaxItemCode() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            itemDAO.setSession(session);
            String maxItemCode = itemDAO.findMaxItemCode();
            session.getTransaction().commit();
         return maxItemCode;
        }
    }
}
