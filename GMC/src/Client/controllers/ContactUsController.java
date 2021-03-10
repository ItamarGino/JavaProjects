package client.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
/**
 * this controller present the contact us information 
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */
public class ContactUsController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button homepage;

	@FXML
	private Hyperlink logoutbtn;

	@FXML
	private JFXButton backbtn;

	@FXML
	void PressBack(ActionEvent event)
	{
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
	}

	@FXML
	void PressLogOut(ActionEvent event) {
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/Login.fxml");
	}

	@FXML
	void Presshome(ActionEvent event) {
		ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/UserHomePage.fxml");
	}
	public static ContactUsController Instance;

	@FXML
	void initialize() {
		Instance=this;
		assert homepage != null : "fx:id=\"homepage\" was not injected: check your FXML file 'ContactUsPage.fxml'.";
		assert logoutbtn != null : "fx:id=\"logoutbtn\" was not injected: check your FXML file 'ContactUsPage.fxml'.";
		assert backbtn != null : "fx:id=\"backbtn\" was not injected: check your FXML file 'ContactUsPage.fxml'.";

	}
}
