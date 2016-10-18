package edu.cursor.library.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.utils.CSVUserUtil;

public class UserServiceImpl implements UserService {
	public static void main(String args[]) {
		UserServiceImpl ser = new UserServiceImpl();
		System.out.println(ser.createUserList());
	}
	private static List<TblUser> userList;
	private String path = System.getProperty("user.dir") + "/src/main/java/edu/cursor/library/user/database/userList.csv";
	 
    @Override
	public List<TblUser> createUserList(){
		List <TblUser> userList = new ArrayList<>();
        Collections.addAll(userList, CSVUserUtil.readFile(path));
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
