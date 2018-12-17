import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.math.BigInteger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        final double WIDTH  = 500;
        final double HEIGHT = 300;

        // Algorithm selection box
        ObservableList<String> algorithms = FXCollections.observableArrayList("Caesar", "Playfair", "DES", "RC4", "RSA");
        ChoiceBox<String> algorithmChoiceBox = new ChoiceBox<>(algorithms);
        algorithmChoiceBox.getSelectionModel().select(0);

        //TextFields for message input and encryption output
        TextField msgTextBox = new TextField("Enter Message Here!");
        msgTextBox.setAlignment(Pos.TOP_LEFT);
        msgTextBox.setPrefHeight(HEIGHT);
        msgTextBox.setPrefWidth(WIDTH);
        
        TextField cipherTextBox = new TextField("Cipher Here!");
        cipherTextBox.setAlignment(Pos.TOP_LEFT);
        cipherTextBox.setPrefHeight(HEIGHT);
        cipherTextBox.setPrefWidth(WIDTH);

        // Controls items for key textfield and clear button
        // Keyword Textbox
        TextField keyValueTF = new TextField("0");

        // Clear Button
        Button clearButton = new Button("Clear!");
        clearButton.setOnAction(event -> {
            cipherTextBox.clear();
            msgTextBox.clear();
        });

        RSA rsa = new RSA();

        // Encrypt and Decrypt buttons
        Button encryptButton = new Button("Encrypt");
        encryptButton.setOnAction(event -> {

            String s =algorithmChoiceBox.getValue() ;

            if (s.equals("DES")){
                DES des = new DES(keyValueTF.getText());
                cipherTextBox.setText(des.encrypt(msgTextBox.getText()));
            }else if (s.equals("Caesar")){
                cipherTextBox.setText(CaesarCipher.encrypt(msgTextBox.getText(), Integer.parseInt(keyValueTF.getText())));
            }else if (s.equals("Playfair")){
                Playfair playfair = new Playfair();
                playfair.createMatrix(keyValueTF.getText());
                cipherTextBox.setText(playfair.encrypt(msgTextBox.getText()));
            }else if (s.equals("RC4")){
                RC4 rC4 = new RC4(keyValueTF.getText());
                cipherTextBox.setText(rC4.encrypt(msgTextBox.getText()));
            }else if (s.equals("RSA")){
                //RSA rsa = new RSA();
                cipherTextBox.setText(rsa.encrypt(new BigInteger(msgTextBox.getText())));
            }
        });
        Button decryptButton = new Button("Decrypt");
        decryptButton.setOnAction(event -> {

            String s =algorithmChoiceBox.getValue() ;

            if (s.equals("DES")){
                DES des = new DES(keyValueTF.getText());
                cipherTextBox.setText(des.decrypt(msgTextBox.getText()));
            }else if (s.equals("Caesar")){
                cipherTextBox.setText(CaesarCipher.decrypt(msgTextBox.getText(), Integer.parseInt(keyValueTF.getText())));
            }else if (s.equals("Playfair")){
                Playfair playfair = new Playfair();
                playfair.createMatrix(keyValueTF.getText());
                cipherTextBox.setText(playfair.decrypt(msgTextBox.getText()));
            }else if (s.equals("RC4")){
                RC4 rC4 = new RC4(keyValueTF.getText());
                cipherTextBox.setText(rC4.decrypt(msgTextBox.getText()));
            }else if (s.equals("RSA")){
                //RSA rsa = new RSA();
                cipherTextBox.setText(rsa.decrypt(new BigInteger(msgTextBox.getText())));
            }
        });

        //Assembling UI control elements in a central vBox
        VBox controlsPane = new VBox(20);
        controlsPane.setPadding(new Insets(5, 5, 5, 5));
        controlsPane.setAlignment(Pos.CENTER);
        controlsPane.setFillWidth(true);
        controlsPane.setPrefWidth(WIDTH/4);
        controlsPane.getChildren().addAll(keyValueTF, clearButton, encryptButton, decryptButton, algorithmChoiceBox);

        //Assembling text fields with controls
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(50, 50, 50, 50));
        pane.setHgap(8);
        pane.setVgap(8);

        /*//Encrypt text while typing
        msgText.setOnKeyTyped(event -> {
            cipherTextBox.appendText(CaesarCipher.encrypt(event.getCharacter(), Integer.parseInt(keyValueTF.getText())));
        });


        //Decrypt text while typing
        cipherTextBox.setOnKeyTyped(event ->
            msgText.appendText(CaesarCipher.decrypt(event.getCharacter(), Integer.parseInt(keyValueTF.getText())))
        );*/

        pane.getChildren().addAll(msgTextBox, controlsPane, cipherTextBox);

        Scene scene = new Scene(pane, 1250, 400);

        primaryStage.setTitle("Encryption Algorithms");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
