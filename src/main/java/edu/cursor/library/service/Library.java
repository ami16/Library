package edu.cursor.library.service;

import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;

public interface Library {

   public void start() ;

   void showMainMenu();
   void showMainMenuSimple() ;
   void showMainMenuLoggedIn() ;
   void showMainMenuAdmin() ;

   void replyReader(int type) throws ISBNFormatException;


   void sayBye();
}