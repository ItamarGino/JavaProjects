/**
 * Sample Skeleton for 'SignUpWindow.fxml' Controller Class
 */

/**
 * The ViewStarter class that extends application represent the start view
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */


package client.controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;

public class PublicController {

	@FXML
    private JFXButton ExitButton;

    @FXML
    private JFXTextField userNameTxt;

    @FXML
    private JFXTextField FirstNameTxt;

    @FXML
    private JFXTextField LastNameTxt;

    @FXML
    private JFXTextField EmailTxt;

    @FXML
    private JFXTextField CellphoneTxt;

    @FXML
    private JFXTextField PasswordTxt;

    @FXML
    private JFXTextField RePasswordTxt;

    @FXML
    private JFXTextField userNameTxt1;

    @FXML
    private JFXTextField userNameTxt11;

    @FXML
    private JFXTextField userNameTxt111;

    @FXML
    private JFXButton SignBtn;

    static Window window;
    public static String arr[]=new String[11] ;//by amit


    /**
     * Checks if all fields are corrects and change window to payment info.
     * @param event
     */
    @FXML
    void pressSignBtn(ActionEvent event) { 			//Shahar

    	String username=userNameTxt.getText();
    	String firstname=FirstNameTxt.getText();
    	String lastname=LastNameTxt.getText();
    	String email=EmailTxt.getText();
    	String cellphone=CellphoneTxt.getText();
    	String password=PasswordTxt.getText();
    	String repassword=RePasswordTxt.getText();
    	String role="customer";//by amit
    	String query = null;
    	arr[0]=username;
    	arr[1]=firstname;
    	arr[2]=lastname;
    	arr[3]=email;
    	arr[4]=cellphone;
    	arr[5]=password;
    	arr[6]=role;
    			
    	// arr= {username,firstname,lastname,email,cellphone,password};
    	if(username.isEmpty()||firstname.isEmpty()||lastname.isEmpty()||email.isEmpty()||cellphone.isEmpty())
    		ClientMessages.popUp(AlertType.ERROR,"ERROR","You have Empty fields!","Fill all the fields!");
    	else if(!ClientTools.isValidEmail(email))
    		ClientMessages.popUp(AlertType.ERROR, "Email Error", "EMAIL ERROR", "EMAIL IS NOT LEGALE");
    	else if(!password.equals(repassword))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "PASSWORD NOT MATCH", "PLEASE ENTER identical PASSWORDS");
    	else if(!ClientTools.validatePhoneNumber(cellphone))
            ClientMessages.popUp(AlertType.ERROR, "Phone number", "PHONE NUMBER ERROR", "PLEASE CHEAK YOUR PHONE NUMBER");
    	else if(!firstname.matches("[a-zA-Z]+"))
    		ClientMessages.popUp(AlertType.ERROR, "First Name","First name not valid","");
    	else if(!firstname.matches("[a-zA-Z]+"))
    		ClientMessages.popUp(AlertType.ERROR, "Last Name","Last name not valid","");
    	else if(!(username.length()>3))
    		ClientMessages.popUp(AlertType.ERROR, "user name","user_name must consist 4 character","");
    	else {
    		
    		//mobed by amit ---> to PaymentController (to func PressSignUp(ActionEvent event)))
          /*  query="INSERT INTO user VALUES('";
    		for(int i=0;i<6;i++)
    			query+=arr[i]+"','";
    		query+="customer');";
    	System.out.println(query);
    	ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SignUp,query));*/
	    ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/PaymentInfo.fxml");
    	}
    }
    
    /**
     * Change to login window.
     * @param event
     * @throws IOException
     */
    @FXML
    void GoBack(ActionEvent event) throws IOException {
		ClientTools.changeWindow((((Node) event.getSource()).getScene().getWindow()), "/client/boundry/Login.fxml");
    }
    
    /**
     * Change window.
     * @param fxml
     */
    public static void changeWin(String fxml) {
    	ClientTools.changeWindow(window, fxml);
    }

}
