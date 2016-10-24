package edu.cursor.library.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

import edu.cursor.library.service.LibraryImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;
import edu.cursor.library.user.utils.IOCsv;

public class UserServiceImpl implements UserService {
	private static List<TblUser> userList = new ArrayList<>();
	private String path = System.getProperty("user.dir")
			+ "/src/main/resources/userList.csv";
    private static LibraryImpl lib = LibraryImpl.getInstance();
    
    public UserServiceImpl() {
    	createUserList();
	}
    
    public void createUserList() {
    	Collections.addAll(userList, IOCsv.readFile(path));
    }
	@Override
	public List<TblUser> getUserList() {
		return userList;
	}
	
	@Override
	public void deleteUser(int choice) {
		try {
		for (Iterator it = userList.iterator(); it.hasNext();) {
			TblUser user = (TblUser) it.next();
			if (user.getId() == choice) {
				it.remove();
				IOCsv.writeFile(userList, path);
				System.out.println("User removed successfully.");
			}
		}
		} catch (Exception e) {
			System.out.println("User is not found");
		} finally { lib.showUserSubMenuAdmin();
		}
		}
	
    @Override
	public void addUser(TblUser newUser) {
		try {
		userList.add(new TblUser(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.geteMail(),
				newUser.getMobileNum(), newUser.getAddress(), newUser.getDateOfRegistration(), newUser.getRole()));
		IOCsv.writeFile(userList, path);
		System.out.println("Profile created successfully.");
		} catch (IllegalArgumentException ie) {
			System.out.println("Something wrong. Try again");
		} finally {  // always return users menu. Because we added user..need fix
//			if (newUser.getRole().equals(Role.ADMIN)) {
//				lib.showUserSubMenuAdmin();
//				}
//				else lib.showMainMenuLogged();
		}
	}

	@Override
	public void showUserProfile(TblUser user) {
		try {
		for (Iterator it = userList.iterator(); it.hasNext();) {
	    TblUser userId = (TblUser) it.next();
		if (userId.getId().equals(user.getId()));
		 } 
		System.out.println(user.toString());
		} catch (Exception ue) {
			System.out.println("User not found");
		}
		finally {
			if (user.getRole().equals(Role.ADMIN)) {
			lib.showMainMenuAdmin();
			}
			else lib.showMainMenuLogged();
		}
	}
	}