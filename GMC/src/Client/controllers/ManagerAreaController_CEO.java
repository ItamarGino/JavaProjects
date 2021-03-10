package client.controllers;

import com.jfoenix.controls.JFXButton;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
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
 * ManagerAreaController_CEO is a controller for CEO Area.
 */
public class ManagerAreaController_CEO extends ManagerAreaController_Content
{

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

	@FXML // fx:id="ApproveVersionButton"
	private JFXButton ApproveVersionButton; // Value injected by FXMLLoader

	@FXML // fx:id="DataArchiveButton"
	private JFXButton DataArchiveButton; // Value injected by FXMLLoader

	@FXML // fx:id="EmployeeInfoButton"
	private JFXButton EmployeeInfoButton; // Value injected by FXMLLoader

	@FXML // fx:id="ViewReportsButton"
	private JFXButton ViewReportsButton; // Value injected by FXMLLoader

	@FXML // fx:id="ApprovePriceButton"
	private JFXButton ApprovePriceButton; // Value injected by FXMLLoader

	@FXML // fx:id="CostumerInfoButton"
	private JFXButton CostumerInfoButton; // Value injected by FXMLLoader

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
	void ShowApprovePrice(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApprovePrice.fxml");
	}

	@FXML
	void ShowCostumerInfo(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_CostumerInfo.fxml");
	}

	@Override
	@FXML
	void ShowManagerInfo(MouseEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/ManagerArea_CEO.fxml");
	}

	@Override
	@FXML
	void ShowEmployeeInfo(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_EmployeeInfo.fxml");
	}

	@Override
	@FXML
	void ShowApproveVersion(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ApproveVersion.fxml");
	}

	@Override
	@FXML
	void ShowReports(ActionEvent event) 
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/CEO_ShowReports.fxml");
	}

	@Override
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{
		assert ApproveVersionButton != null : "fx:id=\"ApproveVersionButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
		assert DataArchiveButton != null : "fx:id=\"DataArchiveButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
		assert EmployeeInfoButton != null : "fx:id=\"EmployeeInfoButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
		assert ViewReportsButton != null : "fx:id=\"ViewReportsButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
		assert ApprovePriceButton != null : "fx:id=\"ApprovePriceButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
		assert CostumerInfoButton != null : "fx:id=\"CostumerInfoButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
		assert InfoButton != null : "fx:id=\"InfoButton\" was not injected: check your FXML file 'ManagerArea_CEO.fxml'.";
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

}
