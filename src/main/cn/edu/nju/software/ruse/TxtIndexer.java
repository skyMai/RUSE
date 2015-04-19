package cn.edu.nju.software.ruse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TxtIndexer extends FileIndexer {
		
     public TxtIndexer(){   }
 
     /*This method get a txt file and get its content as a string
      * and use the string to create a DocProduct.
      */
     FileProduct createProduct(File file){
    	 BufferedReader input=null;
     	try {
     		input=new BufferedReader((new FileReader(file)));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String line;
			String text=new String();
			try {
				while((line=input.readLine())!=null){
					text=text.concat(line+" ");			
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TxtProduct product=new TxtProduct(text);
			return product;
     }
}
