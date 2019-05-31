package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXTextField;
import lk.ijse.dep.dbpos.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import lk.ijse.dep.utiltm.SearchOrder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SearchOrderController {
    public TableView<SearchOrder> tblSearchOrder;
    //Connection connection;
    public JFXTextField txtSearch;
    public Label lbl_Back;
    public ObservableList<SearchOrder> items;

    public void initialize() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        txtSearch.requestFocus();
        tblSearchOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblSearchOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblSearchOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblSearchOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblSearchOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        items = tblSearchOrder.getItems();
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                if (newValue.isEmpty()) {
                    tblSearchOrder.getItems().clear();
                    return;
                } else {
                    tblSearchOrder.getItems().clear();
                    try(Session session=sessionFactory.openSession()) {
                        session.beginTransaction();

                        NativeQuery nativeQuery1 = session.createNativeQuery("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having o.id like ?1");
                        nativeQuery1.setParameter(1, newValue + "%");
                        List<Object[]> list1 = nativeQuery1.list();

                        for (Object[] objects:list1) {
                            items.add(new SearchOrder((String) objects[0],df.format(objects[1]),(String) objects[2],(String) objects[3],Double.parseDouble(objects[4].toString())));
                        }

                        NativeQuery nativeQuery2 = session.createNativeQuery("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having o.date like ?1");
                        nativeQuery2.setParameter(1, newValue + "%");
                        List<Object[]> list2 = nativeQuery2.list();

                        for (Object[] objects:list2) {
                            items.add(new SearchOrder((String) objects[0],df.format(objects[1]),(String) objects[2],(String) objects[3],Double.parseDouble(objects[4].toString())));
                        }
//                        stmt1 = SearchOrderController.this.connection.prepareStatement("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having o.date like ?");
//                        stmt1.setString(1, newValue + "%");
//                        rst1 = stmt1.executeQuery();
//                        if (rst1.isBeforeFirst()) {
//                            while (rst1.next()) {
//                                items.add(new SearchOrder(rst1.getString(1), rst1.getString(2), rst1.getString(3), rst1.getString(4), rst1.getDouble(5)));
//                            }
//                        }
                        NativeQuery nativeQuery3 = session.createNativeQuery("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having o.customerId like ?1");
                        nativeQuery3.setParameter(1, newValue + "%");
                        List<Object[]> list3 = nativeQuery3.list();

                        for (Object[] objects:list3) {
                            items.add(new SearchOrder((String) objects[0],df.format(objects[1]),(String) objects[2],(String) objects[3],Double.parseDouble(objects[4].toString())));
                        }
//                        stmt1 = SearchOrderController.this.connection.prepareStatement("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having o.customerId like ?");
//                        stmt1.setString(1, newValue + "%");
//                        rst1 = stmt1.executeQuery();
//                        if (rst1.isBeforeFirst()) {
//                            while (rst1.next()) {
//                                items.add(new SearchOrder(rst1.getString(1), rst1.getString(2), rst1.getString(3), rst1.getString(4), rst1.getDouble(5)));
//                            }
//                        }
                        NativeQuery nativeQuery4 = session.createNativeQuery("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having c.name like ?1");
                        nativeQuery4.setParameter(1, newValue + "%");
                        List<Object[]> list4 = nativeQuery4.list();
                        if(list4.size()>0) {
                            for (Object[] objects : list4) {
                                items.add(new SearchOrder((String) objects[0], df.format(objects[1]), (String) objects[2], (String) objects[3], Double.parseDouble(objects[4].toString())));
                            }
                        }
//                        stmt1 = SearchOrderController.this.connection.prepareStatement("select o.id,o.date,o.customerId,c.name,sum((id.qty)*(id.unitPrice)) as Total from orders o, customer c,itemdetail id where o.customerId=c.id&& o.id=id.orderId group by id having c.name like ?");
//                        stmt1.setString(1, newValue + "%");
//                        rst1 = stmt1.executeQuery();
//                        if (rst1.isBeforeFirst()) {
//                            while (rst1.next()) {
//                                items.add(new SearchOrder(rst1.getString(1), rst1.getString(2), rst1.getString(3), rst1.getString(4), rst1.getDouble(5)));
//                            }
//                        }
                        session.getTransaction().commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
           }
    });
            }

            public void txtSearch(ActionEvent actionEvent) {
            }

            public void lbl_Back_Action(MouseEvent mouseEvent) throws IOException {
                Parent root = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
                Scene mainScene = new Scene(root);
                Stage dashStage = (Stage) lbl_Back.getScene().getWindow();
                dashStage.setScene(mainScene);
                dashStage.setTitle("DashBoard");
                dashStage.centerOnScreen();
                dashStage.show();

            }
}
