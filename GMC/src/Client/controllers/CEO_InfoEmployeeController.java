package client.controllers;

import com.jfoenix.controls.JFXButton;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;

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
 * CEO_InfoEmployeeController is a controller for CoEO InfoEmployee.
 */
public class CEO_InfoEmployeeController extends Content_EmployeeInfoController
{

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="EmployeeTable"
	private TableView<common.entities.EmployeeDetails> EmployeeTable; // Value injected by FXMLLoader

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

	public static CEO_InfoEmployeeController Instance;
	/**
	 * moving to CEO_ApprovePrice 
	 * @param event
	 */
	@FXML
	void ShowApprovePrice(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApprovePrice.fxml");
	}
	/**
	 * moving to CEO_ApproveVersion 
	 * @param event
	 */
	@Override
	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApproveVersion.fxml");
	}
	/**
	 * moving to CEO_CostumerInfo
	 * @param event
	 */
	@FXML
	void ShowCostumerInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_CostumerInfo.fxml");
	}
	
	@Override
	@FXML
	void ShowDataArchive(ActionEvent event)
	{
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_DataArchive.fxml");
	}
	/**
	 * moving to CEO_EmployeeInfo
	 * @param event
	 */
	@Override
	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_EmployeeInfo.fxml");
	}
	/**
	 * moving to ManagerArea_CEO 
	 * @param event
	 */
	@Override
	@FXML
	void ShowManagerInfo(MouseEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_CEO.fxml");
	}
	/**
	 * moving to CEO_ShowReports 
	 * @param event
	 */
	@FXML
	void ShowReports(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ShowReports.fxml");
	}

	@Override
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
        assert EmployeeTable != null : "fx:id=\"EmployeeTable\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert ViewReportsButton != null : "fx:id=\"ViewReportsButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert ApprovePriceButton != null : "fx:id=\"ApprovePriceButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert CostumerInfoButton != null : "fx:id=\"CostumerInfoButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";
        assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'CEO_EmployeeInfo.fxml'.";

	}
}
