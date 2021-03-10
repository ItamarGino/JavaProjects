
package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.EmployeeDetails;
import common.entities.Site;
import common.entities.VersionDetails;

/**

* @author Shahar Ronen.
* @author Dorin Segall.
* @author Remez David.
* @author Itamar Gino.
* @author Amit Sinter.
* @version June 2019
* 
* This controller handles the insertion of data when editing a new particular site . 
* the controller will contain home page and logout like every page, use the entity site and VersionDetails 
* use the tables: site,site in map, temp site version , map and map catalog  . 
*
*/

public class SiteEditController {
	public static  SiteEditController instance;
	static Stage window;
	private String Accessibility; 
	public String IDMAP;
	public VersionDetails Version;
	public Site site;
	public double SiteLocationX;
	public double SiteLocationY;
	private EmployeeDetails Employee= new EmployeeDetails();

    @FXML
    private JFXTextField Location_x;

    @FXML
    private JFXTextField Location_y;

    @FXML
    private JFXTextField Country_Name;

    @FXML
    private JFXTextField City_Name;

    @FXML
    private JFXTextField site_name;

    @FXML
    private JFXTextArea Description;

    @FXML
    private JFXTextField RecommendedVisitTime;
	
	@FXML
	private ComboBox<String> categorylist;

	@FXML
	private CheckBox Accessibility_no;

	@FXML
	private CheckBox Accessibility_yes;


	@FXML
	private Button save;

	@FXML
	private Button bake_without_save;


	@FXML
	void bake(ActionEvent event) {
	
	ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/MapInfoPage.fxml"); 
	}

	@FXML
	void get_no(ActionEvent event) {
		Accessibility="no";
		if (Accessibility_yes.isSelected())
			Accessibility_yes.setSelected(false);}

	@FXML
	void get_yes(ActionEvent event) {
		Accessibility="yes";
		if (Accessibility_no.isSelected())
			Accessibility_no.setSelected(false);}
	@FXML
	void logout(ActionEvent event) throws IOException {
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
    And sends to the query to update the data to DB.
    @param event - from the event we get the Window and all the details
	 * */

	@FXML
	void save(ActionEvent event) {
		window=(Stage)((Node)event.getSource()).getScene().getWindow();
		String  countryname=Country_Name.getText();
		String  cityname=City_Name.getText();
		String  sitename=site_name.getText();
		String Locationx=Location_x.getText(); 
		String Locationy=Location_y.getText(); 
		String description=Description.getText();
		String Category=null;
		Category=categorylist.getValue();
    	String recommendedVisitTime=RecommendedVisitTime.getText();
    	site = new Site(countryname,cityname,sitename,Locationx,Locationy,Category,description,Accessibility,IDMAP,recommendedVisitTime);
    	int foo;
		try {  foo = Integer.parseInt(recommendedVisitTime);	}
		catch (NumberFormatException e)
		{ ClientMessages.popUp(AlertType.ERROR,"ERROR","LENGHT FORMAT ERROR","Recommended Visit Time MUST be a number!");
		return;}
		
		if(recommendedVisitTime.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","COUNTRY NAME ERROR"," Recommended Visit Time field is Empty!");
		else if(countryname.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","COUNTRY NAME ERROR","city name field is Empty!");
		else if(cityname.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","CITY NAME ERROR","city name field is Empty!");
		else if(sitename.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","SITE NAME ERROR","Site name field is Empty!");
		else if(Locationx.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","LOCATION NAME ERROR","Locationx name field is Empty!");
		else if(Locationy.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","LOCATION NAME ERROR","Locationy name field is Empty!");
		else if(description.isEmpty())
			ClientMessages.popUp(AlertType.ERROR,"ERROR","DESCRIPTION NAME ERROR","description name field is Empty!");
		else if(Category==null)
			ClientMessages.popUp(AlertType.ERROR,"ERROR","CATEGORY NAME ERROR","you didn't choose a Category !!");
		else if(Accessibility==null)
			ClientMessages.popUp(AlertType.ERROR,"ERROR","ACCESSIBILITY NAME ERROR","you didn't choose if Accessibility or not !!");
		else if((!Accessibility_no.isSelected())&&(!Accessibility_yes.isSelected()))
			ClientMessages.popUp(AlertType.ERROR,"ERROR","ACCESSIBILITY NAME ERROR","you didn't choose if Accessibility or not !!");
		else{
	          ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.editsite,site));}}

	/**uploaddata method -the method is activated when initializing the page,
	 * get the data of the site from DB and Uploading all data  to the screen 
  @param ArrayList<String> rs - the result set that return from mysqlconnecion.
	 * */
	void uploaddata(ArrayList<String> rs) {
		Country_Name.setText(rs.get(0));
		Country_Name.setDisable(true);
		City_Name.setText(rs.get(1));
		City_Name.setDisable(true);
		site_name.setText(rs.get(2));
		site_name.setDisable(true);
		categorylist.setValue(rs.get(3));
		Description.setText(rs.get(4));
		if (rs.get(5).equals("yes") ||rs.get(5).equals("YES")) {
			Accessibility_yes.setSelected(true);
			Accessibility="yes";
		}// to check box
		else if (rs.get(5).equals("no")||rs.get(5).equals("NO")){
			Accessibility_no.setSelected(true);
			Accessibility="no";
		}
		RecommendedVisitTime.setText(rs.get(6));
		Location_x.setText(rs.get(7));
		Location_x.setDisable(true);
		Location_y.setText(rs.get(8));
		Location_y.setDisable(true);

	}

	/**sendtoapprove method --the method is activated from client masseges if the site  edit was sucssesfuly
	 @param boolean b<String> if the edit details was sucssesfuly - then we send request to apprpve to manger.
     * send a query to  getVersionNum that insert the requst to Version table , maneger get the requst from this table
 
	 * */
	public void sendtoapprove(boolean b) {
		if (b) {
			Employee.setId(ClientMessages.username);
			Version= new VersionDetails (site.getCountryname(),site.getCityename(),site.getIDMAP(),Employee.getId(),"0");//need to get employee id from entity
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getVersionNum,Version));
			ClientTools.changeWindow((Stage)window,"/client/boundry/MapInfoPage.fxml"); 
		}		
	}
	@FXML
	void initialize() {
		SiteEditController.instance = this;
		SiteLocationX=Double.parseDouble(MapInfoController.InfoOfSite.getLocation_X());
    	SiteLocationY=Double.parseDouble(MapInfoController.InfoOfSite.getLocation_y());
        IDMAP=MapInfoController.InfoOfSite.getIDMAP();
		Accessibility=null;
		categorylist.getItems().removeAll(categorylist.getItems());
		categorylist.getItems().addAll("Historical Sites","Museums Sites","Restaurants","Hotels","Movie Theaters", "Park Sites","Public Sites", "Shops");
		String query="SELECT distinct gcm.site.*,gcm.siteinmap.SiteLocationX,gcm.siteinmap.SiteLocationY FROM gcm.site,gcm.siteinmap,gcm.map WHERE ('"+IDMAP+"'=gcm.siteinmap.IDMAP AND gcm.siteinmap.SiteName=gcm.site.SiteName AND gcm.siteinmap.SiteLocationX="+SiteLocationX+" AND gcm.siteinmap.SiteLocationY="+SiteLocationY+" );";
		System.out.println("my sql "+ query);
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getsitedata,query));
		assert City_Name != null : "fx:id=\"City_Name\" was not injected: check your FXML file 'site.fxml'.";
		assert site_name != null : "fx:id=\"site_name\" was not injected: check your FXML file 'site.fxml'.";
		assert Location_x != null : "fx:id=\"Location_x\" was not injected: check your FXML file 'site.fxml'.";
		assert categorylist != null : "fx:id=\"categorylist\" was not injected: check your FXML file 'site.fxml'.";
		assert Accessibility_no != null : "fx:id=\"Accessibility_no\" was not injected: check your FXML file 'site.fxml'.";
		assert Accessibility_yes != null : "fx:id=\"Accessibility_yes\" was not injected: check your FXML file 'site.fxml'.";
		assert Location_y != null : "fx:id=\"Location_y\" was not injected: check your FXML file 'site.fxml'.";
		assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file 'site.fxml'.";
		assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'site.fxml'.";
		assert Country_Name != null : "fx:id=\"Country_Name\" was not injected: check your FXML file 'SiteEditWindow.fxml'.";
		assert bake_without_save != null : "fx:id=\"bake_without_save\" was not injected: check your FXML file 'site.fxml'.";
        assert RecommendedVisitTime != null : "fx:id=\"RecommendedVisitTime\" was not injected: check your FXML file 'SiteEditWindow.fxml'.";


	}
}










