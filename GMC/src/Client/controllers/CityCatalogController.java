package client.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;

import animatefx.animation.AnimationFX;
import animatefx.animation.Bounce;
import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.City;
import javafx.animation.*;
import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 *This  Controller present all the cities that we have in the catalog ,
 * if the user connect , he has more option than the public that can watch some of the details
 * 
 */
public class CityCatalogController {

	public Integer PageNumber;

	public ArrayList<City> SearchResult;

	public static String CityDetail;

	public static CityCatalogController Instance;

	@FXML
	private Label PageNumberLabel;

	@FXML
	private JFXButton PreviousPageBtn;

	@FXML
	private JFXButton NextPageBtn;

	@FXML
	private ImageView FirstCityImage;

	@FXML
	private Label FirstCityNameLabel;

	@FXML
	private JFXButton FirstCityViewBtn;

	@FXML
	private JFXButton FirstCityPurchaseBtn;

	@FXML
	private JFXButton FirstCityDownloadBtn;

	@FXML
	private Label FirstCityViewsNumberOfCollection;

	@FXML
	private Label FirstCityCollectionPriceLabel;

	@FXML
	private Label FirstCityCollecetionSizeLabel;

	@FXML
	private Label FirstCityVersionNumberOfCollectionLabel;

	@FXML
	private ImageView SecondCityImage;

	@FXML
	private Label SecondCityNameLabel;

	@FXML
	private JFXButton SecondCityViewBtn;

	@FXML
	private JFXButton SecondCityPurchaseBtn;

	@FXML
	private JFXButton SecondCityDownloadBtn;

	@FXML
	private Label SecondCityViewsNumberOfCollection;

	@FXML
	private Label SecondCityCollectionPriceLabel;

	@FXML
	private Label SecondCityCollecetionSizeLabel;

	@FXML
	private Label SecondCityVersionNumberOfCollectionLabel;

	@FXML
	private ImageView ThirdCityImage;

	@FXML
	private Label ThirdCityNameLabel;

	@FXML
	private JFXButton ThirdCityViewBtn;

	@FXML
	private JFXButton ThirdCityPurchaseBtn;

	@FXML
	private JFXButton ThirdCityDownloadBtn;

	@FXML
	private Label ThirdCityViewsNumberOfCollection;

	@FXML
	private Label ThirdCityCollectionPriceLabel;

	@FXML
	private Label ThirdCityCollecetionSizeLabel;

	@FXML
	private Label ThirdCityVersionNumberOfCollectionLabel;

	@FXML
	private ImageView FourthCityImage;

	@FXML
	private Label FourthCityNameLabel;

	@FXML
	private JFXButton FourthCityViewBtn;

	@FXML
	private JFXButton FourthCityPurchaseBtn;

	@FXML
	private JFXButton FourthCityDownloadBtn;

	@FXML
	private Label FourthCityViewsNumberOfCollection;

	@FXML
	private Label FourthCityCollectionPriceLabel;

	@FXML
	private Label FourthCityCollecetionSizeLabel;

	@FXML
	private Label FourthCityVersionNumberOfCollectionLabel;

	@FXML
	private TextField TextFiledSearch;

	@FXML
	private JFXButton SearchBtn;

	@FXML
	private JFXRadioButton PopularplaceBtn;

	@FXML
	private JFXRadioButton CityNameBtn;   

	@FXML
	private AnchorPane FirstCityPane;

	@FXML
	private AnchorPane SecondCityPane;

	@FXML
	private AnchorPane ThirdCityPane;

	@FXML
	private AnchorPane FourthCityPane;

	@FXML
	private JFXButton UserPageButton;

	@FXML
	private JFXButton LogoutBtn;

	@FXML
	private HBox LoadingAni;

	@FXML
	private Circle Circle1;

	@FXML
	private Circle Circle2;

	@FXML
	private Circle Circle3;

	@FXML
	private JFXListView<Label> SearchHistory;

	@FXML
	private JFXButton ViewFullCatalogBtn;




	public static ArrayList<String> city;
	/**
	 * Download's all the city maps in the collection
	 * @param event - when the user press on the "Download" button of a city 
	 */
	@FXML
	void Download(ActionEvent event) {
		if(ClientMessages.username==null) {
			ClientMessages.popUp(AlertType.INFORMATION,"SignUp","Please sign up for watching all !", "Note : go one step back to the login screen and signup for free !");
			return;
		}
		else {
			city=(ArrayList<String>) GetCityDetail((String)((Node) event.getSource()).getId());
			try {
				BorderPane  root = FXMLLoader.load(getClass().getResource("/client/boundry/DownloadVersionWindow.fxml"));
				Scene scene=new Scene(root);
				Stage primaryStage= new Stage();
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			catch (Exception e) {
				System.err.println("Download -CityCatalog Exception !");
			}
		}

	}


	/**
	 * Shows the user details of the purchase
	 * @param event - when the user press on the "Purchase" button of a city 
	 */
	@FXML
	void Purchase(ActionEvent event) {
		if(ClientMessages.username==null) {
			ClientMessages.popUp(AlertType.INFORMATION,"SignUp","Please sign up for watching all !", "Note : go one step back to the login screen and signup for free !");
			return;
		}
		try {
		Instance.CityDetail=(String) GetCityDetail((String)((Node) event.getSource()).getId());
		System.out.println(CityDetail);
		}
		catch(Exception e) {System.err.println("Instance exception");}

		
		try {
			BorderPane root;
			root = FXMLLoader.load(getClass().getResource("/client/boundry/SubscribeWindow.fxml"));

			Scene scene=new Scene(root);
			Stage primaryStage= new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("CityCatalogPurchase open window exception");
		}

	}

	/**
	 * when the user press on view collection --> he gets pop up to sign up
	 * @param event
	 */
	@FXML
	void ViewCollection(ActionEvent event) {
		if(ClientMessages.username==null) {
			ClientMessages.popUp(AlertType.INFORMATION,"SignUp","Please sign up for watching all !", "Note : go one step back to the login screen and signup for free !");
			return;
		}
		Instance.CityDetail=(String) GetCityDetail((String)((Node) event.getSource()).getId());
		System.out.println(CityDetail);
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.CountUpdate,"UPDATE `gcm`.`maps_catalog` SET `ViewCount` =`ViewCount` +1 WHERE (`CityName` ='"+ CityDetail+ "');"));
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/MapViewWindow.fxml");
	}




	/**
	 * GoToUserHomePage return back to the user home page 
	 * @param event when the user click on the UserPageButton button
	 */
	@FXML
	void GoToUserHomePage(ActionEvent event) {
		if(ClientMessages.username==null)
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/Login.fxml");
		else
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
	}

	/**
	 * LogOut Logs out the user from the system
	 * @param event when the user click on the LogoutBtn button
	 */
	@FXML
	void LogOut(ActionEvent event) {
		if(ClientMessages.username==null) {
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/SignUpWindow.fxml");
			return;
		}
		try {
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
	/**
	 * SearchInDataBase Search in the Data Base for the desire data 
	 * @param event when the user click on the "SearchBtn" button
	 */
	@FXML
	void SearchInDataBase(ActionEvent event) {
		if(!TextFiledSearch.getText().equals("")) {//if the text field is empty 
			if(CityNameBtn.isSelected()) {	//if the user chose to search by City name
				String query="SELECT * FROM gcm.maps_catalog WHERE CityName LIKE '"+TextFiledSearch.getText()+"%';";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SearchInCatalog,query));
			}
			else {
				if(PopularplaceBtn.isSelected()) {//if the user chose to search by popular place
					String query="SELECT distinct gcm.maps_catalog.* FROM gcm.maps_catalog,gcm.site WHERE gcm.site.SiteName LIKE '"+TextFiledSearch.getText()+"%' AND gcm.site.cityname=gcm.maps_catalog.CityName;";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SearchInCatalog,query));
				}
				else {//if the user chose to search by textual description
					String query="SELECT distinct gcm.maps_catalog.* FROM gcm.maps_catalog,gcm.site WHERE gcm.maps_catalog.CityName LIKE '"+TextFiledSearch.getText()+"%' OR (gcm.site.SiteName LIKE '"+TextFiledSearch.getText()+"%' AND gcm.maps_catalog.CityName=gcm.site.cityname);";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SearchInCatalog,query));
				}
			}
		}
	}
	/**
	 * Sets loading Animation on screen
	 */
	private void SetAnimation(){
		Bounce c1 =(Bounce) new Bounce(Circle1).setCycleDuration(3).setCycleCount(3).setDelay(Duration.millis(100));
		Bounce c2=(Bounce) new Bounce(Circle2).setCycleDuration(3).setCycleCount(3).setDelay(Duration.millis(200));
		Bounce c3 =(Bounce) new Bounce(Circle3).setCycleDuration(3).setCycleCount(3).setDelay(Duration.millis(300));
		c1.play();
		c2.play();
		c3.play();
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * UploadFullCatalog all the city collections to the screen 
	 * @param event - when the user click on the "View Full Catalog" button
	 */
	@FXML
	void UploadFullCatalog(ActionEvent event) {
		PageNumber=0;
		System.out.println("Before:"+(PageNumber*4));
		System.out.println("After"+((PageNumber*4)+5));
		String query="SELECT * FROM maps_catalog Limit "+(PageNumber*4)+","+5+";";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadDataToCityCatalog,query));
	}

	@FXML
	void CheckCityNameBtn(ActionEvent event) {
		if(CityNameBtn.isSelected())
			CityNameBtn.setSelected(false);
	}

	@FXML
	void CheckPopularPlaceBtn(ActionEvent event) {
		if(PopularplaceBtn.isSelected())
			PopularplaceBtn.setSelected(false);
	}

	/**
	 * UploadNext upload the next four cites in the data base 
	 * @param event when the user click on the Next Page Button 
	 */
	@FXML
	void UploadNext(ActionEvent event) {
		PageNumber++;
		System.out.println("Before:"+(PageNumber*4));
		System.out.println("After"+((PageNumber*4)+5));
		String query="SELECT * FROM maps_catalog Limit "+(PageNumber*4)+","+5+";";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadDataToCityCatalog,query));	

	}

	/**
	 * UploadPrevious upload the previous four cites in the data base
	 * @param event when the user click on the Next Page Button 
	 */
	@FXML
	void UploadPrevious(ActionEvent event) {
		PageNumber--;
		String query="SELECT * FROM maps_catalog Limit "+(PageNumber*4)+","+5+";";	
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadDataToCityCatalog,query));	
	}


	@FXML
	void initialize() {
		AnchorPane CityInfo[]= {FirstCityPane,SecondCityPane,ThirdCityPane,FourthCityPane};
		for(int i=0;i<4;i++) {
			CityInfo[i].setVisible(false);
			//CityInfo[i].setOpacity(0);
		}
		SearchHistory=new JFXListView<Label>();
		//	SearchHistory.
		LoadingAni.setOpacity(0);
		PageNumber=0;
		SearchResult=new ArrayList<>();
		PreviousPageBtn.setDisable(true);
		Instance=this;
		if(ClientMessages.username==null) {
			LogoutBtn.setText("Sign UP");
		}
		String query="SELECT * FROM gcm.maps_catalog order by CityName Limit "+(PageNumber)+",5;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadDataToCityCatalog,query));	
	}


	/**
	 * SetDataOnScreen presents the relevant information of every city
	 * @param msg ArrayList contains all the city's the query retrieved 
	 * @throws SQLException
	 */
	public void SetDataOnScreen(Object msg) throws SQLException {
		ArrayList<City> rs;
		Label CityName[]= {FirstCityNameLabel,SecondCityNameLabel,ThirdCityNameLabel,FourthCityNameLabel};
		ImageView CityImage[]= {FirstCityImage,SecondCityImage,ThirdCityImage,FourthCityImage};
		Label CityViews[]= {FirstCityViewsNumberOfCollection,SecondCityViewsNumberOfCollection,ThirdCityViewsNumberOfCollection,FourthCityViewsNumberOfCollection};
		Label CityPrice[]= {FirstCityCollectionPriceLabel,SecondCityCollectionPriceLabel,ThirdCityCollectionPriceLabel,FourthCityCollectionPriceLabel};
		Label CityMaps[]= {FirstCityCollecetionSizeLabel,SecondCityCollecetionSizeLabel,ThirdCityCollecetionSizeLabel,FourthCityCollecetionSizeLabel};
		Label CityVersion[]= {FirstCityVersionNumberOfCollectionLabel,SecondCityVersionNumberOfCollectionLabel,ThirdCityVersionNumberOfCollectionLabel,FourthCityVersionNumberOfCollectionLabel};
		AnchorPane CityInfo[]= {FirstCityPane,SecondCityPane,ThirdCityPane,FourthCityPane};
		if(SearchResult.isEmpty()) {
			rs=(ArrayList<City>)((Message)msg).getObject();
		}
		else {
			rs=SearchResult;
		}
		for(int i=0;i<4;i++) {
			if(CityInfo[i].getOpacity()!=0) {
				FadeOutTrans(CityInfo[i]);
			}
			else {
				if(CityInfo[i].isVisible()==false)
					CityInfo[i].setVisible(true);
				FadeInTrans(CityInfo[i]);
			}
		}
		FadeInTrans(LoadingAni);
		SetAnimation();
		FadeOutTrans(LoadingAni);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				//LoadingAni.setOpacity(0);
				for(int i=0;i<4;i++) {
					if(CityInfo[i].getOpacity()==0) {
						FadeInTrans(CityInfo[i]);
					}
					else {
						FadeOutTrans(CityInfo[i]);
					}
				}
				int count=0;
				Integer IndexPage=PageNumber+1;//Page Index
				PageNumberLabel.setText(IndexPage.toString());
				for(int i=0;i<rs.size();i++,count++) {
					if(i==4)  //We show only four cites at once 
						break;
					CityName[i].setText(rs.get(i).getCityName()); //upload the data from the sql to the screen 
					CityPrice[i].setText(rs.get(i).getPrice());
					CityMaps[i].setText(rs.get(i).getNumberOfMaps());
					CityVersion[i].setText(rs.get(i).getVersionNumber());
					CityViews[i].setText(rs.get(i).getViewsCount());
					Image image = new Image(rs.get(i).getCityImageUrl());
					CityImage[i].setImage(image);
				}
				if(rs.size()-4<=0) {//if we retrieve less than four cites we show only the ones that we have 
					int j=0;
					NextPageBtn.setDisable(true);
					if(IndexPage==1)
						PreviousPageBtn.setDisable(true);
					else
						PreviousPageBtn.setDisable(false);
					for(int i=rs.size();i<4;i++) {
						FadeOutTrans(CityInfo[i]);
						// CityInfo[i].setVisible(false);
					}
					for(int i=0;i<rs.size();i++) {
						FadeInTrans(CityInfo[i]);
						// CityInfo[i].setVisible(true);
					}
				}
				else {
					for(int i=0;i<4;i++) { //showing all the data on the screen 
						FadeInTrans(CityInfo[i]);
						// CityInfo[i].setVisible(true);
					}
					NextPageBtn.setDisable(false);
					if(IndexPage==1) {
						PreviousPageBtn.setDisable(true);
					}
					else
						PreviousPageBtn.setDisable(false);
				}
				if(rs==SearchResult){//if we Take the citys data from search
					while(count>0) {
						SearchResult.remove(0);
						count--;
					}

				}
			}
		});
	}
	//Temporary not working yet 
	public void SetSearchList(Object msg) throws SQLException {
		ArrayList<String> rs =(ArrayList<String>)((Message)msg).getObject();	
		for(int i=0;i<rs.size();i++) {
			Label lbl= new Label(rs.get(i));
			System.out.println(lbl.getText());
			SearchHistory.getItems().add(lbl);
		}
	}

	private void FadeInTrans(Node node) {
		if(node.isVisible()==false)
			node.setVisible(true);
		FadeTransition fadein=new FadeTransition(Duration.seconds(0.6),node);
		fadein.setFromValue(0.0);
		fadein.setToValue(1.0);
		fadein.play();
	}
	private void FadeOutTrans(Node node) {
		FadeTransition fadeout=new FadeTransition(Duration.seconds(0.6),node);
		fadeout.setFromValue(1.0);
		fadeout.setToValue(0.0);
		fadeout.play();
	}
	/**
	 * @param buttonid the name of the buuton that the user pressed 
	 * @return the name of the chosen city 
	 */
	private Object GetCityDetail(String buttonid) {
		String Name="";
		ArrayList<String> city =new ArrayList<>();
		switch(buttonid){
		case "FirstCityViewBtn":{
			Name= FirstCityNameLabel.getText();
			break;
		}
		case "SecondCityViewBtn":{
			Name= SecondCityNameLabel.getText();
			break;
		}
		case "ThirdCityViewBtn":{
			Name= ThirdCityNameLabel.getText();
			break;
		}
		case "FourthCityViewBtn":{
			Name= FourthCityNameLabel.getText();
			break;
		}
		case "FirstCityPurchaseBtn":{
			Name= FirstCityNameLabel.getText();
			break;
		}
		case "SecondCityPurchaseBtn":{
			Name= SecondCityNameLabel.getText();
			break;
		}
		case "ThirdCityPurchaseBtn":{
			Name= ThirdCityNameLabel.getText();
			break;
		}
		case "FourthCityPurchaseBtn":{
			Name= FourthCityNameLabel.getText();
			break;
		}
		case "FirstCityDownloadBtn":{
			city.add(FirstCityNameLabel.getText());
			city.add(FirstCityVersionNumberOfCollectionLabel.getText());
			return city;
		}
		case "SecondCityDownloadBtn":{
			city.add(SecondCityNameLabel.getText());
			city.add(SecondCityVersionNumberOfCollectionLabel.getText());
			return city;

		}
		case "ThirdCityDownloadBtn":{
			city.add(ThirdCityNameLabel.getText());
			city.add(ThirdCityVersionNumberOfCollectionLabel.getText());
			return city;
		}
		case "FourthCityDownloadBtn":{
			city.add(FourthCityNameLabel.getText());
			city.add(FourthCityVersionNumberOfCollectionLabel.getText());
			return city;

		}
		}

		return Name;

	}


}
