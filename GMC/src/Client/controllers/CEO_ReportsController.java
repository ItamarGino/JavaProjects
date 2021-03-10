package client.controllers;

import com.jfoenix.controls.JFXButton;
import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * CEO_ReportsController => Controller for the CEO Reports
 * 
 */
public class CEO_ReportsController extends Content_ShowReportsController
{

	public static CEO_ReportsController Instance; // instance initialize when fxml is loading (initialize() method) - shahar.

	@FXML // fx:id="dateFrom"
	private DatePicker dateFrom; // Value injected by FXMLLoader

	@FXML // fx:id="dateUntil"
	private DatePicker dateUntil; // Value injected by FXMLLoader

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="ChooseCityBox"
	private ComboBox<String> ChooseCityBox; // Value injected by FXMLLoader

	@FXML // fx:id="ChooseCountryBox"
	private ComboBox<String> ChooseCountryBox; // Value injected by FXMLLoader

	@FXML
	private JFXButton CalculateReportButton;

	@FXML
	private AnchorPane ReportPane;

	@FXML
	private CheckBox pdfButton;

	@FXML
	private Text cityText;

	@FXML
	private Text countyName;

	@FXML
	private Text cityNameText;

	@FXML
	private Text map;

	@FXML
	private Text purchase;

	@FXML
	private Text sub;

	@FXML
	private Text renewal;

	@FXML
	private Text view;

	@FXML
	private Text download;

	@FXML
	private Text date;

	@FXML
	private JFXButton ApproveVersionButton;

	@FXML
	private JFXButton DataArchiveButton;

	@FXML
	private JFXButton EmployeeInfoButton;

    @FXML // fx:id="ReportButton"
    private JFXButton ReportButton; // Value injected by FXMLLoader

	@FXML
	private JFXButton ApprovePriceButton;

	@FXML
	private JFXButton CostumerInfoButton;

	@FXML // fx:id="CityImage"
	private ImageView CityImage; // Value injected by FXMLLoader

	@FXML // fx:id="InfoButton"
	private ImageView InfoButton; // Value injected by FXMLLoader

	@FXML // fx:id="ChooseAllTable"
	private TableView<common.entities.ReportDetails> ChooseAllTable; // Value injected by FXMLLoader

	@FXML // fx:id="ChooseAllTable"
	private TableView<common.entities.ReportDetails_WithoutCountry> ChooseAllTable_OneCountry; // Value injected by FXMLLoader

	public boolean pdfFlag;

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

	@Override
	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApproveVersion.fxml");
	}

	@Override
	@FXML
	void ShowDataArchive(ActionEvent event)
	{
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_DataArchive.fxml");

	}

	@Override
	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_EmployeeInfo.fxml");
	}

	@Override
	@FXML
	void ShowManagerInfo(MouseEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_CEO.fxml");
	}

	@FXML
	void ShowReports(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ShowReports.fxml");
	}

	@Override
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
		assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ReportButton != null : "fx:id=\"ReportButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert pdfButton != null : "fx:id=\"pdfButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert CalculateReportButton != null : "fx:id=\"CalculateReportButton\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ReportPane != null : "fx:id=\"ReportPane\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert countyName != null : "fx:id=\"countyName\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert cityNameText != null : "fx:id=\"cityNameText\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert map != null : "fx:id=\"map\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert purchase != null : "fx:id=\"purchase\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert sub != null : "fx:id=\"sub\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert renewal != null : "fx:id=\"renewal\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert download != null : "fx:id=\"download\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ChooseAllTable != null : "fx:id=\"ChooseAllTable\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ChooseAllTable_OneCountry != null : "fx:id=\"ChooseAllTable_OneCountry\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ChooseCityBox != null : "fx:id=\"ChooseCityBox\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert ChooseCountryBox != null : "fx:id=\"ChooseCountryBox\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert dateFrom != null : "fx:id=\"dateFrom\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
		assert dateUntil != null : "fx:id=\"dateUntil\" was not injected: check your FXML file 'CEO_ShowReports.fxml'.";
	}
/**
 * sending query for countries and cities
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		Instance=this;

		pdfFlag = false;

		// send query for countries and cities:
		String query = "SELECT DISTINCT CountryName, CityName FROM gcm.maps_catalog ORDER BY CountryName;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getCityAndCountryList,query));

	}
}