package lk.ijse.dep.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppInitializer extends Application {
    public static AnnotationConfigApplicationContext ctx;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("DashBoard");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
