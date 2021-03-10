package client.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.sun.jndi.toolkit.url.Uri;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.ReportDetails;
import common.entities.ReportDetails_WithoutCountry;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.controllers.CreatePDFController;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * Content_ShowReportsController is a controller for Content Depatrment Manger Reports.
 */
public class Content_ShowReportsController implements Initializable 
{
	public static Content_ShowReportsController Instance; // instance initialize when fxml is loading (initialize() method) - shahar.
	private ArrayList<String> result;
	private String cityName;
	private String CountryName;

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

	@FXML
	private JFXButton ViewReportsButton;

	@FXML
	private JFXButton ApprovePriceButton;

	@FXML
	private JFXButton CostumerInfoButton;

	@FXML // fx:id="InfoButton"
	private ImageView InfoButton; // Value injected by FXMLLoader

	@FXML // fx:id="ChooseAllTable"
	private TableView<common.entities.ReportDetails> ChooseAllTable; // Value injected by FXMLLoader

	@FXML // fx:id="ChooseAllTable"
	private TableView<common.entities.ReportDetails_WithoutCountry> ChooseAllTable_OneCountry; // Value injected by FXMLLoader

	@FXML
	private ImageView CityImage;

	public LocalDate DateFrom = null;
	public LocalDate DateUntil = null;

	public boolean pdfFlag;

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param event
	 * 
	 * getDateFrom => get the dateFrom and set DateFrom
	 */
	@FXML
	void getDateFrom(ActionEvent event) 
	{
		if(dateFrom.getValue() != null)
		{
			DateFrom = dateFrom.getValue();
		}
	}

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param event
	 * 
	 * getDateUntil => get the dateUntil and set DateUntil
	 */
	@FXML
	void getDateUntil(ActionEvent event) 
	{
		if(dateUntil.getValue() != null)
		{
			DateUntil = dateUntil.getValue();		}
	}

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
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

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * @param event
	 * 
	 * DownloadPDF => Download the request PDF file
	 * Downloading to the folder => Reports
	 */
	@FXML
	void DownloadPDF(ActionEvent event)
	{
		if(this.pdfFlag == false)
			this.pdfFlag = true;
		else
			this.pdfFlag = false;
	}

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param event
	 * @throws IOException
	 * 
	 * CalculateReport => Sending the specific query to DB
	 * 
	 */
	@FXML
	void CalculateReport(ActionEvent event) throws IOException
	{
		if(CountryName.equals("Choose All"))
		{
			if(DateFrom == null && DateUntil == null)
			{
				// Calculating report without time
				// Getting details about *ALL* the city in GCM
				// Sum the view from all the map! not just the view from specific city catalog

				String query ="SELECT DISTINCT maps_catalog.CountryName, maps_catalog.CityName as city, maps_catalog.MapQuantity, " + 
						"maps_catalog.NumberOfOneTimePurchase, maps_catalog.NumberOfSubscription, maps_catalog.NumberOfRenewal, " + 
						"maps_catalog.ViewCount AS views, maps_catalog.Download_Quantity " + 
						"FROM maps_catalog";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Report_ChooseAll,query));
				ChooseAllTable.setVisible(false);
				ChooseAllTable_OneCountry.setVisible(false);
				ReportPane.setVisible(false);
			}
			else // With time !
			{
				if(DateFrom != null && DateUntil != null && DateFrom.isAfter(DateUntil))
				{
					// Calculating report without time
					// Getting details about *ALL* the city in GCM
					// Show the views number from city catalog (not from all the map)

					String dateFrom = DateFrom.toString().split(" ")[0].replaceAll("-", "/");
					String dateUntil = DateUntil.toString().split(" ")[0].replaceAll("-", "/");
					
					String query ="SELECT Distinct maps_catalog.CountryName, maps_catalog.CityName as city, " + 
							"maps_catalog.MapQuantity, maps_catalog.NumberOfOneTimePurchase, " + 
							"maps_catalog.NumberOfSubscription, maps_catalog.NumberOfRenewal, " + 
							"maps_catalog.ViewCount, maps_catalog.Download_Quantity " + 
							"FROM maps_catalog, purchasehistory " + 
							"WHERE maps_catalog.CityName = purchasehistory.CityName " + 
							"AND PurchaseCurrentDate >= '"+ dateUntil +"' AND PurchaseDateExpiration <= '"+ dateFrom +"';";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Report_ChooseAll,query));
					ChooseAllTable.setVisible(false);
					ChooseAllTable_OneCountry.setVisible(false);
					ReportPane.setVisible(false);
				}
			}
		}
		else if(cityName.equals("Choose All") && CountryName.isEmpty() == false)
		{
			if(DateFrom == null && DateUntil == null)
			{
				// Calculating report without time
				// Getting details about *ALL* the city in specific Country
				// Sum the view from all the map! not just the view from specific city catalog

				String query = "SELECT DISTINCT maps_catalog.CityName as city, maps_catalog.MapQuantity, " + 
						"maps_catalog.NumberOfOneTimePurchase, maps_catalog.NumberOfSubscription, maps_catalog.NumberOfRenewal, " + 
						"maps_catalog.ViewCount AS views, maps_catalog.Download_Quantity, CURRENT_TIMESTAMP " + 
						"FROM maps_catalog WHERE  "
						+ " maps_catalog.CountryName = '"+ CountryName +"';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Report_ChooseAll_OneCountry,query));
				ChooseAllTable_OneCountry.setVisible(false);
				ChooseAllTable.setVisible(false);
				ReportPane.setVisible(false);
			}
			else // With time !
			{
				if(DateFrom != null && DateUntil != null)
				{
					// Calculating report without time
					// Getting details about *ALL* the city in specific Country
					// Show the views number from city catalog (not from all the map)
					String dateFrom = DateFrom.toString().split(" ")[0].replaceAll("-", "/");
					String dateUntil = DateUntil.toString().split(" ")[0].replaceAll("-", "/");
					String query = "SELECT DISTINCT maps_catalog.CityName as city, maps_catalog.MapQuantity, " + 
							" maps_catalog.NumberOfOneTimePurchase, maps_catalog.NumberOfSubscription, maps_catalog.NumberOfRenewal, " + 
							" maps_catalog.ViewCount, maps_catalog.Download_Quantity, CURRENT_TIMESTAMP " + 
							" FROM maps_catalog, purchasehistory " + 
							" WHERE maps_catalog.CountryName = '"+ CountryName +"'" + 
							" AND maps_catalog.CityName = purchasehistory.CityName " + 
							" AND PurchaseCurrentDate >= '"+ dateUntil +"' AND PurchaseDateExpiration <= '"+ dateFrom +"';";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Report_ChooseAll_OneCountry,query));
					ChooseAllTable_OneCountry.setVisible(false);
					ChooseAllTable.setVisible(false);
					ReportPane.setVisible(false);
				}
			}
		}
		else if(CountryName.isEmpty() == false && cityName.isEmpty() == false)
		{
			if(DateFrom == null && DateUntil == null)
			{
				// Calculating report without time
				// Getting details about specific city
				// Sum the view from all the map! not just the view from specific city catalog
				String query = "SELECT maps_catalog.CountryName, maps_catalog.CityName, maps_catalog.MapQuantity, maps_catalog.NumberOfOneTimePurchase, maps_catalog.NumberOfSubscription, maps_catalog.NumberOfRenewal, maps_catalog.ViewCount AS views, maps_catalog.Download_Quantity, CURRENT_TIMESTAMP FROM maps_catalog, map WHERE maps_catalog.CountryName = '"+ CountryName +"' AND maps_catalog.CityName = '"+ cityName +"' AND map.CityName = maps_catalog.CityName;";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Report_SingleCity,query));
				ChooseAllTable.setVisible(false);
				ChooseAllTable_OneCountry.setVisible(false);
				ReportPane.setVisible(false);	
			}
			else // With time !
			{
				if(DateFrom != null && DateUntil != null)
				{
					// Calculating report without time
					// Getting details about specific city
					// Show the views number from city catalog (not from all the map)
					String dateFrom = DateFrom.toString().split(" ")[0].replaceAll("-", "/");
					String dateUntil = DateUntil.toString().split(" ")[0].replaceAll("-", "/");
					String query = "SELECT maps_catalog.CountryName, maps_catalog.CityName," + 
							" maps_catalog.MapQuantity, maps_catalog.NumberOfOneTimePurchase," + 
							" maps_catalog.NumberOfSubscription, maps_catalog.NumberOfRenewal," + 
							" maps_catalog.ViewCount, maps_catalog.Download_Quantity, CURRENT_TIMESTAMP" + 
							" FROM maps_catalog, purchasehistory" + 
							" WHERE maps_catalog.CountryName = '"+ CountryName +"' " + 
							" AND maps_catalog.CityName = '"+ cityName +"'" + 
							" AND maps_catalog.CityName = purchasehistory.CityName " +
							" AND PurchaseCurrentDate >= '"+ dateUntil +"' AND PurchaseDateExpiration <= '"+ dateFrom +"';";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Report_SingleCity,query));
					ChooseAllTable.setVisible(false);
					ChooseAllTable_OneCountry.setVisible(false);
					ReportPane.setVisible(false);	
				}
			}
		}
	}

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param event
	 * 
	 * ChooseCountry => Set the countries
	 */
	@FXML
	void ChooseCountry(ActionEvent event)
	{
		if (ChooseCountryBox.getValue() != null && !ChooseCountryBox.getValue().toString().isEmpty())
		{
			String countryName = ChooseCountryBox.getValue().toString();
			ArrayList<String> citiesToChoose = new ArrayList<String>();

			// Search for country's cities:
			for(int i=0; i<result.size(); i++)
				if(result.get(i).equals(countryName))
					citiesToChoose.add(result.get(i+1));

			// Special option => to choose all city
			citiesToChoose.add("Choose All");

			// Set ChoiceBox:
			ObservableList<String> cityChoose = FXCollections.observableArrayList(citiesToChoose);
			ChooseCityBox.setItems(cityChoose);

			this.CountryName = (String) ChooseCountryBox.getValue();

			if(CountryName != null)
			{
				if(CountryName.equals("Choose All") == false) 
				{
					cityText.setVisible(true);
					ChooseCityBox.setVisible(true);
				}
				else
				{
					cityText.setVisible(false);
					ChooseCityBox.setVisible(false);
				}
			}
			if(CountryName != null)
				if((cityName != null || CountryName.equals("Choose All")) && CountryName != null)
				{
					CalculateReportButton.setVisible(true);
				}
		}
	}

	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param event
	 * 
	 * ChooseCity => Set the cities
	 */
	@FXML
	void ChooseCity(ActionEvent event)
	{
		if (ChooseCityBox.getValue() != null && !ChooseCountryBox.getValue().toString().isEmpty())
		{
			this.cityName = ChooseCityBox.getValue();

			if(CountryName!=null)
				if((cityName != null || CountryName.equals("Choose All")) && CountryName != null)
				{
					CalculateReportButton.setVisible(true);
				}
		}
	}

	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_ApproveVersion.fxml");
	}

	@FXML
	void ShowDataArchive(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CityCatalog.fxml");
	}

	@FXML
	void GoToHomePage(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}

	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_EmployeeInfo.fxml");
	}

	@FXML
	void ShowManagerInfo(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_Content.fxml");
	}

	@FXML
	void ShowReport(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_ShowReports.fxml");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
		assert pdfButton != null : "fx:id=\"pdfButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ChooseCityBox != null : "fx:id=\"ChooseCityBox\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert cityText != null : "fx:id=\"cityText\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ChooseCountryBox != null : "fx:id=\"ChooseCountryBox\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert CalculateReportButton != null : "fx:id=\"CalculateReportButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ReportPane != null : "fx:id=\"ReportPane\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert countyName != null : "fx:id=\"countyName\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert cityNameText != null : "fx:id=\"cityNameText\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert map != null : "fx:id=\"map\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert purchase != null : "fx:id=\"purchase\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert sub != null : "fx:id=\"sub\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert renewal != null : "fx:id=\"renewal\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert view != null : "fx:id=\"view\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert download != null : "fx:id=\"download\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ChooseAllTable != null : "fx:id=\"ChooseAllTable\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ChooseAllTable_OneCountry != null : "fx:id=\"ChooseAllTable_OneCountry\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ViewReportsButton != null : "fx:id=\"ViewReportsButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert ApprovePriceButton != null : "fx:id=\"ApprovePriceButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert CostumerInfoButton != null : "fx:id=\"CostumerInfoButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
		assert CityImage != null : "fx:id=\"CityImage\" was not injected: check your FXML file 'Content_ShowReports.fxml'.";
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		Instance=this;
		pdfFlag = false;

		// send query for countries and cities:
		String query = "SELECT CountryName, CityName FROM gcm.maps_catalog ORDER BY CountryName;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getCityAndCountryList,query));
	}


	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetReport_SingleCity_Content => Set the current window with the specific report
	 */
	@SuppressWarnings("unchecked")
	public void SetReport_SingleCity_Content(Object msg) throws SQLException
	{	
		ArrayList<String> resultSingleCity = (ArrayList<String>)((Message)msg).getObject();
		if(resultSingleCity.isEmpty() == false)
		{
			ReportPane.setVisible(true);
			countyName.setText(CountryName);
			cityNameText.setText(cityName);
			date.setText(resultSingleCity.get(8));
			map.setText(resultSingleCity.get(2));
			purchase.setText(resultSingleCity.get(3));
			sub.setText(resultSingleCity.get(4));
			renewal.setText(resultSingleCity.get(5));
			view.setText(resultSingleCity.get(6));
			download.setText(resultSingleCity.get(7));

			//MakePDF()
			if(this.pdfFlag == true)
			{
				CreatePDFController.MakePDF(CountryName,cityName,resultSingleCity.get(2),resultSingleCity.get(3),resultSingleCity.get(4)
						,resultSingleCity.get(5),resultSingleCity.get(6),resultSingleCity.get(7),resultSingleCity.get(8));
			}
		}
	}

	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetReport_ChooseAll_OneCountry_Content => Set the current window with the specific report
	 */
	@SuppressWarnings("unchecked")
	public void SetReport_ChooseAll_OneCountry_Content(Object msg) throws SQLException 
	{	
		ArrayList<String> result = (ArrayList<String>)((Message)msg).getObject();
		if(result.isEmpty() == false)
		{
			ChooseAllTable_OneCountry.setVisible(true);

			ArrayList<common.entities.ReportDetails_WithoutCountry> dataToSend = new ArrayList<common.entities.ReportDetails_WithoutCountry>();
			int jump = 0;
			for(int i=0; i<result.size()/7;i++)
			{
				// city, MapQuantity, NumberOfOneTimePurchase,
				// NumberOfSubscription, NumberOfRenewal, views, Download_Quantity => By the sql VIEW 'mapView'
				common.entities.ReportDetails_WithoutCountry rd = new common.entities.ReportDetails_WithoutCountry(result.get(jump),result.get(1+jump),result.get(2+jump),
						result.get(3+jump),result.get(4+jump),result.get(5+jump),result.get(6+jump));
				dataToSend.add(rd);
				jump+=7;
			}
			// dataToSend containing all the data!
			// Inserting to viewTable:
			ObservableList<common.entities.ReportDetails_WithoutCountry> ol = (ObservableList<ReportDetails_WithoutCountry>) FXCollections.observableArrayList(dataToSend);

			// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
			TableColumn<common.entities.ReportDetails_WithoutCountry,String> CityCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("City");
			CityCol.setMinWidth(80);
			CityCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("city"));

			TableColumn<common.entities.ReportDetails_WithoutCountry,String> MapQuantityCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("#Maps");
			MapQuantityCol.setMinWidth(50);
			MapQuantityCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("MapQuantity"));

			TableColumn<common.entities.ReportDetails_WithoutCountry,String> NumberOfOneTimePurchaseCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("One-Time Purchase");
			NumberOfOneTimePurchaseCol.setMinWidth(50);
			NumberOfOneTimePurchaseCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("NumberOfOneTimePurchase"));

			TableColumn<common.entities.ReportDetails_WithoutCountry,String> NumberOfSubscriptionCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("#Subscriptions");
			NumberOfSubscriptionCol.setMinWidth(80);
			NumberOfSubscriptionCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("NumberOfSubscription"));

			TableColumn<common.entities.ReportDetails_WithoutCountry,String> NumberOfRenewaleCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("#Renewal");
			NumberOfRenewaleCol.setMinWidth(80);
			NumberOfRenewaleCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("NumberOfRenewal"));

			TableColumn<common.entities.ReportDetails_WithoutCountry,String> viewsCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("#View");
			viewsCol.setMinWidth(80);
			viewsCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("views"));

			TableColumn<common.entities.ReportDetails_WithoutCountry,String> Download_QuantityCol = new TableColumn<common.entities.ReportDetails_WithoutCountry,String>("#Download");
			Download_QuantityCol.setMinWidth(80);
			Download_QuantityCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails_WithoutCountry, String>("Download_Quantity"));

			if(ChooseAllTable_OneCountry.getItems().isEmpty() == true)
			{
				ChooseAllTable_OneCountry.setItems(ol);
				ChooseAllTable_OneCountry.getColumns().addAll(CityCol, MapQuantityCol, NumberOfOneTimePurchaseCol, NumberOfSubscriptionCol, NumberOfRenewaleCol, viewsCol, Download_QuantityCol);
			}
			else
			{
				ChooseAllTable_OneCountry.getItems().clear();
				ChooseAllTable_OneCountry.setItems(ol);
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			if(this.pdfFlag == true)
			{
				int jumpPDF = 0;
				for(int i=0; i<result.size()/7; i++)
				{
					CreatePDFController.MakePDF(CountryName,result.get(jumpPDF),result.get(1+jumpPDF),result.get(2+jumpPDF),result.get(3+jumpPDF)
							,result.get(4+jumpPDF),result.get(5+jumpPDF),result.get(6+jumpPDF),dateFormat.format(date)); // without date!
					jumpPDF+=7;
				}
			}
		}
	}

	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetReport_ChooseAll_Content => Set the current window with the specific report
	 */
	@SuppressWarnings("unchecked")
	public void SetReport_ChooseAll_Content(Object msg) throws SQLException 
	{	
		ArrayList<String> result = (ArrayList<String>)((Message)msg).getObject();
		if(result.isEmpty() == false)
		{
			ChooseAllTable.setVisible(true);

			ArrayList<common.entities.ReportDetails> dataToSend = new ArrayList<common.entities.ReportDetails>();
			int jump = 0;
			for(int i=0; i<result.size()/8;i++)
			{
				// CountryName, city, MapQuantity, NumberOfOneTimePurchase,
				// NumberOfSubscription, NumberOfRenewal, views, Download_Quantity => By the sql VIEW 'mapView'
				common.entities.ReportDetails rd = new common.entities.ReportDetails(result.get(0+jump),result.get(1+jump),result.get(2+jump),result.get(3+jump),
						result.get(4+jump),result.get(5+jump),result.get(6+jump),result.get(7+jump));
				dataToSend.add(rd);
				jump+=8;
			}
			// dataToSend containing all the data!
			// Inserting to viewTable:
			ObservableList<common.entities.ReportDetails> ol = (ObservableList<ReportDetails>) FXCollections.observableArrayList(dataToSend);

			// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
			TableColumn<common.entities.ReportDetails,String> CountryNameCol = new TableColumn<common.entities.ReportDetails,String>("Country");
			CountryNameCol.setMinWidth(20);
			CountryNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("CountryName"));

			TableColumn<common.entities.ReportDetails,String> CityCol = new TableColumn<common.entities.ReportDetails,String>("City");
			CityCol.setMinWidth(5);
			CityCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("city"));

			TableColumn<common.entities.ReportDetails,String> MapQuantityCol = new TableColumn<common.entities.ReportDetails,String>("#Maps");
			MapQuantityCol.setMinWidth(20);
			MapQuantityCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("MapQuantity"));

			TableColumn<common.entities.ReportDetails,String> NumberOfOneTimePurchaseCol = new TableColumn<common.entities.ReportDetails,String>("One-Time Purchase");
			NumberOfOneTimePurchaseCol.setMinWidth(20);
			NumberOfOneTimePurchaseCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("NumberOfOneTimePurchase"));

			TableColumn<common.entities.ReportDetails,String> NumberOfSubscriptionCol = new TableColumn<common.entities.ReportDetails,String>("#Subscriptions");
			NumberOfSubscriptionCol.setMinWidth(20);
			NumberOfSubscriptionCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("NumberOfSubscription"));

			TableColumn<common.entities.ReportDetails,String> NumberOfRenewaleCol = new TableColumn<common.entities.ReportDetails,String>("#Renewal");
			NumberOfRenewaleCol.setMinWidth(20);
			NumberOfRenewaleCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("NumberOfRenewal"));

			TableColumn<common.entities.ReportDetails,String> viewsCol = new TableColumn<common.entities.ReportDetails,String>("#View");
			viewsCol.setMinWidth(20);
			viewsCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("views"));

			TableColumn<common.entities.ReportDetails,String> Download_QuantityCol = new TableColumn<common.entities.ReportDetails,String>("#Download");
			Download_QuantityCol.setMinWidth(20);
			Download_QuantityCol.setCellValueFactory(new PropertyValueFactory<common.entities.ReportDetails, String>("Download_Quantity"));

			if(ChooseAllTable.getItems().isEmpty() == true)
			{
				ChooseAllTable.setItems(ol);
				ChooseAllTable.getColumns().addAll(CountryNameCol, CityCol, MapQuantityCol, NumberOfOneTimePurchaseCol, NumberOfSubscriptionCol, NumberOfRenewaleCol, viewsCol, Download_QuantityCol);
			}
			else
			{
				ChooseAllTable.getItems().clear();
				ChooseAllTable.setItems(ol);
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			if(this.pdfFlag == true)
			{
				int jumpPDF = 0;
				for(int i=0; i<result.size()/8; i++)
				{
					CreatePDFController.MakePDF(result.get(jumpPDF),result.get(1+jumpPDF),result.get(2+jumpPDF),result.get(3+jumpPDF)
							,result.get(4+jumpPDF),result.get(5+jumpPDF),result.get(6+jumpPDF),result.get(7+jumpPDF),dateFormat.format(date)); // without date!
					jumpPDF+=8;
				}
			}
		}
	}

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param msg
	 * @throws SQLException
	 * 
	 * SetDataOnScreen_Content => Set the start data and the choosing options
	 */
	@SuppressWarnings("unchecked")
	public void SetDataOnScreen_Content(Object msg) throws SQLException 
	{
		result = (ArrayList<String>)((Message)msg).getObject();
		if(result.isEmpty() == false)
		{
			ArrayList<String> reportType = new ArrayList<String>();
			ArrayList<String> countriesToChoose = new ArrayList<String>();

			for(int i=0; i<result.size(); i++)
			{
				// Removing duplicate countries names in countriesToChoose
				if(countriesToChoose.contains(result.get(i)) == false && i%2 == 0)
					countriesToChoose.add(result.get(i)); // Adding Countries type:
			}

			countriesToChoose.add("Choose All");

			// Adding Reports type:
			reportType.add("Default");
			reportType.add("Statistics");
			reportType.add("Inspection");

			ObservableList<String> countryChoose = FXCollections.observableArrayList(countriesToChoose);

			ChooseCountryBox.setItems(countryChoose);
		}
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return CountryName;
	}

	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

}
