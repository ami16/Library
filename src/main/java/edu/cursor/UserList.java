package edu.cursor;

import java.util.ArrayList;
import java.util.List;

public class UserList {
	List<User> userList =  new ArrayList<User>();

	public void createUserList() {
		userList.add(new User("Peter", "peter_griffin", "peter@gmail.com", "peter1234"));
		userList.add(new User("Lois", "lois", "lois@gmail.com", "lois1234"));
		userList.add(new User("Stewie", "stewie_best","stewie@gmail.com", "coolStew" ));
		userList.add(new User("Bird", "surfin_bird", "sufrin_bird@gmail.com", "pticasinica25"));
	}

}
