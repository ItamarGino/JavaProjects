/**
 * Sample Skeleton for 'Content_EmployeeInfo.fxml' Controller Class
 */

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * Content_EmployeeInfoController is a controller for Content Depatrment Manger EmployeeInfo.
 */
public class Content_EmployeeInfoController implements Initializable
{

	@FXML // fx:id="EmployeeTable"
	private TableView<common.entities.EmployeeDetails> EmployeeTable; // Value injected by FXMLLoader

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="DataArchiveButton"
	private JFXButton DataArchiveButton; // Value injected by FXMLLoader

	@FXML // fx:id="EmployeeInfoButton"
	private JFXButton EmployeeInfoButton; // Value injected by FXMLLoader

	@FXML // fx:id="ApproveVersionButton"
	private JFXButton ApproveVersionButton; // Value injected by FXMLLoader

	@FXML // fx:id="ReportButton"
	private JFXButton ReportButton; // Value injected by FXMLLoader

    @FXML // fx:id="InfoButton"
    private ImageView InfoButton; // Value injected by FXMLLoader

	public static Content_EmployeeInfoController Instance;

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
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_ApproveVersion.fxml");
	}

	@FXML
	void ShowReport(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_ShowReports.fxml");
	}

	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_EmployeeInfo.fxml");
	}
	
	@FXML
	void GoToHomePage(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}

	@FXML
	void ShowManagerInfo(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_Content.fxml");
	}

	@FXML
	void ShowDataArchive(ActionEvent event) 
	{
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_DataArchive.fxml");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
		assert EmployeeTable != null : "fx:id=\"EmployeeTable\" was not injected: check your FXML file 'Content_EmployeeInfo.fxml'.";
		assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'Content_EmployeeInfo.fxml'.";
		assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'Content_EmployeeInfo.fxml'.";
		assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'Content_EmployeeInfo.fxml'.";
		assert ReportButton != null : "fx:id=\"ReportButton\" was not injected: check your FXML file 'Content_EmployeeInfo.fxml'.";
		assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'Content_EmployeeInfo.fxml'.";
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		Instance=this;
		// send query:
		String query = "SELECT id, fname, lname, email, erole FROM gcm.employee;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getEmployeeInfo,query));
	}

	/**
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetDataOnScreen => Showing the request information about employee
	 */
	@SuppressWarnings("unchecked")
	public void SetDataOnScreen(Object msg) throws SQLException 
	{
		// Assembling the data:
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		ArrayList<common.entities.EmployeeDetails> dataToSend = new ArrayList<common.entities.EmployeeDetails>();
		int jump = 0;
		for(int i=0;i<data.size()/5; i++)
		{
			// id ; fname ; lname ; email ; erole => By the sql table 'employee'
			common.entities.EmployeeDetails rd = new common.entities.EmployeeDetails(data.get(0+jump),data.get(1+jump),data.get(2+jump),data.get(3+jump),data.get(4+jump));
			dataToSend.add(rd);
			jump+=5;
		}
		// dataToSend containing all the data!
		// Inserting to viewTable:
		ObservableList<common.entities.EmployeeDetails> ol = FXCollections.observableArrayList(dataToSend);

		// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
		TableColumn<common.entities.EmployeeDetails,String> userNameCol = new TableColumn<common.entities.EmployeeDetails,String>("ID");
		userNameCol.setMinWidth(120);
		userNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.EmployeeDetails, String>("id"));

		TableColumn<common.entities.EmployeeDetails,String> FirstNameCol = new TableColumn<common.entities.EmployeeDetails,String>("First Name");
		FirstNameCol.setMinWidth(120);
		FirstNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.EmployeeDetails, String>("fname"));

		TableColumn<common.entities.EmployeeDetails,String> LastNameCol = new TableColumn<common.entities.EmployeeDetails,String>("Last Name");
		LastNameCol.setMinWidth(120);
		LastNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.EmployeeDetails, String>("lname"));

		TableColumn<common.entities.EmployeeDetails,String> EmailCol = new TableColumn<common.entities.EmployeeDetails,String>("Email");
		EmailCol.setMinWidth(120);
		EmailCol.setCellValueFactory(new PropertyValueFactory<common.entities.EmployeeDetails, String>("email"));

		TableColumn<common.entities.EmployeeDetails,String> roleCol = new TableColumn<common.entities.EmployeeDetails,String>("Role");
		roleCol.setMinWidth(120);
		roleCol.setCellValueFactory(new PropertyValueFactory<common.entities.EmployeeDetails, String>("erole"));

		EmployeeTable.setItems(ol);
		EmployeeTable.getColumns().addAll(userNameCol, FirstNameCol, LastNameCol, EmailCol, roleCol);
	}

}
