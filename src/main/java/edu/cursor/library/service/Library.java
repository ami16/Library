package edu.cursor.library.service;

import edu.cursor.library.security.AuthImpl;

public interface Library {

   public void start() ;
   public void showMainMenu(boolean userIsLogged, AuthImpl auth) ;
   public void showMainMenuLogged() ;
   public void showMainMenuAdmin() ;
   public void showMainMenuSimple() ;

}