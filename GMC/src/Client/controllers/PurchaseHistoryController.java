package client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;

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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 * this controller upload for user (employee or customer) his purchase history.
 * @author Shahar Ronen.
 * @author Dorin Segall.
 * @author Remez David.
 * @author Itamar Gino.
 * @author Amit Sinter.
 * @version June 2019
 */
public class PurchaseHistoryController
{
	static PurchaseHistoryController Instance;
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;


	@FXML
	private TableView<common.entities.PurchaseHistory> PurchaseHistoryTable;

	@FXML
	private JFXButton backbtn;

	@FXML
	private Button homepage;

	@FXML
	private Hyperlink logoutbtn;
	@FXML
	private JFXButton backbt;
	/**
	 * going back to client card info page
	 * @param event
	 */
	@FXML
	void PressBack(ActionEvent event) 
	{
		ClientTools.changeWindow(((Node)event.getSource()).getScene().getWindow(), "/client/boundry/ClientCardPage.fxml");
	}
	/**
	 * when the user press logout 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void PressLogOut(ActionEvent event)
	{
		try {

			ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Logout, ClientMessages.username));
			ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(),"/client/boundry/Login.fxml");

		}catch(Exception ex) {
			System.out.println(ex.toString());

		}
	}
	/**
	 * when the user press on the image of home 
	 * @param event
	 * @throws IOException
	 */

	@FXML
	void Presshome(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");

	}


/**
 * SetDataOnScreen is upload for the user his purchase history
 * we create table in dynamic way
 * @param msg
 * @throws SQLException
 */



	public void SetDataOnScreen(Object msg) throws SQLException {

		// Assembling the data:
		ArrayList<String> data=(ArrayList<String>)((Message)msg).getObject(); //done
		ArrayList<common.entities.PurchaseHistory> dataToSend = new ArrayList<common.entities.PurchaseHistory>();//done
		int jump=0;
		for(int i=0;i<data.size()/3;i++)
		{
			// str[0]=cityName ;str[1]=purchaseType  => By the sql table 'purchhistory'
			common.entities.PurchaseHistory rd = new common.entities.PurchaseHistory(data.get(jump+0),data.get(jump+1),data.get(jump+2));
			dataToSend.add(rd);
			jump+=3;
		}
		// dataToSend containing all the data!
		// Inserting to viewTable:
		ObservableList<common.entities.PurchaseHistory> ol = FXCollections.observableArrayList(dataToSend);

		// Creating the Table: (setCellValueFactory => must be as RequestDetails attributes are!)
		TableColumn<common.entities.PurchaseHistory,String> cityNameCol = new TableColumn<common.entities.PurchaseHistory,String>("City Name");
		cityNameCol.setStyle("-fx-alignment: CENTER");
		cityNameCol.setMinWidth(50);
		cityNameCol.setCellValueFactory(new PropertyValueFactory<common.entities.PurchaseHistory, String>("cityName"));

		TableColumn<common.entities.PurchaseHistory,String> purchaseTypeCol = new TableColumn<common.entities.PurchaseHistory,String>("Purchase Type");
		purchaseTypeCol.setMinWidth(50);
		purchaseTypeCol.setStyle("-fx-alignment: CENTER");

		purchaseTypeCol.setCellValueFactory(new PropertyValueFactory<common.entities.PurchaseHistory, String>("purchtype"));
		TableColumn<common.entities.PurchaseHistory,String> purchaseDateCol = new TableColumn<common.entities.PurchaseHistory,String>("Purchase Date");
		purchaseDateCol.setMinWidth(50);
		purchaseDateCol.setStyle("-fx-alignment: CENTER");

		purchaseDateCol.setCellValueFactory(new PropertyValueFactory<common.entities.PurchaseHistory, String>("PurchaseCurrentDate"));

		PurchaseHistoryTable.setItems(ol);
		PurchaseHistoryTable.getColumns().addAll(cityNameCol,  purchaseTypeCol,purchaseDateCol);
		PurchaseHistoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	@FXML
	void initialize() {
		Instance=this;
		/**
		 * get for each user that not employee his purchase history from DB
		 * we create table with all the relevant data on the screen
		 */
		String query = "SELECT cityName,purchaseType,PurchaseCurrentDate FROM gcm.purchasehistory WHERE userName='"+ClientMessages.username+"';";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.getPurchaseHistoryList,query));


		assert backbtn != null : "fx:id=\"backbtn\" was not injected: check your FXML file 'PurchaseHistory.fxml'.";

	}
}