package server;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.EchoServer.PrintTask;

public class StartServer extends Application {
	public static EchoServer echoserver;
	
	
	public static void LunchServerGui(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) {
try {
	
    Timer timer = new Timer();
    
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 18);
    calendar.set(Calendar.MINUTE, 00);
    calendar.set(Calendar.SECOND, 00);
    java.util.Date time = calendar.getTime();

    timer.schedule(new PrintTask(), time, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
   // timer.schedule(new PrintTask(),time);    
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/server/boundry/ServerWindow.fxml"));
			Scene scene = new Scene(root,600,400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Server");
			primaryStage.setResizable(false);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			    	try {
						echoserver.close();
						 Platform.exit();
					        System.exit(0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
