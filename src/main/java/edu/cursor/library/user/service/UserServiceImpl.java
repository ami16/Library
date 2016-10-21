package edu.cursor.library.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.cursor.library.security.register.RegisterImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.utils.IOCsv;


public class UserServiceImpl implements UserService {
	private RegisterImpl register = new RegisterImpl();
	private static List<TblUser> userList = new ArrayList<>();
	private String path = System.getProperty("user.dir")
		+ "/src/main/java/edu/cursor/library/user/database/userList.csv";

	@Override
	public List<TblUser> getUserList() {
		Collections.addAll(userList, IOCsv.readFile(path));
		return userList;
	}

	@Override
	public void deleteUser(int choice) {
		for (Iterator it = userList.iterator(); it.hasNext();) {
			TblUser user = (TblUser) it.next();
			if (user.getId() == choice) {
				it.remove();
				IOCsv.writeFile(userList, path);
			}
		}
	}

	@Override
	public void addUser(TblUser newUser) {
		userList.add(new TblUser(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.geteMail(),
			newUser.getMobileNum(), newUser.getAddress(), newUser.getDateOfRegistration(), newUser.getRole()));
		IOCsv.writeFile(userList, path);
	}
}