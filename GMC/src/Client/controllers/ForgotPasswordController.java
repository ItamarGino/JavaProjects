/**
 * Sample Skeleton for 'ForgetPassword.fxml' Controller Class
 */

package client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 * this controller sending an email to the user if he forgot his password
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */
public class ForgotPasswordController {

    @FXML // fx:id="borderpane"
    private BorderPane borderpane; // Value injected by FXMLLoader

    @FXML // fx:id="EmailTxt"
    private JFXTextField EmailTxt; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTxt"
    private JFXTextField usernameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="SendBtn"
    private JFXButton SendBtn; // Value injected by FXMLLoader

    public static ForgotPasswordController instance;
    public static Window window;
    /**
     * sending an email to the user with his password 
     * @param event
     */
    @FXML
    void sendPassword(ActionEvent event) {
    	window=((Node)event.getSource()).getScene().getWindow();
    	String email=EmailTxt.getText();
    	String username=usernameTxt.getText();
    	System.out.println("username and email = "+username+" "+email );
    	if(email.isEmpty() || username.isEmpty()) 
    		ClientMessages.popUp(AlertType.ERROR,"Error", "Something missing !","user name or email are missing !");
    	else {
    		String query = "SELECT `Email`,`Password`,`FirstName` FROM user Where `userName`='"+username+"' AND Email='"+email+"' ;";
    		System.out.println(query);
    		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.forgotPassword,query));
    	}
    }
    
    public void hideWindow() {
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
		    	window.hide();
			}
		});
    }

    @FXML
    void initialize() {
    	instance=this;
    	usernameTxt.setText("shaharonen");
    	EmailTxt.setText("shaharonen@gmail.com");
    	
    }
}
