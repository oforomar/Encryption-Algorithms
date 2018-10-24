import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        final double WIDTH  = 500;
        final double HEIGHT = 300;


        //TextFields for message input and encryption output
        TextField msgText = new TextField("Enter Message Here!");
        msgText.setAlignment(Pos.TOP_LEFT);
        msgText.setPrefHeight(HEIGHT);
        msgText.setPrefWidth(WIDTH);
        
        TextField cipherText = new TextField("Cipher Here!");
        cipherText.setAlignment(Pos.TOP_LEFT);
        cipherText.setPrefHeight(HEIGHT);
        cipherText.setPrefWidth(WIDTH);


        //Controls items for key textfield and clear button
        TextField keyValue = new TextField("0");

        Button clearButton = new Button("Clear!");
        clearButton.setOnAction(event -> {
            cipherText.clear();
            msgText.clear();
        });

        //Assembling UI control elements in a central vBox
        VBox controlsPane = new VBox(20);
        controlsPane.setPadding(new Insets(5, 5, 5, 5));
        controlsPane.setAlignment(Pos.CENTER);
        controlsPane.setFillWidth(true);
        controlsPane.setPrefWidth(WIDTH/4);
        controlsPane.getChildren().addAll(keyValue, clearButton);

        //Assembling text fields with controls
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(50, 50, 50, 50));
        pane.setHgap(8);
        pane.setVgap(8);

        //Encrypt text while typing
        msgText.setOnMousePressed(event -> {
            msgText.clear();
            cipherText.clear();
        });

        msgText.setOnKeyTyped(event -> {
            cipherText.appendText(CaesarCipher.encrypt(event.getCharacter(), Integer.parseInt(keyValue.getText())));
        });


        //Decrypt text while typing
        cipherText.setOnKeyTyped(event -> {
            msgText.appendText(CaesarCipher.decrypt(event.getCharacter(), Integer.parseInt(keyValue.getText())));
        });

        cipherText.setOnMousePressed(event -> {
            msgText.clear();
            cipherText.clear();
        });


        pane.getChildren().addAll(msgText, controlsPane, cipherText);

        Scene scene = new Scene(pane, 1250, 400);

        primaryStage.setTitle("Encryption Algorithms");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
