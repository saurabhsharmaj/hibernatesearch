package com.sakila.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import com.sakila.utils.HibernateUtil;

public class IndexManager{
	public static void main(String[] args) throws IOException {	
		FileUtils.deleteDirectory(new File("D:\\lucene\\indexes"));
		FullTextSession fullTextSession =null;
		try{
			Session session = HibernateUtil.getSessionFactory().openSession();
			fullTextSession = Search.getFullTextSession(session);
			System.out.println("create indexes");
			fullTextSession.createIndexer().startAndWait();
			System.out.println("start and wait");

		}catch(Exception ex){
			ex.printStackTrace();
		} finally{
			fullTextSession.close();
			System.out.println("done");
			System.exit(1);
		}

	}


}
