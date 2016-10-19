package edu.cursor.library.user.service;

import java.util.List;
import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.user.entity.TblUser;

public interface UserService {
	
	// for user
//	List<TblUser> createUserList();
	void viewBookList();
	void takeBook(TblBook book);
	void returnBook(TblBook book);
	
	// for admin
      List<TblUser> deleteUser(TblUser user);
      List<TblUser> addUser(TblUser user);
}
