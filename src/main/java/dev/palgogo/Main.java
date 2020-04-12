package dev.palgogo;

import dev.palgogo.chapter10.CornerDetection;
import dev.palgogo.labs.First;
import dev.palgogo.labs.Fourth;
import dev.palgogo.labs.Third;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.nio.file.Paths;
import java.util.List;

/**
 * VM arguments
 * <p>
 * --module-path D:\Programms\javafx\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml
 */
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

        final List<Button> labButtons = getLabButtons();


        Button launchButton = new Button("Launch");
        launchButton.setOnAction(this::onClickButton);
        root.getChildren().add(launchButton);

        root.getChildren().addAll(labButtons);

        Scene scene = new Scene(root, 400.0, 450.0);
        primaryStage.setTitle("OpenCV " + Core.VERSION);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        primaryStage.show();
    }

    public List<Button> getLabButtons() {

        Button firstLabButton = new Button("1 lab");
        Button secondLabButton = new Button("2 lab");
        Button thirdLabButton = new Button("3 lab");
        Button fourthLabButton = new Button("4 lab");
        Button fifthLabButton = new Button("5 lab");
        Button sixthLabButton = new Button("6 lab");
        Button seventhLabButton = new Button("7 lab");
        Button eighthLabButton = new Button("8 lab");

        firstLabButton.setOnAction(this::onFirstLabClickButton);
        secondLabButton.setOnAction(this::onSecondLabClickButton);
        thirdLabButton.setOnAction(this::onThirdLabClickButton);
        fourthLabButton.setOnAction(this::onFourthLabClickButton);
        fifthLabButton.setOnAction(this::onFifthLabClickButton);
        sixthLabButton.setOnAction(this::onSixthLabClickButton);
        seventhLabButton.setOnAction(this::onSeventhLabClickButton);
        eighthLabButton.setOnAction(this::onEighthLabClickButton);

        return List.of(firstLabButton, secondLabButton,
                thirdLabButton, fourthLabButton,
                fifthLabButton, sixthLabButton,
                seventhLabButton, eighthLabButton);
    }


    private void onFirstLabClickButton(ActionEvent event) {
        new First().findAndDrawContour();
    }

    private void onSecondLabClickButton(ActionEvent event) {

    }

    private void onThirdLabClickButton(ActionEvent event) {
        new Third().applyFilters();
    }

    private void onFourthLabClickButton(ActionEvent event) {
        new Fourth().facesOnVideo();
    }

    private void onFifthLabClickButton(ActionEvent event) {

    }

    private void onSixthLabClickButton(ActionEvent event) {

    }

    private void onSeventhLabClickButton(ActionEvent event) {

    }

    private void onEighthLabClickButton(ActionEvent event) {

    }

    private void onClickButton(ActionEvent event) {
        new CornerDetection().keyPointsComparison();
    }

    //todo add seting path to image

    //TODO load lybrary in the run proccess search how
}