package uaspbo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainApp extends Application {
    private double x = 0;
    private double y = 0;
    
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        root.setOnMousePressed((MouseEvent event)->{
            x = event.getSceneX();
            y = event.getSceneY();
        });    
        
        Scene scene = new Scene (root);
        
        stage.setScene(scene);
        stage.show(); 
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}