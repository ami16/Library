package edu.cursor.library.service;

import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;
import edu.cursor.library.security.auth.AuthImpl;

public interface Library {

   public void start() ;

   void showMainMenu();
   void showMainMenuSimple() ;
   void showMainMenuLoggedIn() ;
   void showMainMenuAdmin() ;

   void replyReader(int type) throws ISBNFormatException;


   void sayBye();
}