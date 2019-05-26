package gui.frame;/**
 * create by zhong
 * gui
 * Date 2019/5/12
 */

import ftp.ApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFrame extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ApplicationContext context=null;
        try {
            context=new ApplicationContext("ftp\\handler");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(new Scene(root));
        MainController mainController=fxmlLoader.getController();

        mainController.init();
        mainController.getContext().setAppContext(context);

        primaryStage.show();
    }
}
