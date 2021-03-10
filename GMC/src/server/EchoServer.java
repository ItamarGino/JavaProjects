package server;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.spi.DateFormatProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.text.DateFormatter;

import com.itextpdf.text.Document;
import com.sun.mail.handlers.message_rfc822;
import com.sun.org.apache.bcel.internal.generic.NEW;

import client.controllers.ClientMessages;
import client.controllers.ClientTools;
import client.controllers.UserHomePageController;
import common.controllers.*;
import common.entities.City;
import common.entities.PurchaseHistory;
import common.entities.Site;
import common.entities.Tour;
import common.entities.User;
import common.entities.VersionDetails;
import common.entities.ZipVersion;
import common.ocsf.server.*;
import javafx.scene.control.Alert.AlertType;
import server.controllers.CreatePDFController;
import server.controllers.SendMailController;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * @version June 2019
 */

public class EchoServer extends AbstractServer 
{
	public static mysqlConnection mysql=new mysqlConnection();
	public static ArrayList<String> connectedUsers=new ArrayList<>();

	//Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) 
	{
		super(port);
	}


	/*  public synchronized boolean chekconnection(String user, ConnectionToClient client) {	  
	  try {
		  if(!userConnected.containsKey(user)) {
			  userConnected.put(user, client);
			  return false;
		  }

			  ConnectionToClient userC = userConnected.get(user);

			  if ((userC == null) || (!userC.isAlive())) {
				  userConnected.replace(user, client);
				  return true;
			  }
	  }catch(Exception ex) {
		  return false;
	  }
	  return false;
  }*/


	//Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient (Object msg, ConnectionToClient client)
	{
		ArrayList<String> arrString;
		try {
			switch(((Message)msg).getOperationtype()) {
			case Login:
				try {
					ResultSet rs=mysql.getQuery(((Message)msg).getObject().toString());
					if(rs.next()) {

						if(connectedUsers.contains(rs.getString("userName"))) {
							sendToClient(new Message(OperationType.Login,ReturnMsgType.userConnected,rs.getString(1)),client); }
						else {
							connectedUsers.add(rs.getString("userName"));
							ClientMessages.role=rs.getString("Role");
							System.out.println("loged in!");
							sendToClient(new Message(OperationType.Login,ReturnMsgType.successfully,new User(rs)),client);
							System.out.println(connectedUsers);
							rs.close();
						}
					}
					else {
						String[] str=((Message)msg).getObject().toString().split("'");
						sendToClient(new Message(OperationType.Login,ReturnMsgType.userNotExist,str[1]),client); 
						System.out.println("Not exists");
						break;
					}
					break;
				} catch (SQLException e) {e.printStackTrace();}
				break;
			case Logout:
				System.out.println("logout to "+((Message)msg).getObject().toString());
				int index=connectedUsers.indexOf(((Message)msg).getObject().toString());

				connectedUsers.remove(index);
				sendToClient(new Message(OperationType.Logout, null, null), client);
				break;
			case SignUp:
				if(!mysql.insertOrUpdate(((Message)msg).getObject().toString()))
					sendToClient(new Message(OperationType.SignUp, ReturnMsgType.usernameExist, "Username is already exists"), client);
				else
					sendToClient(new Message(OperationType.SignUp, ReturnMsgType.successfully, null), client);
				break;
			case getPriceApproveRequestsData:			
				ResultSet requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 5);
				sendToClient(new Message(OperationType.getPriceApproveRequestsData,ReturnMsgType.getPriceApproveRequestsData,arrString),client); 
				requestData.close();
				break;
			case getListOfManager:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 1);
				requestData.close();
				sendToClient(new Message(OperationType.getListOfManager,ReturnMsgType.getListOfManager,arrString),client); 
				break;
			case getRole:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 1);
				sendToClient(new Message(OperationType.getRole,ReturnMsgType.getRole,arrString),client); 
				requestData.close();
				break;
			case getCostumerInfo:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 5);
				sendToClient(new Message(OperationType.getCostumerInfo,ReturnMsgType.getCostumerInfo,arrString),client); 
				requestData.close();
				break;
			case getEmployeeInfo:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 5);
				sendToClient(new Message(OperationType.getEmployeeInfo,ReturnMsgType.getEmployeeInfo,arrString),client); 
				requestData.close();
				break;
			case getCityList:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 1);
				sendToClient(new Message(OperationType.getCityList,ReturnMsgType.getCityList,arrString),client); 
				requestData.close();
				break;
			case forgotPassword:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				String email=null;
				String password;
				String firstname;
				try {
					requestData.next();
					email=requestData.getString(1);
					password=requestData.getString(2);
					firstname=requestData.getString(3);
					System.out.println("Send password to : Email = "+email+" Password = "+password);
				}
				catch(Exception e) {
					sendToClient(new Message(OperationType.forgotPassword, ReturnMsgType.unsuccessfully,null), client);
					break;
				}
				String mailmessage="Hi "+firstname+" ,\nYour password is : "+password + " \n\nWe are witing for you ! \n\nG_10 Team.";
				SendMailController.sendEmail(email,"Hi "+firstname+" Your Password is here !",mailmessage);
				sendToClient(new Message(OperationType.forgotPassword, ReturnMsgType.successfully,email), client);

				break;

			case getCityAndCountryList:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 2);
				sendToClient(new Message(OperationType.getCityAndCountryList,null,arrString),client); 
				break;
			case getVersions:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 5);
				sendToClient(new Message(OperationType.getVersions,null,arrString),client); 
				break;
			case Report_SingleCity:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 9);
				sendToClient(new Message(OperationType.Report_SingleCity,null,arrString),client); 
				break;
			case Report_ChooseAll:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 8);
				sendToClient(new Message(OperationType.Report_ChooseAll,null,arrString),client); 
				break;
			case Report_ChooseAll_OneCountry:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 7);
				sendToClient(new Message(OperationType.Report_ChooseAll_OneCountry,null,arrString),client); 
				break;

				//by amit --> for upload client details into client card --> when role="customer"
			case getClientCardDetails:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> arr=ClientTools.disassemble(requestData, 11);				
				requestData.close();
				sendToClient(new Message(OperationType.getClientCardDetails,arr), client);
				break;
				//by amit --> for upload client details into client card without payment information --> when role="ceo" or role="contentManeger"
			case getClientCardDetailsWithoutPayment:

				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arr=ClientTools.disassemble(requestData, 6);
				requestData.close();
				sendToClient(new Message(OperationType.getClientCardDetailsWithoutPayment,arr), client);
				break;
				//by amit --> for change client details into client card --> when role=customer and insert into user table
			case changeClientCardDetails_User:
				mysql.insertOrUpdate(((Message)msg).getObject().toString());
				break;
				//by amit --> for change client details into client card --> when role=customer and insert into payment table
			case changeClientCardDetails_Payment:
				mysql.insertOrUpdate(((Message)msg).getObject().toString());
				break;
				//by amit --> while registration  --> when role=customer and insert into payment table
			case SignUpPayment:
				mysql.insertOrUpdate(((Message)msg).getObject().toString()); 
				break;
			case getRoleClientCard:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 1);
				sendToClient(new Message(OperationType.getRoleClientCard,null,arrString),client); 
				requestData.close();
				break;

				/*+++New Remez Upload City Name And Price+++*/



			case UploadWhatsNew:{
				ResultSet rs=mysql.getQuery(((Message)msg).getObject().toString());
				//System.out.println("send to client");
				ArrayList<City> r=new ArrayList<>();
				while(rs.next()) {
					r.add(new City(rs));// arraylist of citys
				} 
				rs.close();
				sendToClient(new Message(OperationType.UploadWhatsNew,r), client);
				break;
			}
			/*+++New Remez Upload City Name And Price++++*/  


			/* *****************Shahar EDIT *********************/


			case mapDownload: // Shahar Update
				PurchaseHistory purchase;
				Double version;
				String s=null;
				ResultSet rs=mysql.getQuery(((Message)msg).getObject().toString());
				System.out.println(((Message)msg).getObject().toString());
				if(rs.next()) {
					purchase=new PurchaseHistory(rs);
					version=rs.getDouble("Version");
				}

				else {
					sendToClient(new Message(OperationType.mapDownload, ReturnMsgType.unsuccessfully, null), client);
					break;
				}


				if(purchase.getDownload().equals("no")) {
					requestData=mysql.getQuery("SELECT * FROM city_versions WHERE Version="+rs.getDouble("Version")+" AND CityName='"+rs.getString("CityName")+"';");

					try {
						requestData.next();
					}
					catch(Exception e) { System.err.println("mapDownload-requstData Exception!");}

					if(purchase.getPurchaseType().equals("One-time"))
						mysql.insertOrUpdate("UPDATE `gcm`.`purchasehistory` SET `Download` = 'yes' WHERE (`Version` = "+version+") and (`userName` = '"+purchase.getUsername()+"') and (`CityName` = '"+purchase.getCityName()+"');");
					sendToClient(new Message(OperationType.mapDownload, ReturnMsgType.successfully, new ZipVersion(requestData)), client);
					break;
				}
				else {
					sendToClient(new Message(OperationType.mapDownload, ReturnMsgType.Downloaded, null), client);
					break;
				}



			case UploadNewVersion:
				System.err.println("Upload Version Shahar");
				VersionDetails v=(VersionDetails) (((Message)msg).getObject());
				rs=mysql.getQuery("SELECT ID FROM map WHERE CityName='"+v.getCityName()+"';");

				File f = new File(System.getProperty("user.home")+"\\Desktop\\Version.zip");
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
				int i=1;

				while(rs.next()) {
					System.err.println(rs.getString(1));
					requestData=mysql.getQuery("SELECT DISTINCT site.Accessibility,map.CityName,site.Description,site.SiteName,map.ID FROM site,map,siteinmap WHERE siteinmap.IDMAP ='"+rs.getString(1)+"' AND site.SiteName = siteinmap.SiteName  AND map.CityName=site.cityname AND map.ID ='"+rs.getString(1)+"';");
					ResultSet result=mysql.getQuery("SELECT MapImage FROM gcm.map WHERE ID='"+rs.getString("ID")+"';");
					result.next();
					InputStream is =result.getBinaryStream("MapImage");
					File photo =new File(System.getProperty("user.home")+"\\Desktop\\111.jpg");
					OutputStream os = new FileOutputStream(photo);
					byte [] content =new byte[1024];
					int size=0;
					while((size=is.read(content))!=-1) {
						os.write(content, 0,size);	
					}
					String CityImageUrl=(photo.toURI().toString());
					os.close();
					is.close();


					ZipEntry e=new ZipEntry((i++)+".pdf");
					File file=null;
					if(requestData.next()) {
						requestData.previous();
						file=new File(CreatePDFController.createMapPDF(requestData,true));
					}
					else {
						String query="SELECT DISTINCT map.CityName,map.ID FROM map WHERE map.ID ='"+rs.getString(1)+"';";
						file=new File(CreatePDFController.createMapPDF(mysql.getQuery(query), false));
					}
					photo.delete();
					System.err.println(file.toString());
					out.putNextEntry(e);
					Files.copy(file.toPath(), out);
					file.delete();
					out.closeEntry();
				}
				out.close();

				String filePath = System.getProperty("user.home")+"\\Desktop\\Version.zip";
				InputStream inputStream = new FileInputStream(new File(filePath));
				String sql = "INSERT INTO city_versions values (?,?,?,?)";
				mysql.in(sql,(VersionDetails)((Message)msg).getObject(), inputStream);
				inputStream.close();
				f.delete();
				
				mysql.insertOrUpdate("UPDATE `gcm`.`maps_catalog` SET `Version No.` = format(`Version No.`+0.1,1) WHERE (`CityName` = '"+((VersionDetails)((Message)msg).getObject()).getCityName()+"') and (`CountryName` = '"+((VersionDetails)((Message)msg).getObject()).getCountryName()+"');");

				
				break;

			case SendNewVersionMessage:


				/*				DateTimeFormatter dateformat=DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate date=LocalDate.now();
				v=(VersionDetails) ((Message)msg).getObject();
				rs=mysql.getQuery("SELECT user.userName ,user.FirstName,user.Email,user.PhoneNumber,purchasehistory.PurchaseDateExpiration FROM gcm.user,gcm.purchasehistory WHERE user.userName=purchasehistory.userName AND PurchaseType='Period' AND purchasehistory.CityName='"+v.getCityName()+"';");
				while(rs.next()) {
					if(date.isBefore(LocalDate.parse(rs.getString("PurchaseDateExpiration"),dateformat))) {
						String message="Hi "+rs.getString("FirstName")+" ! \nA new version of "+v.getCityName()+" released and now the version is "+v.getVersionNumber()+" ! \nPlease come and download to be update at all news! \n\n\nThank you, \nG<10> Team ;)";
						System.out.println("********************************************************************************************************\n");
						System.out.println("Sending S.M.S to "+rs.getString("userName")+" phoneNumber : "+rs.getString("PhoneNumber")+ " \n"+message);
						System.out.println("********************************************************************************************************\n");
						SendMailController.sendEmail(rs.getString("Email"), "New Version Released !", message);
					}
				}
				break;*/

				v=(VersionDetails) ((Message)msg).getObject();
				rs=mysql.getQuery("SELECT user.userName ,user.FirstName,user.Email,user.PhoneNumber,purchasehistory.PurchaseDateExpiration FROM gcm.user,gcm.purchasehistory WHERE user.userName=purchasehistory.userName AND purchasehistory.CityName='"+v.getCityName()+"';");
				while(rs.next()) {
					String message="Hi "+rs.getString("FirstName")+" ! \nA new version of "+v.getCityName()+" released and now the version is "+v.getVersionNumber()+" ! \nPlease come and download to be update at all news! \n\n\nThank you, \nG<10> Team ;)";
					System.out.println("********************************************************************************************************\n");
					System.out.println("Sending S.M.S to "+rs.getString("userName")+" phoneNumber : "+rs.getString("PhoneNumber")+ " \n"+message);
					System.out.println("********************************************************************************************************\n");
					//SendMailController.sendEmail(rs.getString("Email"), "New Version Released !", message);
				}

				break;

			case UploadversionsToComboDownload:
				rs=mysql.getQuery(((Message)msg).getObject().toString());
				sendToClient(new Message(OperationType.UploadversionsToComboDownload, ClientTools.disassemble(rs, 1)), client);
				break;
				/* *****************Shahar END EDIT *********************/

				/* ***** Remez Update !! ******/
				
			case CountUpdate:
				mysql.insertOrUpdate(((Message)msg).getObject().toString());
				break;

			case UploadDataToCityCatalog:{
				rs=mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<City> r=new ArrayList<>();
				while(rs.next()) {
					r.add(new City(rs));// arraylist of citys 
				}
				rs.close();
				sendToClient(new Message(OperationType.UploadDataToCityCatalog, ReturnMsgType.CitysInfo,r), client);
				break;
			} 
			/*+++New Remez Upload Data to City Catalog++++*/
			case SearchInCatalog:{
				rs=mysql.getQuery(((Message)msg).getObject().toString());
				int SearchResultCount=0;
				ArrayList<City> r=new ArrayList<>();
				while(rs.next()) {
					r.add(new City(rs));// arraylist of citys 
					SearchResultCount++;
				}
				if(SearchResultCount==0 ) {
					sendToClient(new Message(OperationType.SearchInCatalog, ReturnMsgType.unsuccessfully,null), client);
					break;
				}
				rs.close();
				sendToClient(new Message(OperationType.SearchInCatalog, ReturnMsgType.successfully,r), client);
				break;
			}


			//ItamarEdit:                **********************************************************************************************************
			//ItamarEdit:                **********************************************************************************************************
		case getTempSiteDetails:
			requestData = mysql.getQuery(((Message)msg).getObject().toString());
			arrString = ClientTools.disassemble(requestData, 10);
			sendToClient(new Message(OperationType.getTempSiteDetails,arrString),client); 
			break;
		case getTempTourDetails:
			requestData = mysql.getQuery(((Message)msg).getObject().toString());
			arrString = ClientTools.disassemble(requestData, 7);
			sendToClient(new Message(OperationType.getTempTourDetails,arrString),client); 
			break;
		case CheckIfSiteExists:
			requestData = mysql.getQuery(((Message)msg).getObject().toString());
			arrString = ClientTools.disassemble(requestData, 1);
			sendToClient(new Message(OperationType.CheckIfSiteExists,arrString),client); 
			break;
		case CheckIfTourExists:
			requestData = mysql.getQuery(((Message)msg).getObject().toString());
			arrString = ClientTools.disassemble(requestData, 1);
			sendToClient(new Message(OperationType.CheckIfTourExists,arrString),client); 
			break;
		case InsertSite:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case InsertTour:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case InsertToSiteInMap:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case InsertToTourInMap:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case UpdateSite:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case UpdateTour:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case removeVersionRow:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case removeTempRow:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case UpdatePriceForMap:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
		case getCostumerHistoryInfo:
			requestData = mysql.getQuery(((Message)msg).getObject().toString());
			arrString = ClientTools.disassemble(requestData, 6);
			sendToClient(new Message(OperationType.getCostumerHistoryInfo,arrString),client); 
			break;
		case getNews:
			requestData = mysql.getQuery(((Message)msg).getObject().toString());
			arrString = ClientTools.disassemble(requestData, 4);
			sendToClient(new Message(OperationType.getNews,arrString),client); 
			break;


			//Itamar Edit::          *********************************************************

		case getEmployeeID:
			String[] arr1 = ((Message)msg).getObject().toString().split(";");
			// arr = username ; CountryName ; CityName ; CityName ; price (size = 6)
			String query = "SELECT id FROM employee WHERE userName = '"+ arr1[0]+"';";
			requestData = mysql.getQuery(query);
			ArrayList<String> EID = ClientTools.disassemble(requestData, 1);
			// Checking version table first:
			query = "SELECT VersionNumber FROM versions WHERE CountryName='"+ arr1[1] +"' AND CityName='"+arr1[2]+"';";

			requestData = mysql.getQuery(query);
			int vnumber;
			if(requestData != null)
			{
				if(requestData.next())
				{
					query = "INSERT INTO versions VALUE ('"+arr1[1]+"','"+ arr1[2] +"',"+(requestData.getInt(1)-1)+",-1,'"+ EID.get(0) +"'); ";
					vnumber = (requestData.getInt(1)-1);
				}
				else
				{
					query = "INSERT INTO versions VALUE ('"+arr1[1]+"','"+ arr1[2] +"',-1,-1,'"+ EID.get(0) +"'); ";
					vnumber = -1;
				}
			}
			else
			{
				query = "INSERT INTO versions VALUE ('"+arr1[1]+"','"+arr1[2]+"',-1,-1,'"+EID.get(0)+"'); ";
				vnumber = -1;
			}
			mysql.insertOrUpdate(query);
			query = "DELETE FROM gcm.newmap WHERE CountryName='"+ arr1[1] +"' AND CityName='"+arr1[2]+"' AND Discription='"+arr1[3]+"';";
			mysql.insertOrUpdate(query);
			query = "INSERT INTO newmapwithprice VALUE ('"+arr1[1]+"','"+arr1[2]+"','"+arr1[3]+"',null,'"+arr1[4]+"','"+vnumber+"');";
			mysql.insertOrUpdate(query);

			query = "UPDATE gcm.newmapwithprice SET Image = (SELECT Image FROM gcm.newmap WHERE CountryName='"+arr1[1]+"' AND CityName='"+arr1[2]+"' AND Discription='"+arr1[3]+"')" + 
					" WHERE (CountryName = '"+arr1[1]+"') and (CityName = '"+arr1[2]+"') and (Discription = '"+arr1[3]+"');";
			mysql.insertOrUpdate(query);
		case getNewMapDetails:
			requestData = mysql.getQuery("SELECT CountryName, CityName, Discription, Image FROM gcm.newmap;");
			ArrayList <common.entities.NewMapDetails> dataToSend = new ArrayList <common.entities.NewMapDetails>();
			try {
				while(requestData.next())
				{
					String stringBlob = requestData.getBlob(4).toString().replaceAll("com.mysql.cj.jdbc.Blob","");
					// Attention ! must run from 1 to 4 (Not from zero)
					dataToSend.add(new common.entities.NewMapDetails(requestData.getString(1),
							requestData.getString(2),requestData.getString(3),stringBlob));
				}
				requestData.close();
			} catch (SQLException e){e.printStackTrace();break;}
			sendToClient(new Message(OperationType.getNewMapDetails,dataToSend),client); 
			break;


			/* ***************************************************************/

		case getNewMapWithPriceDetails:
			String[] rowVal = ((Message)msg).getObject().toString().split(";");
			// rowVal = CountryName ; CityName ; VersionNumber
			if(rowVal != null)
			{
				query = "SELECT CountryName, CityName, Discription, Image, Price, VersionNumber"
						+ " FROM newmapwithprice WHERE CountryName='"+rowVal[0]+"' "
						+ " AND CityName='"+rowVal[1]+"' AND VersionNumber='"+rowVal[2]+"';";
				requestData = mysql.getQuery(query);
				
				query = "DELETE FROM gcm.newmapwithprice WHERE CountryName='"+rowVal[0]+"' "
						+ " AND CityName='"+rowVal[1]+"' AND VersionNumber='"+rowVal[2]+"';";
				mysql.insertOrUpdate(query);

				ArrayList <common.entities.NewMapWithPriceDetails> dataToSend1 = new ArrayList <common.entities.NewMapWithPriceDetails>();
				try {
					while(requestData.next())
					{
						String stringBlob = requestData.getBlob(5).toString().replaceAll("com.mysql.cj.jdbc.Blob", "");
						// Attention ! must run from 1 to 5 (Not from zero)
						dataToSend1.add(new common.entities.NewMapWithPriceDetails(requestData.getString(1),
								requestData.getString(2),requestData.getString(3),stringBlob,requestData.getString(5)));	
					}
				} catch (Exception e){e.printStackTrace();}
				if(dataToSend1.isEmpty() == false)
				{
					query = "SELECT CountryName,MapQuantity FROM maps_catalog WHERE CityName = '"+dataToSend1.get(0).getCityName()+"';";
					requestData = mysql.getQuery(query);
					if(requestData.next())
					{
						query = "INSERT INTO gcm.map"
								+" VALUES (0,'"+dataToSend1.get(0).getCountryName()+"', '"+dataToSend1.get(0).getCityName()+"',"
								+ " '"+dataToSend1.get(0).getDiscription()+"', '"+dataToSend1.get(0).getPrice()+"',"
								+ " '0',0);";
						mysql.insertOrUpdate(query);

						requestData = mysql.getQuery("SELECT Discription FROM"
								+ " newmapwithprice WHERE CountryName='"+rowVal[0]+"' AND CityName='"+rowVal[1]+"' AND VersionNumber='"+rowVal[2]+"'");
						arrString = ClientTools.disassemble(requestData, 1);

						query = "UPDATE gcm.map SET MapImage = (SELECT Image FROM gcm.newmap WHERE CountryName='"+rowVal[0]+"' AND CityName='"+rowVal[1]+"' AND Discription='"+rowVal[2]+"')" + 
							" WHERE (CountryName = '"+rowVal[0]+"') and (CityName = '"+rowVal[1]+"') and (Description = '"+arrString.get(0)+"');";
						mysql.insertOrUpdate(query);
						////UPDATEEEEEEEEEEEEEEEEEEEEEEEE *2
					}
					else
					{
						query = "INSERT INTO gcm.maps_catalog"
								+" VALUES ('"+dataToSend1.get(0).getCountryName()+"', '"+dataToSend1.get(0).getCityName()+"',"
								+ " '"+dataToSend1.get(0).getPrice()+"','"+"0"+"','"+"0"+"',0,1.0,0,"
								+ "'"+"0"+"',0,0,0,0,'"+"0"+"');";
						mysql.insertOrUpdate(query);
						query = "UPDATE gcm.maps_catalog SET CitySymbol = (SELECT defaultCitySign FROM gcm.default)" + 
								" WHERE (CountryName = '"+rowVal[0]+"') and (CityName = '"+rowVal[1]+"');";
						mysql.insertOrUpdate(query);

						query = "INSERT INTO gcm.map"
								+" VALUES (0,'"+dataToSend1.get(0).getCountryName()+"', '"+dataToSend1.get(0).getCityName()+"',"
								+ " '"+dataToSend1.get(0).getDiscription()+"', '"+dataToSend1.get(0).getPrice()+"',"
								+ " '0',0);";
						mysql.insertOrUpdate(query);

						requestData = mysql.getQuery("SELECT Discription FROM"
								+ " newmapwithprice WHERE CountryName='"+rowVal[0]+"' AND CityName='"+rowVal[1]+"' AND VersionNumber='"+rowVal[2]+"'");
						arrString = ClientTools.disassemble(requestData, 1);

						query = "UPDATE gcm.map SET MapImage = (SELECT Image FROM gcm.newmap WHERE CountryName='"+rowVal[0]+"' AND CityName='"+rowVal[1]+"' AND Discription='"+rowVal[2]+"')" + 
								" WHERE (CountryName = '"+rowVal[0]+"') and (CityName = '"+rowVal[1]+"') and (Description = '"+arrString.get(0)+"');";
						mysql.insertOrUpdate(query);
					}
				}
				requestData.close();
			}
			break;
			
		case UpdateSiteInTour:
			mysql.insertOrUpdate(((Message)msg).getObject().toString());
			break;
			/* ******************************************************************/

				//ItamarEdit - END       **********************************************************************************************************


				///////////////////////////////////////////////////dorin//////////////////////////////////////////////////////////////////////////
				/** add site case  -receives from the entity the site name and check first if this name already use in the DB -by use mysqlserver  and return massage to client
				 * if the name not in use its continue to the next query of edit site and sent massage to client 
				 * successfully - if the site edited successfully
				 *  cityNameNotExist - if was problem in edit details
				 * */

			case ADDSITE:
				try {Site site =(Site) ((Message)msg).getObject();
				rs=mysql.getQuery("SELECT SiteName FROM gcm.site WHERE SiteName='"+site.getSitename()+"';");
				if(rs.next()) 
				{System.out.println("SITE NAME ALREDY EXIST ");
				sendToClient(new Message(OperationType.CheckIfExist,ReturnMsgType.exsit,site),client);}

				else {   

					if(!mysql.insertOrUpdate("INSERT INTO `gcm`.`temp_site` (`CountryName`, `CitynameTemp`, `SiteName`, `Location_x`, `Location_y`, `Category`, `Description`, `Accessibility`, `MapId`, `RecommendedVisitTime`) VALUES ('"+site.getCountryname()+"', '"+site.getCityename()+"', '"+site.getSitename()+"', '"+site.getLocation_X()+"', '"+site.getLocation_y()+"', ' "+site.getCatagory()+"', '"+site.getDescription()+"','"+site.isAccessibility()+"','"+site.getIDMAP()+"','"+site.getRecommendedVisitTime()+"');"))
						sendToClient(new Message(OperationType.editsite,ReturnMsgType.cityNameNotExist,site), client); 	
					else
						sendToClient(new Message(OperationType.editsite,ReturnMsgType.successfullyadd,site), client);}

				break;
				} catch (SQLException e) 
				{System.out.println("ADDSITE exception !");
				e.printStackTrace();}
				break;


				/** add tour case  -receives from the entity the tore name and check first if this name already use in the DB -by use mysqlserver  and return massage to client
				 * if the  tour name not in use its continue to the next query of edit tour and sent massage to client 
				 * successfully - if the tour edited successfully
				 *  cityNameNotExist - if was problem in edit tour  details * */
			case ADDTOUR:
				try {Tour tour =(Tour) ((Message)msg).getObject();
				rs=mysql.getQuery("SELECT TourName FROM gcm.tour WHERE TourName='"+tour.getTourname()+"';");
				if(rs.next()) 

				{System.out.println("TOUR NAME ALREDY EXIST ");
				sendToClient(new Message(OperationType.CheckIfExist,ReturnMsgType.exsit,tour),client);}

				else
				{if(!mysql.insertOrUpdate("INSERT INTO `gcm`.`temp_tour` (`CountryNameTemp`,`CityNameTemp`, `TourName`, `Description`, `Recommended`, `MapId`) VALUES ('"+tour.getCountryname()+"','"+tour.getCityename()+"', '"+tour.getTourname()+"', '"+tour.getTdescription()+"',  '"+tour.getRecommanded()+"', '"+tour.getIDMAP()+"');"))
					sendToClient(new Message(OperationType.edittour, ReturnMsgType.cityNameNotExist, tour), client); 	
				else
					sendToClient(new Message(OperationType.edittour, ReturnMsgType.successfullyadd,tour), client);}

				} catch (SQLException e) 

				{System.out.println("ADDTOUR exception !");
				e.printStackTrace();}
				break;


				/**edit site case  -receives from the mysqlserver whether query return massage to client 
				 * successfully - if the site edited successfully
				 *  cityNameNotExist - if was problem in edit details
				 * */
			case editsite:
				try {Site site =(Site) ((Message)msg).getObject();
				if(!mysql.insertOrUpdate("INSERT INTO `gcm`.`temp_site` (`CountryName`, `CitynameTemp`, `SiteName`, `Location_x`, `Location_y`, `Category`, `Description`, `Accessibility`, `MapId`, `RecommendedVisitTime`) VALUES ('"+site.getCountryname()+"', '"+site.getCityename()+"', '"+site.getSitename()+"', '"+site.getLocation_X()+"', '"+site.getLocation_y()+"', ' "+site.getCatagory()+"', '"+site.getDescription()+"','"+site.isAccessibility()+"','"+site.getIDMAP()+"','"+site.getRecommendedVisitTime()+"');"))
					sendToClient(new Message(OperationType.editsite, ReturnMsgType.SiteNameNotExist,site), client);
				else
					sendToClient(new Message(OperationType.editsite, ReturnMsgType.successfullyedit,site), client);
				break;
				}catch (Exception e) 

				{System.out.println("editsiet exception !");
				e.printStackTrace();}
				break;

				/**edit tour case  -receives from the mysqlserver whether query return massage to client 
				 * successfully - if the tour edited successfully
				 *  cityNameNotExist - if was problem in edit details
				 * */

			case edittour:
				try {Tour tour =(Tour) ((Message)msg).getObject();
				if(!mysql.insertOrUpdate("INSERT INTO `gcm`.`temp_tour` (`CountryNameTemp`,`CityNameTemp`, `TourName`, `Description`, `Recommended`, `MapId`) VALUES ('"+tour.getCountryname()+"','"+tour.getCityename()+"', '"+tour.getTourname()+"', '"+tour.getTdescription()+"', '"+tour.getRecommanded()+"', '"+tour.getIDMAP()+"');"))
					sendToClient(new Message(OperationType.edittour, ReturnMsgType.cityNameNotExist, tour), client);
				else
					sendToClient(new Message(OperationType.edittour, ReturnMsgType.successfullyedit, tour), client);
				break;
				}catch (Exception e) 
				{System.out.println("editTOUR exception !");
				e.printStackTrace();}
				break;


				/**get tour data case  -receives from the  mysql.getQuery the result set of tour table , make an ArrayList  and send it to the client.
				 *use to upload to edit tour screen
				 * */		

			case gettourdata:
				requestData  = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> Arr_tourdata =  ClientTools.disassemble(requestData, 5);
				requestData.close();
				sendToClient(new Message(OperationType.gettourdata,Arr_tourdata), client);
				break;
				/**get site data case  -receives from the  mysql.getQuery the result set of site table , make an ArrayList  and send it to the client.
				 *use to upload to edit site screen

				 * */	
			case getsitedata:
				requestData  = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> Arr_sitedata = ClientTools.disassemble(requestData,9);
				requestData.close();
				sendToClient(new Message(OperationType.getsitedata,Arr_sitedata), client);
				break;
				/**get site  data case  -receives from the  mysql.getQuery the result set of site table , make an ArrayList  and send it to the client.
				 *use to upload to add  site screen
				 * */	
			case getsiteforadd:
				requestData  = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> Arr_sited = ClientTools.disassemble(requestData,2);
				requestData.close();
				sendToClient(new Message(OperationType.getsiteforadd,Arr_sited), client);
				break;
				/**get tour for add case  -receives from the  mysql.getQuery the result set of toue table , make an ArrayList  and send it to the client.
				 *use to upload to add tour screen
				 * */
			case gettourforadd:
				requestData  = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> Arr_site = ClientTools.disassemble(requestData,2);
				requestData.close();
				sendToClient(new Message(OperationType.gettourforadd,Arr_site), client);
				break;

				/**get List Exist Site Data case a  -receives from the  mysql.getQuery the result set of site in tour table, make an ArrayList  and send it to the client.
				 * from this array list we make tha table on edit tour window.
				 * */		

			case GetListExistSiteData:
				requestData  = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> Arr_ListSite =  ClientTools.disassemble(requestData,2);
				requestData.close();
				sendToClient(new Message(OperationType.GetListExistSiteData,Arr_ListSite), client);
				break;

				/**get  List Exist Site Data  case  -receives from the  mysql.getQuery the result set of site in tour table , make an ArrayList  and send it to the client.
				 * from this array list we make tha table on add tour window.
				 * */		

			case GetListExistSiteDataADD:
				requestData  = mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<String> Arr_ListSites =  ClientTools.disassemble(requestData,2);
				requestData.close();
				sendToClient(new Message(OperationType.GetListExistSiteDataADD,Arr_ListSites), client);
				break;

				/**get Version Num case get from mysql getQuery the coronet version of this catalog , then  Increases the version number and sand
				 * qurey to insert the data to versions table - the manger get the approve request from this table .
				 * */	
			case getVersionNum:
				DecimalFormat decimalFormat = new DecimalFormat("#.##");
				VersionDetails versiondetail=(VersionDetails)(((Message)msg).getObject());
				String GETVersion ="SELECT `Version No.` FROM gcm.maps_catalog WHERE CityName='"+versiondetail.getCityName()+"';";
				requestData  = mysql.getQuery(GETVersion);
				ArrayList<String> VersionNum =  ClientTools.disassemble(requestData, 1);
				System.out.println(VersionNum.get(0));
				float fl = Float.parseFloat(VersionNum.get(0));
				fl=Float.valueOf(decimalFormat.format(fl));
				fl=(float) (fl+0.1);
				String VersionNums=String.valueOf(fl);
				versiondetail.setVersionNumber(VersionNums);
				try {	while(!mysql.insertOrUpdate("INSERT INTO `gcm`.`versions` (`CountryName`, `CityName`, `MapID`, `EmployeeID`, `VersionNumber`) VALUES ('"+versiondetail.getCountryName()+"', '"+versiondetail.getCityName()+"', '"+versiondetail.getMapID()+"', '"+versiondetail.getEmployeeID()+"', '"+versiondetail.getVersionNumber()+"'); "))
				{float f2 = Float.parseFloat(versiondetail.getVersionNumber());
				f2=Float.valueOf(decimalFormat.format(f2));
				f2=(float) (f2+0.1);
				String VersionNums2=String.valueOf(decimalFormat.format(f2));
				versiondetail.setVersionNumber(VersionNums2);
				}
				System.out.println("set Version successfully ");				
				break;
				}
				catch (Exception e) 
				{System.out.println("Set Version exception !");
				e.printStackTrace();}
				break;




				///////////////////////////////////////////////////dorin//////////////////////////////////////////////////////////////////////////


				/* ***** Remez Update  END!! ******/

				/* ************************************** AMIT EDIT *************************************/

			case UserPurchase:
				mysql.insertOrUpdate(((Message)msg).getObject().toString());
				sendToClient(new Message(OperationType.UserPurchase,null,null), client);
				break;

			case getDetailOFpurchase:
				try {  requestData = mysql.getQuery(((Message)msg).getObject().toString());
				if(requestData!= null)
				{arrString = ClientTools.disassemble(requestData, 4);
				sendToClient(new Message(OperationType.getDetailOFpurchase,null,arrString),client);
				requestData.close(); 
				break;
				}


				} catch(IndexOutOfBoundsException ex) {
					System.out.println(ex.toString());
					System.out.println("NO history!");
				}

			case getPriceOfMap:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData,4);

				sendToClient(new Message(OperationType.getPriceOfMap,arrString),client);
				requestData.close(); 
				break;	

			case getPurchaseHistoryList:
			{


				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 3);
				sendToClient(new Message(OperationType.getPurchaseHistoryList,null,arrString),client);
				requestData.close(); 
				break;

			}

			/* ************************************** AMIT END EDIT *************************************/

			/* ****************************************REMMMMEEEZZZZZZZZ***************************************************/

			case UploadSitesToMap:{
				rs=mysql.getQuery(((Message)msg).getObject().toString());
				ArrayList<Site> r=new ArrayList<>();
				while(rs.next()) {
					r.add(new Site(rs));// arraylist of Sites 
				}
				rs.close();
				sendToClient(new Message(OperationType.UploadSitesToMap,r), client);
				break;
			}
			/* ********************************************* REMEZZ ******************************************************/

			 case UploadSitesAndToursToMap:{
										try {
										  File photo1,photo2;
										  String[] Qureys=(String[])(((Message)msg).getObject());
										  String MapID= Qureys[3];
										  String CityName=Qureys[6];
										  ResultSet rs_Sites=mysql.getQuery(Qureys[0]);//Upload Sites
										  ResultSet rs_Tours=mysql.getQuery(Qureys[1]);//Upload Sites
										  ResultSet rs_MapImage=mysql.getQuery(Qureys[2]);//getMap Image
										  ResultSet rs_Map_Price=mysql.getQuery(Qureys[4]);//get Map Price
										  ResultSet rs_City_Image=mysql.getQuery(Qureys[5]);//get City Image
										  ArrayList<Site> sites=new ArrayList<>();
										  ArrayList<Tour> tours=new ArrayList<>();
										  ArrayList<Object> Info=new ArrayList<>();
										  rs_MapImage.next();
										  rs_City_Image.next();
										  rs_Map_Price.next();
										  if((photo1=new File("Maps/"+MapID+".png")).isFile()) {
											  Info.add(photo1);//1
											}
											else {
												InputStream is=rs_MapImage.getBinaryStream(1);
												 photo1 =new File("Maps/"+MapID+".png");
												 OutputStream os = new FileOutputStream(photo1);
												 byte [] content =new byte[1024];
												 int size=0;
													while((size=is.read(content))!=-1) {
														os.write(content, 0,size);	
													}
													Info.add(photo1);
													os.close();
													is.close();	
													
											}
										  
										  if((photo2=new File("Citys/"+CityName+".jpg")).isFile()) {
											  Info.add(photo2);
											}
											else {
												InputStream is=rs_MapImage.getBinaryStream(1);
												 photo2 =new File("Citys/"+CityName+".jpg");
												 OutputStream os = new FileOutputStream(photo2);
												 byte [] content =new byte[1024];
												 int size=0;
													while((size=is.read(content))!=-1) {
														os.write(content, 0,size);	
													}
													Info.add(photo2);//2
													os.close();
													is.close();	
													
											}
										  while(rs_Sites.next()) {
											sites.add(new Site(rs_Sites));// arraylist of Sites 
											
										  }
										  while(rs_Tours.next()) {
											  ResultSet rs_SitesInTour=mysql.getQuery("SELECT distinct gcm.siteintour.SiteName FROM gcm.siteintour,gcm.tour,gcm.siteinmap,gcm.tourinmap WHERE ('"+rs_Tours.getString("TourName")+"'=gcm.siteintour.TourName AND gcm.siteinmap.IDMAP='"+MapID+"');");  
											  tours.add(new Tour(rs_Tours,rs_SitesInTour));// arraylist of Sites 
										  }
										  Info.add(rs_Map_Price.getString("Price"));//3
										  rs_Map_Price.close();
										  rs_City_Image.close();
										  rs_MapImage.close();
										  rs_Tours.close();
										  rs_Sites.close();
										  Info.add(sites);//4
										  Info.add(tours);//5
										  sendToClient(new Message(OperationType.UploadSitesAndToursToMap,Info), client);
										 // photo.delete();
										  break;
										}catch(Exception e) {System.out.println("Cant Upload sites And Tours");}
									  }
									  /*+++New Remez Upload Tours to List ++++*/
									  case UploadToursToMap:{
										  String info[]= (String[])((Message)msg).getObject();
										  rs=mysql.getQuery(info[0]);
										  ArrayList<Tour> r=new ArrayList<>();
										  while(rs.next()) {
											  ResultSet rs_SitesInTour=mysql.getQuery("SELECT distinct gcm.siteintour.SiteName FROM gcm.siteintour,gcm.tour,gcm.siteinmap,gcm.tourinmap WHERE ('"+rs.getString("TourName")+"'=gcm.siteintour.TourName AND gcm.siteinmap.IDMAP='"+info[1]+"');");  
											  r.add(new Tour(rs,rs_SitesInTour));// arra
										  }
										  rs.close();
										  sendToClient(new Message(OperationType.UploadToursToMap,r), client);
										  break;  
									  }

			/* ********************************************* REMEZZ ******************************************************/

			/*+++New Remez Delete Tours to List ++++*/
			case DeleteTour:{
				String temp[]=new String[2];//(String[])(((Message)msg).getObject());
				temp=(String[])(((Message)msg).getObject());
				Object obj[]=new Object[2];// {ans,temp[1]};
				obj[1]=temp[1];
				if(mysql.insertOrUpdate(temp[0])){
					Boolean ans=new Boolean(true);
					obj[0]=ans;
					sendToClient(new Message(OperationType.DeleteTour,obj), client);
				}
				else {
					Boolean ans=new Boolean(false);
					obj[0]=ans;
					sendToClient(new Message(OperationType.DeleteTour,obj), client);
				}
				break;
			}
			/*+++New Remez Delete Tours to List ++++*/
			case DeleteSiteFromMap:{
				String temp[]=new String[2];
				temp=(String[])(((Message)msg).getObject());
				if(mysql.insertOrUpdate(temp[1])) {
					sendToClient(new Message(OperationType.DeleteSiteFromMap, ReturnMsgType.successfully,temp[0]), client);
				}
				else
					sendToClient(new Message(OperationType.DeleteSiteFromMap,ReturnMsgType.unsuccessfully,temp[0]), client);
				break;
			}
			case SetNewPriceRequestForMap:{
				if(mysql.insertOrUpdate((String)(((Message)msg).getObject()))) {
					sendToClient(new Message(OperationType.SetNewPriceRequestForMap,ReturnMsgType.successfully,null), client);
				}
				else {
					sendToClient(new Message(OperationType.SetNewPriceRequestForMap,ReturnMsgType.unsuccessfully,null), client);
				}
				break;
			}

			/* ***************************************REMMMMEEEZZZZZZZZ ENNNNDDDDD***************************************************/
	
			/* ****************************************Gino EDIT ************************************************/
			
			case getMapDetails:
				arrString =new ArrayList<>();
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				while(requestData.next()) {
					System.out.println(requestData.getString("ID"));
					arrString.add(requestData.getString("ID"));
					System.out.println("SELECT Count(siteinmap.SiteName) AS sites FROM siteinmap WHERE IDMAP='"+requestData.getString("ID")+"';");
					rs=mysql.getQuery("SELECT Count(siteinmap.SiteName) AS sites FROM siteinmap WHERE IDMAP='"+requestData.getString("ID")+"';");
					rs.next();
					arrString.add(rs.getString("sites"));
					System.out.println("SELECT Count(tourinmap.TourName) AS tours FROM tourinmap WHERE ID='"+requestData.getString("ID")+"';");
					rs=mysql.getQuery("SELECT Count(tourinmap.TourName) AS tours FROM tourinmap WHERE ID='"+requestData.getString("ID")+"';");
					rs.next();
					arrString.add(rs.getString("tours"));
				}
				sendToClient(new Message(OperationType.getMapDetails, arrString), client);
				break;
				
			case getMapDetailsBySiteName:
				arrString =new ArrayList<>();
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				if(requestData.next()) {
					rs =mysql.getQuery("SELECT map.ID AS id FROM map WHERE map.CityName ='"+requestData.getString("CityName")+"' GROUP BY map.ID;");
					while(rs.next()) {
						System.out.println(rs.getString("ID"));
						arrString.add(rs.getString("ID"));
						System.out.println("SELECT Count(siteinmap.SiteName) AS sites FROM siteinmap WHERE IDMAP='"+rs.getString("ID")+"';");
						requestData=mysql.getQuery("SELECT Count(siteinmap.SiteName) AS sites FROM siteinmap WHERE IDMAP='"+rs.getString("ID")+"';");
						requestData.next();
						arrString.add(requestData.getString("sites"));
						System.out.println("SELECT Count(tourinmap.TourName) AS tours FROM tourinmap WHERE ID='"+rs.getString("ID")+"';");
						requestData=mysql.getQuery("SELECT Count(tourinmap.TourName) AS tours FROM tourinmap WHERE ID='"+rs.getString("ID")+"';");
						requestData.next();
						arrString.add(requestData.getString("tours"));
					}
				}
				sendToClient(new Message(OperationType.getMapDetails, arrString), client);
				break;
				
				
				
			case getMapsitesDetails:
				requestData = mysql.getQuery(((Message)msg).getObject().toString());
				arrString = ClientTools.disassemble(requestData, 8);
				sendToClient(new Message(OperationType.getMapsitesDetails, arrString), client);
				break;
			
			case ifSubscribe:
				DateTimeFormatter dateformat=DateTimeFormatter.ofPattern("yyyy/MM/dd");
				LocalDate date=LocalDate.now();
				rs=mysql.getQuery(((Message)msg).getObject().toString());
				if(rs.next()) {
					if(date.isBefore(LocalDate.parse(rs.getString("PurchaseDateExpiration"),dateformat)))
						sendToClient(new Message(OperationType.ifSubscribe, true), client);
					else
						sendToClient(new Message(OperationType.ifSubscribe, false), client);
					
				}
			
				break;
			}



		}catch(Exception ex) {
			System.out.println(ex.toString());
			System.out.println("echo exeptipon");
		}
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of 
	 * the server instance (there is no UI in this phase).
	 *
	 * @param args[0] The port number to listen on.  Defaults to 5555 
	 *          if no argument is entered.
	 */
	public static void main(String[] args) 
	{
		int port = 0; //Port to listen on

		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = DEFAULT_PORT; //Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try 
		{
			sv.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}

		StartServer.echoserver=sv;
		StartServer.LunchServerGui(args);
	}
	static class PrintTask extends TimerTask {
		public void run() {

			System.out.println("Task executed");
			DateTimeFormatter dateformat=DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate date=LocalDate.now();
			ResultSet rs=mysql.getQuery("SELECT user.FirstName,user.userName,user.Email,purchasehistory.PurchaseDateExpiration,purchasehistory.CityName FROM gcm.user,gcm.purchasehistory WHERE user.userName=purchasehistory.username;");
			try {
				while(rs.next()) {
					if(date.plusDays(3).equals(LocalDate.parse(rs.getString("PurchaseDateExpiration"),dateformat)))
						SendMailController.sendEmail(rs.getString("Email"),"Subscription almost Expired !","Hi "+rs.getString("FirstName")+" ! \nYour "+"\""+rs.getString("CityName")+"\""+" subscription will be expire in 3 days from now ! \nCome and renewal your subscription now and get 10% discount !!!\n\nWe are waiting for you, \nG<10> Team :)");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void StopServer() {
	}


}
//End of EchoServer class