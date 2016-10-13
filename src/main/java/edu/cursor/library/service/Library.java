package edu.cursor.library.service;

import edu.cursor.library.security.AuthImpl;

public interface Library {

   public void start() ;

   void showMainMenu(boolean userIsLogged, AuthImpl auth) ;
   void showMainMenuSimple() ;
   void showMainMenuLogged() ;
   void showMainMenuAdmin() ;

   void replyReader(int type);


   void sayBye();
}