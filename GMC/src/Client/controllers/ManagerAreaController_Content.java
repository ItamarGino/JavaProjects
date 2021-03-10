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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * 
 * ManagerAreaController_Content is a controller for Content Depatrment Manger Area.
 */
public class ManagerAreaController_Content
{
	public static ManagerAreaController_Content Instance;

	@FXML
	private Text versionTXT;

	@FXML
	private Text priceTXT;

	@FXML
	private Text siteTXT;

	@FXML
	private Text TourTXT;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

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

	@FXML
	void GoToHomePage(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/UserHomePage.fxml");
	}

	@FXML
	void ShowReports(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_ShowReports.fxml");
	}    

	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_ApproveVersion.fxml");
	}

	@FXML
	void ShowManagerInfo(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_Content.fxml");
	}

	@FXML
	void ShowEmployeeInfo(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Content_EmployeeInfo.fxml");
	}

	@FXML
	void ShowDataArchive(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CityCatalog.fxml");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{
		assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert ReportButton != null : "fx:id=\"ReportButton\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert versionTXT != null : "fx:id=\"versionTXT\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert priceTXT != null : "fx:id=\"priceTXT\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert siteTXT != null : "fx:id=\"siteTXT\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";
		assert TourTXT != null : "fx:id=\"TourTXT\" was not injected: check your FXML file 'ManagerArea_Content.fxml'.";

		Instance=this;
		// send query:
		String query = "SELECT a.versionNews, b.requestNews, c.temp_siteNews, d.temp_tourNews "+
				"FROM (SELECT COUNT(versions.MapID) AS versionNews FROM versions) AS a, "+
				"(SELECT COUNT(request.mapID) AS requestNews FROM request) AS b, "+
				"(SELECT COUNT(temp_site.SiteName) AS temp_siteNews FROM temp_site) AS c, "+
				"(SELECT COUNT(temp_tour.TourName) AS temp_tourNews FROM temp_tour) AS d;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getNews,query));
	}

	/** @author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
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
		ArrayList<String> data = (ArrayList<String>)((Message)msg).getObject();

		versionTXT.setText(data.get(0));
		priceTXT.setText(data.get(1));
		siteTXT.setText(data.get(2));
		TourTXT.setText(data.get(3));
	}
}
