package cn.edu.nju.software.ruse;

import java.util.Hashtable;
import java.util.LinkedList;

public class SimpleHasher {
    LinkedList hashfile(String content,Hashtable hashtable,String filename,LinkedList keyList){
    	keyList.add(filename);
        hashtable.put(filename, content);
        return keyList;
    }
}
