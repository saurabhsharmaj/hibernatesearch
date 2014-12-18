package com.sakila.entity;

public enum Rating {
	G("G"), PG("PG"), R("R");
private String rating;

private Rating(String rating) {
	this.rating = rating;
}

public String getValue(){
	return rating;
}


}
