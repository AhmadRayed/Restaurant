package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent parentRoot = FXMLLoader.load(getClass().getResource("/Application/View/Login.fxml"));
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setIconified(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(parentRoot));
            primaryStage.show();

        }catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}