package aqu.cs.darks1ders.algorithms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;

public class App extends Application {
    private TextField filePathField;
    private TextField keyField;
    private Label statusLabel;
    User user;
    @Override
    public void start(Stage primaryStage) {
        if(user == null) {
            System.exit(0);
        }
        primaryStage.setTitle("DarkS1ders File Encryption");
        VBox root = new VBox(10);
        Image icon = new Image(getClass().getResource("/icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("modern-container");
        Label fileLabel = new Label("Select File:");
        fileLabel.getStyleClass().add("label-header");
        filePathField = new TextField();
        filePathField.setPromptText("No file selected");
        filePathField.setEditable(false);
        filePathField.setPrefWidth(300);
        filePathField.getStyleClass().add("text-field-modern");
        Button selectFileButton = new Button("Browse");
        selectFileButton.getStyleClass().add("button-modern");
        selectFileButton.setOnAction(e -> selectFile(primaryStage));
        Label keyLabel = new Label("Encryption Key:");
        keyLabel.getStyleClass().add("label-header");
        keyField = new TextField();
        keyField.setPromptText("Enter encryption key");
        keyField.setPrefWidth(300);
        keyField.getStyleClass().add("text-field-modern");
        Button encryptButton = new Button("Encrypt File");
        encryptButton.getStyleClass().add("button-modern");
        encryptButton.setOnAction(e -> encryptFile(primaryStage));
        Button decryptButton = new Button("Decrypt File");
        decryptButton.getStyleClass().add("button-modern");
        decryptButton.setOnAction(e -> decryptFile(primaryStage));
        statusLabel = new Label("");
        statusLabel.getStyleClass().add("status-label");
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutDialog());
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        menu.getItems().addAll(aboutItem, exitItem);
        menuBar.getMenus().add(menu);
        root.getChildren().addAll(
            menuBar,
            fileLabel,
            filePathField,
            selectFileButton,
            keyLabel,
            keyField,
            encryptButton,
            decryptButton,
            statusLabel
        );
        Scene scene = new Scene(root, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void encryptFile(Stage stage) {
        processFile(stage, true);
    }

    private void decryptFile(Stage stage) {
        processFile(stage, false);
    }

    private void processFile(Stage stage, boolean encrypt) {
        if (filePathField.getText().isEmpty() || keyField.getText().isEmpty()) {
            showAlert("Error", "Please select a file and enter an encryption key.");
            return;
        }
        try {
            File inputFile = new File(filePathField.getText());
            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save " + (encrypt ? "Encrypted" : "Decrypted") + " File");
            File outputFile = fileChooser.showSaveDialog(stage);
            
            if (outputFile != null) {
                byte[] processedBytes = processAES(inputBytes, keyField.getText(), encrypt);
                Files.write(outputFile.toPath(), processedBytes);
                statusLabel.setText("File " + (encrypt ? "encrypted" : "decrypted") + " successfully!");
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred");
        }
    }

    private byte[] processAES(byte[] data, String key, boolean encrypt) throws Exception {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] keyHash = md.digest(keyBytes);
        SecretKeySpec secretKey = new SecretKeySpec(keyHash, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }


    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("DarkS1ders File Encryption");
        alert.setContentText("A secure file encryption and decryption tool\nVersion 0.2 Licensed for: @" + user.username);
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void launchApp() {
        launch();
    }

    public static void main(String[] args) {
        launch(args);
    }
}