package edu.cursor.library.model.DAO;

import edu.cursor.library.model.TblUser;
import java.util.List;


public interface UserDAO {

   List<TblUser> findAll();
   TblUser findById( int id );
   long createUser(TblUser user);
   void updateUser(TblUser user);
   void deleteUser(int userId);

}