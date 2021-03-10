package common.entities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import client.controllers.DownloadVersionController;
/**

* @author Shahar Ronen.
* @author Dorin Segall.
* @author Remez David.
* @author Itamar Gino.
* @author Amit Sinter.
* @version June 2019
* 
* ZipVersion Entity Temporarily saves all Zip  data
We use it to transfer  the zip file from the server to the client 
*/
public class ZipVersion implements Serializable {

	private byte contents[];
	private String country;
	private String city;
	private Double version;
	Object in;
	int direct;
	int count=0;
	/**The following method save the details of the zip folder .
	*/
	public ZipVersion (ResultSet rs) {
		contents = new byte[10000000];
		try {
			country = rs.getString("Country");
			city = rs.getString("CityName");
			version = rs.getDouble("Version");

		} catch (SQLException e) {
			System.err.println("ZipVersion Exception");
			e.printStackTrace();
		}

		retriveZipFromDB (rs);
		System.out.println(direct);

	}
	/**The following method receive the Zip folder which stored the DPF files from DB .
	*/
	public void retriveZipFromDB (ResultSet rs) {
		try {
			InputStream is =rs.getBinaryStream("Files");
			while ((direct = is.read(contents)) >= 0)
				count+=direct;
			is.close();

		}
		catch(Exception e) {
			System.err.println("ZipVersion Exception - retriveZipFromDB");
		}
	}
	
	

/*	public void createZip () throws IOException {

		int i=0;
		ZipEntry entry;
		File f =new File("C:\\Users\\shaha\\Desktop\\"+country+"_"+city+"_"+version.toString().replace('.', '_')+".zip");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));

		while ((entry = new ZipEntry("city"+i++)) != null) {
			System.out.println(entry.getName());
			
			File pdf_file=new File("1.pdf");
			OutputStream os=new FileOutputStream(pdf_file);
			os.write(contents, 0, direct);
			//System.err.println("size = "+entry.getSize());
			out.putNextEntry(entry);
			Files.copy(f.toPath(), out);
			out.closeEntry();
		}
		f.delete();

		out.close();

	}*/
	
	/**The following method receive data from db and creates a Zip  folder which stored the DPF files.
	*/
	
	public void createZip () throws IOException {
		try {

			InputStream is=new ByteArrayInputStream(contents);
			ZipInputStream zipIn = new ZipInputStream(is);
			ZipEntry entry;
			System.out.println(DownloadVersionController.path+ "\\" + country + "_" + city+"_"+version.toString().replace('.', '_')+".zip");
			File f =new File(DownloadVersionController.path+ "\\" + country + "_" + city+"_"+version.toString().replace('.', '_')+".zip");
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
			while ((entry = zipIn.getNextEntry()) != null) {
				System.out.println(entry.getName());
				f=readContents(zipIn);
				//System.err.println("size = "+entry.getSize());
				out.putNextEntry(entry);
				Files.copy(f.toPath(), out);
				zipIn.closeEntry();
			}

			out.close();
			is.close();
		}
		catch(Exception e) { System.err.println("retriveZipFromDB exception");}
	}
	
	private static File readContents(InputStream contentsIn) throws IOException {
	    byte arr[] = new byte[6200];
	    int direct;
	    File f=new File("1.pdf");
	    OutputStream os=new FileOutputStream(f);
	    while ((direct = contentsIn.read(arr, 0, arr.length)) >= 0) {
	    	os.write(arr, 0, direct);
	        //System.out.println("Read " + direct + "bytes content.");
	    }
	    return f;
	}
}