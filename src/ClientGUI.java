import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientGUI extends Application {

    private final int port = 55555;
    private Stage primaryStage;
    private GridPane messagingPane;
    private GridPane loginPane;
    private GridPane connectionPane;
    private Scene messagingScene;
    private Scene loginScene;
    private Scene connectionScene;
    private Label connectionErrorLabel;
    private TextArea inputArea;
    private TextArea messageView;
    private TextField usernameField;
    private TextField hostField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button sendButton;
    private Button connectButton;
    private Button disconnectButton;
    private ClientMain client; //socket containing class, does all the communication with server
    private ArrayList<String> messages;
    private String clientName;      //the username currently using the client

    public static void main(String[] args){
        launch(args);
    }

    private void setLoginPaneSettings(){
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setPadding(new Insets(20, 20, 20, 20));
        loginPane.setHgap(10);
        loginPane.setVgap(10);
    }

    private void setMessagingPaneSettings(){
        messagingPane.setAlignment(Pos.CENTER);
        messagingPane.setPadding(new Insets(20, 20, 20, 20));
        messagingPane.setHgap(10);
        messagingPane.setVgap(10);
    }

    private void setConnectionPaneSettings(){
        connectionPane.setAlignment(Pos.CENTER);
        connectionPane.setPadding(new Insets(20, 20, 20, 20));
        connectionPane.setHgap(10);
        connectionPane.setVgap(10);
    }

    public void updateMessageView(){
        messageView.clear();
        for(String message : messages){
            messageView.appendText(message + "\n");
        }
    }

    private void startMessagingScene(){
        //create all the controls and member variables needed for the messaging scene, then go there
        messagingPane = new GridPane();
        setMessagingPaneSettings();
        createMessagingControls();
        primaryStage.setScene(messagingScene);
        messagingScene = new Scene(messagingPane, 400, 400);
        primaryStage.setScene(messagingScene);
    }

    private void startConnectionScene(){
        //create all the controls and member variables needed for the messaging scene, then go there
        connectionPane = new GridPane();
        setConnectionPaneSettings();
        createConnectionControls();
        connectionScene = new Scene(connectionPane, 400, 400);
        primaryStage.setScene(connectionScene);
}

    private void createLoginControls(){
        Label usernameLabel = new Label("Username: ");
        loginPane.add(usernameLabel, 1, 1);
        Label passwordLabel = new Label("Password: ");
        loginPane.add(passwordLabel, 1, 2);
        usernameField = new TextField();
        loginPane.add(usernameField, 2, 1);
        passwordField = new PasswordField();
        loginPane.add(passwordField, 2, 2);
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");
        loginPane.add(errorLabel, 1, 4, 2, 1);
        loginButton = new Button("Log in");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientName = usernameField.getText();
                //only go to the messaging Scene if the log in credentials are valid (return if not)
                if(clientName.trim().isEmpty() || clientName==null){
                    errorLabel.setText("Error: You must have a username");
                    return;
                }
                startConnectionScene();
            }
        });
        loginPane.add(loginButton, 2, 3);

    }

    private void createMessagingControls(){
        messageView = new TextArea();
        messageView.setPrefHeight(200);
        messageView.setEditable(false);
        messagingPane.add(messageView, 1, 1);
        inputArea = new TextArea();
        inputArea.setPrefHeight(60);
        messagingPane.add(inputArea, 1, 2);
        sendButton = new Button("Send Message");
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //when the button is pressed, grab the text from the inputArea, and send it to the server
                String message = clientName + ": " + inputArea.getText();
                inputArea.clear();
                client.sendMessageToServer(message);
            }
        });
        disconnectButton = new Button("Disconnect");
        disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        messagingPane.add(sendButton, 1, 3);
    }

    private void createConnectionControls(){
        Label hostLabel = new Label("Host Address: ");
        connectionPane.add(hostLabel, 1, 1);
        connectionErrorLabel = new Label("");
        connectionErrorLabel.setStyle("-fx-text-fill: red;");
        connectionPane.add(connectionErrorLabel, 1, 3, 2, 1);
        hostField = new TextField();
        connectionPane.add(hostField, 2, 1);
        connectButton = new Button("Connect to Server");
        ClientGUI gui = this;
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startMessagingScene();
                messages = new ArrayList<String>();
                client = new ClientMain(hostField.getText(), port, messages, gui);
            }
        });
        connectionPane.add(connectButton, 2, 2);
    }

    public void setConnectionError(String message){
        primaryStage.setScene(connectionScene);
        connectionErrorLabel.setText(message);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loginPane = new GridPane();
        setLoginPaneSettings();

        //create all the controls
        createLoginControls();

        loginScene = new Scene(loginPane, 400, 400);
        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();
    }
}
