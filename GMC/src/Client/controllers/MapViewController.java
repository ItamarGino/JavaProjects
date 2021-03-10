
package client.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.org.apache.bcel.internal.generic.POP;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * MapViewController => Controller for the Search/Add/Edit/Delete Map's Content
 * 
 */
public class MapViewController 
{
	public static  MapViewController Instance;
	static Stage window;
	public  boolean cityflag;
	public  boolean siteflag;
	public static String cityName;
	
	@FXML
    private JFXListView<?> SearchHistory;

    @FXML
    private TextField TextFiledSearch;

    
    @FXML
    private JFXRadioButton PopularplaceBtn;

    @FXML
    private JFXRadioButton CityNameBtn;

    @FXML
    private Label num_Historical_Sites;

    @FXML
    private Label num_museum;

    @FXML
    private Label num_restauran;

    @FXML
    private Label num_hotels;

    @FXML
    private Label num_movie;

    @FXML
    private Label num_park_sites;

    @FXML
    private Label num_public_sits;

    @FXML
    private Label num_shops;

    @FXML
    private ImageView city_img;

    @FXML
    private JFXButton EditMapDetails;

    @FXML
    private TableView<common.entities.SearchMapDetails> table_maps;

	public static String id_map;

	@FXML
	void UploAdMapsData(MouseEvent event) 
	{
		if(table_maps.getSelectionModel().getSelectedItem() != null) 
		{   
			try {
				id_map = table_maps.getSelectionModel().getSelectedItem().getId(); // id column
				if(id_map != null)
				{
					String query="SELECT DISTINCT a.Category as histor, b.Category as museum, c.Category as resturant, d.Category as shop, e.Category as hotel, f.Category as movie, g.Category as park, h.Category as public"
							+" FROM siteinmap,site, "
							+" (SELECT count(Category) AS Category FROM site,siteinmap WHERE site.Category = 'Historical Sites' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) AS a, (SELECT count(Category) AS Category FROM site,siteinmap WHERE site.Category = 'Museums Sites' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) as b,"
							+" (SELECT count(Category) AS Category FROM site,siteinmap WHERE site.Category = 'Restaurants' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) AS c,"
							+" (SELECT count(Category) AS Category FROM site,siteinmap WHERE site.Category = 'Shops' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) AS d, "
							+" (SELECT count(Category) AS Category  FROM site,siteinmap WHERE site.Category = 'Hotels' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) AS e,"
							+" (SELECT count(Category) AS Category  FROM site,siteinmap WHERE site.Category = 'Movie Theaters'AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+"  ) AS f," 
							+" (SELECT count(Category) AS Category FROM site,siteinmap WHERE site.Category = 'Parks Sites' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) AS g," 
							+" (SELECT count(Category) AS Category FROM site,siteinmap WHERE site.Category = 'Public Sites' AND siteinmap.SiteName=site.SiteName and siteinmap.IDMAP="+id_map+" ) AS h" 
							+" WHERE  siteinmap.SiteName = site.SiteName AND siteinmap.IDMAP="+ id_map +";";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getMapsitesDetails,query));
				}
			}catch(Exception e) {return;}
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
	 * Search => Getting the text from the SearchBar
	 * By the user's option, Sending SQL query 
	 */
	@FXML
	void Search(ActionEvent event) 
	{
		String RS=TextFiledSearch.getText(); 
		if (RS==null) return;
		if (siteflag==true && cityflag==false)
		{
			// By siteName
			String query = "SELECT maps_catalog.CityName FROM maps_catalog, site WHERE maps_catalog.CityName = site.cityname AND site.SiteName = '" + RS +
					"' GROUP BY maps_catalog.CityName;";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getMapDetailsBySiteName,query));
		}
		else if (cityflag==true && siteflag==false)
		{
			// By CityName
			String query = "SELECT DISTINCT ID FROM map WHERE CityName='"+RS+"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getMapDetails,query));
		}
		else if(siteflag==true && cityflag==true)
		{
			ClientMessages.popUp(AlertType.ERROR,"Error","You can not choose both button","Please remove one of the button - 'City Name' Or 'Popular place'");
		}
		else 
		{
			// By Description
			String query="SELECT Distinct map.CityName FROM map, maps_catalog WHERE map.Description = '"+RS+"';";
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getMapDetailsBySiteName,query));
		}

	}

	@FXML
	void by_popular_place(ActionEvent event) 
	{
		if(CityNameBtn.isSelected()) {
			CityNameBtn.setSelected(false);
		}

		if(siteflag==false)   
			siteflag=true;
		else
			siteflag=false;
	}

	@FXML
	void bycitymane(ActionEvent event) 
	{
		if(PopularplaceBtn.isSelected()) {
			PopularplaceBtn.setSelected(false);
		}
		
		
		if(cityflag==false)   
			cityflag=true;
		else
			cityflag=false;
	}
	
	

	@FXML
	void edit_map(ActionEvent event)
	{
		cityName=CityCatalogController.Instance.CityDetail;
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/MapInfoPage.fxml");
	}

	@FXML
	void homepage(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");

	}

	@FXML
	void logout(ActionEvent event) throws IOException 
	{
		try {

			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println(ex.toString());
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
	 * Getting the information about the search from the SQL
	 * Set data specific from chosen map
	 */
	public void setDataFromMap(Object msg)
	{
		try {
			@SuppressWarnings("unchecked")
			ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
			if(data.isEmpty() == false)
			{
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						num_Historical_Sites.setText(data.get(0));
						num_museum.setText(data.get(1));
						num_restauran.setText(data.get(2));
						num_shops.setText(data.get(3));
						num_hotels.setText(data.get(4));
						num_movie.setText(data.get(5));
						num_park_sites.setText(data.get(6));
						num_public_sits.setText(data.get(7));
					}
				});
			}
		}
		catch(Exception e) {e.printStackTrace();};
	}


	/**@ @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param msg
	 * @throws SQLException
	 * 
	 * Getting the information about the search from the SQL
	 * if not null => Set to the screen
	 */
	@SuppressWarnings("unchecked")
	public void setDataOnTable(Object msg) throws SQLException 
	{
		// Assembling the data:
		// Data was found! Set the data to the screen:
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();
		if(data.isEmpty() == false)
		{
			ArrayList<common.entities.SearchMapDetails> dataToSend = new ArrayList<common.entities.SearchMapDetails>();
			int jump = 0;
			for(int i=0; i<data.size()/3;i++)
			{
				// id ; SITECOUNT ; TOURCOUNT
				common.entities.SearchMapDetails rd = new common.entities.SearchMapDetails(data.get(0+jump), data.get(1+jump), data.get(2+jump));
				dataToSend.add(rd);
				jump+=3;
			}
			// dataToSend containing all the data!s
			// Inserting to viewTable:
			ObservableList<common.entities.SearchMapDetails> ol = FXCollections.observableArrayList(dataToSend);

			// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
			TableColumn<common.entities.SearchMapDetails,String> IDCol = new TableColumn<common.entities.SearchMapDetails,String>("Map ID");
			IDCol.setMinWidth(120);
			IDCol.setCellValueFactory(new PropertyValueFactory<common.entities.SearchMapDetails, String>("id"));

			TableColumn<common.entities.SearchMapDetails,String> SiteCol = new TableColumn<common.entities.SearchMapDetails,String>("Number Of Sites");
			SiteCol.setMinWidth(120);
			SiteCol.setCellValueFactory(new PropertyValueFactory<common.entities.SearchMapDetails, String>("SITECOUNT"));

			TableColumn<common.entities.SearchMapDetails,String> TourCol = new TableColumn<common.entities.SearchMapDetails,String>("Number Of Tours");
			TourCol.setMinWidth(120);
			TourCol.setCellValueFactory(new PropertyValueFactory<common.entities.SearchMapDetails, String>("TOURCOUNT"));

			if(table_maps.getItems().isEmpty() == true)
			{
				table_maps.setItems(ol);
				table_maps.getColumns().addAll(IDCol, SiteCol, TourCol);
			}
			else
			{
				table_maps.getItems().clear();
				table_maps.setItems(ol);
			}
		}
		else
		{
			// The SearchBar did not found information!
			ClientMessages.popUp(AlertType.ERROR, "No data was found ..", "We did'nt found any information",
					"Try something else then - '"+ TextFiledSearch.getText() +"'");
		}
	}


	@FXML
	void initialize() 
	{
		Instance = this;
		cityflag=false;
		siteflag=false;
		EditMapDetails.setVisible(false);
		EditMapDetails.setDisable(true);
		File photo;
	////Newwwwwwwwwwwwww
	//Newwwwwwwwwwww
		cityName=CityCatalogController.CityDetail;
		//EditMapDetails.setVisible(false);
		if((photo=new File("Citys/"+cityName+".jpg")).isFile()) {
			city_img.setImage(new Image((photo.toURI().toString())));
			}
		System.out.println("Citys/"+cityName+".jpg)");
		////Newwwwwwwwwwwwww
		//Newwwwwwwwwwww
		assert SearchHistory != null : "fx:id=\"history_list\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert TextFiledSearch != null : "fx:id=\"TextFiledSearch\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_Historical_Sites != null : "fx:id=\"num_Historical_Sites\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_museum != null : "fx:id=\"num_museum\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_restauran != null : "fx:id=\"num_restauran\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_hotels != null : "fx:id=\"num_hotels\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_movie != null : "fx:id=\"num_movie\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_park_sites != null : "fx:id=\"num_park_sites\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_public_sits != null : "fx:id=\"num_public_sits\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert num_shops != null : "fx:id=\"num_shops\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert city_img != null : "fx:id=\"city_img\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert EditMapDetails != null : "fx:id=\"EditMapDetails\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		assert table_maps != null : "fx:id=\"table_maps\" was not injected: check your FXML file 'MapViewWindow.fxml'.";
		System.out.println();
		/* By city => return the count of site and tour   */
		/* Default => Akko temp !!!!!!!!!!!!!!!!!!!!!! */ ///////////// REMEZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ DONT FORGET
/*		String query="SELECT DISTINCT map.ID AS id, "
				+ "A.SiteName AS SITECOUNT, B.TourName "
				+ "AS TOURCOUNT FROM map,(SELECT COUNT(siteinmap.SiteName) AS "
				+ "SiteName, IDMAP FROM siteinmap,map WHERE map.CityName='"+cityName+"' "
				+ "AND siteinmap.IDMAP=map.ID) AS A,(SELECT COUNT(tourinmap.TourName)AS TourName "
				+ ",map.ID FROM tourinmap,map WHERE map.CityName='"+cityName+"' AND tourinmap.ID=map.ID) "
				+ "AS B WHERE B.ID=map.ID AND map.ID=A.IDMAP GROUP BY map.ID;";*/
		
		String query = "SELECT DISTINCT ID FROM map WHERE CityName='"+cityName+"';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getMapDetails,query));

		/* Getting the Role, if (employee ; contentManager ; ceo) => can see the EditMapDetails button	*/
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		String query2 = "SELECT Role FROM gcm.user WHERE userName='"+ClientMessages.username+"';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getRole,query2));*/
		if(ClientMessages.user.getRole().equals("employee") || ClientMessages.user.getRole().equals("contentManager") || ClientMessages.user.getRole().equals("ceo")) {
			EditMapDetails.setVisible(true);
			EditMapDetails.setDisable(false);
			EditMapDetails.setText("Edit Map");
		}
		else {
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.ifSubscribe,"SELECT PurchaseDateExpiration FROM gcm.purchasehistory WHERE userName='"+ClientMessages.user.getUserName()+"' AND CityName='"+cityName+"' AND PurchaseType='Period';"));
		}
	}

	public void setEditMapDetails(Object msg) 
	{

		if((boolean) ((Message)msg).getObject()) {
			EditMapDetails.setVisible(true);
			EditMapDetails.setDisable(false);
			EditMapDetails.setText("View Map");
		}
		else {
			EditMapDetails.setVisible(false);
			EditMapDetails.setDisable(true);
		}
		
	}
	/*
	 * 	String query="SELECT DISTINCT map.ID AS id, A.SiteName AS SITECOUNT, B.TourName AS TOURCOUNT" + 
				"FROM map," + 
				"(SELECT COUNT(siteinmap.SiteName) AS SiteName, IDMAP FROM siteinmap,map " + 
				"WHERE map.CityName='Akko' AND siteinmap.IDMAP=map.ID) AS A," + 
				"(SELECT COUNT(tourinmap.TourName)AS TourName ,map.ID FROM tourinmap,map " + 
				"WHERE map.CityName='Akko' AND tourinmap.ID=map.ID) AS B" + 
				"WHERE B.ID=map.ID AND map.ID=A.IDMAP " + 
				"GROUP BY map.ID;";

	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
}
