package edu.cursor;

import java.util.ArrayList;
import java.util.List;

public class UserList {
	List<User> userList = new ArrayList<User>();

	public void createUserList() {
		userList.add(new User("peter_griffin", "peter1234", "peter@gmail.com", "Peter", 0));
		userList.add(new User("lois", "lois1234", "lois@gmail.com", "Lois", 0));
		userList.add(new User("stewie_best", "coolStew", "stewie@gmail.com", "Stewie", 0 ));
		userList.add(new User("surfin_bird", "pticasinica25", "sufrin_bird@gmail.com", "Bird", 0 ));
	}

}
