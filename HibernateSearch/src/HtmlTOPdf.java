import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;




public class HtmlTOPdf {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//writePDF();
		writePDF2();
	}

	public static void writePDF(){/*
		try {String k = "<html><body>Terms &amp; Conditions</body></html>";
		OutputStream file = new FileOutputStream(new File("D:\\pdf1.pdf"));
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, file);
		document.open();
		InputStream is = new ByteArrayInputStream(k.getBytes());
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
		document.close();
		file.close();} catch (Exception e) {
			e.printStackTrace();
		}	
	*/}

	public static void writePDF2(){

		try {String k = "<html><body>Terms & Conditions</body></html>";
		Document document = new Document();
		HTMLWorker worker = new HTMLWorker(document);
		StringReader stringReader = new StringReader(k);
		File file = File.createTempFile("tempPdf", ".pdf");
		try {
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			worker.parse(stringReader);
		} catch (DocumentException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			document.close();
		}
		FileInputStream inputStream = new FileInputStream(file);
		FileOutputStream outputStream = 
				new FileOutputStream(new File("d:/pdf2.pdf"));

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
