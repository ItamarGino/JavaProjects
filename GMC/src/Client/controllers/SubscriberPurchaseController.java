package client.controllers;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.PurchaseHistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
/**
 * 
 * @author Amit Sinter.
 * @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * this controller is about the payment process and if the user chosen between subscribe or one time purchase.
 */

public class SubscriberPurchaseController {
	/**
	 * dtf is a variable that present the format od the date that we used
	 * localdate is a variable that present Current date
	 * dateAfterSixMonth is a variable that present the date after six month
	 */
	DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd");
	LocalDate localDate = LocalDate.now();
	LocalDate dateAfterSixMonth=localDate.plusMonths(6);
	static SubscriberPurchaseController Instance;
	


	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;



	@FXML
	private Label cityname;

	@FXML
	private Label durationsubscrib;

	@FXML
	private Label price;
	@FXML
	private Button catalogctn;
	@FXML
	private Button subscriptionperiod;

	@FXML
	private Button onetimepurchase;
	@FXML
	private Label todaydate;
	@FXML
	private Label cityname1;
	   @FXML
	    private Label versionNum;
	    @FXML
	    private Label versionNum1;
	    @FXML
	    private Label dicount;



	@FXML
	private Label price1;
/**
 * this func going back to the catalog when the user press x at the left side on the screen
 * @param event
 */
	@FXML
	void PressX(ActionEvent event) 
	{
		((Node)event.getSource()).getScene().getWindow().hide();
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
		
	}
	/**
	 * this function get event and we insert the purchase details into the table for One-Time purchase.
	 * the info that get in -->city name,type of purchase ="One-Time",user name,current date ,purchase Date Expiration, version number, download -->if the user download or not
	 * @param event
	 */

	
	@FXML
	void PressPurchase(ActionEvent event) 
	{
		String cityName = cityname.getText();
		String userName=ClientMessages.username;
		String Currentdate=dtf.format(localDate);
		String purchaseDateExpiration=dtf.format(localDate);
		String type="One-time";
		String versionNumber=versionNum.getText();
		String download="no";
		String query;


		query="INSERT INTO gcm.purchasehistory VALUES('"+userName+"','"+cityName+"','"+type+"','"+Currentdate+"','"+purchaseDateExpiration+"','"+versionNumber+"','"+download+"');";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UserPurchase,query));
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.CountUpdate,"UPDATE `gcm`.`maps_catalog` SET `NumberOfOneTimePurchase` =`NumberOfOneTimePurchase` +1 WHERE (`CityName` ='"+ cityName+ "');"));

		((Node)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * his function get event and we insert the purchase details into the table for Period purchase.
	 *  the info that get in -->city name,type of purchase ="One-Time",user name,current date ,purchase Date Expiration, version number, download -->if the user download or not
	 * @param event
	 */

	@FXML
	void PressSubscriptionForPeriod(ActionEvent event) 
	{
		String cityName = cityname.getText();
		String userName=ClientMessages.username;
		String Currentdate=dtf.format(localDate);
		String purchaseDateExpiration=dtf.format(dateAfterSixMonth);
		String type="Period";
		String versionNumber=versionNum.getText();
		String download="no";
		String query;


		query="INSERT INTO gcm.purchasehistory VALUES('"+userName+"','"+cityName+"','"+type+"','"+Currentdate+"','"+purchaseDateExpiration+"','"+versionNumber+"','"+download+"');";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UserPurchase,query));
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.CountUpdate,"UPDATE `gcm`.`maps_catalog` SET `NumberOfSubscription` =`NumberOfSubscription` +1 WHERE (`CityName` ='"+ cityName+ "');"));
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	/**
	 * this function set all the labels into the screen and show the correct price by role and the price about subscription purchase or one time purchase.
	 * @param rs - getting array list with all the correct and relevant information from DB
	
	 */

	@SuppressWarnings("static-access")
	public void PriceOfMap(ArrayList<String> rs)
	{
		durationsubscrib.setText(dtf.format(localDate) + "-" + dtf.format(dateAfterSixMonth));
		todaydate.setText(dtf.format(localDate));
		cityname.setText(CityCatalogController.Instance.CityDetail);
		cityname1.setText(CityCatalogController.Instance.CityDetail);
		price.setText(rs.get(3)+ " $ ");
		price1.setText(rs.get(1)+" $ ");
		dicount.setText(rs.get(3)+" $ ");
		versionNum.setText(rs.get(2));
		versionNum1.setText(rs.get(2));

		if(rs.get(0).equals("ceo")  ||rs.get(0).equals("contentManager") || rs.get(0).equals("employee"))
		{
			price.setText("0"+" $ ");
			price1.setText("0"+" $ ");
			dicount.setText("0"+" $ ");

		}


	}
	/**
	 * this function gives 10% discount for subscription that bought in the past the same map
	 * @param rs - get array list with all the relevant information to calculate the discount for the subscription
	 */

	public void purchaseDetails(ArrayList<String> rs) 
	{
		if(!(rs.isEmpty())) 
		{
			LocalDate expirDate=localDate.parse(rs.get(2), dtf);
			System.out.println(expirDate.isBefore(LocalDate.now()));
			if(((expirDate.isAfter(LocalDate.now())) || (expirDate.equals(LocalDate.now()))))
			{
				double newPrice=Double.parseDouble(rs.get(3));
				double newPrice1=newPrice/10.0;
				newPrice=newPrice - newPrice1;
				price.setText(rs.get(3)+ " $ ");
				dicount.setText(Double.toString(newPrice) + " $ ");
				price1.setText(rs.get(0) + " $ ");
			}

			if(rs.get(1).equals("ceo")  ||rs.get(1).equals("contentManager") || rs.get(1).equals("employee"))
			{
				price.setText("0"+" $ ");
				price1.setText("0"+" $ ");
				dicount.setText("0 $");

			}
		}
		else return;

	}




/**
 *  this func initialize the Current occurrence 
 * and sending a query that get the information about the price,version number for the every user type
 * @throws InterruptedException
 */


@FXML
void initialize() throws InterruptedException {

	Instance=this;
	//get the city name and her price.
	@SuppressWarnings("static-access")
	String query1 = "SELECT Role,Price,`Version No.`,PriceForSubscriber FROM gcm.maps_catalog,gcm.user WHERE maps_catalog.CityName='"+CityCatalogController.Instance.CityDetail+"' AND user.userName='"+ClientMessages.username+"';";
	ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getPriceOfMap,query1));
	@SuppressWarnings("static-access")
	String query = "SELECT Price,Role,PurchaseDateExpiration,PriceForSubscriber FROM  gcm.purchasehistory,gcm.maps_catalog,gcm.user WHERE maps_catalog.CityName='"+CityCatalogController.Instance.CityDetail+"' AND purchasehistory.userName='"+ClientMessages.username+"' AND user.userName='"+ClientMessages.username+"';";
	ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getDetailOFpurchase,query));
	TimeUnit.MILLISECONDS.sleep(200);
	assert catalogctn != null : "fx:id=\"catalogctn\" was not injected: check your FXML file 'SubscribeWindow.fxml'.";
	assert cityname != null : "fx:id=\"cityname\" was not injected: check your FXML file 'SubscribeWindow.fxml'.";
	assert durationsubscrib != null : "fx:id=\"durationsubscrib\" was not injected: check your FXML file 'SubscribeWindow.fxml'.";
	assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'SubscribeWindow.fxml'.";
	assert subscriptionperiod != null : "fx:id=\"subscriptionperiod\" was not injected: check your FXML file 'SubscribeWindow.fxml'.";
	assert onetimepurchase != null : "fx:id=\"onetimepurchase\" was not injected: check your FXML file 'SubscribeWindow.fxml'.";

}
}




