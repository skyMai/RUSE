package cn.edu.nju.software.ruse;

public class IndexCommand implements Command{
     Indexer indexer;
     public IndexCommand(Indexer indexer){
    	 this.indexer=indexer;
     }
     public void execute(){
    	 indexer.indexDirectory();
     }
}
