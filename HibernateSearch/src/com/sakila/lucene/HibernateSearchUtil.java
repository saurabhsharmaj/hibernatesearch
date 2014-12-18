package com.sakila.lucene;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import com.sakila.entity.Actor;
import com.sakila.entity.City;
import com.sakila.utils.HibernateUtil;


public class HibernateSearchUtil {

	static String DOUBLE_QUOTES= "\"";
	
	private FullTextQuery createLuceneQueryForActor(final String queryString, final String fields,
			final StandardAnalyzer standardAnalyzer) {
		FullTextSession fem = Search.getFullTextSession(HibernateUtil.getSessionFactory().openSession());
		Query luceneQuery = null;
		// Search by multiple fields
		MultiFieldQueryParser multiParser = new MultiFieldQueryParser(Version.LUCENE_31,
				StringUtils.split(fields, ","), standardAnalyzer);
		multiParser.setAutoGeneratePhraseQueries(false);
		multiParser.setAllowLeadingWildcard(true);
		org.hibernate.search.FullTextQuery query = null;
		try {
		    BooleanQuery.setMaxClauseCount(262144);
			luceneQuery = multiParser.parse(queryString);
			System.out.println(luceneQuery);
			query = fem.createFullTextQuery(luceneQuery, Actor.class);
			System.out.println(query);
		} catch (org.apache.lucene.queryParser.ParseException e) {

			e.printStackTrace();
		}
		return query;
	}
	
	public static FullTextQuery createLuceneQuery(final String queryString, final String fields,
			final StandardAnalyzer standardAnalyzer) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		FullTextSession fem = Search.getFullTextSession(session);
		Query luceneQuery = null;
		// Search by multiple fields
		MultiFieldQueryParser multiParser = new MultiFieldQueryParser(Version.LUCENE_31,
				StringUtils.split(fields, ","), standardAnalyzer);
		multiParser.setAutoGeneratePhraseQueries(false);
		multiParser.setAllowLeadingWildcard(true);
		org.hibernate.search.FullTextQuery query = null;
		try {
		    BooleanQuery.setMaxClauseCount(262144);
			luceneQuery = multiParser.parse(queryString);
			query = fem.createFullTextQuery(luceneQuery, City.class);
			// Apply filter on the records to include only active users
			//query.enableFullTextFilter("deletedRecordFilter").setParameter("isFilterOnUser", "true");
			//query.setProjection("country");
		} catch (org.apache.lucene.queryParser.ParseException e) {
			
			e.printStackTrace();
		}
		return query;
	}
	
	
	
	public static Query createLuceneQuery(final String queryString, final String fields) {
		org.apache.lucene.search.Query luceneQuery = null;
		// Search by multiple fields
		MultiFieldQueryParser multiParser = new MultiFieldQueryParser(Version.LUCENE_31,
				StringUtils.split(fields, ","), new StandardAnalyzer(Version.LUCENE_31));
		multiParser.setAutoGeneratePhraseQueries(false);
		multiParser.setAllowLeadingWildcard(true);
		try {
			luceneQuery = multiParser.parse(queryString);
		} catch (org.apache.lucene.queryParser.ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return luceneQuery;
	}

	public static String getManipulatedStringForSearch(final String searchString) {
		StringBuilder luceneQueryString = new StringBuilder();
		List<String> searchStringList = Arrays.asList(StringUtils.split(searchString, " "));
		for (String searchStringParam : searchStringList) {
			if(StringUtils.startsWith(searchStringParam, DOUBLE_QUOTES) && !StringUtils.endsWith(searchStringParam, DOUBLE_QUOTES) ){
				searchStringParam=searchStringParam+DOUBLE_QUOTES;
			}
			
			if(!StringUtils.startsWith(searchStringParam, DOUBLE_QUOTES) && StringUtils.endsWith(searchStringParam, DOUBLE_QUOTES) ){
				searchStringParam=DOUBLE_QUOTES+searchStringParam;
			}
			
			String AND_OPERATOR_STRING ="AND";
			String OR_OPERATOR_STRING ="OR";
			String NOT_OPERATOR_STRING="NOT";
			if (!(searchStringParam.trim().equals(AND_OPERATOR_STRING .trim())
					|| searchStringParam.trim().equals(OR_OPERATOR_STRING .trim()) || searchStringParam.trim().equals(
							NOT_OPERATOR_STRING.trim()))
							&& (StringUtils.isAlphanumeric(searchStringParam.trim())
									|| StringUtils.contains(searchStringParam, "@") || StringUtils.startsWith(
											searchStringParam, "*"))) {
				// If it's search like email strings then we need to handle it
				// carefully
				// because such fields are saved as it is. So we will add * as
				// prefix and suffix to
				// handle autosuggestions for such strings even in case of
				// partial searches.				
					if (!StringUtils.startsWith(searchStringParam, "*")) {
						luceneQueryString.append("*" + searchStringParam.toLowerCase().trim() + "* ");
					} else {
						luceneQueryString.append(searchStringParam.toLowerCase().trim() + "* ");
					}
			} else {
				luceneQueryString.append(searchStringParam + " ");
			}

		}
		String luceneQueryStringtemp = StringUtils.replace(luceneQueryString.toString().trim(), ")*", "*)");
		return luceneQueryStringtemp.trim();
	}

}
