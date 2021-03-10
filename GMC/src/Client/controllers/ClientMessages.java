package client.controllers;

import java.io.File;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import common.controllers.Message;
import common.controllers.ReturnMsgType;
import common.entities.City;
import common.entities.Site;
import common.entities.Tour;
import common.entities.User;
import common.entities.ZipVersion;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import server.mysqlConnection;

/*** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinte
 * The next class receives information from the server and send it to the client.
 *In each message, you can send to the client any object that needs to be continued -
 *you can send information back to the controller, use the POPUP method to display client information,
 *and transfer data from db to screens.
 * 
 * */


public class ClientMessages {


	public static String username;
	public static String role;
	public static User user;
	public static String ListOfPurchaseHistory;//

	/*** popUp -
	 * The next method receives 3 strings and 1 AlertType and make from them a popUp massege to the user
	 * The method display the massege on the screen.
	 * 
	 * */
	public static void popUp(AlertType type,String title,String head,String content)
	{

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Alert alert=new Alert(type);
				alert.setTitle(title);
				alert.setHeaderText(head);
				alert.setContentText(content); 
				alert.showAndWait();
			}
		});
	}
	/*** messageFromServer -
	 * The next method receives 1 Object and , divides all actions into cases according to Operationtype
	 * and use the Object to perform the operation and after send a massege to the client with the appropriate details 
	 * */
	public static void messageFromServer(Object msg) 
	{

		switch(((Message)msg).getOperationtype()) {
		case Login:
			switch(((Message)msg).getReturnmsg()) {
			case successfully:
				user=(User) ((Message)msg).getObject();
				username=user.getUserName();
				LogInController.changeWin("/client/boundry/UserHomePage.fxml");
				break;
			case userConnected:
				popUp(AlertType.ERROR, "User already connected!", "user is connected!","\""+username+"\" is already connected to the system!");
				break;
			case userNotExist:
				popUp(AlertType.ERROR, "Log in error", " The username : "+ " \"" + username+ "\" or the password are incorrect", "The username or the password incorrect");
				break;
			default:
				break;
			}
			break;

		case Logout:
			username=null;
			break;

		case SignUp:
			switch(((Message)msg).getReturnmsg()) {
			case successfully:
				PublicController.changeWin("/client/boundry/Login.fxml");
				popUp(AlertType.INFORMATION, username+" is sign now !", "THANK YOU !", username+" is sign now !");
				break;
			case usernameExist:
				popUp(AlertType.ERROR, "username Error", "user_name is already in use!", "Please try different user_name");
				break;
			default:
				break;
			}
			break;
		case getPriceApproveRequestsData:  
			try {
				CEO_ApprovePriceController.Instance.SetDataOnScreen(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;

		case getCostumerInfo:
			try {
				CEO_ViewCostumerController.Instance.SetDataOnScreen(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;
		case getEmployeeInfo:
			try {
				Content_EmployeeInfoController.Instance.SetDataOnScreen(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;
		case getCityList:
			try {
				CEO_ApprovePriceController.Instance.SetDataOnScreen(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;


		case getListOfManager:
			try {
				UserHomePageController.Instance.SetDataOnScreen_Manager(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;



		case UploadWhatsNew:
			try {
				UserHomePageController.Instance.SetDataOnScreen(msg);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("client exception");
			}
			break;

		case forgotPassword:
			switch (((Message)msg).getReturnmsg()) {
			case successfully:
				try {
					ForgotPasswordController.instance.hideWindow();
				}
				catch (Exception e) {
					System.out.println("Client Messages - forgot password exception");
					break;
				}

				ClientMessages.popUp(AlertType.INFORMATION,"Password","Your password was sending to your Email","please wait to your Email and come back. we are waiting for you ;)");
				break;
			case unsuccessfully:
				ClientMessages.popUp(AlertType.ERROR,"Error","USER NAME OR EMAIL ARE INCORRECT","please enter user name and email as they shown in your account");
				break;
			}
			break;			


		case getCityAndCountryList:
			try {
				if(CEO_ReportsController.Instance == null)
					Content_ShowReportsController.Instance.SetDataOnScreen_Content(msg);
				else
					CEO_ReportsController.Instance.SetDataOnScreen_Content(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;
		case getVersions:
			try {
				if(CEO_ApproveVersionController.Instance == null)
					Content_ApproveVersionController.Instance.SetDataOnScreen(msg);
				else
					CEO_ApproveVersionController.Instance.SetDataOnScreen(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;
		case Report_SingleCity:
			try {
				if(Content_ShowReportsController.Instance == null)
					CEO_ReportsController.Instance.SetReport_SingleCity_Content(msg);
				else
					Content_ShowReportsController.Instance.SetReport_SingleCity_Content(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;
		case Report_ChooseAll:
			try {
				if(Content_ShowReportsController.Instance == null)
					CEO_ReportsController.Instance.SetReport_ChooseAll_Content(msg);
				else
					Content_ShowReportsController.Instance.SetReport_ChooseAll_Content(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;
		case Report_ChooseAll_OneCountry:
			try {
				if(Content_ShowReportsController.Instance == null)
					CEO_ReportsController.Instance.SetReport_ChooseAll_OneCountry_Content(msg);
				else
					Content_ShowReportsController.Instance.SetReport_ChooseAll_OneCountry_Content(msg);
			} catch (SQLException e1) {e1.printStackTrace();}
			break;


			//by amit --> for get client details into client card --> when role=customer 
		case getClientCardDetails:
			ArrayList<String> r=(ArrayList<String>)((Message)msg).getObject();
			ClientCardController.Instance.setDetailsOnScreen(r,true);
			break;
			//by amit --> for get client details into client card --> when role=ceo or role=contentManeger
		case getClientCardDetailsWithoutPayment:
			ArrayList<String> r1=(ArrayList<String>)((Message)msg).getObject();
			ClientCardController.Instance.setDetailsOnScreen(r1,false);
			break;

		case getRoleClientCard:
			ArrayList<String> arr=(ArrayList<String>)((Message)msg).getObject();
			if(arr.get(0).equals("ceo") || arr.get(0).equals("contentManager") || arr.get(0).equals("employee"))
				ClientCardController.Instance.executeCardDetails(true);
			else
				ClientCardController.Instance.executeCardDetails(false);
			break;


			/*+++New Remez Upload Data to City Catalog++++*/  	  
		case UploadDataToCityCatalog:{
			switch (((Message)msg).getReturnmsg()) {
			case CitysInfo:{
				try {
					CityCatalogController.Instance.SetDataOnScreen(msg);
					break;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("client exception");
					break;
				}
			}
			case SearchInfo:{
				try {
					CityCatalogController.Instance.SetSearchList(msg);
					System.out.println("HEY");
					break;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("client exception");
					break;
				}
			}

			}
			break;
		}
		/*+++New Remez Upload Data to City Catalog++++*/ 

		/*New Remez Search!!!!!*/
		case SearchInCatalog:{
			if ((((Message)msg).getReturnmsg())==ReturnMsgType.successfully) {
				CityCatalogController.Instance.SearchResult=(ArrayList<City>)((Message)msg).getObject();
				Integer temp=CityCatalogController.Instance.PageNumber.intValue();
				try {
					CityCatalogController.Instance.PageNumber=0;
					CityCatalogController.Instance.SetDataOnScreen(msg);
				} catch (SQLException e) {
					System.out.println("client exception");
					CityCatalogController.Instance.PageNumber=temp.intValue();
					e.printStackTrace();
				}
				break;
			}
			else {
				popUp(AlertType.ERROR, "Search Faild", "No results found.","");
				break;
			}
		}
		/*New Remez Search!!!!!*/	


		//Itamar Edit
		case getTempSiteDetails:
			try {
				Content_ApproveVersionController.Instance.Check_UpdateSite(msg);
			} catch (Exception e1) {e1.printStackTrace();}
			break;
		case getTempTourDetails:
			try {
				Content_ApproveVersionController.Instance.Check_UpdateTour(msg);
			} catch (Exception e1) {e1.printStackTrace();}
			break;
		case CheckIfSiteExists:
			try {
				Content_ApproveVersionController.Instance.InsertOrUpdate_UpdateSite(msg);
			} catch (Exception e1) {e1.printStackTrace();}
			break;
		case CheckIfTourExists:
			try {
				Content_ApproveVersionController.Instance.InsertOrUpdate_UpdateTour(msg);
			} catch (Exception e1) {e1.printStackTrace();}
			break;
		case getCostumerHistoryInfo:
			try {
				CEO_ViewCostumerController.Instance.SetHistoryOnScreen(msg);
			} catch (Exception e1) {e1.printStackTrace();}
			break;
		case getNews:
			try {
				ManagerAreaController_Content.Instance.SetDataOnScreen(msg);
			} catch (Exception e1) {e1.printStackTrace();}
			break;

			//Itamar Edit - getRole
		case getRole: 
			try {
				if(UserHomePageController.Instance != null)
					UserHomePageController.Instance.SetDataOnScreen_ManagerOpen(msg);
				else if(MapViewController.Instance != null)
					MapViewController.Instance.setEditMapDetails(msg);
			} catch (SQLException e1) {e1.printStackTrace();}			
			break;
			//Itamar Edit
		case getNewMapDetails: 
			try {
				AddNewMapController.Instance.SetDataOnScreen(msg);
			} catch (Exception e1) {e1.printStackTrace();}		


			/* **********************Itamar END Edit****************************/

		case mapDownload:
			switch(((Message)msg).getReturnmsg()) {
			case Downloaded:
				popUp(AlertType.ERROR, "DownLoad", "Sorry , you Download this catalog once", "Thanks !");
				break;
			case successfully:
				try {
					((ZipVersion)((Message)msg).getObject()).createZip();
				} catch (IOException e2) {
					System.err.println("Client message - mapDownload");
				}
				popUp(AlertType.INFORMATION, "DownLoad", "All city maps are in your computer now !", "Thanks !");
				break;
			case unsuccessfully:
				popUp(AlertType.ERROR, "DownLoad", "Please buy our city catalog before !", "Thanks !");
				break;
			}
			break;

		case UploadversionsToComboDownload:
			DownloadVersionController.instance.insertToCombobox((ArrayList<String>)((Message)msg).getObject());

			break;



			/**editsite case  -receives from the server whether the  site edit/add query was received and returns the customer a suitable pop-up
			 *  * successfullyedit - if the tour edited successfully
			 *  successfullyadd -- if the tour added successfully
			 *  cityNameNotExist - if was problem in edit details
			 * */
		case editsite://dorin

		{Site site =(Site) ((Message)msg).getObject();
		switch(((Message)msg).getReturnmsg()) {
		case successfullyedit:
		{popUp(AlertType.INFORMATION, site.getSitename()+" ", "THANK YOU !", site.getSitename()+" Details saved send to  approve Version successfully!");
		SiteEditController.instance.sendtoapprove(true);
		break;}
		case successfullyadd:
		{popUp(AlertType.INFORMATION, site.getSitename()+" ", "THANK YOU !", site.getSitename()+" Details saved  and send to  approve Version successfully !");
		SiteAddController.instance.sendtoapprove(true);
		break;}
		case SiteNameNotExist:
		{popUp(AlertType.ERROR, "Data entry failet!" , "PAY ATTENTION !", site.getSitename()+ " Data entry failed -  Site name alredy exist in version to update!!");
		break;}
		}
		break;
		}


		/**edittour case  -receives from the server whether the tour edit/add query was received and returns the customer a suitable pop-up
		 *  * successfullyedit - if the tour edited successfully
		 *  successfullyadd -- if the tour added successfully
		 *  cityNameNotExist - if was problem in edit details
		 * */
		case edittour: 
		{Tour tour =(Tour) ((Message)msg).getObject();
		switch(((Message)msg).getReturnmsg())
		{

		case successfullyedit:	
		{popUp(AlertType.INFORMATION, tour.getTourname()+" ", "THANK YOU !", tour.getTourname()+" Details saved  and send to  approve Version successfully !");
		TourEditController.instance.sendtoapprove(true);
		break;}
		case successfullyadd:
		{popUp(AlertType.INFORMATION, tour.getTourname()+" ", "THANK YOU !", tour.getTourname()+" Details saved  and send to  approve Version successfully !");
		TourAddController.instance.sendtoapprove(true);
		break;}
		case cityNameNotExist:
		{popUp(AlertType.ERROR, "Data entry failet!" , "PAY ATTENTION !", tour.getTourname()+ "  tour  name  alredy exist in version to update!");
		break;}
		default:
			break;}
		}


		/**Check If Exist case  -receives from the server whether the Check If Exist query was received and returns the customer a suitable pop-up
		 * exsit- if the site/tour name alredy exist - and cant continue to the next query!
		 *
		 * */
		case CheckIfExist:
			switch(((Message)msg).getReturnmsg()) {
			case exsit:
			{ 
				popUp(AlertType.ERROR, "Name already in use!", "Chang name!","name is already in use!");
				break;
			}
			default:
				break;
			}
			break;	

			/**get tour data case  -receives from the server the  ArrayList  of tour table and send it to the edit  controller.
			 *
			 * */
		case gettourdata:
		{ arr  =(ArrayList<String>)((Message)msg).getObject();
		TourEditController.instance.uploaddata(arr);
		break;	}

		/**get site data case  -receives from the server the ArrayList of site table  and send it to the  edit controller.
		 * */
		case getsitedata:
		{ arr  =(ArrayList<String>)((Message)msg).getObject();
		SiteEditController.instance.uploaddata(arr);
		break;	}
		/**get site add  data case  -receives from the server the ArrayList of site table  and send it to the add controller.
		 * */
		case getsiteforadd:
		{ arr  =(ArrayList<String>)((Message)msg).getObject();
		SiteAddController.instance.uploaddata(arr);
		break;	}
		/**get tour add data case  -receives from the server the  ArrayList  of tour table and send it to the add controller.
		 *
		 * */
		case gettourforadd:
		{ arr  =(ArrayList<String>)((Message)msg).getObject();
		TourAddController.instance.uploaddata(arr);
		break;	}

		/**get list of site in tour  -receives from the server the  ArrayList  of  site in tour table and send it to the edit controller.
		 *
		 * */
		case GetListExistSiteData:// go to TourEditController
		{ arr  =(ArrayList<String>)((Message)msg).getObject();
		TourEditController.instance.UploadListData(arr);}
		break;	
		/**get list of site in tour  -receives from the server the  ArrayList  of  site in tour table and send it to the add controller.
		 *
		 * */
		case GetListExistSiteDataADD://go to TourAddController
		{ arr  =(ArrayList<String>)((Message)msg).getObject();
		TourAddController.instance.UploadListData(arr);}
		break;	




		case getPurchaseHistoryList:
			/*
						PurchaseHistoryController.Instance.SetDataOnScreen(msg);
						break;*/
			try {
				PurchaseHistoryController.Instance.SetDataOnScreen(msg);}
			catch (Exception e1) {e1.printStackTrace();}
			break;
		case getPriceOfMap:
			ArrayList<String> r112=(ArrayList<String>)((Message)msg).getObject();
			SubscriberPurchaseController.Instance.PriceOfMap(r112);
			break;


		case getDetailOFpurchase:
			ArrayList<String> r12=(ArrayList<String>)((Message)msg).getObject();
			SubscriberPurchaseController.Instance.purchaseDetails(r12);

			break;

		case UserPurchase:
			popUp(AlertType.INFORMATION, "sucsess", "THANK YOU !","The purchase process has passed successfully");
			break;



		case UploadSitesToMap:{
			try{
				MapInfoController.Instance.initializeSitesOnMap(msg);
				break;
			}
			catch(Exception e) {System.out.println("Cant upload sites to map");e.printStackTrace();}
		}
		case UploadSitesAndToursToMap:{
			try{
				ArrayList<Object> info=(ArrayList<Object>)((Message)msg).getObject();
				MapInfoController.Instance.SetMapImageAndCityImage((File)info.get(0),(File)info.get(1));
				ArrayList<Tour> tours=(ArrayList<Tour>)info.get(4);
				MapInfoController.Instance.SetTourList(tours);//TourList
				ArrayList<Site> sites=(ArrayList<Site>)info.get(3);
				MapInfoController.Instance.CurrentPriceField.setText((String)info.get(2));
				MapInfoController.Instance.SetTourList(tours);
				MapInfoController.Instance.SetSiteList(sites);
				MapInfoController.Instance.initializeSitesOnMap(sites);
				break;
			}
			catch(Exception e) {System.out.println("Cant upload sites to map");e.printStackTrace();}
		}
		/*New Remez Upload Sites!!!!!*/	

		/*New Remez Upload Tours!!!!!*/	
		case UploadToursToMap:{
			try{
				MapInfoController.Instance.initializeToursOnMap(msg);
				break;
			}
			catch(Exception e) {System.out.println("Cant upload tours to map");e.printStackTrace();}
		}
		case DeleteTour:{
			Object[] temp=(Object[])(((Message)msg).getObject());
			if(((Boolean)temp[0]).booleanValue()) {
				popUp(AlertType.CONFIRMATION, "Delete Succeeded",((String)temp[1])+" Deleted From DB","");
				MapInfoController.Instance.SetLabelNumberMinusOne(MapInfoController.Instance.TourNumber);
			}
			else
				popUp(AlertType.ERROR, "Delete Faild",((String)temp[1])+" Not Deleted From DB","");
			break;
		}
		/*New Remez Upload Tours!!!!!*/	
		case DeleteSiteFromMap:{
			if ((((Message)msg).getReturnmsg())==ReturnMsgType.successfully) {
				popUp(AlertType.CONFIRMATION, "Delete Succeeded",((String)((Message)msg).getObject())+"Deleted From DB","");
				MapInfoController.Instance.DeleteSiteFromMap((String)((Message)msg).getObject());
			}
			else {
				popUp(AlertType.ERROR, "Delete Faild",((String)((Message)msg).getObject())+" Not Deleted From DB","");
			}
			break;
		}
		case SetNewPriceRequestForMap:{
			if ((((Message)msg).getReturnmsg())==ReturnMsgType.successfully) {
				popUp(AlertType.CONFIRMATION, "Price Updated","Price Updated successfully","");
			}
			else
			{
				popUp(AlertType.ERROR, "Price Updated Fail","Faild to update price!","");
			}
			break;
		}


		case getMapDetails:
			try {
				MapViewController.Instance.setDataOnTable(msg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.err.println("Client Messages - get map details");
				e.printStackTrace();
			}
			break;
		case getMapsitesDetails:
			MapViewController.Instance.setDataFromMap(msg);
			break;


		case ifSubscribe:
			MapViewController.Instance.setEditMapDetails(msg);
			break;


		default:
			break;

		}
	}
}