package edu.cursor.library.user.service;

import java.util.List;
import edu.cursor.library.user.entity.TblUser;

public interface UserService {

	List<TblUser> getUserList();
    List<TblUser> deleteUser(int choice);
    List<TblUser> addUser(TblUser user);
}
