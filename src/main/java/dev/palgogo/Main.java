package dev.palgogo;

import dev.palgogo.utils.CvUtilsFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {
    static {
        System.load(Paths.get(".").toAbsolutePath().normalize().toString() + "\\libs\\opencv_java420.dll");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(15.0);
        root.setAlignment(Pos.CENTER);

        Button launchButton = new Button("Launch");
        launchButton.setOnAction(this::onClickButton);
        root.getChildren().add(launchButton);

        Scene scene = new Scene(root, 400.0, 150.0);
        primaryStage.setTitle("OpenCV " + Core.VERSION);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        primaryStage.show();
    }

    private void onClickButton(ActionEvent event) {
        Path path = Paths.get("src", "test", "resources", "photos", "meXS.jpg");
        Path fullResourcePath = path.toAbsolutePath();
        //Load image from file
        Mat img = Imgcodecs.imread(/*path to image*/fullResourcePath.toString());

        if (img.empty()) {
            System.out.println("can't load an image");
            return;
        }
        //process image
        //show in separate window
        CvUtilsFX.showImage(img, "cool image");
    }

    //TODO load lybrary in the run proccess search how
}