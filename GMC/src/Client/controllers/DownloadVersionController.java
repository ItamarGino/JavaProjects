/**
 * Sample Skeleton for 'DownloadVersionWindow.fxml' Controller Class
 */

package client.controllers;

import java.io.File;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
/**
 * this controller is about download verion
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */
public class DownloadVersionController {

	@FXML // fx:id="browse"
	private JFXButton browse; // Value injected by FXMLLoader

	@FXML // fx:id="saveBtn"
	private JFXButton saveBtn; // Value injected by FXMLLoader

	@FXML // fx:id="backBtn"
	private JFXButton backBtn; // Value injected by FXMLLoader

	@FXML // fx:id="pathLbl"
	private Label pathLbl; // Value injected by FXMLLoader

	@FXML // fx:id="combobox"
	private  ComboBox<String> combobox; // Value injected by FXMLLoader
	
    @FXML
    private Label choose_ver_error_lbl;

	public static DownloadVersionController instance;
	public static String path;
	@FXML
	void pressBack(ActionEvent event) {

		((Node) event.getSource()).getScene().getWindow().hide();
	}
/**
 * when the user press on browse
 * @param event
 */
	@FXML
	void pressBrowse(ActionEvent event) {
		pathLbl.setTextFill(Color.BLACK);
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(new Stage());

		if(selectedDirectory == null){
			pathLbl.setText("");
		}else{
			pathLbl.setText(selectedDirectory.getAbsolutePath());
		}
	}
/**
 * the user must to selet a path to save the file on his computer
 * @param event
 */
	@FXML
	void pressSave(ActionEvent event) {

		if(pathLbl.getText().isEmpty() || pathLbl.getText().equals("YOU MUST CHOOSE PATH !")) {
			pathLbl.setText("YOU MUST CHOOSE PATH !");
			pathLbl.setTextFill(Color.RED);
		}
		else {
			
			if (combobox.getSelectionModel().isEmpty())
				choose_ver_error_lbl.setVisible(true);
				else {
				path=pathLbl.getText();
				String query=" SELECT * FROM purchasehistory WHERE cityname='"+CityCatalogController.Instance.city.get(0)+"' AND userName='"+ClientMessages.username+"' AND Version="+combobox.getValue()+";";
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.mapDownload,query));
				ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.CountUpdate,"UPDATE `gcm`.`maps_catalog` SET `Download_Quantity` =`Download_Quantity` +1 WHERE (`CityName` ='"+ CityCatalogController.Instance.city.get(0)+ "');"));
				CityCatalogController.Instance.UploadFullCatalog(event);
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		}

	}

	@FXML
	public  void insertToCombobox( ArrayList<String> list) {

		combobox.getItems().removeAll(combobox.getItems());
		for(String s : list) {
			combobox.getItems().add(s);

		}
	}
	
    @FXML
    void pressComboBox(MouseEvent event) {
    	choose_ver_error_lbl.setVisible(false);

    }
    
    
	@FXML
	void initialize() {
		instance=this;
		String query="SELECT Version FROM gcm.purchasehistory WHERE userName='"+ClientMessages.username+"' AND CityName='"+CityCatalogController.city.get(0)+"' AND Download='no' ;";
		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.UploadversionsToComboDownload,query));
	}
}
