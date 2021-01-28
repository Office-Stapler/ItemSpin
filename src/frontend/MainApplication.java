package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/Main.fxml"));
        stage.setTitle("Gatcha spin");
        stage.setWidth(1200);
        stage.setHeight(800);
        MainController controller = new MainController();
        controller.setWindow(stage);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        // set up the stage
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
