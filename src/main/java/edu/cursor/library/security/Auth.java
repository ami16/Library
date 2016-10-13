package edu.cursor.library.security;

import edu.cursor.library.user.entity.TblUser;

public interface Auth {

   void logIn(TblUser user);
   void logOut() ;

}