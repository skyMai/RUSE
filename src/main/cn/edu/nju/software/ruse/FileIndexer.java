package cn.edu.nju.software.ruse;
import java.util.*;
import java.io.*;

//this interface hash all terms in a file
public abstract class FileIndexer {
    /* This method get the content of a file from a FileProduct and
     * hash each word.
     */
	 LinkedList hashFile(Hashtable hash,File file,LinkedList keyList){
		String text=createProduct(file).file;
		 StringTokenizer tokenizer=new StringTokenizer(text," \t\n\r\f~!@#$%^&*()_+|`-=\\{}[]:\";'<>?,./'");
		 int pos=0;
		   while(tokenizer.hasMoreTokens()){
			   ComplexHasher hasher=new ComplexHasher();
			   keyList=hasher.complexHashWord(pos,(tokenizer.nextToken()).toLowerCase(),hash,file.getName(),keyList);
			   pos++;
		   }
		  return keyList; 
	}
//this abstract method is the factory method
	abstract FileProduct createProduct(File file);
}
