package client.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.sun.corba.se.spi.ior.Writeable;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ClientTools {


	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param rs
	 * @param NumOfCol
	 * @return ArrayList<String>
	 * 
	 * disassembling the information given by the ResultSet rs
	 */
	public static ArrayList<String> disassemble(ResultSet rs,int NumOfCol)
	{
		ArrayList<String> returnString = new ArrayList<String>();
		try {
			while(rs.next())
			{
				// Attention ! must run from 1 to NumOfCol (Not from zero)
				for(int i=1 ; i <= NumOfCol; i++)
					returnString.add(rs.getString(i));
			}
			rs.close();
			return returnString;
		} catch (SQLException e)
		{
			System.out.println("ClientTools.disassemble() - int NumOfCol Error");
			e.printStackTrace();
		}
		return returnString;
	}

	/**
	 * validatePhoneNumber returns if phone number is correct
	 * 
	 * @param phoneNo phone number of specific subscriber
	 * @return boolean
	 */
	public static boolean validatePhoneNumber(String phoneNo) {// checks phone number input
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{10}"))
			return true;
		// validating phone number with -, . or spaces
		else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
			return true;
		// validating phone number with extension length from 3 to 5
		else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
			return true;
		// validating phone number where area code is in braces ()
		else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return true;
		// return false if nothing matches the input
		else
			return false;

	}


	/**
	 * isValidEmail returns if an email is correct
	 * 
	 * @param email email of specific subscriber
	 * @return boolean
	 */
	public static boolean isValidEmail(String email) { // checks if an email address is valid
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static void changeWindow(Window window,String fxml) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				window.hide();
				try {
					BorderPane  root = FXMLLoader.load(getClass().getResource(fxml));
					Scene scene=new Scene(root);
					Stage primaryStage= new Stage();
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * isValidCardNumber returns if the card number correct
	 * @param cardnumber of specific subscriber
	 * @return boolean
	 */

	public static boolean isValidCardNumber(String cardNum)
	{
		// validate phone numbers of format "16-digits"
		if (cardNum.matches("\\d{16}"))
			return true;

		return false;
	}
	/**
	 * isValidIDCardHolder returns if the ID number correct
	 * @param IDCardNumber of specific subscriber
	 * @return boolean
	 */
	public static boolean isValidIDCardHolder(String ID)
	{
		// validate phone numbers of format "9-digits"

		if(ID.matches("\\d{9}"))
			return true;
		else
			return false;
	}
	/**
	 * isValidExpirationDate returns if the date number of the card is correct
	 * @param mm month of specific subscriber card holder
	 * @param yy year of specific subscriber card holder 
	 * @return boolean
	 */

	public static boolean isValidExpirationDate(String mm,String yy)
	{
		Date todayDate = new Date();
		int month=Integer.parseInt(mm);
		int year=Integer.parseInt("1"+yy);
		if((month<1 && month>12)||(todayDate.getYear()>year) || (todayDate.getMonth()>month && todayDate.getYear()==year))
			return false;
		return true;
	}
	/**
	 * isValidCVV  returns if the cvv number of the card is correct
	 * @param cvv  of specific subscriber card holder
	 * @return boolean
	 */
	public static boolean isValidCVV(String cvv)
	{
		if(cvv.length()<3 || cvv.length()>3)
			return false;

		for(int i=0 ;i<3;i++)
		{
			if(!Character.isDigit(cvv.charAt(i)))
				return false;
		}

		return true;
	}
	/**
	 * retriveZipFromDB we are reading a zip file from DB
	 * @param rs
	 * @param col
	 * @param file_name
	 */

	public static void retriveZipFromDB (ResultSet rs,String col,String file_name) {
		try {
			System.out.println(col);
			InputStream is =rs.getBinaryStream(col);
			ZipInputStream zipIn = new ZipInputStream(is);
			ZipEntry entry;
			File f =new File(file_name+".zip");
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
			byte [] content =new byte[1024];
			int size=0;

			while ((entry = zipIn.getNextEntry()) != null) {
				System.out.println(entry.getName());
				f=readContents(zipIn);
				//System.err.println("size = "+entry.getSize());
				out.putNextEntry(entry);
				Files.copy(f.toPath(), out);
				zipIn.closeEntry();
			}
			f.delete();

			out.close();
			is.close();
		}
		catch(Exception e) { System.err.println("retriveZipFromDB exception");}
	}
	/**
	 * readContents --> we are reading files that exists in a zip file
	 * @param contentsIn
	 * @return
	 * @throws IOException
	 */
	
	private static File readContents(InputStream contentsIn) throws IOException {
	    byte contents[] = new byte[4096];
	    int direct;
	    File f=new File("1.pdf");
	    OutputStream os=new FileOutputStream(f);
	    while ((direct = contentsIn.read(contents, 0, contents.length)) >= 0) {
	    	os.write(contents, 0, direct);
	        //System.out.println("Read " + direct + "bytes content.");
	    }
	    return f;
	}
	/**
	 * inputstreamToByteArray --> convert a file into input stream variable
	 * @param rs
	 * @param col
	 * @return
	 */
	
	public static Object[] inputstreamToByteArray(ResultSet rs,String col) {
		int direct;
		int count=0;
		byte[] contents=new byte[400000000];
		InputStream is;
		try {
			is = rs.getBinaryStream(col);

		while ((direct = is.read(contents)) >= 0)
			count+=direct;
		is.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		Object[] ob=new Object[2];
		ob[0]=contents;
		ob[1]=count;
		return ob;
	}
}
