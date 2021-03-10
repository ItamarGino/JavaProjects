package client.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.jfoenix.controls.JFXButton;
import com.sun.crypto.provider.RSACipher;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.City;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import server.EchoServer;
/**
 * this controller present to the each user type his Permissions his options
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */

public class UserHomePageController {

	@FXML
	private JFXButton ClientCardButton;

	@FXML
	private JFXButton CityCatalogButton;

	@FXML
	private JFXButton ManegerArea;

	@FXML
	private Label ta;

	@FXML
	private URL location;

	@FXML
	private ImageView FirstImage;

	@FXML
	private ImageView SecondImage;

	@FXML
	private ImageView ThirdImage;

	@FXML
	private Label FirstCityName;

	@FXML
	private Label FirstCityPrice;

	@FXML
	private Label SecondCityName;

	@FXML
	private Label SecondCityPrice;

	@FXML
	private Label ThirdCityName;

	@FXML
	private Label ThirdCityPrice;

	public static UserHomePageController Instance; // instance initialize when fxml is loading (initialize() method) - shahar.

	public static Window mainWindow;

	@FXML
	void GoToCityCatalogPage(ActionEvent event) throws IOException {
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/CityCatalog.fxml");
	}

	//by amit
	@FXML
	 void GoToClientCard(ActionEvent event) throws IOException
	{
	    	ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ClientCardPage.fxml");
	}
	// Itamar Edit =>
	@FXML
	void GoToManagerArea(ActionEvent event)
	{
		// When the user press on MangerArea
		// Assuming that only managers can see & press this button!
		// Checking if the manager is CEO or ContentManager
		mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
		String query = "SELECT Role FROM gcm.user WHERE userName='"+ClientMessages.username+"';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getRole,query));
	}

	@FXML
	void pressLogOut(ActionEvent event) throws IOException {
		try {

			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println("press log out exception");
			System.out.println(ex.toString());
		}
	}


	 @FXML
	    void initialize() {
	    	Instance=this;
	    	String query="SELECT * FROM maps_catalog ORDER BY `Version No.` Limit 0,3;";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadWhatsNew,query));
			// Cheking if the user is manager
			// if so => showing Manager Area Button
			ManegerArea.setVisible(false);

			// send query: (return all the manager)
			query = "SELECT userName FROM gcm.user WHERE Role='ceo' OR Role='contentManager';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getListOfManager,query));
			// not a manager!! ManegerArea still not visible
	        assert ClientCardButton != null : "fx:id=\"ClientCardButton\" was not injected: check your FXML file 'UserHomePage.fxml'.";
	        assert CityCatalogButton != null : "fx:id=\"CityCatalogButton\" was not injected: check your FXML file 'UserHomePage.fxml'.";
	        assert ManegerArea != null : "fx:id=\"ManegerArea\" was not injected: check your FXML file 'UserHomePage.fxml'.";
	    }

	 /*+++New Remez Upload City Name And Price+++*/
	    /**
	     * show the user what's new in the catalog
	     * @param msg
	     * @throws SQLException
	     */
	    
	   public void SetDataOnScreen(Object msg) throws SQLException {
		 ArrayList<City> rs=(ArrayList<City>)((Message)msg).getObject();
		 Label CityName[]= {FirstCityName,SecondCityName,ThirdCityName};
		 Label CityPrice[]= {FirstCityPrice,SecondCityPrice,ThirdCityPrice};
		 ImageView CityImage[]= {FirstImage,SecondImage,ThirdImage};
		//Set 3 cities and city price on whats new when initialize by version number - remez
		 Platform.runLater(new Runnable() {
			@Override
			public void run() {
				 for(int i=0;i<3;i++) {
					 CityName[i].setText(rs.get(i).getCityName());
					 CityPrice[i].setText(rs.get(i).getPrice()+"¤");
					 Image image = new Image(rs.get(i).getCityImageUrl());
					 CityImage[i].setImage(image);
					// CityImage[i].setStyle("-fx-background-color: #D6E8EC");
				 }	
			}
		});
	   }
	   /*+++New Remez Upload City Name And Price+++*/

	public void SetDataOnScreen_ManagerOpen(Object msg) throws SQLException 
	{
		ArrayList<String> data=(ArrayList<String>)((Message)msg).getObject();
		if(data.get(0).equals("ceo"))
		{
			// Open CEO Manager Area
			ClientTools.changeWindow(mainWindow,"/client/boundry/ManagerArea_CEO.fxml");
		}
		else if(data.get(0).equals("contentManager"))
		{
			// Open Content Manager Area
			ClientTools.changeWindow(mainWindow,"/client/boundry/ManagerArea_Content.fxml");
		}	
		//	ClientTools.changeWindow(mainWindow,"/client/boundry/ManagerArea_CEO.fxml");
	}

	public void SetDataOnScreen_Manager(Object msg) throws SQLException 
	{
		ArrayList<String> data=(ArrayList<String>)((Message)msg).getObject();

		// Checking if username exists in the list of names:
		for(int i=0;i<data.size();i++)
		{
			if(ClientMessages.username.equals(data.get(i)))
			{
				// Real manager!
				ManegerArea.setVisible(true);
			}
		}
	}
}