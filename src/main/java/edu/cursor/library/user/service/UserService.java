package edu.cursor.library.user.service;

import java.util.List;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;
import edu.cursor.library.user.entity.TblUser;

public interface UserService {

	List<TblUser> getUserList();
	void showUserList();
//	void deleteUser(int choice);
//	void addUser(TblUser user);
	void showUserProfile(TblUser user) throws ISBNFormatException;


	void takeBookRequest(TblUser user) throws ISBNFormatException;
	void takeBook(TblUser user, TblBook book);
	void returnBook(TblUser user, TblBook book);

	// for admin
	void deleteUser(int userId);
	boolean addUser(TblUser user);
}