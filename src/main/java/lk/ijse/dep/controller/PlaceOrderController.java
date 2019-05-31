package lk.ijse.dep.controller;

import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.business.custom.ItemBO;
import lk.ijse.dep.business.custom.OrdersBO;
import lk.ijse.dep.business.custom.impl.CustomerBOimpl;
import lk.ijse.dep.business.custom.impl.ItemBOimpl;
import lk.ijse.dep.business.custom.impl.OrdersBOimpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.dto.ItemDetailDTO;
import lk.ijse.dep.dto.OrdersDTO;
import lk.ijse.dep.entity.Customer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.dep.main.AppInitializer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import lk.ijse.dep.utiltm.OrderDetailTM;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderController {

    public JFXTextField txtOrderDate;
    public JFXTextField txtOrderID;
    public JFXTextField txtCustomerName;
    public ComboBox<String> cmbItemCode;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXButton btnAdd;
    public TableView<OrderDetailTM> tblOrder;
    public ComboBox<String> cmbCustomerID;
    public Label lblTotal;
    public Label lblPlaceOrder;
    public ObservableList<OrderDetailTM> orderDetailTMS;
    public String qtyOnHand;
    public LocalDate orderDate;
    public OrderDetailTM value;
    public int verifier = 0;
    public Label lblBack;
    public JFXButton btnReport;
    public Connection connection;
    CustomerBO customerBO = AppInitializer.ctx.getBean(CustomerBOimpl.class);
    ItemBO itemBO =AppInitializer.ctx.getBean(ItemBOimpl.class);
    OrdersBO ordersBO =AppInitializer.ctx.getBean(OrdersBOimpl.class);
    public void initialize() {

        lblTotal.setText("0.00");
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        orderDetailTMS = tblOrder.getItems();
        //Auto generate the date
        orderDate = LocalDate.now();
        txtOrderDate.setText(orderDate.toString());
        txtOrderDate.setEditable(false);
        orderIdGenerator();
//set customer ID


        try {
            List<CustomerDTO> customerDTOId = customerBO.getCustomerId();
            for (CustomerDTO customerDTO:customerDTOId) {
                cmbCustomerID.getItems().add(customerDTO.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    List<CustomerDTO> customerDTOS= customerBO.getAll();
                    for (CustomerDTO customerDTO:customerDTOS) {
                        if(customerDTO.getId().equals(newValue)){
                            txtCustomerName.setText(customerDTO.getName());
                            break;
                        }
                    }
                    txtCustomerName.setEditable(false);
                    cmbItemCode.requestFocus();
                } catch (Exception e) {
                }
            }
        });
        //set item code
        try {
            List<ItemDTO> allItems = itemBO.getAll();
            for (ItemDTO itemDTO:allItems) {
                cmbItemCode.getItems().add(itemDTO.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //set item description,qtyonhand,unitprice
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                tblOrder.refresh();
                if (cmbItemCode.getSelectionModel().selectedItemProperty().getValue() != null) {
                    try {
                        System.out.println("*");
                        ItemDTO itemDTO= itemDetail(cmbItemCode.getSelectionModel().selectedItemProperty().getValue().toString());
                        String description = itemDTO.getDescription();
                        String unitPrice = Double.toString(itemDTO.getUnitPrice());
                        qtyOnHand = Integer.toString(itemDTO.getQtyOnHand());
                        txtDescription.setText(description);
                        txtQtyOnHand.setText(qtyOnHand);
                        txtUnitPrice.setText(unitPrice);
                        txtDescription.setEditable(false);
                        txtUnitPrice.setEditable(false);
                        txtQtyOnHand.setEditable(false);
                        txtQty.requestFocus();
                        tblOrder.getSelectionModel().clearSelection();
                        btnAdd.setText("Add");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //when select table row add data to the field
        tblOrder.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                value = (OrderDetailTM) observable.getValue();
                if (value != null) {
                    cmbItemCode.getSelectionModel().select(value.getCode());
                    txtQty.setText(Integer.toString(value.getQty()));
                    try {
                        verifier = 1;
                        btnAdd.setText("Update");
                        txtQtyOnHand.setText(Integer.toString(itemDetail(value.getCode()).getQtyOnHand()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
//add changelistener for the textfield to list the changes in qtyonhand
        txtQtyOnHand.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    for (OrderDetailTM orderDetailTM : orderDetailTMS) {
                        if ((verifier != 1) && orderDetailTM.getCode().equals(cmbItemCode.getSelectionModel().getSelectedItem().toString()) && tblOrder.getItems().size() > 0 && (cmbItemCode.getSelectionModel().selectedItemProperty().getValue() != null)) {
                            txtQtyOnHand.setText(Integer.toString(Integer.parseInt(qtyOnHand) - orderDetailTM.getQty()));
                        }
                    }
                    //verifier=0;
                } catch (NumberFormatException e) {
                    txtQty.clear();
                    JOptionPane.showMessageDialog(null, "Please Enter Only Nmbers");
                }
            }

        });
        deleteButton();
    }

    public void deleteButton() {
        //delete button
        TableColumn<OrderDetailTM, OrderDetailTM> unfriendCol = new TableColumn<>("");
        unfriendCol.setMinWidth(90);
        unfriendCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        unfriendCol.setCellFactory(param -> new TableCell<OrderDetailTM, OrderDetailTM>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(OrderDetailTM orderDetailTM, boolean empty) {
                super.updateItem(orderDetailTM, empty);
                if (orderDetailTM == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        cmbItemCode.getSelectionModel().select(orderDetailTM.getCode());
                        tblOrder.getItems().remove(orderDetailTM);
                        try {
                            txtQtyOnHand.setText(Integer.toString(itemDetail(orderDetailTM.getCode()).getQtyOnHand()));
                            txtQty.clear();
                            double Total = 0;
                            calTotal();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        tblOrder.getColumns().add(unfriendCol);
    }

    public ItemDTO itemDetail(String str) throws Exception {
        ItemDTO item = itemBO.get(str);
        return item;
    }

    public void btnAdd_Action(ActionEvent actionEvent) {
        //validate qty text field
        try {
            if (Integer.parseInt(txtQtyOnHand.getText()) >= Integer.parseInt(txtQty.getText())) {
                double Total = 0;
                //verifier=1;

                //get the qty to integer
                int qty = Integer.parseInt(txtQty.getText());
                //check same item is in the table & increment if it is there
                for (OrderDetailTM orderDetailTM : orderDetailTMS) {
                    if (cmbItemCode.getSelectionModel().getSelectedItem().toString().equals(orderDetailTM.getCode())) {
                        if (verifier != 1) {
                            orderDetailTM.setQty(orderDetailTM.getQty() + Integer.parseInt(txtQty.getText()));
                            qty = orderDetailTM.getQty() + Integer.parseInt(txtQty.getText());
                        } else {
                            orderDetailTM.setQty(Integer.parseInt(txtQty.getText()));
                            qty = Integer.parseInt(txtQty.getText());
                        }
                        orderDetailTM.setTotal(Double.parseDouble(txtUnitPrice.getText()) * qty);
                        tblOrder.refresh();
                        calTotal();
                        verifier = 0;
                        txtQtyOnHand.clear();
                        txtQty.clear();
                        btnAdd.setText("Add");
                        tblOrder.getSelectionModel().clearSelection();
                        return;
                    }
                }

                orderDetailTMS.add(new OrderDetailTM(
                        cmbItemCode.getSelectionModel().getSelectedItem().toString(),
                        txtDescription.getText(), Integer.parseInt(txtQty.getText()),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Double.parseDouble(txtUnitPrice.getText()) * qty));
                tblOrder.refresh();
                calTotal();
                txtQtyOnHand.clear();
                txtQty.clear();
                txtQty.requestFocus();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please Enter A Valid Quantity");
            txtQty.clear();
            txtQty.requestFocus();
        }
    }

    public void txtQty_Action(ActionEvent actionEvent) {
        btnAdd.requestFocus();
        //btnAdd.setText("Add");
    }

    public void txtOrderID_Action(ActionEvent actionEvent) {
        cmbCustomerID.requestFocus();
    }

    public void lblPlaceOrder_Action(MouseEvent mouseEvent) {
        if (orderDetailTMS.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Add Atleast One Item For Order");
        } else {
                List<ItemDetailDTO> ordersDTOS=new ArrayList<>();
                for (OrderDetailTM orderDetailTM:tblOrder.getItems()) {
                    ItemDetailDTO itemDetailDTO = new ItemDetailDTO(txtOrderID.getText(),
                            orderDetailTM.getCode(),
                            orderDetailTM.getQty(),
                            orderDetailTM.getUnitPrice());
                    ordersDTOS.add(itemDetailDTO);
                }
            try {
                CustomerDTO customerDTO = customerBO.get(cmbCustomerID.getValue());
                Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getSalary());
            OrdersDTO ordersDTO = new OrdersDTO(txtOrderID.getText(), orderDate, customer, ordersDTOS);
                boolean result = ordersBO.placeOrder(ordersDTO);
                if(result){
                    new Alert(Alert.AlertType.INFORMATION,"YOUR ORDER PLACED SUCCESSFULLY",ButtonType.OK).show();

                        JasperReport jasperReport  = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/reports/PlaceOrder.jasper"));
                        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
                        objectObjectHashMap.put("orderID", txtOrderID.getText());
                        objectObjectHashMap.put("OrderDate", txtOrderDate.getText());
                        objectObjectHashMap.put("CustomerID", cmbCustomerID.getValue().toString());
                        objectObjectHashMap.put("CustomerName", txtCustomerName.getText());
                        objectObjectHashMap.put("total", Double.parseDouble(lblTotal.getText()));
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, objectObjectHashMap, new JRBeanCollectionDataSource(orderDetailTMS));
                        JasperViewer.viewReport(jasperPrint, false);

                }else {
                    new Alert(Alert.AlertType.INFORMATION,"YOUR ORDER NOT PLACED",ButtonType.OK).show();
                }
//                InputStream is = this.getClass().getResourceAsStream("/reports/PlaceOrder.jrxml");
//                JasperDesign jasperDesign = JRXmlLoader.load(is);
//                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                } catch (JRException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        orderIdGenerator();
        orderDetailTMS.clear();
        tblOrder.refresh();
        cmbCustomerID.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        txtQtyOnHand.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtCustomerName.clear();
        txtQty.clear();
        lblTotal.setText("0.00");

    }

    //Getting the orderid
    public void orderIdGenerator() {
        try {
            int maxIndexInt;
            String maxIndex =ordersBO.getdMaxId();
            if (maxIndex.equals("0")){
                maxIndexInt=0;
            }else {
                maxIndexInt = Integer.parseInt(maxIndex.substring(1));
            }
            if (++maxIndexInt < 10) {
                txtOrderID.setText("D00" + maxIndexInt);
            } else if (maxIndexInt < 100) {
                txtOrderID.setText("D0" + maxIndexInt);
            } else if (maxIndexInt < 1000) {
                txtOrderID.setText("D" + maxIndexInt);
            }
            txtOrderID.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lblBack_Clicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene mainScene = new Scene(root);
        Stage dashStage = (Stage) lblBack.getScene().getWindow();
        dashStage.setScene(mainScene);
        dashStage.setTitle("DashBoard");
        dashStage.centerOnScreen();
        dashStage.show();

    }

    public void calTotal() {
        double Total = 0;
        for (OrderDetailTM orderDetailsTM : orderDetailTMS) {
            Total += orderDetailsTM.getTotal();
        }
        lblTotal.setText(Total + "");
    }
}
