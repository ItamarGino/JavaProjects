
package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.ArrayList;
import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.EmployeeDetails;
import common.entities.SiteDetails;
import common.entities.Tour;
import common.entities.VersionDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 *
 * @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * @version June 2019
 * 
 * This controller handles the insertion of data when editing a new particular tour . 
 *  The screen will contain the tour details  and the fields will be subject to change. 
 * the controller will contain home page and logout like every page  use the tour,SiteDetails,VersionDetails entity's
 * and tour, temp tour, site in tour, tour in map , version , map , map catalog tables.
 * 
 *
 */

public class TourEditController {

	public static  TourEditController instance;
	static Stage window;
	public String recommended=null; 
	public String IDMAP;//
	public String tourName;
	public String cityName;
	public Tour tour;
	public VersionDetails Version;
	public SiteDetails sitedelete = new SiteDetails();
	public SiteDetails siteadd = new SiteDetails();
	public boolean flag;
	public   ArrayList<String> sitelist ;
	public  static ArrayList<Tour> SiteInTour ;
	public ArrayList<SiteDetails> existsite = new ArrayList<>();
	public ArrayList<SiteDetails> notexistsite = new ArrayList<>();
	public boolean FirstFlagAdd = true;
	public boolean FirstFlagDelete = true;
	private EmployeeDetails Employee= new EmployeeDetails(); // change



    @FXML
    private Label tourname;

    @FXML
    private Label Recommended;

    @FXML
    private Label CountryName;

    @FXML
    private JFXTextField CountryName1;

    @FXML
    private JFXTextField City_Name;

	@FXML
	private TextField tour_name;


    @FXML
    private JFXTextArea Description;
	@FXML
	private CheckBox Recommended_no;

	@FXML
	private CheckBox Recommended_yes;

	@FXML
	private TableView<SiteDetails> site_of_list_to_delete;// site in the tour

	@FXML
	private TableView<SiteDetails> site_of_list_to_add;// site not in the tour

	@FXML
	private Button save;

	@FXML
	private Button bake_without_save;
	
	/** 4 The following methods are responsible for adding and deleting a site from a tour.
     When you click a row in a table - we will save the name of the site that was clicked in a temporary variable
     Then, if you click Delete or Add, we will run the appropriate method that updates the list "site list" and the tables on
      the screen by updating our data structure and calling method "Upload Table".
      addTOtempexist - get the site name from the table of the site in tour
      delete - delete the name from the list of the sites in the tour , and add to the list of the sites that not in the tour.
      addTOtempnotexist-get the site name from the table of the site that not in the  tour
      add - delete the name from the list of the sites that not in the  tour , and add to the list of the sites in the tour.
          @param event - from the event we get the Window and all the details


	 * */

	@FXML
	void addTOtempexist(MouseEvent event) {//list of site that need to delete

		if(site_of_list_to_delete.getSelectionModel().getSelectedItem()!=null) 
		{
			if(sitedelete == null)
			{
				sitedelete = new SiteDetails();
				sitedelete.setSiteName(site_of_list_to_delete.getSelectionModel().getSelectedItem().getSiteName());
				sitedelete.setRecommendedVisitTime(site_of_list_to_delete.getSelectionModel().getSelectedItem().getRecommendedVisitTime());
				System.out.println("site delete "+ sitedelete.getSiteName());
			}
			else if(sitedelete != null && FirstFlagDelete == true)
			{
				FirstFlagDelete = false;
				sitedelete.setSiteName(site_of_list_to_delete.getSelectionModel().getSelectedItem().getSiteName());
				sitedelete.setRecommendedVisitTime(site_of_list_to_delete.getSelectionModel().getSelectedItem().getRecommendedVisitTime());
			}
		}
	}
	@FXML
	void delete(ActionEvent event) {
		if(sitedelete != null) {
			if (sitedelete.getSiteName() != null)
			{sitelist.remove(sitedelete.getSiteName());
			int index = 0;
			for (int i=0; i<existsite.size() ;i++)
				if(existsite.get(i).getSiteName().equals(sitedelete.getSiteName()))	
					index=i;
			existsite.remove(index);
			boolean f=false;
			for (int j=0; j<notexistsite.size(); j++)
			{
				if((notexistsite.get(j).getSiteName().equals(sitedelete.getSiteName())))
					f=true;
			}
			if (!f)// if not exist
				notexistsite.add(sitedelete);
			UploadTable(existsite);
			UploadTable(notexistsite);

			System.out.println(sitelist.toString());

			sitedelete=null;
			}
		}
	}	
	@FXML
	void addTOtempnotexist(MouseEvent event) {//list of site that need to add
		if(site_of_list_to_add.getSelectionModel().getSelectedItem()!=null)
		{
			if(siteadd == null)
			{
				siteadd = new SiteDetails();
				siteadd.setSiteName(site_of_list_to_add.getSelectionModel().getSelectedItem().getSiteName());
				siteadd.setRecommendedVisitTime(site_of_list_to_add.getSelectionModel().getSelectedItem().getRecommendedVisitTime());
				System.out.println("site add "+ siteadd.getSiteName());
			}
			else if(siteadd != null && FirstFlagAdd == true)
			{
				FirstFlagAdd = false;
				siteadd.setSiteName(site_of_list_to_add.getSelectionModel().getSelectedItem().getSiteName());
				siteadd.setRecommendedVisitTime(site_of_list_to_add.getSelectionModel().getSelectedItem().getRecommendedVisitTime());
			}
		}
	}
	@FXML
	void ADD(ActionEvent event) {//get site from page before
		if(siteadd != null)
		{
			if (siteadd.getSiteName() != null)
			{
				sitelist.add(siteadd.getSiteName());
				int index = 0;
				boolean f=false;
				for (int i=0; i<existsite.size() ;i++)
					if(existsite.get(i).getSiteName().equals(siteadd.getSiteName()))
						f=true;

				if (!f) existsite.add(siteadd);

				for (int j=0;j<notexistsite.size();j++)
					if(notexistsite.get(j).getSiteName().equals(siteadd.getSiteName()))	
						index=j;
				notexistsite.remove(index);
				UploadTable(existsite);
				UploadTable(notexistsite);
				System.out.println(sitelist.toString());
				siteadd=null;
			}
		}
	}
	@FXML
	
	void bake(ActionEvent event) {	//need to change
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/MapInfoPage.fxml"); }

	@FXML
	void get_no(ActionEvent event) {
		recommended="NO";
		if (Recommended_yes.isSelected())
			Recommended_yes.setSelected(false);}

	@FXML
	void get_yes(ActionEvent event) {
		recommended="YES";
		if (Recommended_no.isSelected())
			Recommended_no.setSelected(false);}

	@FXML
	void logout(ActionEvent event)  throws IOException {
		try {

			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
	@FXML
	void HomePage(ActionEvent event) {
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}
	/**save method -the method is activated when you press the Save button on the screen,
    Uploading all data ,checks that all fields are valid and full
    And sends to the query to update the data to DB, send also query to approve request .
    @param event - from the event we get the Window and all the details

	 * */

	@FXML
	void save(ActionEvent event)
	{
		window=(Stage)((Node)event.getSource()).getScene().getWindow();
		String  CountryName=CountryName1.getText();
		String  cityname=City_Name.getText();
		String  tourname=tour_name.getText();
		String description=Description.getText();
		
		tour = new Tour(CountryName,cityname,tourname,description,recommended,IDMAP);
		if(CountryName.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","COUNTRY NAME ERROR","city name field is Empty!");
		else if(cityname.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","CITY NAME ERROR","city name field is Empty!");
		else if(tourname.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","TOUR NAME ERROR","Site name field is Empty!");
		else if(description.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","DESCRIPTION NAME ERROR","description name field is Empty!");
		else if(recommended==null)
			ClientMessages.popUp(AlertType.ERROR,"ERROR","RECOMMENDED NAME ERROR","you didn't choose if recommended !!");
		else if((!Recommended_yes.isSelected())&&(!Recommended_no.isSelected()))
			ClientMessages.popUp(AlertType.ERROR,"ERROR","RECOMMENDED NAME ERROR","you didn't choose if recommended !!");
		else if(sitelist.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","SITE IN TOUR ERROR","Tour must contain at least one site! Please enter a site!!");
		
		else {ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.edittour,tour));}

	}
	/**uploaddata method -the method is activated when initializing the page,
	 * get the data of the tour from DB and Uploading all data of this tour  to the screen 
  @param ArrayList<String> rs - the result set that return from mysqlconnecion.
	 * */

	void uploaddata(ArrayList<String> rs) {
		CountryName1.setText(rs.get(0));
		CountryName1.setDisable(true);
		City_Name.setText(rs.get(1));
		City_Name.setDisable(true);
		tour_name.setText(rs.get(2));
		tour_name.setDisable(true);
		Description.setText(rs.get(3));
		if (rs.get(4).equals("yes") ||rs.get(4).equals("YES")) {		
			{Recommended_yes.setSelected(true);
			recommended="yes";
			}
		}// to check box
		else if (rs.get(4).equals("no")||rs.get(4).equals("NO")){
			Recommended_no.setSelected(true);
			recommended="no";
		}
	}
	/**UploadListData method -the method is activated when initializing the page,
	 * get the lists of the  sites in tour and site in city that not in the tour from DB and Uploading 
	 * all data of this tour to the tables on screen 
     * @param ArrayList<String> data - the result set that return from mysql connection.
     * flag = true on the begging - the method upload the sites in the tour to the table and change the flag to false
     * flag = false -the method upload the sites in city that not in the tour to the table
     * then call to UploadTable method.
	 * */
	@SuppressWarnings("unchecked")
	public void UploadListData(ArrayList<String> data) 
	{ 
		// Assembling the data:
		//@SuppressWarnings("unchecked")
		int jump = 0;
		if (flag)
		{
			for(int i=0;i<data.size()/2; i++)
			{
				// siteName , recommended time=> By the sql table 'siteintour', 'site'
				SiteDetails rd = new SiteDetails(data.get(0+jump),data.get(1+jump));
				existsite.add(rd);
				jump+=2;
			}
			UploadTable(existsite);
			sitelist=data;
			for(int i=0;i<sitelist.size(); i++)
				sitelist.remove(i+1);//only names
		}
		else
		{
			for(int i=0;i<data.size()/2; i++)
			{
				// siteName , recommended time=> By the sql table 'siteintour', 'site'
				SiteDetails rd = new SiteDetails(data.get(0+jump),data.get(1+jump));
				notexistsite.add(rd);
				jump+=2;
			}

			UploadTable(notexistsite);
		}

	}
	/**UploadTable method -the method is activated from UploadListData in the initialize or from Add/delete method when we update the tables
	 * get the lists of the  sites in tour or site in city that not in the tour from the "existsite" or "notexistsite" array list and Uploading 
     * @param ArrayList<String> dataToSend - "existsite" or "notexistsite" 
     * flag = true - site_of_list_to_add 
     *  flag = false - site_of_list_to_edit
	 * */

	@SuppressWarnings("unchecked")
	public void UploadTable(ArrayList<SiteDetails> dataToSend) 
	{
		// dataToSend containing all the data!
		// Inserting to viewTable:
		ObservableList<SiteDetails> ol = FXCollections.observableArrayList(dataToSend);
		// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
		TableColumn<SiteDetails,String> SiteName = new TableColumn<SiteDetails,String>("Sites:");
		SiteName.setMinWidth(60);
		SiteName.setCellValueFactory(new PropertyValueFactory<SiteDetails,String>("SiteName"));
		TableColumn<SiteDetails,String> RecommendedVisitTime = new TableColumn<SiteDetails,String>("Recommended Visit Time:");
		RecommendedVisitTime.setMinWidth(60);
		RecommendedVisitTime.setCellValueFactory(new PropertyValueFactory<SiteDetails,String>("RecommendedVisitTime"));
		if (flag)
		{
			if(site_of_list_to_delete.getItems().isEmpty() == true)
			{
				site_of_list_to_delete.setItems(ol);
				site_of_list_to_delete.getColumns().addAll(SiteName,RecommendedVisitTime);

				flag = false;
			}
			else
			{
				site_of_list_to_delete.getItems().clear();
				site_of_list_to_delete.setItems(ol);
				flag = false;
			}
		}
		else 
		{	if(site_of_list_to_add.getItems().isEmpty() == true)
		{
			site_of_list_to_add.setItems(ol);
			site_of_list_to_add.getColumns().addAll(SiteName,RecommendedVisitTime);
			flag=true;
		}
		else
		{
			site_of_list_to_add.getItems().clear();
			site_of_list_to_add.setItems(ol);
			flag=true;

		}
		}

	}
	/**sendtoapprove method -the method is activated from client masseges if the tour edit was sucssesfuly
     * @param boolean b<String> if the edit details was sucssesfuly - then we send request to apprpve to manger.
     * send a query to  getVersionNum that insert the requst to Version table , maneger get the requst from this table
 
	 * */
	public void sendtoapprove(boolean b){
		if (b) {
			Employee.setId(ClientMessages.username);
			tour.setSites(sitelist);
			System.out.println(tour.getCountryname().toString()+  tour.getCityename().toString() +  tour.getIDMAP().toString() +   tour.getSites().toString() + tour.getTourname().toString() + tour.getTdescription().toString() +  tour.getRecommanded().toString());
			SiteInTour=new  ArrayList<>();
			SiteInTour.add(tour);
			System.out.println(SiteInTour.size());
			Version= new VersionDetails (tour.getCountryname(),tour.getCityename(),tour.getIDMAP(),Employee.getId(),"0");
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getVersionNum,Version));
			ClientTools.changeWindow((Stage)window,"/client/boundry/MapInfoPage.fxml"); }

		}
	


	@FXML
	void initialize() {
		instance = this;
		flag=true;
		IDMAP=MapInfoController.InfoOfEditTour.getIDMAP();
		cityName=MapInfoController.InfoOfEditTour.getCityename();
		tourName=MapInfoController.InfoOfEditTour.getTourname();
		String query="SELECT * FROM gcm.tour WHERE TourName='"+tourName+"';";//need to get tour from page before
		System.out.println("my sql "+ query);
		String query2="	SELECT distinct site.SiteName,site.RecommendedVisitTime FROM gcm.siteintour,gcm.site WHERE siteintour.SiteName=site.SiteName AND siteintour.TourName ='"+tourName+"' ;" ; 
		System.out.println("my sql "+ query2);
		String query3="	SELECT distinct site.SiteName,site.RecommendedVisitTime  FROM site,siteintour WHERE  site.cityname = '"+cityName+"' AND (siteintour.TourName <> '"+tourName+"') AND site.SiteName=siteintour.SiteName;" ;
		System.out.println("my sql "+ query3);
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.gettourdata,query));
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.GetListExistSiteData,query2));
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.GetListExistSiteData,query3));
	    assert tourname != null : "fx:id=\"tourname\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert Recommended != null : "fx:id=\"Recommended\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert City_Name != null : "fx:id=\"City_Name\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert tour_name != null : "fx:id=\"tour_name\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert CountryName != null : "fx:id=\"CountryName\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert CountryName1 != null : "fx:id=\"CountryName1\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert Recommended_yes != null : "fx:id=\"Recommended_yes\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert Recommended_no != null : "fx:id=\"Recommended_no\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert bake_without_save != null : "fx:id=\"bake_without_save\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert site_of_list_to_delete != null : "fx:id=\"site_of_list_to_delete\" was not injected: check your FXML file 'TourEditWindow.fxml'.";
        assert site_of_list_to_add != null : "fx:id=\"site_of_list_to_add\" was not injected: check your FXML file 'TourEditWindow.fxml'.";

	}

}

