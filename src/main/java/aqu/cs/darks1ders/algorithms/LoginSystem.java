package aqu.cs.darks1ders.algorithms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class LoginSystem extends Application {
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;
    private Data data;
    private Encryption enc;
    @Override
    public void start(Stage primaryStage) {
        data = new Data();
        enc = new Encryption();
        primaryStage.setTitle("DarkS1ders Login");
        VBox root = new VBox(10);
        Image icon = new Image(getClass().getResource("/icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("modern-container");
        Text titleLabel = new Text("Login");
        titleLabel.setFont(Font.font(24));
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("label-header");
        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(300);
        usernameField.getStyleClass().add("text-field-modern");
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label-header");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(300);
        passwordField.getStyleClass().add("text-field-modern");
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button-modern");
        loginButton.setOnAction(e -> login(primaryStage));
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
            titleLabel,
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            loginButton,
            statusLabel
        );
        Scene scene = new Scene(root, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void login(Stage stage) {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }
        User user = data.getUser(enc.enc(usernameField.getText()));
        if(user == null) {
            statusLabel.setText("Invalid username or password.");
            return;
        }
        if(!user.password.equals(enc.enc(passwordField.getText()))) {
            statusLabel.setText("Invalid username or password.");
            return;
        }
        App app = new App();
        app.user = new User(enc.dec(user.username), "");
        app.start(stage);
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("DarkS1ders Login");
        alert.setContentText("A secure login form\nVersion 0.2");
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
