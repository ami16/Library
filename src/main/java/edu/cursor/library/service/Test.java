package edu.cursor.library.service;

import edu.cursor.library.book.entity.Book2;
import edu.cursor.library.book.enums.Genre;
import org.joda.time.LocalDate;

public class Test extends Book2 {

   public Test() {
      super( 111, "Author", "Title", LocalDate.parse("2016"), LocalDate.parse("2000"), Genre.HISTORY );
   }



   public static void main(String[] args) {
      Book2 book = new Book2(111, "Author", "Title", LocalDate.parse("2016"), LocalDate.parse("2000"), Genre.HISTORY) ;
      System.out.println(book);
      System.out.print( book.getISBN() + ", " + book );
   }
}