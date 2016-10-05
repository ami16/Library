package edu.cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BookFunctions {

   public static List<Book> bookList = new ArrayList<>();

   public static List<Book> getBookList() {
      return bookList;
   }

   public static List<Book> getBookList(int userId) {
      List<Book> result = bookList.stream()
          .filter( book -> (book.getTakenBy() == userId) )
          .collect(Collectors.toList());
      return result ;
   }

   public static void setBookList(List<Book> _bookList) {
      bookList = _bookList;
   }

   /**
    * @since 0.4
    * When user is logged in
    * he can see his books taken
    */
   public static void showMyBooksMenu( int _id ){

      Auth auth = Auth.getInstance();

      boolean is = false;
      Scanner scan = new Scanner(System.in);
      String userReply = "";

      do{
         if( Book.getUserBooks( auth.getLoggedUser().getId() ).size() == 0 ) {
            System.out.println("You have no books taken. \nz - to Main menu");
         } else {
            System.out.println("r - Return book \nz - to Main menu");
         }

         userReply = scan.nextLine().trim();
         if( userReply.equalsIgnoreCase("r") ){
            is = true;
            UserFunctions.returnBook( auth.getLoggedUser().getId() );
            Book.showUserBooks( auth.getLoggedUser().getId() );
            showMyBooksMenu( auth.getLoggedUser().getId() );
         }
         if( userReply.equalsIgnoreCase("z") ){
            is = true;
         }

      } while(!is) ;
   }

   public static List<Book> createBookList() {

      bookList.add(new Book(1000000005, "Mothers, Tell Your Daughters", "Bonnie Jo Campbell", BookCategories.BIOGRAPHY, 2015, 1980, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000009, "Elon Musk: Tesla, SpaceX & Fantastic Future", "Ashlee Vance", BookCategories.BIOGRAPHY, 2015, 2014, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000015, "The Jemima Code", "Toni Tipton-Martin", BookCategories.BIOGRAPHY, 2000, 1992, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000016, "The Wright Brothers", "David McCullough", BookCategories.BIOGRAPHY, 2016, 1897, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000021, "Fortune Smiles", "Adam Johnson", BookCategories.BIOGRAPHY, 2007, 1943, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000031, "The Fifth Season", "N.K. Jemisin", BookCategories.BIOGRAPHY, 2015, 2000, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000032, "Nimona", "Noelle Stevenson", BookCategories.BIOGRAPHY, 2011, 1993, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000037, "Real Men Don't Sing", "Allison McCracken", BookCategories.BIOGRAPHY, 2016, 2011, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000002, "Accidental Saints", "Nadia Bolz-Weber", BookCategories.LOVE_STORY, 2015, 1914, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000007, "Undermajordomo Minor", "Patrick deWitt", BookCategories.LOVE_STORY, 2000, 1943, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000010, "Adventures Of Lovelace And Babbage", "Sydney Padua", BookCategories.LOVE_STORY, 2001, 1951, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000019, "The Bear Ate Your Sandwich", "Julia Sarcone-Roach", BookCategories.LOVE_STORY, 2001, 1898, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000020, "Among The Wild Mulattos", "Tom Williams", BookCategories.LOVE_STORY, 2013, 1991, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000033, "The Sellout", "Paul Beatty", BookCategories.LOVE_STORY, 2004, 1897, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000036, "Fates And Furies", "Lauren Groff", BookCategories.LOVE_STORY, 1997, 1880, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000003, "Slade House", "David Mitchell", BookCategories.FICTION, 2010, 1950, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000004, "Spy Games", "Adam Brookes", BookCategories.FICTION, 2000, 1949, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000012, "Strangers Drowning", "Larissa MacFarquhar", BookCategories.FICTION, 2003, 1939, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000013, "Honeydew", "Edith Pearlman", BookCategories.FICTION, 2010, 1977, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000008, "All The Old Knives", "Olen Steinhauer", BookCategories.FICTION, 2016, 1913, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000014, "The Unauthorised Life", "Jonathan Bate", BookCategories.FICTION, 2000, 1965, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000023, "Habitat", "Lauren Liess", BookCategories.FICTION, 2010, 1997, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000026, "Death And Mr. Pickwick", "Stephen Jarvis", BookCategories.FICTION, 1999, 1954, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000011, "Small Plates To Share", "Ghillie Başan", BookCategories.TALES, 2014, 1898, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000001, "Peter Pan", "Author The First", BookCategories.TALES, 2003, 1918, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000018, "The Turnip Princess", "Franz-Xaver von Schönwerth", BookCategories.TALES, 2014, 1886, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000022, "A History of Japan", "Shigeru Mizuki", BookCategories.TALES, 2007, 2005, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000024, "A Spool Of Blue Thread", "Anne Tyler", BookCategories.TALES, 1999, 1931, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000034, "Hotels of North America", "Rick Moody", BookCategories.TALES, 2010, 1995, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000035, "Kitchen Hacks", "America's Test Kitchen", BookCategories.TALES, 1997, 1905, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000006, "Ancillary Mercy", "Ann Leckie", BookCategories.FANTASY, 2001, 1919, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000017, "Secrets Of State", "Matthew Palmer", BookCategories.FANTASY, 2004, 1880, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000025, "March", "Andrew Aydin", BookCategories.FANTASY, 2014, 1995, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000027, "Home", "Carson Ellis", BookCategories.FANTASY, 2012, 1971, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000028, "Listen Slowly", "Thanhhà Lại", BookCategories.FANTASY, 2015, 1881, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000029, "Murder On Steep Street", "Heda Margolius Kovály", BookCategories.FANTASY, 1999, 1962, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000030, "The Shepherd's Crown", "Terry Pratchett", BookCategories.FANTASY, 2011, 1934, 1, BookStates.AVAILBALE));

      return bookList;
   }

   public static boolean validateISBN( String val ){
      try{
         return val.matches("^(\\d{10})$");
      } catch (NumberFormatException e){
         return false;
      }

   }

}