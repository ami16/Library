package edu.cursor.library.user.service;

import java.util.List;
import edu.cursor.library.user.entity.TblUser;

public interface UserService {

	List<TblUser> getUserList(); 
	void showUserList(); 
	void deleteUser(int choice); 
	void addUser(TblUser user); 
	void showUserProfile(TblUser user); 
}