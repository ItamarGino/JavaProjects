package client.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import common.entities.Site;
import common.entities.Tour;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MapInfoController  {


	public static MapInfoController Instance;
	public static Site InfoOfSite;
	public static Tour InfoOfEditTour;
	public static Tour InfoOfnewTour;
	private String Operation="";
	private ArrayList<Tour> TourList;
	private ArrayList<Site> SiteList;
	private Image imageoflocation;
	private Image Recomand;
	private Image NotRecomand;
	private Image Delete;
	private Image Edit;
	private String CityName;
	private String MapID;
	private String DeleteMe;
	public String MapPrice;
	private Boolean Permission1;
	private Boolean Permission2;

	  @FXML
	    private JFXButton UserPageButton;

	    @FXML
	    private JFXButton LogoutBtn;

	    @FXML
	    private Hyperlink Historical;

	    @FXML
	    private Label HistoricalNumber;

	    @FXML
	    private Hyperlink Museums;

	    @FXML
	    private Label MuseumsNumber;

	    @FXML
	    private Hyperlink Restsaurant;

	    @FXML
	    private Label RestaurantNumber;

	    @FXML
	    private Hyperlink Hotels;

	    @FXML
	    private Label HotelsNumber;

	    @FXML
	    private Hyperlink MovieTheaters;

	    @FXML
	    private Label MovieTheatersNumber;

	    @FXML
	    private Hyperlink PublicSites;

	    @FXML
	    private Label PublicSitesNumber;

	    @FXML
	    private Hyperlink Shops;

	    @FXML
	    private Label ShopsNumber;

	    @FXML
	    private Hyperlink ParkSites;

	    @FXML
	    private Label ParkNumber;

	    @FXML
	    private Hyperlink Tours;

	    @FXML
	    public Label TourNumber;

	    @FXML
	    private JFXButton ViewFullSitesBtn;

	    @FXML
	    private JFXButton SetNewPriceBtn;

	    @FXML
	    private Label MapNameLbl;

	    @FXML
	    private ImageView CityImage;

	    @FXML
	    private Label SitesOrTourLbl;

	    @FXML
	    private Label Watching;

	    @FXML
	    private AnchorPane SiteOptions;

	    @FXML
	    private Label Addlbl;

	    @FXML
	    private JFXButton AddBtn;

	    @FXML
	    private Label Editlbl;

	    @FXML
	    private JFXButton EditBtn;

	    @FXML
	    private Label Deletelbl;

	    @FXML
	    private JFXButton DeleteBtn;

	    @FXML
	    private AnchorPane MapView;

	    @FXML
	    private AnchorPane SitesAnc;

	    @FXML
	    private AnchorPane TourAnc;

	    @FXML
	    private JFXListView<HBox> ListOfTours;

	    @FXML
	    private JFXButton AddTourBtn;

	    @FXML
	    private AnchorPane SetCityPriceAnc;

	    @FXML
	    public JFXTextField CurrentPriceField;

	    @FXML
	    private JFXTextField NewPriceField;

	    @FXML
	    private JFXTextField Descriptionlbl;
    
    
    
    @FXML
    void BackToSiteView(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
			AnchorPane[] arr= {TourAnc,SetCityPriceAnc};
			for(int i=0;i<2;i++) {
				arr[i].setDisable(true);
				FadeOutTrans(arr[i]);
			}
			FadeInTrans(SitesAnc);
			SitesAnc.setDisable(false);
			FadeInTrans(SiteOptions);
			SiteOptions.setDisable(false);
			}
		});
    }

    @FXML
    void ChangePaneToSetNewPrice(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
			AnchorPane[] arr= {TourAnc,SitesAnc,SiteOptions};
			for(int i=0;i<3;i++) {
				arr[i].setDisable(true);
				FadeOutTrans(arr[i]);
			}
			FadeInTrans(SetCityPriceAnc);
			SetCityPriceAnc.setDisable(false);
			NewPriceField.setDisable(false);
			Descriptionlbl.setDisable(false);
			}
		});
    }
    
    @FXML
    void SetPriceToMap(ActionEvent event) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(NewPriceField.getText().equals("")) {
					NewPriceField.setUnFocusColor(Color.RED);
					NewPriceField.setText("No new price entered");
				}
				else {
					String SetNewPrice="INSERT INTO gcm.request (`mapID`, `employee`, `mdetails`, `lprice`, `rprice`) VALUES ('"+MapID+"','"+ClientMessages.username+"', '"+Descriptionlbl.getText()+"', '"+CurrentPriceField.getText()+"', '"+NewPriceField.getText()+"');";
					ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SetNewPriceRequestForMap,SetNewPrice));
					NewPriceField.setUnFocusColor(Color.BLACK);
					NewPriceField.setText("");
					Descriptionlbl.setText("");
					SetCityPriceAnc.setDisable(true);
					SiteOptions.setDisable(false);
					FadeOutTrans(SetCityPriceAnc);
					SitesAnc.setDisable(false);
					FadeInTrans(SitesAnc);
					FadeInTrans(SiteOptions);
				}
			}
		});
    	
    }
	@FXML
	void initialize() {
		Instance=this;
		Label lbl[]= {HistoricalNumber,MuseumsNumber,RestaurantNumber,HotelsNumber,MovieTheatersNumber,ParkNumber,ShopsNumber,PublicSitesNumber,TourNumber};
		for(int i=0;i<9;i++)
			lbl[i].setText("0");
		File photo;
		String PhotoPath[]= {"Icons/placeholder.png","Icons/dislike.png","Icons/like.png","Icons/delete-button.png","Icons/editmap.png"};
		Image img[]= {imageoflocation,NotRecomand,Recomand,Delete,Edit};
		/*for(int i=0;i<5;i++) {
			if((photo=new File(PhotoPath[i])).isFile()) {
				System.out.println("Hey");
				img[i]=new Image((photo.toURI().toString()));
			}
		}*/
		if((photo=new File("Icons/placeholder.png")).isFile())
			imageoflocation=new Image((photo.toURI().toString()));
		if((photo=new File("Icons/dislike.png")).isFile())
			NotRecomand=new Image((photo.toURI().toString()));
		if((photo=new File("Icons/like.png")).isFile());
		Recomand=new Image((photo.toURI().toString()));
		if((photo=new File("Icons/delete-button.png")).isFile())
			Delete=new Image((photo.toURI().toString()));
		if((photo=new File("Icons/editmap.png")).isFile())
			Edit=new Image((photo.toURI().toString()));
		
		if(!ClientMessages.user.getRole().equals("contentManager") && !ClientMessages.user.getRole().equals("ceo")) {
			DeleteBtn.setDisable(true);
			DeleteBtn.setVisible(false);
			Deletelbl.setVisible(false);
			Permission1=false;
			SetNewPriceBtn.setVisible(false);
			SetNewPriceBtn.setDisable(true);
			if(!ClientMessages.user.getRole().equals("employee")) {
				EditBtn.setVisible(false);
				EditBtn.setDisable(true);
				Editlbl.setVisible(false);
				AddBtn.setDisable(true);
				AddBtn.setVisible(false);
				Addlbl.setVisible(false);
				AddTourBtn.setDisable(true);
				AddTourBtn.setVisible(false);
				Permission2=false;
			}
			else {
				Permission2=true;
			}
		}
		else
		{
			Permission2=true;
			Permission1=true;//for ceo or content
		}
		Node Arr[]= {MapNameLbl,SitesOrTourLbl,SitesAnc,CityImage,Watching,TourAnc,SetCityPriceAnc};
		SetOpcityToZero(Arr);
		InfoOfSite=new Site();
		MapNameLbl.setText(MapViewController.id_map+" Details");
		SitesOrTourLbl.setText("All Sites In "+MapViewController.cityName);
		Watching.setText("All Sites");
		SetCityPriceAnc.setDisable(true);
		TourAnc.setDisable(true);
		SetSizeOfAnchor(1334.0, 519.0);
		CityName=MapViewController.cityName;
		CurrentPriceField.setDisable(true);
		MapID=MapViewController.id_map;
		NewPriceField.setDisable(false);
		for(int i=0;i<5;i++)
			FadeInTrans(Arr[i]);
		//Upload Sites Qurey
		String GetSites="SELECT distinct gcm.site.*,gcm.siteinmap.SiteLocationX,gcm.siteinmap.SiteLocationY FROM gcm.site,gcm.siteinmap,gcm.map WHERE (gcm.site.cityname='"+CityName+"' AND '"+MapID+"'=gcm.siteinmap.IDMAP AND gcm.siteinmap.SiteName=gcm.site.SiteName);";
		//Upload Tours Qurey
		String GetTours="SELECT distinct gcm.tour.* FROM gcm.tour,gcm.tourinmap WHERE (gcm.tour.TourName=gcm.tourinmap.TourName AND gcm.tourinmap.ID='"+MapID+"' AND gcm.tour.CityName='"+CityName+"');";
		//Upload Map Image
		String GetMapImage="SELECT gcm.map.MapImage FROM gcm.map WHERE ID='"+MapID+"';";
		//Upload Map Price
		String GetMapPrice="SELECT gcm.map.Price From gcm.map WHERE gcm.map.ID='"+MapID+"';";
		//Upload City Image
		String GetCityImage="SELECT gcm.maps_catalog.CitySymbol FROM gcm.maps_catalog WHERE (gcm.maps_catalog.CityName='"+CityName+"');";//Need Country Name
		String Qureys[]= {GetSites,GetTours,GetMapImage,MapID,GetMapPrice,GetCityImage,CityName};
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadSitesAndToursToMap,Qureys));	
	}
	public void SetMapImageAndCityImage(File photo1,File photo2) {
		File temp1=new File(photo1.toURI());
		File temp2=new File(photo2.toURI());
		SitesAnc.setStyle("-fx-background-image: url("+temp1.toURI().toString()+");\n"+"-fx-background-position: center center;\n"+"-fx-background-size: 1334 519;\n"+"-fx-background-repeat:no-repeat ;");
		CityImage.setImage(new Image(temp2.toURI().toString()));
	}
	@FXML
	void UploadTours(ActionEvent event) {
		if(SetCityPriceAnc.isDisable()==false) {
			SetCityPriceAnc.setDisable(true);
			SetCityPriceAnc.setOpacity(0);
		}
		if(!SitesAnc.isDisable())
		SwitchPanes();
		SetWatching("Tours");
		ListOfTours.getItems().clear();
		if(TourList==null) {
			String query="SELECT distinct gcm.tour.* FROM gcm.tour,gcm.tourinmap WHERE (gcm.tour.TourName=gcm.tourinmap.TourName AND gcm.tourinmap.ID='"+MapID+"' AND gcm.tour.CityName='"+CityName+"');";
			String[]info= {query,MapID};
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadToursToMap,info));}
		else
			initializeToursOnMap(TourList);

	}
	@FXML
	void AddSite(ActionEvent event) {
		try {
			Operation="ADD";
			SitesAnc.setCursor(new ImageCursor(imageoflocation));
		}
		catch(Exception e) {
			System.out.println("Cant change cursor ADD");
			e.printStackTrace();
		}
	}
	@FXML
	void DeleteSite(ActionEvent event) {
		try {
			Operation="Delete";
			SitesAnc.setCursor(new ImageCursor(Delete));
		}
		catch(Exception e) {
			System.out.println("Cant change cursor Delete");
			e.printStackTrace();
		}
	}
	@FXML
	void EditSite(ActionEvent event) {
		try {
			Operation="Edit";
			SitesAnc.setCursor(new ImageCursor(Edit));
		}
		catch(Exception e) {
			System.out.println("Cant change cursor Edit");
			e.printStackTrace();
		}
	}
	private void GetXAndY(MouseEvent e) {
		if(Operation.equals("Delete")) {
			String SiteName=((JFXButton)e.getSource()).getId();
			String Qurey="Delete From gcm.siteinmap Where (gcm.siteinmap.SiteName='"+SiteName+"' AND gcm.siteinmap.IDMAP='"+MapID+"');";
			String arr[]= {SiteName,Qurey};
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.DeleteSiteFromMap,arr));
		}
		else
			if(Operation.equals("Edit")) {
				double x=((JFXButton)e.getSource()).getTranslateX();
				double y=((JFXButton)e.getSource()).getTranslateY();
				InfoOfSite.setLocation_X(Double.toString(x));
				InfoOfSite.setLocation_y(Double.toString(y));
				InfoOfSite.setIDMAP(MapID);
				System.out.println("Edit\nLocationX:"+InfoOfSite.getLocation_X()+"\nLocationY:"+InfoOfSite.getLocation_y()+"\nMap ID:"+InfoOfSite.getIDMAP());
				ClientTools.changeWindow((Stage)((Node)e.getSource()).getScene().getWindow(),"/client/boundry/SiteEditWindow.fxml");
			}
	}
	@FXML
	void SendOperation(MouseEvent event) {
		if(Operation.equals("ADD")) {
			InfoOfSite.setLocation_X(Double.toString(event.getSceneX()));
			InfoOfSite.setLocation_y(Double.toString(event.getSceneY()));
			InfoOfSite.setIDMAP(MapID);
			System.out.println("ADD\nLocationX:"+InfoOfSite.getLocation_X()+"\nLocationY:"+InfoOfSite.getLocation_y()+"\nMap ID:"+InfoOfSite.getIDMAP());
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/SiteAddWindow.fxml");
		}	
	}

	public void DeleteSiteFromMap(String SiteName) {
		Platform.runLater(new Runnable() {	
			@Override
			public void run() {
				try {
					for(Node node:SitesAnc.getChildren()) {
						if(((JFXButton)node).getId().equals(SiteName)) {
							SitesAnc.getChildren().remove(node);
							break;
						}

					}
					for(int i=0;i<SiteList.size();i++) {
						if(SiteList.get(i).getSitename().equals(SiteName)) {
							switch(SiteList.get(i).getCatagory()) {
							case "Park Sites":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(ParkNumber.getText());
								temp--;
								ParkNumber.setText(temp.toString());
								break;
							}
							case "Historical Sites":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(HistoricalNumber.getText());
								temp--;
								HistoricalNumber.setText(temp.toString());
								break;
							}
							case "Museums Sites":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(MuseumsNumber.getText());
								temp--;
								MuseumsNumber.setText(temp.toString());
								break;
							}
							case "Restaurants":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(RestaurantNumber.getText());
								temp--;
								RestaurantNumber.setText(temp.toString());
								break;
							}
							case "Hotels":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(HotelsNumber.getText());
								temp--;
								HotelsNumber.setText(temp.toString());
								break;
							}
							case "Movie Theaters":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(MovieTheatersNumber.getText());
								temp--;
								MovieTheatersNumber.setText(temp.toString());
								break;
							}
							case "Public Sites":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(PublicSitesNumber.getText());
								temp--;
								PublicSitesNumber.setText(temp.toString());
								break;
							}
							case "Shops":{
								SiteList.remove(i);
								Integer temp=Integer.parseInt(ShopsNumber.getText());
								temp--;
								ShopsNumber.setText(temp.toString());
								break;
							}
							}

						}
						SitesAnc.setCursor(Cursor.DEFAULT);
					}
				}catch(Exception e) {
					System.out.println("Hey!!!!!");
				}
			}
		});
	}
	@FXML
	void UploadSpecSites(ActionEvent event) {
		try {//{HistoricalNumber,MuseumsNumber,RestaurantNumber,HotelsNumber,MovieTheatersNumber,ParkNumber,ShopsNumber,PublicSitesNumber,TourNumber};
			String Qurey ="SELECT distinct gcm.site.*,gcm.siteinmap.SiteLocationX,gcm.siteinmap.SiteLocationY FROM gcm.site,gcm.siteinmap,gcm.map WHERE (gcm.site.cityname='"+CityName+"' AND '"+MapID+"'=gcm.siteinmap.IDMAP AND gcm.siteinmap.SiteName=gcm.site.SiteName AND gcm.site.Category=";
			switch((String)((Node) event.getSource()).getId()) {
			case "Historical":{
				SetLabelNumberToZero(HistoricalNumber);
				SetWatching("Historical Sites");
				Qurey+="'Historical Sites');";
				break;
			}
			case "Museums":{
				SetLabelNumberToZero(MuseumsNumber);
				SetWatching("Museums Sites");
				Qurey+="'Museums Sites');";
				break;
			}
			case "Restsaurant":{
				SetLabelNumberToZero(RestaurantNumber);
				SetWatching("Restaurants");
				Qurey+="'Restaurants');";
				break;
			}
			case "Hotels":{
				SetLabelNumberToZero(HotelsNumber);
				SetWatching("Hotels");
				Qurey+="'Hotels');";
				break;
			}
			case "MovieTheaters":{
				SetLabelNumberToZero(MovieTheatersNumber);
				SetWatching("Movie Theaters");
				Qurey+="'Movie Theaters');";
				break;
			}
			case "PublicSites":{
				SetLabelNumberToZero(PublicSitesNumber);
				SetWatching("Public Sites");
				Qurey+="'Public Sites');";
				break;
			}
			case "Shops":{
				SetLabelNumberToZero(ShopsNumber);
				SetWatching("Shops");
				Qurey+="'Shops');";
				break;
			}
			case "ParkSites":{
				SetLabelNumberToZero(ParkNumber);
				SetWatching("ParkSites");
				Qurey+="'Parks Sites');";
				break;
			}
			default:{
				Label lbl[]= {HistoricalNumber,MuseumsNumber,RestaurantNumber,HotelsNumber,MovieTheatersNumber,ParkNumber,ShopsNumber,PublicSitesNumber};
				for(int i=0;i<8;i++)
					SetLabelNumberToZero(lbl[i]);
				Qurey="SELECT distinct gcm.site.*,gcm.siteinmap.SiteLocationX,gcm.siteinmap.SiteLocationY FROM gcm.site,gcm.siteinmap,gcm.map WHERE (gcm.site.cityname='"+CityName+"' AND '"+MapID+"'=gcm.siteinmap.IDMAP AND gcm.siteinmap.SiteName=gcm.site.SiteName);";
				break;
			}
			}
			if(SitesAnc.isDisable()) {
				SwitchPanes();
				FadeOutTrans(SetCityPriceAnc);
				SetCityPriceAnc.setDisable(true);
			}
			RemoveSitesFromAnc(SitesAnc);
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadSitesToMap,Qurey));
		}
		catch(Exception e) {
			System.out.println("Faild to Upload Specific Sites");
			e.printStackTrace();
		}	
	}
	public void initializeSitesOnMap(Object msg) {
		ArrayList<Site> rs;
		if(msg instanceof Message)
			rs=(ArrayList<Site>)((Message)msg).getObject();
		else
			rs=(ArrayList<Site>)msg;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {	
					FadeInTrans(SitesAnc);
					FadeInTrans(Watching);
					for(int i=0;i<rs.size();i++) {
						System.out.println(i);
						ImageView img=new ImageView(imageoflocation);
						img.setFitWidth(24);
						img.setFitHeight(24);
						JFXButton btn=new JFXButton("",img);
						btn.setStyle("-fx-background-color: transparent;");	
						btn.setPrefSize(24, 24);
						btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->GetXAndY(e));
						Tooltip tooltip=new Tooltip("Site Name:"+rs.get(i).getSitename()+"\nDescription:"+rs.get(i).getDescription()+"\nCategory:"+rs.get(i).getCatagory());
						File photo=GetIconOfSite(rs.get(i).getCatagory());
						ImageView temp=new ImageView(new Image(photo.toURI().toString()));
						temp.setFitHeight(24);
						temp.setFitWidth(24);
						tooltip.setGraphic(temp);
						tooltip.setGraphicTextGap(3.0);
						tooltip.setAutoHide(true);
						btn.setTooltip(tooltip);
						btn.setTranslateX(Double.parseDouble(rs.get(i).getLocation_X()));
						btn.setTranslateY(Double.parseDouble(rs.get(i).getLocation_y()));
						btn.setId(rs.get(i).getSitename());
						SitesAnc.getChildren().add(btn);
						UpdateLabels(rs.get(i).getCatagory());

					}
				}catch(Exception e) {System.out.println("Cant Upload Sites");}
			}
		});	
	}
	
	public void PicturePane()
	{
		
	/*	Platform.runLater(new Runnable() {
			
			@Override
			public void run() {

				WritableImage image = SitesAnc.snapshot(new SnapshotParameters(), null);
		        File file = new File("chart.png");
		        if(SitesAnc.getOpacity()==0) {System.out.println("Shitttttttt!!!!");}
		        try {
		        	
		            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		        } catch (IOException e) {
		            // TODO: handle exception here
		        }
		        String Qurey="UPDATE gcm.map SET `MapImageWithSites` = ? WHERE (`ID` = '"+MapID+"');";
		    Object obj[]= {file,Qurey}; 
			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.SaveMapWithSitePic,obj));
			}
		});*/
	}
	
	
	
	
	
	public void UpdateLabels(String Catagory) {
		//{HistoricalNumber,MuseumsNumber,RestaurantNumber,HotelsNumber,MovieTheatersNumber,ParkNumber,ShopsNumber,PublicSitesNumber,TourNumber};
		switch (Catagory) {
		case "Parks Sites":{
			Integer temp=Integer.parseInt(ParkNumber.getText());
			temp++;
			ParkNumber.setText(temp.toString());
			break;
		}
		case "Historical Sites":{
			Integer temp=Integer.parseInt(HistoricalNumber.getText());
			temp++;
			HistoricalNumber.setText(temp.toString());
			break;
		}
		case "Museums Sites":{
			Integer temp=Integer.parseInt(MuseumsNumber.getText());
			temp++;
			MuseumsNumber.setText(temp.toString());
			break;
		}
		case "Restaurants":{
			Integer temp=Integer.parseInt(RestaurantNumber.getText());
			temp++;
			RestaurantNumber.setText(temp.toString());
			break;
		}
		case "Hotels":{
			Integer temp=Integer.parseInt(HotelsNumber.getText());
			temp++;
			HotelsNumber.setText(temp.toString());
			break;
		}
		case "Movie Theaters":{
			Integer temp=Integer.parseInt(MovieTheatersNumber.getText());
			temp++;
			MovieTheatersNumber.setText(temp.toString());
			break;
		}
		case "Public Sites":{
			Integer temp=Integer.parseInt(PublicSitesNumber.getText());
			temp++;
			PublicSitesNumber.setText(temp.toString());
			break;
		}
		case "Shops":{
			Integer temp=Integer.parseInt(ShopsNumber.getText());
			temp++;
			ShopsNumber.setText(temp.toString());
			break;
		}
		default:{	
			break;
		}
		}
	}
	private void FadeInTrans(Node node) {
		if(node.isVisible()==false)
			node.setVisible(true);
		FadeTransition fadein=new FadeTransition(Duration.seconds(0.8),node);
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
	private void SetSizeOfAnchor(double width,double height) {
		SitesAnc.setPrefSize(width, height);
		SitesAnc.setMaxSize(width, height);
		SitesAnc.setMinSize(width, height);
		TourAnc.setPrefSize(width, height);
		TourAnc.setMaxSize(width, height);
		TourAnc.setMinSize(width, height);
		SetCityPriceAnc.setPrefSize(width, height);
		SetCityPriceAnc.setMaxSize(width, height);
		SetCityPriceAnc.setMinSize(width, height);
	}
	public File GetIconOfSite(String Category) {
		File photo=null;
		switch (Category) {
		case "Parks Sites":{
			photo=new File("Icons/park.png");	
		}
		break;
		case "Historical Sites":{
			photo=new File("Icons/history.png");
			break;
		}
		case "Museums Sites":{
			photo=new File("Icons/gallery.png");
			break;
		}
		case "Restaurants":{
			photo=new File("Icons/food.png");
			break;
		}
		case "Hotels":{
			photo=new File("Icons/hotel.png");
			break;
		}
		case "Movie Theaters":{
			photo=new File("Icons/theater.png");
			break;
		}
		case "Public Sites":{
			photo=new File("Icons/buildings.png");
			break;
		}
		case "Shops":{
			photo=new File("Icons/store.png");
			break;
		}
		default:{

			break;
		}
		}
		return photo;
	}
	public void SetWatching(String txt) {
		Watching.setText(txt);
	}

	private void SwitchPanes() {//TourAnc,SitesAnc
		if(SitesAnc.isDisable()) {
			SitesAnc.setDisable(false);
			SiteOptions.setDisable(false);
			FadeOutTrans(TourAnc);
			TourAnc.setDisable(true);
			FadeInTrans(SiteOptions);
			FadeInTrans(SitesAnc);
		}
		else {
			SitesAnc.setDisable(true);
			SiteOptions.setDisable(true);
			FadeOutTrans(SitesAnc);
			FadeOutTrans(SiteOptions);
			TourAnc.setDisable(false);
			FadeInTrans(TourAnc);
		}
	}
	private void RemoveSitesFromAnc(AnchorPane sitesAnc) {
		sitesAnc.getChildren().clear();
	}
	private void SetLabelNumberToZero(Label lbl) {
		lbl.setText("0");
	}
	public void SetLabelNumberMinusOne(Label lbl){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<TourList.size();i++) {
					if(TourList.get(i).getTourname().equals(DeleteMe)) {
						TourList.remove(i);
					}
					if(((HBox) ListOfTours.getItems().get(i)).getId().equals(DeleteMe))
						ListOfTours.getItems().remove(i);
				}
				System.out.println(String.valueOf(Integer.parseInt(lbl.getText())-1));
				lbl.setText(String.valueOf(Integer.parseInt(lbl.getText())-1));
				if(Integer.parseInt(lbl.getText())==0) {
					ListOfTours.getItems().clear();
				}

			}
		});
	}
	private void SetOpcityToZero(Node[] arr) {
		try {
			for(int i=0;i<arr.length;i++)
				arr[i].setOpacity(0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	void GoToUserHomePage(ActionEvent event) {
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
	}

	@FXML
	void LogOut(ActionEvent event) {
		try {

			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
	public void initializeToursOnMap(Object msg) {
		ArrayList<Tour> rs;
		if(msg instanceof Message)
			rs=(ArrayList<Tour>)((Message)msg).getObject();
		else
			rs=(ArrayList<Tour>)msg;
		try {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					for(int i=0;i<rs.size();i++) {
						ImageView img;
						HBox hbox =new HBox(400);
						HBox Hbobtn=new HBox(60);
						hbox.setId(rs.get(i).getTourname());
						Label lbl =new Label("TourName:"+rs.get(i).getTourname());
						if(rs.get(i).getRecommanded().equals("YES") || rs.get(i).getRecommanded().equals("yes"))
							img=new ImageView(Recomand);
						else
							img=new ImageView(NotRecomand);
						SetImageSize(img,24,24);
						lbl.setGraphic(img);
						
						//EditBtn
						if(Permission2) {
						img=new ImageView(Edit);
						SetImageSize(img,24,24);
						JFXButton Edit=new JFXButton("Edit",img);
						Edit.setTextFill(Color.WHITE);
						SetButtonSize(Edit,110,30);
						Edit.setStyle("-fx-background-color: #53D8FE");
						Edit.addEventHandler(ActionEvent.ACTION, (e)->EditTour(e));
						Hbobtn.getChildren().add(Edit);}
						//EditBtn
						//DeleteBtn
						if(Permission1) {
						img=new ImageView(Delete);
						SetImageSize(img,24,24);
						JFXButton Delete=new JFXButton("Delete",img);
						Delete.setTextFill(Color.WHITE);
						SetButtonSize(Delete,110,30);
						Delete.setStyle("-fx-background-color: #53D8FE");
						Delete.addEventHandler(ActionEvent.ACTION, (e)->DeleteTour(e));
						Hbobtn.getChildren().add(Delete);
						}
						//DeleteBtn
						if(rs.get(i).getSites().size()!=0) {
							 ArrayList<String> SiteListNames=rs.get(i).getSites();	
								MenuButton SiteListInTour =new MenuButton("Sites In Tour");
								SiteListInTour.setPrefSize(110, 30);
								SiteListInTour.setStyle("-fx-background-color: #53D8FE");
								SiteListInTour.setTextFill(Color.WHITE);
								for(int j=0;j<SiteListNames.size();j++) {
									MenuItem site=new MenuItem();
									site.setText(SiteListNames.get(j));
									SiteListInTour.getItems().add(site);
								}
								Hbobtn.getChildren().add(SiteListInTour);
						 }
						hbox.getChildren().add(Hbobtn);
						hbox.getChildren().add(lbl);
						ListOfTours.getItems().add(hbox);
						 
					}
				}
				
			});
		}
		
		catch(Exception e) {
			System.out.println("Cant Upload Tours");
			e.printStackTrace();
		}
	}
	//Delete Tour
	private void DeleteTour(ActionEvent event) {
		HBox hbox=(HBox)(((JFXButton)event.getSource()).getParent());
		String query="Delete From gcm.tourinmap Where (gcm.tourinmap.TourName='"+hbox.getId()+"' AND gcm.tourinmap.ID='"+MapID+"');";
		DeleteMe=hbox.getId();
		String[] temp= {query,hbox.getId()};
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.DeleteTour,temp));
	}
	//Edit Tour
	private void EditTour(ActionEvent event) {
		HBox hbox=(HBox)(((JFXButton)event.getSource()).getParent());
		InfoOfEditTour=new Tour();
		InfoOfEditTour.setIDMAP(MapID);
		InfoOfEditTour.setCityename(CityName);
		InfoOfEditTour.setTourname(hbox.getId());
		System.out.println("Map ID: "+InfoOfEditTour.getIDMAP()+"\nCity Name: "+InfoOfEditTour.getCityename()+"\nTour Name:"+InfoOfEditTour.getTourname());
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/TourEditWindow.fxml");
	}
	//Add Tour
	@FXML
	void AddTour(ActionEvent event) {
		InfoOfnewTour=new Tour();
		InfoOfnewTour.setIDMAP(MapID);
		InfoOfnewTour.setCityename(CityName);
		System.out.println("City Name: "+InfoOfnewTour.getCityename()+"\nMap Id:"+InfoOfnewTour.getIDMAP());
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/TourAddWindow.fxml");
	}
	private void SetImageSize(ImageView img,double Width,double Height) {
		img.setFitHeight(Height);
		img.setFitWidth(Width);
	}
	private void SetButtonSize(JFXButton btn,double Width,double Height) {
		btn.setPrefWidth(Width);
		btn.setPrefHeight(Height);
	}
	public void SetSiteList(ArrayList<Site> List) {
		SiteList=List;
	}
	public String GetMapID() {
		return MapID;
	}
	public void SetTourList(ArrayList<Tour> List) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				TourList=List;//Set The Tour List
				TourNumber.setText(Integer.toString(TourList.size()));//Sets the Number of tours
			}
		});
	}

}
