package client.controllers;

import com.jfoenix.controls.JFXButton;
import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.RequestDetails;

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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
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
 * CEO_ApprovePriceController is a controller for CoEO ApprovePrice.
 */
public class CEO_ApprovePriceController implements Initializable 
{
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="TableBox"
	private VBox TableBox; // Value injected by FXMLLoader

	@FXML // fx:id="PriceApproveTable"
	private TableView<common.entities.RequestDetails> PriceApproveTable; // Value injected by FXMLLoader

	@FXML // fx:id="approveButton"
	private JFXButton approveButton; // Value injected by FXMLLoader

	@FXML // fx:id="rejectButton"
	private JFXButton rejectButton; // Value injected by FXMLLoader

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

    @FXML // fx:id="InfoButton"
    private ImageView InfoButton; // Value injected by FXMLLoader

	public static CEO_ApprovePriceController Instance;

	public common.entities.RequestDetails rowVal;

	public String MapID;

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
/**
 * moving to page for ceo --> manager area
 * @param event
 */
	@FXML
	void ShowManagerInfo(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_CEO.fxml");
	}
	/**
	 * moving to home page 
	 * @param event
	 */
	@FXML
	void GoToHomePage(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}
	/**
	 * moving to ApprovePrice window 
	 * @param event
	 */
	@FXML
	void ShowApprovePrice(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApprovePrice.fxml");
	}
	/**
	 * moving to CostumerInfo window 
	 * @param event
	 */
	@FXML
	void ShowCostumerInfo(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_CostumerInfo.fxml");
	}
	/**
	 * moving to ShowReports window 
	 * @param event
	 */
	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ShowReports.fxml");
	}

	/**
	 * @param event
	 * 
	 * On Mouse Click => Showing the buttons
	 */
	@FXML
	void ShowYesNoButtons(MouseEvent event)
	{
		approveButton.setVisible(true);
		rejectButton.setVisible(true);
	}

	/**
	 * @param event
	 * 
	 * send Approvment about the new price of map
	 */
	@FXML
	void sendApprovment(ActionEvent event)
	{
		if(PriceApproveTable.getSelectionModel().getSelectedItem() != null) 
		{    
			rowVal = PriceApproveTable.getSelectionModel().getSelectedItem();
			MapID = rowVal.getMapID();
			// send query:
			String query = "UPDATE gcm.map "
					+ "SET Price = '"+ rowVal.getRequestPrice() +"' "
					+ "WHERE ID = '"+ rowVal.getMapID() +"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UpdatePriceForMap,query));
		}
		removeRow();
	}

	/**
	 * @param event
	 * 
	 * send Rejection --> the price was not approve 
	 */
	@FXML
	void sendRejection(ActionEvent event)
	{
		removeRow();
	}

	/**
	 * removeRow => remove the chosen row from request table
	 */
	void removeRow()
	{
		// send query:
		String query = "DELETE FROM gcm.request WHERE mapID ='"+ rowVal.getMapID() +"' AND employee ='"+rowVal.getEmployeeName()+"' AND mdetails ='"+rowVal.getMapDetails()+"' "
				+ "AND lprice ='"+rowVal.getLastPrice()+"' AND rprice ='"+rowVal.getRequestPrice()+"';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.removeVersionRow,query));

		// send query:
		String query2 = "SELECT MapID, employee, mdetails, lprice, rprice FROM gcm.request;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getPriceApproveRequestsData,query2));
	}

	@FXML
	void ShowDataArchive(ActionEvent event) 
	{
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_DataArchive.fxml");
	}
	/**
	 * moving to ApproveVersion window 
	 * @param event
	 */
	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApproveVersion.fxml");
	}
	
	/**
	 * moving to ShowReports window 
	 * @param event
	 */
	@FXML
	void ShowReports(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ShowReports.fxml");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
        assert TableBox != null : "fx:id=\"TableBox\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert PriceApproveTable != null : "fx:id=\"PriceApproveTable\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert approveButton != null : "fx:id=\"approveButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert rejectButton != null : "fx:id=\"rejectButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert ViewReportsButton != null : "fx:id=\"ViewReportsButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert ApprovePriceButton != null : "fx:id=\"ApprovePriceButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert CostumerInfoButton != null : "fx:id=\"CostumerInfoButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
        assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'CEO_ApprovePrice.fxml'.";
	}


	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		Instance=this;
		// send query:
		String query = "SELECT  MapID, employee, mdetails, lprice, rprice FROM gcm.request;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getPriceApproveRequestsData,query));
	}

	/** 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetDataOnScreen => Showing the information
	 */
	@SuppressWarnings("unchecked")
	public void SetDataOnScreen(Object msg) throws SQLException 
	{
		// Assembling the data:
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		ArrayList<common.entities.RequestDetails> dataToSend = new ArrayList<common.entities.RequestDetails>();
		int jump = 0;
		for(int i=0; i<data.size()/5;i++)
		{
			// MapID ; employee ; mdetails ; lprice ; rprice => By the sql table 'request'
			common.entities.RequestDetails rd = new common.entities.RequestDetails(data.get(0+jump),data.get(1+jump),
					data.get(2+jump),data.get(3+jump),data.get(4+jump));
			dataToSend.add(rd);
			jump+=5;
		}
		// dataToSend containing all the data!
		// Inserting to viewTable:
		ObservableList<common.entities.RequestDetails> ol = FXCollections.observableArrayList(dataToSend);

		// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
		TableColumn<common.entities.RequestDetails,String> mapIDCol = new TableColumn<common.entities.RequestDetails,String>("Map ID");
		mapIDCol.setMinWidth(120);
		mapIDCol.setCellValueFactory(new PropertyValueFactory<common.entities.RequestDetails, String>("mapID"));

		TableColumn<common.entities.RequestDetails,String> employeeNameCol = new TableColumn<common.entities.RequestDetails,String>("Employee");
		employeeNameCol.setMinWidth(120);
		employeeNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.RequestDetails, String>("employeeName"));

		TableColumn<common.entities.RequestDetails,String> mapDetailsCol = new TableColumn<common.entities.RequestDetails,String>("Map Details");
		mapDetailsCol.setMinWidth(120);
		mapDetailsCol.setCellValueFactory(new PropertyValueFactory<common.entities.RequestDetails, String>("mapDetails"));

		TableColumn<common.entities.RequestDetails,String> lastPriceCol = new TableColumn<common.entities.RequestDetails,String>("Last Price");
		lastPriceCol.setMinWidth(120);
		lastPriceCol.setCellValueFactory(new PropertyValueFactory<common.entities.RequestDetails, String>("lastPrice"));

		TableColumn<common.entities.RequestDetails,String> requestPriceCol = new TableColumn<common.entities.RequestDetails,String>("Request Price");
		requestPriceCol.setMinWidth(120);
		requestPriceCol.setCellValueFactory(new PropertyValueFactory<common.entities.RequestDetails, String>("RequestPrice"));

		if(PriceApproveTable.getItems().isEmpty() == true)
		{
			PriceApproveTable.setItems(ol);
			PriceApproveTable.getColumns().addAll(mapIDCol,employeeNameCol, mapDetailsCol,lastPriceCol,requestPriceCol);
		}
		else
		{
			PriceApproveTable.getItems().clear();
			PriceApproveTable.setItems(ol);
		}
	}
}
