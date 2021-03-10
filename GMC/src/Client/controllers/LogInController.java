package client.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


import client.ViewStarter;
import common.controllers.Message;
import common.controllers.OperationType;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import server.EchoServer;

/**
 * The ViewStarter class that extends application represent the start view
 * @author  Shahar Ronen
 * @author Dorin Segal
 * @author Amit Sinter
 * @author Remez David
 * @author Itamar Gino
 */

public class LogInController {
	
    @FXML
    private BorderPane barChart;
    
	@FXML
    private Hyperlink NewAccount;

    @FXML
    private ResourceBundle resources;
    
    @FXML
    private JFXTextField UserNameText;

    @FXML
    private JFXPasswordField PasswordText;

    @FXML
    private JFXButton LoginButton;

    @FXML
    private JFXButton ViewCatalogButton;

    @FXML
    private URL location;

    @FXML
    private Hyperlink ForgotPassword;

    static Stage window;
    
    public static LogInController instance;
    
    /**
     * This method change the window
     * @param  current_window
     * @param fxml
     * 
     */
    public static void changeWin(String fxml) {
    	ClientTools.changeWindow(window, fxml);
    }
    
    /**
     * This method accept user_name and password from the user and checks if the user exist in DB.
     * 
     * @category Log in
     * @return void
     * @param event
     * @throws IOException
     */
    @FXML
    void pressLogin(ActionEvent event) throws IOException {
    	window=(Stage)((Node)event.getSource()).getScene().getWindow();
    	String user=UserNameText.getText();
    	String pass=PasswordText.getText();
    	if(user.isEmpty()||pass.isEmpty())
    		ClientMessages.popUp(AlertType.ERROR,"ERROR","One or more fields Empty!","Fill username and password!");
    	else {
    		String query="SELECT * FROM user WHERE userName='"+user+"' AND Password='"+pass+"';";
    		ViewStarter.client.handleMessageFromClientUI(new Message(OperationType.Login,query));
    	}
		}
    
    /** 
     * Change to 'CityCatalog' window.
     * @param event
     * @throws IOException
     */
    @FXML
    void GoToCityCatalog(MouseEvent event) throws IOException {
    	ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/CityCatalog.fxml");
    	//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/SiteEditWindow.fxml");

    }
    
    /**
     * Change to Sign up window.
     * @param event
     * @throws IOException
     */
    @FXML
    void GoToNewUserAccount(MouseEvent event) throws IOException {
    	ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/SignUpWindow.fxml");
       	//ClientTools.changeWindow((Stage)((Node)event.getSource()).getScene().getWindow(), "/client/boundry/SiteAddWindow.fxml");

    }

    /**
     * Open new window of forget password.
     * @param event
     * @throws IOException
     */
    @FXML
    void GoToForrgotPassword(MouseEvent event) throws IOException {
		BorderPane  root = FXMLLoader.load(getClass().getResource("/client/boundry/ForgetPassword.fxml"));
		Scene scene=new Scene(root);
		Stage primaryStage= new Stage();
		primaryStage.setScene(scene);
		primaryStage.show();
    }


    @FXML
    void initialize() {
    	instance = this;
    	//UserNameText.setText("shahar");
    	//PasswordText.setText("1");
        assert LoginButton != null : "fx:id=\"LoginButton\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert ForgotPassword != null : "fx:id=\"ForgotPassword\" was not injected: check your FXML file 'HomePage.fxml'.";
        assert ViewCatalogButton != null : "fx:id=\"ViewCatalogButton\" was not injected: check your FXML file 'HomePage.fxml'.";
     // LoginButton

    }
}