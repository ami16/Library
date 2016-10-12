package edu.cursor.library.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;
import org.apache.commons.lang3.text.*;
import org.joda.time.LocalDate;

public class Test {
   public Test() {
   }

   public static void main(String[] args) {
      TblBook book = new TblBook(111, "Author", "Title", LocalDate.parse("2016"), LocalDate.parse("2000"), Genre.HISTORY) ;
      System.out.println(book);
      System.out.print( book.getISBN() + ", " + book );
   }
}