package edu.cursor.library.service;

import java.util.List;

import edu.cursor.library.model.TblBook;
import edu.cursor.library.infrastructure.exception.ISBNFormatException;
import edu.cursor.library.model.TblUser;

public interface UserService {

//	List<TblUser> getUserList();
//	void showUserList();
//	void showUserProfile(TblUser user) throws ISBNFormatException;


	void takeBookRequest(TblUser user) throws ISBNFormatException;
	void takeBook(TblUser user, TblBook book);
	void returnBook(TblUser user, TblBook book);

	// for admin
//	void deleteUser(int userId);
//	boolean addUser(TblUser user);
}