package client.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
/**
 * this controller getting the payment information from the user when he sign up .
 * @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * @version June 2019
 */
public class PaymentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField idtxt;

    @FXML
    private JFXTextField cardnumtxt;

    @FXML
    private JFXTextField mmtxt;

    @FXML
    private JFXTextField yytxt;

    @FXML
    private JFXTextField cvvtxt;

    @FXML
    private JFXButton sginypbtn;
    @FXML // fx:id="homepagebtn"
    private Button homepagebtn; // Value injected by FXMLLoader

    @FXML // fx:id="backbtn"
    private JFXButton backbtn; // Value injected by FXMLLoader
    /**
     * if the user press back --> he arrive to sign up window and need to start over the process if he wants to sign up.
     * @param event
     */

    @FXML
    void PressBack(ActionEvent event) {
    	ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/SignUpWindow.fxml");}
    

    @FXML
    /**
     * insert the payment details into the table on DB,getting from the screen
     * the details that user insert and sending it to DB by query
     * @param event
     */
    void PressSignUp(ActionEvent event) {
    	String IDCardHolder = idtxt.getText();
    	String CardNumber=cardnumtxt.getText();
    	String MonthExpiration=mmtxt.getText();
    	String YearExpiration=yytxt.getText();
    	String CvvCard=cvvtxt.getText();
    	String PaymentQuery=null;
    	String UserQuery=null;
    	if(IDCardHolder.isEmpty()||CardNumber.isEmpty()||MonthExpiration.isEmpty()||YearExpiration.isEmpty()||CvvCard.isEmpty())
    		ClientMessages.popUp(AlertType.ERROR,"ERROR","You have Empty fields!","Fill all the fields!");
    	else if(!ClientTools.isValidCardNumber(CardNumber))
    		ClientMessages.popUp(AlertType.ERROR, "CardNumber Error", "CardNumber ERROR", "CardNumber IS NOT LEGALE");
    	else if(!ClientTools.isValidIDCardHolder(IDCardHolder))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "IDCardHolder ERROR", "PLEASE ENTER ID with 9 digits ");
    	else if(!ClientTools.isValidExpirationDate(MonthExpiration,YearExpiration))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "Expiration ERROR", "PLEASE ENTER ExpirationDate of card like this : 01/23 ");
    	else if(!ClientTools.isValidCVV(CvvCard))
    		ClientMessages.popUp(AlertType.ERROR, "ERROR", "cvv ERROR", "PLEASE ENTER legal cvv ");
    	else
    	{
    		/*UserQuery="INSERT INTO user VALUES('";
       		for(int i=0;i<6;i++)
       			UserQuery+=PublicController.arr[i]+"','";
       		UserQuery+="customer');";*/
    		UserQuery="INSERT INTO gcm.user VALUES('"+PublicController.arr[0]+"','"+PublicController.arr[1]+"','"+PublicController.arr[2]+"','"+PublicController.arr[3]+"','"+PublicController.arr[4]+"','"+PublicController.arr[5]+"','"+PublicController.arr[6]+"');";
       		PaymentQuery="INSERT INTO gcm.payment VALUES('"+PublicController.arr[0]+"','"+IDCardHolder+"','"+CardNumber+"','"+MonthExpiration+"','"+YearExpiration+"','"+CvvCard+"');";
      	System.out.println(UserQuery);
      	ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SignUp,UserQuery));
    		//PaymentQuery="INSERT INTO gcm.payment VALUES('"+PublicController.arr[0]+"','"+IDCardHolder+"','"+CardNumber+"','"+MonthExpiration+"','"+YearExpiration+"','"+CvvCard+"');";
    		
    		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SignUpPayment,PaymentQuery));
    		
    		Platform.runLater(new Runnable() {
				
				@Override
				public void run() {

		    		ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/Login.fxml");
					
				}
			});
}

    	}
    /**
     * the user will go back to login screen
     * @param event
     */
    @FXML
    void PressHomePage(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {

				ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/Login.fxml");
				
			}
		});
    	
    }
    

    @FXML
    void initialize() {
        assert idtxt != null : "fx:id=\"idtxt\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert cardnumtxt != null : "fx:id=\"cardnumtxt\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert mmtxt != null : "fx:id=\"mmtxt\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert yytxt != null : "fx:id=\"yytxt\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert cvvtxt != null : "fx:id=\"cvvtxt\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert sginypbtn != null : "fx:id=\"sginypbtn\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert backbtn != null : "fx:id=\"backbtn\" was not injected: check your FXML file 'PaymentInfo.fxml'.";
        assert homepagebtn != null : "fx:id=\"homepagebtn\" was not injected: check your FXML file 'PaymentInfo.fxml'.";

    }}
    