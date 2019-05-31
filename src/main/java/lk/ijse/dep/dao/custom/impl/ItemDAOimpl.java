package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.entity.Item;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
@Component
public class ItemDAOimpl implements ItemDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
this.session=session;
    }

    public void save(Item item) throws SQLException {
        session.save(item);
    }

    public void update(Item item) throws SQLException {
        session.merge(item);
    }

    public void delete(String code) throws SQLException {
        session.delete(session.load(Item.class,code));
            }

    public Item find(String code) throws SQLException {
        return session.find(Item.class,code);
    }

    public List<Item> findAll() throws SQLException {
       return session.createNativeQuery("SELECT * from Item",Item.class).list();
            }
    public String findMaxItemCode() throws SQLException {
        Object objects = session.createNativeQuery("SELECT code from item ORDER BY code DESC LIMIT 1").uniqueResult();
        return objects.toString();
    }
}

