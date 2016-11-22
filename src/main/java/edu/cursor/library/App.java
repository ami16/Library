package edu.cursor.library;

import edu.cursor.library.service.LibraryServiceImpl;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class App {



   public static void main(String[] args) {
      log.debug("Library start working");
      LibraryServiceImpl lib = new LibraryServiceImpl();
      lib.start();
   }

}