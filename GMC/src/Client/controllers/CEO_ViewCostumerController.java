package client.controllers;

import com.jfoenix.controls.JFXButton;
import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * CEO_ViewCostumerController => Controller for the CEO Costumer Info
 * 
 */
public class CEO_ViewCostumerController implements Initializable 
{
	public static CEO_ViewCostumerController Instance;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="TableBox"
	private VBox TableBox; // Value injected by FXMLLoader

	@FXML // fx:id="CostumerTable"
	private TableView<common.entities.CostumerDetails> CostumerTable; // Value injected by FXMLLoader

	@FXML // fx:id="ApproveVersionButton"
	private JFXButton ApproveVersionButton; // Value injected by FXMLLoader

	@FXML // fx:id="DataArchiveButton"
	private JFXButton DataArchiveButton; // Value injected by FXMLLoader

	@FXML // fx:id="EmployeeInfoButton"
	private JFXButton EmployeeInfoButton; // Value injected by FXMLLoader

	@FXML // fx:id="ViewReportsButton"
	private JFXButton ViewReportsButton; // Value injected by FXMLLoader

	@FXML // fx:id="ApprovePriceButton"
	private JFXButton ApprovePriceButton; // Value injected by FXMLLoader

	@FXML // fx:id="CostumerInfoButton"
	private JFXButton CostumerInfoButton; // Value injected by FXMLLoader


	@FXML // fx:id="CostumerHistoryTable"
	private TableView<common.entities.CostumerHistoryDetails> CostumerHistoryTable; // Value injected by FXMLLoader
	
    @FXML // fx:id="InfoButton"
    private ImageView InfoButton; // Value injected by FXMLLoader

	public common.entities.CostumerDetails rowVal;

	/** 
	 * Pressing LogOut => 
	 * Move to the Login
	 * 
	 * @param event
	 * @throws IOException
	 */
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
	void ShowManagerInfo(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_CEO.fxml");
	}
	
	@FXML
	void GoToHomePage(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}
	

	@FXML
	void ShowApprovePrice(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApprovePrice.fxml");
	}

	@FXML
	void ShowCostumerInfo(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_CostumerInfo.fxml");
	}

	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_EmployeeInfo.fxml");
	}

	@FXML
	void ShowDataArchive(ActionEvent event) 
	{
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_DataArchive.fxml");
	}

	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApproveVersion.fxml");
	}

	@FXML
	void ShowReports(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ShowReports.fxml");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
        assert CostumerTable != null : "fx:id=\"CostumerTable\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert CostumerHistoryTable != null : "fx:id=\"CostumerHistoryTable\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert ViewReportsButton != null : "fx:id=\"ViewReportsButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert ApprovePriceButton != null : "fx:id=\"ApprovePriceButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert CostumerInfoButton != null : "fx:id=\"CostumerInfoButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
        assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'CEO_CostumerInfo.fxml'.";
	}

	/**
	 * getting all the customers information from DB 
	 * @param event
	 */
	@FXML
	void ShowCostumerDetails(MouseEvent event) 
	{
		if(CostumerTable.getSelectionModel().getSelectedItem() != null) 
		{    
			rowVal = CostumerTable.getSelectionModel().getSelectedItem();
			String query = "SELECT userName, CityName, PurchaseType, PurchaseCurrentDate, PurchaseDateExpiration, numberOfVersion "
					+ "FROM gcm.purchasehistory WHERE userName = '"+ rowVal.getUserName() +"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getCostumerHistoryInfo,query));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		Instance=this;
		// send query:
		String query = "SELECT userName,FirstName,LastName,Email,PhoneNumber FROM gcm.user WHERE Role = 'customer';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getCostumerInfo,query));
	}

	/** 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetDataOnScreen => Showing the costumers information
	 */
	@SuppressWarnings("unchecked")
	public void SetDataOnScreen(Object msg) throws SQLException 
	{
		ArrayList<String> data=(ArrayList<String>)((Message)msg).getObject();
		ArrayList<common.entities.CostumerDetails> dataToSend = new ArrayList<common.entities.CostumerDetails>();
		int jump = 0;
		for(int i=0;i<data.size()/5;i++)
		{
			// userName ; FirstName ; LastName ; Email ; PhoneNumber => By the sql table 'user'
			common.entities.CostumerDetails rd = new common.entities.CostumerDetails(data.get(jump+0),data.get(jump+1),data.get(jump+2),data.get(jump+3),data.get(jump+4));
			dataToSend.add(rd);
			jump+=5;
		}
		// dataToSend containing all the data!
		// Inserting to viewTable:
		ObservableList<common.entities.CostumerDetails> ol = FXCollections.observableArrayList(dataToSend);

		// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
		TableColumn<common.entities.CostumerDetails,String> userNameCol = new TableColumn<common.entities.CostumerDetails,String>("User Name");
		userNameCol.setMinWidth(110);
		userNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerDetails, String>("userName"));

		TableColumn<common.entities.CostumerDetails,String> FirstNameCol = new TableColumn<common.entities.CostumerDetails,String>("First Name");
		FirstNameCol.setMinWidth(110);
		FirstNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerDetails, String>("FirstName"));

		TableColumn<common.entities.CostumerDetails,String> LastNameCol = new TableColumn<common.entities.CostumerDetails,String>("Last Name");
		LastNameCol.setMinWidth(120);
		LastNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerDetails, String>("LastName"));

		TableColumn<common.entities.CostumerDetails,String> EmailCol = new TableColumn<common.entities.CostumerDetails,String>("Email");
		EmailCol.setMinWidth(120);
		EmailCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerDetails, String>("Email"));

		TableColumn<common.entities.CostumerDetails,String> PhoneNumberCol = new TableColumn<common.entities.CostumerDetails,String>("Phone Number");
		PhoneNumberCol.setMinWidth(120);
		PhoneNumberCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerDetails, String>("PhoneNumber"));

		CostumerTable.setItems(ol);
		CostumerTable.getColumns().addAll(userNameCol, FirstNameCol, LastNameCol, EmailCol, PhoneNumberCol);
	}

	/**
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetHistoryOnScreen => Set the history of chosen costumer from CostumerTable
	 */
	@SuppressWarnings("unchecked")
	public void SetHistoryOnScreen(Object msg) throws SQLException
	{
		ArrayList<String> data=(ArrayList<String>)((Message)msg).getObject();
		if(data.isEmpty() == false)
		{
			ArrayList<common.entities.CostumerHistoryDetails> dataToSend = new ArrayList<common.entities.CostumerHistoryDetails>();
			int jump = 0;
			for(int i=0;i<data.size()/6;i++)
			{
				// userName ; CityName ; PurchaseType ; PurchaseCurrentDate ; PurchaseDateExpiration ; numberOfVersion => By the sql table 'purchasehistory'
				common.entities.CostumerHistoryDetails rd = new common.entities.CostumerHistoryDetails(data.get(jump+0),data.get(jump+1),data.get(jump+2),data.get(jump+3),data.get(jump+4),data.get(jump+5));
				dataToSend.add(rd);
				jump+=6;
			}
			// dataToSend containing all the data!
			// Inserting to viewTable:
			ObservableList<common.entities.CostumerHistoryDetails> ol = FXCollections.observableArrayList(dataToSend);

			// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
			TableColumn<common.entities.CostumerHistoryDetails,String> userNameCol = new TableColumn<common.entities.CostumerHistoryDetails,String>("User Name");
			userNameCol.setMinWidth(50);
			userNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerHistoryDetails, String>("userName"));

			TableColumn<common.entities.CostumerHistoryDetails,String> CityNameCol = new TableColumn<common.entities.CostumerHistoryDetails,String>("City Name");
			CityNameCol.setMinWidth(50);
			CityNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerHistoryDetails, String>("CityName"));

			TableColumn<common.entities.CostumerHistoryDetails,String> PurchaseTypeCol = new TableColumn<common.entities.CostumerHistoryDetails,String>("Purchase Type");
			PurchaseTypeCol.setMinWidth(100);
			PurchaseTypeCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerHistoryDetails, String>("PurchaseType"));

			TableColumn<common.entities.CostumerHistoryDetails,String> PurchaseCurrentDateCol = new TableColumn<common.entities.CostumerHistoryDetails,String>("Purchase Current Date");
			PurchaseCurrentDateCol.setMinWidth(125);
			PurchaseCurrentDateCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerHistoryDetails, String>("PurchaseCurrentDate"));

			TableColumn<common.entities.CostumerHistoryDetails,String> PurchaseDateExpirationCol = new TableColumn<common.entities.CostumerHistoryDetails,String>("Purchase Date Expiration");
			PurchaseDateExpirationCol.setMinWidth(125);
			PurchaseDateExpirationCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerHistoryDetails, String>("PurchaseDateExpiration"));

			TableColumn<common.entities.CostumerHistoryDetails,String> numberOfVersionCol = new TableColumn<common.entities.CostumerHistoryDetails,String>("Number Of Version");
			numberOfVersionCol.setMinWidth(150);
			numberOfVersionCol.setCellValueFactory(new PropertyValueFactory<common.entities.CostumerHistoryDetails, String>("numberOfVersion"));

			if(CostumerHistoryTable.getItems().isEmpty() == true)
			{
				CostumerHistoryTable.setItems(ol);
				CostumerHistoryTable.getColumns().addAll(userNameCol, CityNameCol, PurchaseTypeCol, PurchaseCurrentDateCol, PurchaseDateExpirationCol, numberOfVersionCol);
			}
			else
			{
				CostumerHistoryTable.getItems().clear();
				CostumerHistoryTable.setItems(ol);
			}
		}
	}

}
