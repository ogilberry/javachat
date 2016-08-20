import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientGUI extends Application {

    private Stage primaryStage;
    private GridPane messagingPane;
    private GridPane loginPane;
    private Scene messagingScene;
    private Scene loginScene;
    private TextArea inputArea;
    private TextArea messageView;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button sendButton;
    private ClientMain client;
    private ArrayList<String> messages;
    private String clientName;      //the username currently using the client

    public static void main(String[] args){
        launch(args);
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
        messagingPane.add(sendButton, 1, 3);
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

    public void updateMessageView(){
        messageView.clear();
        for(String message : messages){
            messageView.appendText(message + "\n");
        }
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
                if(clientName.trim().isEmpty() || clientName==null){
                    errorLabel.setText("Error: You must have a username");
                    return;
                }
                primaryStage.setScene(messagingScene);
            }
        });
        loginPane.add(loginButton, 2, 3);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        messagingPane = new GridPane();
        loginPane = new GridPane();
        setMessagingPaneSettings();
        setLoginPaneSettings();

        //create all the controls
        createLoginControls();
        createMessagingControls();
        clientName = "User x";
        messages = new ArrayList<String>();
        client = new ClientMain("localhost", 55555, messages, this);

        messagingScene = new Scene(messagingPane, 400, 400);
        loginScene = new Scene(loginPane, 400, 400);

        this.primaryStage.setScene(loginScene);
        this.primaryStage.show();
    }
}
