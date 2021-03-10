package client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

//import com.itextpdf.layout.element.Image;
import javafx.scene.image.Image;
import client.Client;
//import client.controllers.MainViewController;
//import common.controllers.Message;
//import common.controllers.enums.OperationType;
//import common.entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The ViewStarter class that extends application represent the start view
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */
public class ViewStarter extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static Client client;
	private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
	/**
     * main method
	 */
	public static void main(String[] args) {
		String host = "";
		int port = 0; // The port number

		try {		
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = DEFAULT_PORT;
		}

		try {
			host = args[0];
		} catch (Exception e) {
			host = "localhost";
		}

		try {
			client = new Client(host, port);
			System.out.println("Client setup connection! " + host + ":" + port);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client." + exception);
			System.exit(1);
		}

		launch(args);
	}
	/**
     * start method
     * @param primaryStage       primary Stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/client/boundry/Login.fxml"));
			Scene scene = new Scene(root,800,600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Map Collection Catalog");
			//primaryStage.getIcons().add(new Image("FinalLogo.jpg"));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * stop method
	 */
	@Override
	public void stop() {
		//User usr = ViewStarter.client.mainViewController.getUser();
		//ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, usr));
	}
}