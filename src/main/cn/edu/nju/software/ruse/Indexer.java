package cn.edu.nju.software.ruse;

import java.io.*;
import java.util.*;

public class Indexer {
	private String path="F:\\大三上学期\\Design Pattern\\FileLocation";
    private Hashtable hash=new Hashtable(10000);
    private Hashtable timehash=new Hashtable(1000);
    private Hashtable sizehash=new Hashtable(1000);
    private Hashtable fileTimeHash=new Hashtable(1000);
    private Hashtable fileSizeHash=new Hashtable(1000);
    private FileIndexer fileIndexer;
    private LinkedList key=new LinkedList();
    private String indexDir="indexFile.txt";
    private String timeIndex="timeIndex.txt";
    private String sizeIndex="sizeIndex.txt";
    private String fileTimeIndex="timeRecord.txt";
    private String fileSizeIndex="sizeRecord.txt";
    private static Indexer uniqueInstance;
    private LinkedList timeKey=new LinkedList();
    private LinkedList sizeKey=new LinkedList();
    private LinkedList fileSizeKey=new LinkedList();
    private LinkedList fileTimeKey=new LinkedList();
    
	private Indexer(String path,String indexDir){
        this.path=path;
        this.indexDir=indexDir+"/index.txt";
        this.timeIndex=indexDir+"/timeIndex.txt";
        this.sizeIndex=indexDir+"/sizeIndex.txt";
        this.fileTimeIndex=indexDir+"/timeRecord.txt";
        this.fileSizeIndex=indexDir+"/sizeRecord.txt";
	}
	private Indexer(){}
	
	public static Indexer getInstance(String path,String indexDir){
		if(uniqueInstance==null){
			uniqueInstance=new Indexer(path,indexDir);
		}
		else{
			uniqueInstance.path =path;
			uniqueInstance.indexDir=indexDir+"/index.txt";
			uniqueInstance.timeIndex=indexDir+"/timeIndex.txt";
			uniqueInstance.sizeIndex=indexDir+"/sizeIndex.txt";
			uniqueInstance.fileTimeIndex=indexDir+"/timeRecord.txt";
			uniqueInstance.fileSizeIndex=indexDir+"/sizeRecord.txt";
		}
		return uniqueInstance;
	}
	
	
	/* Following method index all files under a flat directory and 
	 * store the result into a certain file
	 */	
	public void indexDirectory() {
		File dir=new File(path);        		
	    hashAllFiles(dir);
		try{
		FileWriter indexFile=new FileWriter(indexDir);
		for(int i=0;i<key.size();i++){
			String term=(String)key.get(i);
			indexFile.write(term+" ");
			LinkedList fileList=(LinkedList)(hash.get(term)); 
			for(int j=0;j<fileList.size();j++){
				HashNode hashNode=(HashNode)fileList.get(j);
				indexFile.write(hashNode.filename+"(");
				LinkedList poslist=hashNode.position;
				for(int k=0;k<poslist.size();k++){
				indexFile.write((poslist.get(k)).toString());
				if(k!=poslist.size()-1)
					indexFile.write(",");
					else
						indexFile.write(")");
				}
                if(j!=fileList.size()-1)
                	indexFile.write(" ");
			}
			indexFile.write("\r\n");
			
		}
		indexFile.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		writeToFile(timeKey, timehash, timeIndex);
		writeToFile(sizeKey, sizehash, sizeIndex);
		simpleWriteToFile(fileSizeKey,fileSizeHash,fileSizeIndex);
		simpleWriteToFile(fileTimeKey,fileTimeHash,fileTimeIndex);
		
	}
	
	void hashAllFiles(File dir){
			File file[]=dir.listFiles();
		for(int i=0;i<file.length;i++){
			if(file[i].isDirectory())
				hashAllFiles(file[i]);
			else {
			String wholeName=file[i].getName();
			SimpleHasher simpleHasher=new SimpleHasher();
			Hasher commonHasher=new Hasher();
			String modTime=getModTime(file[i]);
			timeKey=commonHasher.hashWord(modTime,timehash,wholeName,timeKey);
			long fileSize=file[i].length();
			sizeKey=commonHasher.hashWord(String.valueOf(fileSize), sizehash,wholeName,sizeKey);
			fileSizeKey=simpleHasher.hashfile(String.valueOf(fileSize), fileSizeHash, wholeName, fileSizeKey);
			fileTimeKey=simpleHasher.hashfile(modTime, fileTimeHash, wholeName, fileTimeKey);
			//if the file is “txt”
				if((file[i].getName()).endsWith(".txt")){
					fileIndexer=new TxtIndexer();
				}
			//if the file is "doc"
		            else if((file[i].getName()).endsWith(".doc")){
		            	fileIndexer=new DocIndexer();
		            }  
			key=fileIndexer.hashFile(hash, file[i],key);
			}
		}
	}
	
	
	public void writeToFile(LinkedList keyList,Hashtable hashtable,String filePath){
		try{
			FileWriter indexFile=new FileWriter(filePath);
			
			for(int i=0;i<keyList.size();i++){
				String term=(String)keyList.get(i);
				indexFile.write(term+" ");
				LinkedList fileList=(LinkedList)(hashtable.get(term)); 
				for(int j=0;j<fileList.size();j++){
					indexFile.write((String)fileList.get(j));
	                if(j!=fileList.size()-1)
	                	indexFile.write(" ");
				}
				indexFile.write("\r\n");
				
			}
			indexFile.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}	
	}
	public void simpleWriteToFile(LinkedList keyList,Hashtable hashtable,String filePath){
		try{
			FileWriter indexFile=new FileWriter(filePath);
			for(int i=0;i<keyList.size();i++){
				String term=(String)keyList.get(i);
				indexFile.write(term+" ");
				indexFile.write((String)hashtable.get(term));
				indexFile.write("\r\n");	
			}
			indexFile.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}	
	}

//This method return the last modified time of a file in the format of "yyyyMMddHHmmss"
	String getModTime(File file){
		Date date=new Date(file.lastModified());
		StringTokenizer token=new StringTokenizer(date.toString(),": ");
		int i=0;  String month = null;  String hour = null;  String min = null; 
		String day = null; String year = null;  String sec = null;
		while(token.hasMoreTokens()){
			String element=token.nextToken();
			if(i==1){
				if(element.equalsIgnoreCase("Jan"))
					month="01";
				else if(element.equalsIgnoreCase("Feb"))
				    month="02";
				else if(element.equalsIgnoreCase("Mar"))
					month="03";
				else if(element.equalsIgnoreCase("Apr"))
					month="04";
				else if(element.equalsIgnoreCase("May"))
					month="05";
				else if(element.equalsIgnoreCase("Jun"))
					month="06";
				else if(element.equalsIgnoreCase("Jul"))
					month="07";
				else if(element.equalsIgnoreCase("Aug"))
					month="08";
				else if(element.equalsIgnoreCase("Sep"))
					month="09";
				else if(element.equalsIgnoreCase("Oct"))
					month="10";
				else if(element.equalsIgnoreCase("Nov"))
					month="11";
				else if(element.equalsIgnoreCase("Dec"))
					month="12";
			} 
			else if(i==2)   day=element;
			else if(i==7)   year=element;
			else if(i==3)   hour=element;
			else if(i==4)   min=element;
			else if(i==5)   sec=element;
			i++;
		}
		
		String result=year+month+day+hour+min+sec;  
		return result;
	}
	
	 
	
	
   

}
