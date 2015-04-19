package cn.edu.nju.software.ruse;

import java.io.File;
import java.util.List;

/**
 * A facade for testing your RUSE. Change the implementation of the
 * two methods to adapt your version of RUSE.
 * 
 */
public class Tester {

	/**
	 * Entrance to the indexing functionality.
	 * 
	 * @param dataDir
	 *            the root directory of the files
	 * @param indexDir
	 *            the directory to place your index data
	 */
	public static void index(File dataDir, File indexDir) {
		IndexInvoker invoker=new IndexInvoker();
		Indexer indexer=Indexer.getInstance(dataDir.getAbsolutePath(), indexDir.getAbsolutePath());
		IndexCommand command=new IndexCommand(indexer);
		invoker.setCommand(command);
		invoker.doIndex();
	}

	/**
	 * Entrance to the search functionality.
	 * 
	 * @param indexDir
	 *            the index directory
	 * @param query
	 *            the query
	 * @param order
	 * 			0: no order; 1: ordered by file size; 2: ordered by last modified time
	 * @return a list containing the results that match the query
	 */
	public static List<Result> search(File indexDir, String query, int order) {
		Searcher searcher=new Searcher(indexDir.getAbsolutePath());
	   	return searcher.query(query,order);
	}
	
	public static class Result {
		public int fileSize;
		public String fileName,  modTime;
	}

}
