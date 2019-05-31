package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.custom.ItemDetailDAO;
import lk.ijse.dep.entity.ItemDetail;
import lk.ijse.dep.entity.ItemDetailPK;
import org.hibernate.Session;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
@Component
public class ItemDetailDAOimpl implements ItemDetailDAO {
    private Session session;

    @Override
    public void setSession(Session session) {
this.session=session;
    }

    public void save(ItemDetail itemDetail) throws SQLException {
session.save(itemDetail);
    }

    public void update(ItemDetail itemDetail) throws SQLException {
        session.merge(itemDetail);
    }

    public void delete(ItemDetailPK itemDetailPK) throws SQLException {
        session.delete(session.load(ItemDetail.class, (Serializable) itemDetailPK));
            }

    public ItemDetail find(ItemDetailPK itemDetailPK) throws SQLException {
       return session.find(ItemDetail.class,itemDetailPK);
    }

    public List<ItemDetail> findAll() throws SQLException {
return session.createNativeQuery("SELECT * FROM itemdetail",ItemDetail.class).list();
    }
}


