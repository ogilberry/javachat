import javafx.application.Application;
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

    private GridPane root;
    private TextArea inputArea;
    private ListView messageView;
    private Button sendButton;
    private ClientMain client;
    private ArrayList<String> messages;

    public static void main(String[] args){
        launch(args);
    }

    private void createMessagingControls(){
        messageView = new ListView();
        root.add(messageView, 1, 1);
        inputArea = new TextArea();
        root.add(inputArea, 1, 2);
        sendButton = new Button("Send Message");
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //when the button is pressed, grab the text from the inputArea, and send it to the server
                String message = inputArea.getText();
                inputArea.clear();
                client.sendMessageToServer(message);
            }
        });
        root.add(sendButton, 1, 3);
    }

    private void setRootSettings(){
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setHgap(10);
        root.setVgap(10);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new GridPane();
        setRootSettings();

        messages = new ArrayList<String>();
        client = new ClientMain("localhost", 55555, messages);

        //create all the controls
        createMessagingControls();

        Scene messagingScene = new Scene(root, 400, 400);
        primaryStage.setScene(messagingScene);
        primaryStage.show();
    }
}
