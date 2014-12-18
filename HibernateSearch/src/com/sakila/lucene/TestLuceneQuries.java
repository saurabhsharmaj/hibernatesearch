package com.sakila.lucene;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.sakila.entity.Actor;
import com.sakila.entity.Country;
import com.sakila.utils.HibernateUtil;

public class TestLuceneQuries {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		searchCountry();
		searchName();
		searchActor1() ;
		searchActor2();
	}
	
	public static void searchActor2(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String searchString = "c: saurabh " ;
		searchString = "firstName: ( \"saurabh Kumar\" AND julia )";
		
		searchString = "firstName: ( EN OR EL )  AND lastName:  on " ;
		searchString = HibernateSearchUtil.getManipulatedStringForSearch(searchString);
		
		System.out.println("Mainuplated: "+searchString);
		Query luceneQuery =HibernateSearchUtil.createLuceneQuery(searchString, "firstName");
		System.out.println("luceneQuery: "+luceneQuery);
		org.hibernate.search.FullTextQuery fullTextQuery = Search.getFullTextSession(session).createFullTextQuery(luceneQuery, Actor.class);
		System.out.println("FullTextQuery: "+fullTextQuery.getQueryString());
		List<Actor> actors = fullTextQuery.list();
		System.out.println("Actors Found:"+actors.size());
		for (Actor actor : actors) {
			System.out.println(actor);
		}

	}
	
	public static void searchCountry(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String searchString = "country : India " ;
		
		org.hibernate.search.FullTextQuery fullTextQuery = HibernateSearchUtil.createLuceneQuery(searchString, "country",new StandardAnalyzer(Version.LUCENE_31));
		System.out.println("FullTextQuery: "+fullTextQuery.getQueryString());
		List<Country> countries = fullTextQuery.list();
		System.out.println("Country Found:"+countries.size());
		for (Country country : countries) {
			System.out.println(country);
		}
	}
	
	public static void searchName(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query luceneQuery1 = HibernateSearchUtil.createLuceneQuery("Saurabh sharma", "firstName");
		Query luceneQuery2 = HibernateSearchUtil.createLuceneQuery("Saurabh Singh", "firstName");
		luceneQuery1.combine(new Query[]{luceneQuery2});
		org.hibernate.search.FullTextQuery fullTextQuery = Search.getFullTextSession(session).createFullTextQuery(luceneQuery1, Actor.class);
		System.out.println("FullTextQuery: "+fullTextQuery.getQueryString());
		List<Actor> actors = fullTextQuery.list();
		System.out.println("Actors Found:"+actors.size());
		for (Actor actor : actors) {
			System.out.println(actor);
		}
		
	}
	
	private static List<Actor> searchActor1() {
		String queryString="firstName: julia AND lastName: MCQUEEN";
		Session session = HibernateUtil.getSessionFactory().openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Actor.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("firstName").matching(queryString).createQuery();
		System.out.println(luceneQuery);
		// wrap Lucene query in a javax.persistence.Query
		org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Actor.class);
		List<Actor> contactList = fullTextQuery.list();
		System.out.println("size:"+contactList.size());
		for (Actor actor : contactList) {
			System.out.println(actor);
		}
		fullTextSession.close();
		return contactList;
	}

}
