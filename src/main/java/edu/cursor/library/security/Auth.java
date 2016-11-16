package edu.cursor.library.security;

import edu.cursor.library.model.TblUser;

public interface Auth {

   void logIn(TblUser user);
   void logOut() ;

}