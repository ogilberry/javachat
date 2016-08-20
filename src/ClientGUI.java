import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientGUI extends Application {

    private GridPane messagingPane;
    private GridPane loginPane;
    private Scene messagingScene;
    private Scene loginScene;
    private TextArea inputArea;
    private TextArea messageView;
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
        
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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

        primaryStage.setScene(messagingScene);
        primaryStage.show();
    }
}
