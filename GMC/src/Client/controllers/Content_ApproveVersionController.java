/**
 * Sample Skeleton for 'Content_ApproveVersion.fxml' Controller Class
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
import java.util.concurrent.TimeUnit;

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
 * Content_ApproveVersionController is a controller for Content Depatrment Manger ApproveVersion.
 */
public class Content_ApproveVersionController implements Initializable
{
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="VersionApproveTable"
	private TableView<common.entities.VersionDetails> VersionApproveTable; // Value injected by FXMLLoader

	@FXML // fx:id="approveButton"
	private JFXButton approveButton; // Value injected by FXMLLoader

	@FXML // fx:id="rejectButton"
	private JFXButton rejectButton; // Value injected by FXMLLoader

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

	public common.entities.VersionDetails rowVal;
	public ArrayList<common.entities.NewMapWithPriceDetails> data;
	public ArrayList<String> dataTable;
	public ArrayList<String > tour_temp;
	public ArrayList<String > site_temp;
	public String MapID;
	public static Content_ApproveVersionController Instance;
	public boolean removeFlag = false;
	public int index;
	public String temp;

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
	void ShowDataArchive(ActionEvent event) 
	{
		//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_DataArchive.fxml");
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

	@FXML
	void ShowYesNoButtons(MouseEvent event)
	{
		approveButton.setVisible(true);
		rejectButton.setVisible(true);
	}

	/**
	 * 
	 * @param event
	 * 
	 * sendApprovment => Collecting data from temp_site & temp_tour tables
	 */
	@FXML
	void sendApprovment(ActionEvent event)
	{
		if(VersionApproveTable.getSelectionModel().getSelectedItem() != null) 
		{    
			rowVal = VersionApproveTable.getSelectionModel().getSelectedItem();
			index = VersionApproveTable.getSelectionModel().getSelectedIndex(); // FROM 0 !
			MapID = rowVal.getMapID();
			if(MapID.equals("New"))
			{
				// Checking first if there is a map catelog for the city
				// First => getting the data from newmapwithprice
				// send query:
				String query = rowVal.getCountryName()+";"+rowVal.getCityName()+";";
				temp = dataTable.get(2+index*5);
				query = query + dataTable.get(2+index*5);

				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getNewMapWithPriceDetails,query));
			}
			else
			{
				// send query:
				String query = "SELECT CountryName, CitynameTemp, SiteName, Location_x, Location_y, Category, Description, Accessibility, MapId, RecommendedVisitTime FROM temp_site WHERE temp_site.MapId = '"+ rowVal.getMapID() +"';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getTempSiteDetails,query));
				// send query:
							try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				String query2 = "SELECT CountryNameTemp, CityNameTemp, TourName, Description, Recommended, MapId,SitesInTour  FROM temp_tour WHERE temp_tour.MapId = '"+ rowVal.getMapID() +"';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getTempTourDetails,query2));
			}
			removeFlag = false;
			removeRow();
			MapID = null;
			
						/* ***** Shahar Update *****/
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println(event.getSource());
		System.out.println(event.getSource().toString());
		if(event.getSource().equals(approveButton)) {
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SendNewVersionMessage, rowVal));
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadNewVersion, rowVal));
		}
	}

	/**
	 * @param event
	 * 
	 * send Rejection --> price was not approve
	 */
	@FXML
	void sendRejection(ActionEvent event)
	{
		if(VersionApproveTable.getSelectionModel().getSelectedItem() != null) 
		{ 
			rowVal = VersionApproveTable.getSelectionModel().getSelectedItem();
			removeFlag = true;
			if(MapID == null)
				sendApprovment(event);
			else
				removeRow();
		}
	}

	/**
	 * Removing a row at versions table
	 * Turn removeFlag to true for removing the from temp_site & temp_tour tables
	 */
	void removeRow()
	{
		// send query:
		String query = "DELETE FROM gcm.versions WHERE CountryName ='"+ rowVal.getCountryName() +"' AND CityName ='"+rowVal.getCityName()+"' AND MapID ='"+dataTable.get(2+index*5)+"' "
				+ "AND EmployeeID ='"+rowVal.getEmployeeID()+"' AND VersionNumber ="+dataTable.get(4+index*5)+";";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.removeVersionRow,query));

		// send query:
		String query1 = "SELECT CountryName, CityName, MapID, EmployeeID, VersionNumber FROM gcm.versions;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getVersions,query1));
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize()
	{
		assert VersionApproveTable != null : "fx:id=\"VersionApproveTable\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert approveButton != null : "fx:id=\"approveButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert rejectButton != null : "fx:id=\"rejectButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert ReportButton != null : "fx:id=\"ReportButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
		assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'Content_ApproveVersion.fxml'.";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{	
		Instance=this;
		// send query:
		String query = "SELECT CountryName, CityName, MapID, EmployeeID, VersionNumber FROM gcm.versions;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getVersions,query));
	}

	/**
	 * SetDataOnScreen => 
	 * Set VersionApproveTable information
	 * 
	 * @param msg
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public void SetDataOnScreen(Object msg) throws SQLException 
	{
		// Assembling the data:
		dataTable = (ArrayList<String>)((Message)msg).getObject();
		ArrayList<common.entities.VersionDetails> dataToSend = new ArrayList<common.entities.VersionDetails>();
		int jump = 0;
		for(int i=0; i<dataTable.size()/5;i++)
		{
			// CountryName ; CityName ; MapID ; EmployeeID ; VersionNumber => By the sql table 'versions'
			common.entities.VersionDetails rd;
			if(dataTable.get(2+jump).equals("-1") || dataTable.get(4+jump).equals("-1.0")) // if new map => By default -1 == 'New'
				rd = new common.entities.VersionDetails(dataTable.get(0+jump), dataTable.get(1+jump), "New",dataTable.get(3+jump),"New");
			else
				rd = new common.entities.VersionDetails(dataTable.get(0+jump), dataTable.get(1+jump), dataTable.get(2+jump), dataTable.get(3+jump),dataTable.get(4+jump));
			dataToSend.add(rd);
			jump+=5;
		}
		// dataToSend containing all the data!
		// Inserting to viewTable:
		ObservableList<common.entities.VersionDetails> ol = FXCollections.observableArrayList(dataToSend);

		// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
		TableColumn<common.entities.VersionDetails,String> CountryNameCol = new TableColumn<common.entities.VersionDetails,String>("Country Name");
		CountryNameCol.setMinWidth(120);
		CountryNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.VersionDetails, String>("CountryName"));

		TableColumn<common.entities.VersionDetails,String> CityNameCol = new TableColumn<common.entities.VersionDetails,String>("City Name");
		CityNameCol.setMinWidth(120);
		CityNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.VersionDetails, String>("CityName"));

		TableColumn<common.entities.VersionDetails,String> MapIDCol = new TableColumn<common.entities.VersionDetails,String>("Map ID");
		MapIDCol.setMinWidth(120);
		MapIDCol.setCellValueFactory(new PropertyValueFactory<common.entities.VersionDetails, String>("MapID"));

		TableColumn<common.entities.VersionDetails,String> EmployeeIDCol = new TableColumn<common.entities.VersionDetails,String>("Employee ID");
		EmployeeIDCol.setMinWidth(120);
		EmployeeIDCol.setCellValueFactory(new PropertyValueFactory<common.entities.VersionDetails, String>("EmployeeID"));

		TableColumn<common.entities.VersionDetails,String> VersionNumberCol = new TableColumn<common.entities.VersionDetails,String>("Version Number");
		VersionNumberCol.setMinWidth(120);
		VersionNumberCol.setCellValueFactory(new PropertyValueFactory<common.entities.VersionDetails, String>("VersionNumber"));

		if(VersionApproveTable.getItems().isEmpty() == true)
		{
			VersionApproveTable.setItems(ol);
			VersionApproveTable.getColumns().addAll(CountryNameCol, CityNameCol,MapIDCol, VersionNumberCol,EmployeeIDCol);
		}
		else
		{
			VersionApproveTable.getItems().clear();
			VersionApproveTable.setItems(ol);
		}
	}

	/**
	 * @param msg
	 * 
	 * Check_UpdateSite => Only if there is a data at temp tables
	 * 					   Checking if the siteName exists at site table
	 * 					   Deleting the selected row from temp_site
	 */
	public void Check_UpdateSite(Object msg) 
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		site_temp = data;
		if(site_temp.isEmpty() == false)
		{
			if(removeFlag == false) // => Approve
			{
				// send query:
				String query = "SELECT SiteName FROM gcm.site WHERE SiteName = '"+ site_temp.get(2) +"';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.CheckIfSiteExists,query));
			}
			// send query:
			String query2 = "DELETE FROM gcm.temp_site WHERE CountryName ='"+ data.get(0) 
			+"' AND CitynameTemp ='"+data.get(1)+"' AND SiteName ='"+data.get(2)+"' "
			+ "AND Location_x ='"+data.get(3)+"' AND Location_y ='"+data.get(4)+
			"' AND Category ='"+data.get(5)+"' AND Description ='"+data.get(6)+
			"' AND Accessibility ='"+data.get(7)+"' AND MapId ='"+data.get(8)+"' AND RecommendedVisitTime ='"+data.get(9)+"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.removeTempRow,query2));
		}
	}

	/**
	 * @param msg
	 * Check_UpdateTour => Only if there is a data at temp tables
	 * 					   Checking if the siteName exists at tour table
	 * 					   Deleting the selected row from temp_tour
	 */
	public void Check_UpdateTour(Object msg) 
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		tour_temp = data;
		if(tour_temp.isEmpty() == false)
		{
			if(removeFlag == false) // => Approve
			{
				// send query:
				String query = "SELECT TourName FROM gcm.tour WHERE TourName = '"+ tour_temp.get(2) +"';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.CheckIfTourExists,query));
			}
			// send query:
			String query2 = "DELETE FROM gcm.temp_tour WHERE CountryNameTemp ='"+ data.get(0) 
			+"' AND CityNameTemp ='"+data.get(1)+"' AND TourName ='"+data.get(2)+"' "
			+ "AND Description ='"+data.get(3)+"' AND Recommended ='"+data.get(4)+"' AND MapId ='"+data.get(5)+"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.removeTempRow,query2));
		}

	}

	/**
	 * 
	 * @param msg
	 * 
	 * InsertOrUpdate_UpdateSite => 
	 * 				if there is no data about the site at site table - insert a new row and update siteinmap table
	 * 				else - just update the selected row at site table
	 */
	public void InsertOrUpdate_UpdateSite(Object msg) 
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		if(data.isEmpty() == true)
		{
			System.out.println( "data = "+data);
			System.out.println(site_temp);
			// INSERT site
			String query = "INSERT INTO gcm.site "
					+ " (CountryName, cityname, SiteName, Category, Description, Accessibility, RecommendedVisitTime) " 
					+ " VALUES ('"+ site_temp.get(0) +"' ,'"+ site_temp.get(1) +"' ,'"+ site_temp.get(2)
					+"' ,'"+ site_temp.get(5) +"' ,'"+ site_temp.get(6) +"' ,'"+ site_temp.get(7) +"','"+ site_temp.get(9) +"' );";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.InsertSite,query));

			// INSERT siteinmap
			String query2 = "INSERT INTO gcm.siteinmap (SiteName, IDMAP, SiteLocationX, SiteLocationY) "
					+ "VALUES ('"+ site_temp.get(2) +"', '"+ site_temp.get(9) +"','"+ site_temp.get(3) +"','"+ site_temp.get(4) +"');";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.InsertToSiteInMap,query2));
		}
		else
		{
			// UPDATE
			String query = "UPDATE gcm.site "
					+ "SET Category = '"+ site_temp.get(5) +"',"
					+ " Description = '"+ site_temp.get(6) +"',"
					+ " Accessibility = '"+ site_temp.get(7) +"',"
					+ " RecommendedVisitTime = '"+ site_temp.get(9) +"'"
					+ "WHERE SiteName = '"+ site_temp.get(2) +"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UpdateSite,query));

		}
	}

	/**
	 * @param msg
	 * 
	 * InsertOrUpdate_UpdateTour => 
	 * 				if there is no data about the tour at site table - insert a new row and update tourinmap table
	 * 																 - And update siteintour table
	 * 				else - just update the selected row at tour table
	 */
	public void InsertOrUpdate_UpdateTour(Object msg)
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		String[] sitesToAdd = tour_temp.get(6).replaceAll("'", "").split(",");
		if(data.isEmpty() == true)
		{
			// INSERT
			String query = "INSERT INTO gcm.tour " 
					+ " (CountryName,CityName, TourName, Description, Recommended) " 
					+ " VALUES ('"+ tour_temp.get(0) +"' ,'"+ tour_temp.get(1) +"' ,'"+ tour_temp.get(2) +"' ,'"+ tour_temp.get(3) +"'"
					+ ",'"+ tour_temp.get(4) +"');";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.InsertTour,query));

			// INSERT tourinmap
			String query2 = "INSERT INTO gcm.tourinmap (TourName, ID) "
					+ "VALUES ('"+ tour_temp.get(2) +"', '"+ tour_temp.get(5) +"');";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.InsertToTourInMap,query2));

			// tour_temp.get(6) => sites list
			sitesToAdd = tour_temp.get(6).replaceAll("'", "").split(",");
			for(int i=0;i<sitesToAdd.length;i++)
			{
				String query3 = "INSERT INTO gcm.siteintour (SiteName, TourName)"
						+ " VALUES ( '"+sitesToAdd[i] +"','"+ tour_temp.get(2) +"');";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UpdateSiteInTour,query3));
			}
		}
		else
		{
			// UPDATE
			String query = "UPDATE gcm.tour "
					+ "SET Description = '"+ tour_temp.get(3) +"',"
					+ " Recommended = '"+ tour_temp.get(4) +"'"
					+ " WHERE TourName = '"+ tour_temp.get(2) +"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UpdateTour,query));

			// tour_temp.get(6) => sites list
			sitesToAdd = tour_temp.get(6).replaceAll("'", "").split(",");
			for(int i=0;i<sitesToAdd.length;i++)
			{
				String query3 = "UPDATE gcm.siteintour "
						+ " SET SiteName = '"+ sitesToAdd[i] +"' "
						+ " WHERE TourName = '"+ tour_temp.get(2) +"';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UpdateSiteInTour,query3));
			}
		}
	}
}
