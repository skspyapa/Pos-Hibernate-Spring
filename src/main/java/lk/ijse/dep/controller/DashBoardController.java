package lk.ijse.dep.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashBoardController {
    public Label lblTitle;
    public JFXButton Manage_Cus;
    public JFXButton Manage_Itm;
    public JFXButton plc_Order;
    public JFXButton viw_Order;


    public void initialize() {

        TranslateTransition tt = new TranslateTransition(Duration.millis(5000), lblTitle);
        tt.setFromX(-2500);
        tt.setToX(0);

        RotateTransition rt = new RotateTransition(Duration.millis(3000), lblTitle);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();
        tt.play();

    }

    public void Manage_Itm_Clicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/ManageItem.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage) Manage_Itm.getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.setTitle("ItemTM Form");
        mainStage.centerOnScreen();
        mainStage.show();
    }

    public void Manage_Cus_Clicked(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/view/ManageCustomer.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage) Manage_Cus.getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.setTitle("CustomerTM Form");
        mainStage.centerOnScreen();
        mainStage.show();

    }

    public void plc_Order_Clicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrder.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage) plc_Order.getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.setTitle("Place Order");
        mainStage.centerOnScreen();
        mainStage.show();
    }

    public void viw_Order_Clicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/SearchOrders.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainStage = (Stage) plc_Order.getScene().getWindow();
        mainStage.setScene(mainScene);
        mainStage.setTitle("View Order");
        mainStage.centerOnScreen();
        mainStage.show();
    }
}
