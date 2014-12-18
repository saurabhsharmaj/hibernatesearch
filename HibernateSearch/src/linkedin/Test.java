package linkedin;

import java.util.ArrayList;
import java.util.List;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<User> users = new ArrayList<User>();
		User u1;
		users.add(u1=new User("saurabh"));
		users.add(u1);
		users.add(u1);
		users.add(u1);
		System.out.println(u1);
		users.remove(u1);

		System.out.println("Existing:");
		for (User user : users) {
			System.out.println(user);
		}
		
		String []geoGraphyArray = new String[]{"India", "-USA","Canada"};
		StringBuffer msgTextBuffer = new StringBuffer();
		for (String geography : geoGraphyArray) {
            String countryName = geography
                    .replaceFirst("-", "").trim();
            msgTextBuffer.append("\"" + countryName + "\", ");
        }
		
	    String queryString = msgTextBuffer.toString();
       /* int charIndex = queryString.lastIndexOf("OR");
        if (charIndex > 0) {
            queryString = queryString.substring(0, charIndex);
        }
        query.append(queryString);
	    query.append(" ) AND");		*/	
				
		
		    System.out.println(queryString.replaceAll(", $", ""));
	}

}
