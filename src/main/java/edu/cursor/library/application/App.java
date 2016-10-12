package edu.cursor.library.application;

import edu.cursor.library.service.LibraryImpl ;

public class App {

   public static void main(String[] args) {
      LibraryImpl lib = new LibraryImpl() ;
      lib.start();
   }

}