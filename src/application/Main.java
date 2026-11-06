package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Memuat file FXML dari folder views
        Parent root = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
        
        // Membuat Scene
        Scene scene = new Scene(root, 450, 500); // Lebar 450, Tinggi 500
        
        primaryStage.setTitle("Daftar Kegiatan Harian");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}