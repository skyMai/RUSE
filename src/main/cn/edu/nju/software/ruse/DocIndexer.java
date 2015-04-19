package cn.edu.nju.software.ruse;

import java.io.*;
import org.apache.poi.hwpf.extractor.WordExtractor; 
//import org.omg.CORBA.portable.InputStream;

public class DocIndexer extends FileIndexer {

/*This method get a doc file and get its content as a string
 * and use the string to create a DocProduct.
 */
	 FileProduct createProduct(File file){
		 String text=null;
		 WordExtractor extractor;
	    try{
			extractor = new WordExtractor(new FileInputStream(file));
			 text = extractor.getText();
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 DocProduct product=new DocProduct(text);
		 return product;
	 }
}
