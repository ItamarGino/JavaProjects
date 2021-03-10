package server.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class CreatePDFController {
	private static ResultSet rs;
	private static String city;
	private static boolean have_sites;
	private static String ID;

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,
			Font.BOLD,BaseColor.RED);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD, BaseColor.BLUE);

	public static String createMapPDF(ResultSet result,boolean have_sites) {
		String FILE=null;
		CreatePDFController.have_sites=have_sites;
		try {
			rs=result;
			rs.next();
			System.out.println(rs.getString("CityName"));
			city=rs.getString("CityName");
			ID=rs.getString("ID");
			FILE = ("C:\\Users\\shaha\\Desktop\\" +city+ "_"+ID+ "_map.pdf");
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
			document.open();
			addMetaData(document);
			//addTitlePage(document);

			Image img = Image.getInstance("D:\\temp\\eclipse\\photon\\Project_X\\Icons\\braude.jpg");
			img.scaleAbsoluteHeight(80);
			img.scaleAbsoluteWidth(100);
			img.setAbsolutePosition(450, 750);
			document.add(img);

			img = Image.getInstance("D:\\temp\\eclipse\\photon\\Project_X\\Icons\\FinalLogo.jpg");
			img.scaleAbsoluteHeight(90);
			img.scaleAbsoluteWidth(130);
			img.setAbsolutePosition(50, 740);
			document.add(img);

			addContent(document);


			img = Image.getInstance("C:\\Users\\shaha\\Desktop\\111.jpg");
			img.setRotationDegrees(90);
			img.scaleAbsoluteHeight(document.getPageSize().getHeight()-10);
			img.scaleAbsoluteWidth(document.getPageSize().getWidth()-10);
			img.setAbsolutePosition(5,0);
			document.add(img);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FILE;
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Lars Vogel");
		document.addCreator("Lars Vogel");
	}


	private static void addContent(Document document) throws DocumentException, SQLException {
		Paragraph preface = new Paragraph(city, catFont);
		// Lets write a big header
		preface.add(new Paragraph(" ", catFont));
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
		// add a table
		createTable(document);
		preface= new Paragraph("***Please scroll down for map view***",smallBold);
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add(preface);
		document.newPage();
	}

	private static void createTable(Document doc) throws DocumentException, SQLException {
		PdfPTable table = new PdfPTable(3);

		Paragraph p=new Paragraph(city, catFont);
		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);

		/*        PdfPCell c1 = new PdfPCell(new Phrase("Site's ID on map",subFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.YELLOW);
        table.addCell(c1);*/

		PdfPCell c1 = new PdfPCell(new Phrase("Site name",subFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Site description",subFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c1);
		table.setHeaderRows(1);

		c1 = new PdfPCell(new Phrase("Accessibility",subFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(BaseColor.YELLOW);
		table.addCell(c1);
		table.setHeaderRows(1);


		if(have_sites) {
			do {
				table.addCell(rs.getString("SiteName"));
				//table.addCell(rs.getString("Location_x"));
				table.addCell(rs.getString("Description"));
				table.addCell(rs.getString("Accessibility"));
			}while(rs.next());
		}
		doc.add(table);

	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public static void image(Document document , String image) throws DocumentException, MalformedURLException, IOException {
		Image img = Image.getInstance(image);
		if(image.contains("braude"))
			img.setAlignment(Element.ALIGN_RIGHT);
		else
			img.setAlignment(Element.ALIGN_LEFT);

		img.scaleAbsoluteHeight(80);
		img.scaleAbsoluteWidth(120);
		img.setAbsolutePosition(450, 750);
		document.add(img);
	}




	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 *
	 *CreatePDFController => Creating PDF
	 */
	/**@author Shahar Ronen.
	 * @author Dorin Segall.
	 * @author Remez David.
	 * @author Itamar Gino.
	 * @author Amit Sinter.
	 * 
	 * @param CountryName
	 * @param cityName
	 * @param mapQ
	 * @param purchase
	 * @param sub
	 * @param renewal
	 * @param view
	 * @param download
	 * @param date
	 * 
	 * MakePDF => Creating a new PDF file at the location Reports (folder)
	 * Base on the input
	 */
	public static void MakePDF(String CountryName, String cityName, String mapQ, String purchase, String sub
			, String renewal, String view, String download, String date)
	{
		File root = new File("Icons");

		com.itextpdf.text.Document document = new com.itextpdf.text.Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File("Reports","Report_"+ CountryName +"_"+ cityName +".pdf")));
		} catch (FileNotFoundException | DocumentException e) {e.printStackTrace();}

		document.open();
		Font font16 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Font font20 = FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK);
		Font font30 = FontFactory.getFont(FontFactory.COURIER_BOLD, 30, BaseColor.BLACK);

		Chunk head = new Chunk("Report	-	"+ cityName +"\n", font30);
		head.setLineHeight(80);
		head.setCharacterSpacing(10);
		Chunk countryNcity = new Chunk(CountryName+"		-			"+cityName+"                "+date+"\n\n\n", font20);
		countryNcity.setLineHeight(60);
		Chunk rest = new Chunk("Maps Quantity:			"+ Integer.parseInt(mapQ)+"\n"
				+ "Number Of One-Time Purchases:			"+ Integer.parseInt(purchase)+"\n"
				+ "Number Of Subscriptions:			"+Integer.parseInt(sub)+"\n"
				+ "Number Of Renewals:			"+ Integer.parseInt(renewal)+"\n"
				+ "Number Of Views:			"+Integer.parseInt(view)+"\n"
				+ "Number Of Downloads:			"+ Integer.parseInt(download)+"\n", font16);
		rest.setLineHeight(40);


		//Chunk date = new Chunk(resultSingleCity.get(8)+"\n", font16);

		// Adding the images:
		Image imgGCM = null,imgBraude = null;
		try {
			imgGCM = Image.getInstance(new File(root, "FinalLogo.jpg").getAbsolutePath());
			imgBraude = Image.getInstance(new File(root, "braude.jpg").getAbsolutePath());
		} catch (BadElementException | IOException e) {e.printStackTrace();}

		imgGCM.setAbsolutePosition(450, 10);
		imgGCM.setBorderWidth(0);

		imgBraude.setAbsolutePosition(50, 10);
		imgBraude.setBorderWidth(0);

		imgGCM.scaleAbsoluteWidth(80);
		imgGCM.scaleAbsoluteHeight(80);
		imgBraude.scaleAbsoluteWidth(80);
		imgBraude.scaleAbsoluteHeight(80);

		try {
			document.add(imgGCM);
			document.add(imgBraude);
			document.add(head);
			document.add(countryNcity);
			document.add(rest);
		} catch (DocumentException e) {e.printStackTrace();}

		document.close();
	}
}
