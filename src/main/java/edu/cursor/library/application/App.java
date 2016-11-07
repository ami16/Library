package edu.cursor.library.application;

import edu.cursor.library.service.LibraryImpl ;
import org.apache.log4j.Logger;


public class App {

   private static final Logger log = Logger.getLogger(App.class);

   public static void main(String[] args) {
      LibraryImpl lib = new LibraryImpl();
      lib.start();
   }

}