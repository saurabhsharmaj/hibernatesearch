import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.sakila.entity.Actor;
import com.sakila.entity.City;
import com.sakila.entity.Country;
import com.sakila.entity.Film;
import com.sakila.utils.HibernateUtil;


public class StartUpManager {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		//new StartUpManager().SearchByActor(session,19);				

		//		new StartUpManager().SearchByActor(session, 19);
		/*new StartUpManager().SearchByFilm(session, 1);
		new StartUpManager().getCities(session,new Integer[]{44});
		new StartUpManager().getCountries(session,new Integer[]{231});*/
	}

	//OneTOMany
	public void getCities(Session session, Integer[]countryIds){		
		Criteria cr = session.createCriteria(Country.class);
		cr.add(Restrictions.in("countryId",countryIds));       
		List<Country> resultList = cr.list();
		System.out.println("county size: "+resultList.size());
		for (Country country : resultList) {			
			System.out.println(country);
			Set<City> cities = country.getCities();
			System.out.println("cities size: "+cities.size());
			for (City city : cities) {
				System.out.println(city);
			}

		}

	}

	//ManyToOne
	public void getCountries(Session session, Integer[]cityIds){		
		Criteria cr = session.createCriteria(City.class);
		cr.add(Restrictions.in("cityId",cityIds));
		// cr.createAlias("country_id", "xcountry_id");
		List<City> resultList = cr.list();
		System.out.println("City size: "+resultList.size());
		for (City city : resultList) {			
			System.out.println(city);
			Country country = city.getCountry();
			System.out.println(country);

		}

	}

	//ManyTOMany
	public void SearchByFilm(Session session,Integer filmId){
		Criteria cr = session.createCriteria(Film.class);
		cr.add(Restrictions.eq("filmId", filmId));       
		List<Film> resultList = cr.list();
		System.out.println(resultList+ "num of Films:" + resultList.size());
		for (Film next : resultList) {
			Set<Actor> actors= next.getActors();
			System.out.println(resultList+ "num of Actors:" + actors.size());
			for (Actor actor : actors) {
				System.out.println(actor);
			}
			System.out.println("============= X =============== X ============== X ==================");
		}
	}

	//ManyToMany
	public void SearchByActor(Session session,Integer actorId){
		Criteria cr = session.createCriteria(Actor.class);
		cr.add(Restrictions.eq("actorId", actorId));       
		List<Actor> resultList = cr.list();
		System.out.println(resultList+ "num of employess:" + resultList.size());
		for (Actor next : resultList) {
			Set<Film> films= next.getFilms();
			System.out.println(resultList+ "num of Films:" + films.size());
			for (Film film : films) {
				System.out.println(film);
			}
			System.out.println("============= X =============== X ============== X ==================");
		}
	}

}
