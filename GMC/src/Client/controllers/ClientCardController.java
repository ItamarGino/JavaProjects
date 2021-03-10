package client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * this controller present the details of the user that connect to the system,the user can watch his details and change some of them
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */

public class ClientCardController 
{
	static ClientCardController Instance;

    @FXML
    private Button homeBtn;
    
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="usernametxt"
	private Label usernametxt; // Value injected by FXMLLoader

	@FXML // fx:id="passwordtxt"
	private JFXTextField passwordtxt; // Value injected by FXMLLoader

	@FXML // fx:id="firstnametxt"
	private JFXTextField firstnametxt; // Value injected by FXMLLoader

	@FXML // fx:id="lastnametxt"
	private JFXTextField lastnametxt; // Value injected by FXMLLoader

	@FXML // fx:id="emailtxt"
	private JFXTextField emailtxt; // Value injected by FXMLLoader

	@FXML // fx:id="cellphonetxt"
	private JFXTextField cellphonetxt; // Value injected by FXMLLoader

	@FXML // fx:id="idtxt"
	private JFXTextField idtxt; // Value injected by FXMLLoader

	@FXML // fx:id="cardnumtxt"
	private JFXTextField cardnumtxt; // Value injected by FXMLLoader

	@FXML // fx:id="mmtxt"
	private JFXTextField mmtxt; // Value injected by FXMLLoader

	@FXML // fx:id="yytxt"
	private JFXTextField yytxt; // Value injected by FXMLLoader

	@FXML // fx:id="cvvtxt"
	private JFXTextField cvvtxt; // Value injected by FXMLLoader

	@FXML // fx:id="saveButton"
	private JFXButton saveButton; // Value injected by FXMLLoader

    @FXML
    private GridPane paymentinfounseen;
    @FXML
    private JFXButton backbtn;
    @FXML
    private Label paymenttxt;
    @FXML
    private Hyperlink logoutlink;
    
    @FXML
    private JFXButton purchbtn;

    /**
     * Change screen to HomePageScreen when HomeButton pressed
     */
    @FXML
    void goToHomeScreen(ActionEvent event) {
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
    }
    
    
    /**
     * going to purchase history
     * @param event
     */
    @FXML
    void GoToPurchaseHistoryPage(ActionEvent event)
    {
    	ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/PurchaseHistory.fxml");
    }
    
    
    /**
     * going back to homepage
     * @param event
     */
    @FXML
    void pressback(ActionEvent event) 
    {
    	ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
    }
	
	/**
	 * checking if the the changes that the user insert is legal 
	 * and Update the changes on DB by sending query.
	 * @param event
	 */
    @FXML
	void updateChange(ActionEvent event)
	{
		if(paymenttxt.isVisible() && (idtxt.getText().isEmpty()||cardnumtxt.getText().isEmpty()||mmtxt.getText().isEmpty()||yytxt.getText().isEmpty()||cvvtxt.getText().isEmpty()))
    		ClientMessages.popUp(AlertType.ERROR,"ERROR","You have Empty fields!","Fill all the fields!");
    	else if(paymenttxt.isVisible() && !ClientTools.isValidCardNumber(cardnumtxt.getText()))
    		ClientMessages.popUp(AlertType.ERROR, "CardNumber Error", "CardNumber ERROR", "CardNumber IS NOT LEGALE");
    	else if(paymenttxt.isVisible() && !ClientTools.isValidIDCardHolder(idtxt.getText()))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "IDCardHolder ERROR", "PLEASE ENTER ID with 9 digits ");
    	else if(paymenttxt.isVisible() && !ClientTools.isValidExpirationDate(mmtxt.getText(),yytxt.getText()))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "Expiration ERROR", "PLEASE ENTER ExpirationDate of card like this : 01/23 ");
    	else if(paymenttxt.isVisible() && !ClientTools.isValidCVV(cvvtxt.getText()))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "cvv ERROR", "PLEASE ENTER legal cvv ");
    	else if(firstnametxt.getText().isEmpty()||lastnametxt.getText().isEmpty()||emailtxt.getText().isEmpty()||cellphonetxt.getText().isEmpty())
    		ClientMessages.popUp(AlertType.ERROR,"ERROR","You have Empty fields!","Fill all the fields!");
    	else if(!ClientTools.isValidEmail(emailtxt.getText()))
    		ClientMessages.popUp(AlertType.ERROR, "Email Error", "EMAIL ERROR", "EMAIL IS NOT LEGALE");
    	else if(!ClientTools.validatePhoneNumber(cellphonetxt.getText()))
            ClientMessages.popUp(AlertType.ERROR, "Phone number", "PHONE NUMBER ERROR", "PLEASE CHEAK YOUR PHONE NUMBER");
    	else if(!firstnametxt.getText().matches("[a-zA-Z]+")||!lastnametxt.getText().matches("[a-zA-Z]+"))
            ClientMessages.popUp(AlertType.ERROR, "Name", "First or Last name ERROR", "PLEASE CHEAK YOUR First or Last name");
    	else if(passwordtxt.getText().isEmpty())
            ClientMessages.popUp(AlertType.ERROR, "Password", "Password field is empty", "Please enter password .");

    	else {
		
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Before Change");
			alert.setHeaderText("Are you sure?");
			alert.setContentText("you can click cancel"); 
			alert.showAndWait();
			
		if(!alert.getResult().getButtonData().isCancelButton()) {
		String queryUser = "UPDATE gcm.user SET FirstName = '"+firstnametxt.getText()+"', LastName = '"+lastnametxt.getText()+"', Email = '"+emailtxt.getText()+"', PhoneNumber = '"+cellphonetxt.getText()+"', Password = '"+passwordtxt.getText()+"' WHERE userName = '"+ ClientMessages.username +"';" ;
		String queryPayment = "	UPDATE gcm.payment SET ID = '"+idtxt.getText()+"', CardNumber = '"+cardnumtxt.getText()+"', mmExpiriationDate = '"+mmtxt.getText()+"', yyExpiriationDate = '"+yytxt.getText()+"', CVC = '"+cvvtxt.getText()+"' WHERE userName = '"+ ClientMessages.username +"';" ;
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.changeClientCardDetails_User,queryUser));
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.changeClientCardDetails_Payment,queryPayment));
		}
    	}
    	
	}

    /**
     * present client card details on screen.
     * @param rs - Array list
     * @param customer - if the user is customer or manager
     */
	public void setDetailsOnScreen(ArrayList<String> rs,boolean customer)
	{
		// if flag = true => regular user
		// else => manager
		
		usernametxt.setText(rs.get(0));
		firstnametxt.setText(rs.get(1));
		lastnametxt.setText(rs.get(2));
		emailtxt.setText(rs.get(3));
		cellphonetxt.setText(rs.get(4));
		passwordtxt.setText(rs.get(5));
		
		if(customer)
		{
			idtxt.setText(rs.get(6));
			cardnumtxt.setText(rs.get(7));
			mmtxt.setText(rs.get(8));
			yytxt.setText(rs.get(9));
			cvvtxt.setText(rs.get(10));
		}
		
	}
	
	/**
	 * Removes the connected client from the system and returns to Login screen
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	    void pressLogOut(ActionEvent event)throws IOException {
		 
	    	try {

	    	ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
	    	ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

	    	}catch(Exception ex) {
	    		System.out.println(ex.toString());
	    	}
	    }
	 
	 /**
	  * execute the client details from DB by user type.
	  * @param manager - boolean.
	  */
	 public void executeCardDetails(boolean manager) {
		
		 if(manager) { 
			 paymentinfounseen.setVisible(false);
			 paymenttxt.setVisible(false);
			 purchbtn.setVisible(false);
			 String query = "SELECT user.userName, FirstName, LastName, Email, PhoneNumber, Password FROM gcm.user WHERE user.userName = '"+ ClientMessages.username+"';";
			 ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getClientCardDetailsWithoutPayment,query));
		 }
		 else {
			 String query = "SELECT user.userName, FirstName, LastName, Email, PhoneNumber, Password, ID, CardNumber, mmExpiriationDate, yyExpiriationDate, CVC FROM gcm.user, gcm.payment WHERE user.userName = '"+ ClientMessages.username +"' AND user.userName = payment.userName;";
			 ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getClientCardDetails,query));
		 }
	 }
	@FXML
	/**
	 * this func initialize the Current occurrence and sending a query that get the information about the type of the user
	 */
	void initialize() {
		Instance=this;
		// Cheking if the user is manager
		// if so => Does not showing payment!
		// send query: (return all the manager)
		String queryPayment = "SELECT Role FROM gcm.user WHERE userName='"+ClientMessages.username+"';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getRoleClientCard,queryPayment));
		}
	}
