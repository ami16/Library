package edu.cursor.library.user.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import edu.cursor.library.user.entity.TblUser;

public class UserServiceImpl implements UserService {
	public static final String WRONG_CHOICE = "Incorrect item. Try again";
	private static List<TblUser> userList;
	private static Scanner sc;
	private String path = System.getProperty("user.dir") + "/src/main/java/edu/cursor/library/user/database/573.csv";
	 BufferedReader bfr = null;
	 
@Override
	public List<TblUser> createUserList(){
		List <TblUser> userList = new ArrayList<>();
        Collections.addAll(userList, CSVUtils.readFile(bfr, path));
        return userList;
		}

	@Override
	public void viewBookList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeBook(TblBook book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void returnBook(TblBook book) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<TblUser> deleteUser(TblUser user) {
		// TODO Auto-generated method stub
		return userList;
	}
	@Override
	public List<TblUser> addUser(TblUser user) {
		// TODO Auto-generated method stub
		return userList;
	}
}