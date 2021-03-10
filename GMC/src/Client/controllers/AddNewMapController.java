/**
 * Sample Skeleton for 'AddNewMapPage.fxml' Controller Class
 */

package client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;

import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * AddNewMapController => Controller for the Employee for add new Map from external company
 * 
 */
public class AddNewMapController 
{

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="homepage"
	private Button homepage; // Value injected by FXMLLoader

	@FXML // fx:id="logoutbtn"
	private Hyperlink logoutbtn; // Value injected by FXMLLoader

	@FXML // fx:id="newMapTable"
	private TableView<common.entities.NewMapDetails> newMapTable; // Value injected by FXMLLoader

	@FXML // fx:id="backbtn"
	private JFXButton backbtn; // Value injected by FXMLLoader

	@FXML // fx:id="addbtn1"
	private JFXButton addbtn1; // Value injected by FXMLLoader

	@FXML // fx:id="SetPrice"
	private JFXTextField SetPrice; // Value injected by FXMLLoader

	public static AddNewMapController Instance;
	public static common.entities.NewMapDetails ChosenRow;
	public static ArrayList<common.entities.NewMapDetails> data;

	public static String price = new String();

	
	/**
	 * SetPrice => getting the map price from the employee
	 * @param event
	 */
	@FXML
	void SetPrice(ActionEvent event)
	{
		price = SetPrice.getText();
	}

	@FXML
	void ChooseMap(MouseEvent event) 
	{
		if(newMapTable.getSelectionModel().getSelectedItem() != null) 
		{    
			ChosenRow = newMapTable.getSelectionModel().getSelectedItem();
			SetPrice.setVisible(true);
		}
	}

	/**
	 * @param event
	 * 
	 * PressAdd => Working only if a row was chosen in the table && price was set
	 */
	@FXML
	void PressAdd(ActionEvent event) 
	{
		if(ChosenRow != null && price.isEmpty() == false)
		{
			// First => Get the EmployeeID from DataBase
			// send query:
			String query = ClientMessages.username +";"
					+ChosenRow.CountryName+";"+ChosenRow.CityName+";"+ChosenRow.Discription+";"+price+";";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getEmployeeID,query));
			// Then (on CreateRowAtVersion method) => Send the request to add a new map to the manager
		}
	}

	@FXML
	void PressBack(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}

	/** .
	 * Pressing LogOut => 
	 * Move to the Login
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void PressLogOut(ActionEvent event) throws IOException {
		try {

			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println("press log out exception");
			System.out.println(ex.toString());
		}
	}

	@FXML
	void Presshome(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{
		assert homepage != null : "fx:id=\"homepage\" was not injected: check your FXML file 'AddNewMapPage.fxml'.";
		assert logoutbtn != null : "fx:id=\"logoutbtn\" was not injected: check your FXML file 'AddNewMapPage.fxml'.";
		assert newMapTable != null : "fx:id=\"newMapTable\" was not injected: check your FXML file 'AddNewMapPage.fxml'.";
		assert backbtn != null : "fx:id=\"backbtn\" was not injected: check your FXML file 'AddNewMapPage.fxml'.";
		assert addbtn1 != null : "fx:id=\"addbtn1\" was not injected: check your FXML file 'AddNewMapPage.fxml'.";
		assert SetPrice != null : "fx:id=\"SetPrice\" was not injected: check your FXML file 'AddNewMapPage.fxml'.";

		Instance=this;
		// send query:
		String query = "SELECT CountryName, CityName, Discription, Image FROM gcm.newmap;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getNewMapDetails,query));
	}

	/**
	 * 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetDataOnScreen => Set the New-Map from newmap Table
	 */
	@SuppressWarnings("unchecked")
	public void SetDataOnScreen(Object msg) throws SQLException
	{
		ArrayList<common.entities.NewMapDetails> data=(ArrayList<common.entities.NewMapDetails>)((Message)msg).getObject();
		if(data.isEmpty() == false)
		{
			// dataToSend containing all the data!
			// Inserting to viewTable:
			ObservableList<common.entities.NewMapDetails> ol = FXCollections.observableArrayList(data);

			// Creating the Table: (setCellValueFactory => must be as NewMapDetails attributes are!)
			TableColumn<common.entities.NewMapDetails,String> CountryNameCol = new TableColumn<common.entities.NewMapDetails,String>("Country Name");
			CountryNameCol.setMinWidth(150);
			CountryNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.NewMapDetails, String>("CountryName"));

			TableColumn<common.entities.NewMapDetails,String> CityNameCol = new TableColumn<common.entities.NewMapDetails,String>("City Name");
			CityNameCol.setMinWidth(150);
			CityNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.NewMapDetails, String>("CityName"));

			TableColumn<common.entities.NewMapDetails,String> DiscriptionCol = new TableColumn<common.entities.NewMapDetails,String>("Discription");
			DiscriptionCol.setMinWidth(150);
			DiscriptionCol.setCellValueFactory(new PropertyValueFactory<common.entities.NewMapDetails, String>("Discription"));

			TableColumn<common.entities.NewMapDetails,String> ImageCol = new TableColumn<common.entities.NewMapDetails,String>("Image");
			ImageCol.setMinWidth(150);
			ImageCol.setCellValueFactory(new PropertyValueFactory<common.entities.NewMapDetails, String>("Image"));

			if(newMapTable.getItems().isEmpty() == true)
			{
				newMapTable.setItems(ol);
				newMapTable.getColumns().addAll(CountryNameCol, CityNameCol, DiscriptionCol, ImageCol);
			}
			else
			{
				newMapTable.getItems().clear();
				newMapTable.setItems(ol);
			}
		}
		else
		{
			newMapTable.getItems().clear();
		}
	}

	/*
	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param msg
	 * @throws SQLException
	 *
	 * CreateRowAtVersion => Send the request to add a new map to the manager
	 *//*
	public void CreateRowAtVersion(Object msg) 
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> data=(ArrayList<String>)((Message)msg).getObject();
		if(data.isEmpty() == true)
			return;
		String EID = data.get(0); // EmployeeID

		// Sending the request:
		// send query:                    *** Defalut value => (-1) mean "New"
		String query = "INSERT INTO versions VALUE ('"+ ChosenRow.getCountryName() +"','"+ ChosenRow.getCityName() +"',-1,-1,'"+ EID +"'); ";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SendRequestForAddNewMap,query));
	}
	  */
}