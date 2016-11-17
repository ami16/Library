package edu.cursor.library.service;

import edu.cursor.library.util.BookDaoUtils;
import edu.cursor.library.infrastructure.persistence.MySqlBookDao;
import edu.cursor.library.model.BookComparator;
import edu.cursor.library.model.BookGenre;
import edu.cursor.library.model.TblBook;
//import edu.cursor.library.infrastructure.persistence.GenreUtils;
//import edu.cursor.library.infrastructure.persistence.IOUtils;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

//   private static String projPath = System.getProperty("user.dir"),
//       dbPath = "/src/main/resources/",
//       fileName = "bookList.csv",
//       file = projPath + dbPath + fileName;
   private static BookServiceImpl instance;

//   private CSVFileBookDao bookDao = new CSVFileBookDao() ;
   private MySqlBookDao bookDao = new MySqlBookDao() ;
   private BookDaoUtils bookDaoUtils = new BookDaoUtils();
   private Scanner scan = new Scanner(System.in);



   private BookServiceImpl() {
   }

   public static BookServiceImpl getInstance() {
      if (instance == null) {
         synchronized (BookServiceImpl.class) {
            if (instance == null) {
               instance = new BookServiceImpl();
            }
         }
      }
      return instance;
   }

   public List<TblBook> readBookList() {
      return bookDao.findAll();
   }

   public Map<TblBook, Long> getBookListGroupedMap() {
      Comparator<TblBook> cmpr = BookComparator.ISBN_;

      Map<TblBook, Long> map = readBookList().stream()
         .collect(
             Collectors.groupingBy(
                 i -> i, Collectors.counting()
             ));

      SortedMap<TblBook, Long> newMap = new TreeMap<>(cmpr);
      newMap.putAll(map);

      return newMap ;
   }

   public Map<TblBook, Long> getBookListGroupedMap( Comparator<TblBook> comparator ) {

      Map<TblBook, Long> map = readBookList().stream()
          .collect(
          Collectors.groupingBy(
              i -> i, Collectors.counting()
          ));

      SortedMap<TblBook, Long> newMap = new TreeMap<>(comparator);
      newMap.putAll(map);

      return newMap ;
   }

//   @Override
   public void viewBookListGrouped() {

      List<TblBook> bookList = readBookList();

      if (bookList.size() == 0) {
         bookList = readBookList();
      }
      bookList.stream().collect(
          Collectors.groupingBy(
              i -> i, Collectors.counting()
          ))
          .forEach((k, v) -> System.out.print(k + ", (" + v + "); \n"));
   }

   public void viewBookListGrouped(Comparator<TblBook> comparator) {
      Map<TblBook, Long> map = readBookList().stream()
          .collect(
             Collectors.groupingBy(
                 i -> i, Collectors.counting()
             )
          );

      SortedSet<TblBook> keys = new TreeSet<TblBook>(comparator);
      keys.addAll(map.keySet());

      for (TblBook book : keys) {
         System.out.print(book + ", (" + map.get(book) + "); \n");
      }
   }

   public TblBook getBookById(int isbn) {
      return bookDao.findById(isbn).get(0);
   }

   public List<TblBook> getBooksById(int isbn) {
      return readBookList().stream()
          .filter(i -> i.getISBN().equals(isbn) )
          .collect(Collectors.toList());
   }





   public boolean bookExists(int isbn) {
      return bookDao.findById(isbn).stream()
          .anyMatch(s -> s.getISBN() == isbn) ;
   }

   public boolean validateISBN( String val ){
      try{
         return val.matches("^(\\d{1,10})$");
      } catch (NumberFormatException e){
         return false;
      }
   }

   public boolean validateAuthor( String val ){
      try{
         return val.matches("^((?!(\\d+|\\$|\\#|\\!|\\@|\\%|\\^|\\&|\\*))[\\s\\S]){3,}$");
      } catch (NumberFormatException e){
         return false;
      }
   }

   public boolean validateTitle( String val ){
      try{
         return val.matches("^((?!\\s{2}).)*$");
      } catch (NumberFormatException e){
         return false;
      }
   }

   public boolean validateDate( String val ){
      /**
       * Validation for JodaTime
       * <b>YYY-MM-DD</b>
       */
      try{
         return val.matches("^\\d{4}\\-([0][1]|[0][1-9]|[1][0-2])\\-([0][1]|[0][1-9]|[1,2][0-9]|[3][0-1])$");
      } catch (NumberFormatException e){
         return false;
      }
   }

   public boolean validateGenre( String val ){
      /**
       * 1: FICTION,
       * 2: FANTASY,
       * 3: HISTORY,
       * 4: MYSTERY,
       * 5: HORROR,
       * 6: ENCYCLOPEDIA,
       * 7: COMEDY,
       * 8: UNKNOWN
       */
      try{
         int userChoice = Integer.parseInt(val);
         return userChoice <= 8 && userChoice > 0;
      } catch( Exception e ){
         return false;
//         e.printStackTrace();
      }

//      return false;
   }


   public boolean readIsbn( String isbn ){
      return validateISBN( isbn );
   }

   public boolean readAuthor( String author ){
      return validateAuthor( author );
   }

   public boolean readTitle( String title ){
      return validateTitle( title );
   }

   public boolean readDate( String date ){
      return validateDate(date);
   }

   public boolean readReplyGenre(String val ){
      return validateGenre( val );
   }




   public void changeIsbn(TblBook book){

      boolean correctIsbn = false ;
      do{
         System.out.println("ISBN: " + book.getISBN() + ". Input new ISBN: ");
         String userIsbn = scan.nextLine().trim();
         correctIsbn = validateISBN( userIsbn );
         if( !correctIsbn ){
            System.out.println("Incorrect ISBN.");
         } else {
            book.setISBN( Integer.parseInt(userIsbn) );
            bookDao.updateBook(book);
         }
      } while ( !correctIsbn );
   }

   public void changeAuthor(TblBook book){

      boolean correctAuthor = false ;
      do{
         System.out.println("Author: " + book.getAuthor() + ". Input new Author: ");
         String userAuthor = scan.nextLine().trim();
         correctAuthor = validateAuthor( userAuthor );
         if( !correctAuthor ){
            System.out.println("Incorrect Author.");
         } else {
            book.setAuthor( userAuthor );
            bookDao.updateBook(book);
         }
      } while ( !correctAuthor );
   }

   public void changeTitle(TblBook book){

      boolean correctTitle = false ;
      do{
         System.out.println("Title: " + book.getTitle() + ". Input new Title: ");
         String userTitle = scan.nextLine().trim();
         correctTitle = validateTitle( userTitle );
         if( !correctTitle ){
            System.out.println("Incorrect Title.");
         } else {
            book.setTitle( userTitle );
            bookDao.updateBook(book);
         }
      } while ( !correctTitle );
   }

   public void changeDate(TblBook book, int dateWrittenPublished){
      boolean correctDate = false ;

      StringBuilder dateChoice = new StringBuilder();
      switch (dateWrittenPublished){
         case 1: dateChoice.append("Written"); break;
         case 2: dateChoice.append("Published"); break;
      }

      do{
         System.out.println( dateChoice + " Date: " + book.getWritYear() + ". Input new date: ");
         String userDate = scan.nextLine().trim();
         correctDate = validateDate( userDate );
         if( !correctDate ){
            System.out.println("Incorrect Date.");
         } else {
            if( dateWrittenPublished == 1 ) {
               book.setWritYear(LocalDate.parse(userDate));
            } else if( dateWrittenPublished == 2 ) {
               book.setPublYear(LocalDate.parse(userDate));
            }
            bookDao.updateBook(book);
         }
      } while ( !correctDate );
   }


   public Set<BookGenre> inputGenre(){
      Set<BookGenre> genresList = new HashSet<>();
      boolean enoughGenres = false ;
      do {

         boolean correctGenre = false;
         do {
            // 6. Genres
            System.out.println("Chose genre(s) (one by one)");
            System.out.println("1: FICTION, 2: FANTASY, 3: HISTORY, 4: MYSTERY, 5: HORROR, 6: ENCYCLOPEDIA, " +
                "7: COMEDY, 8: UNKNOWN");
            String userReplyGenre = scan.nextLine().trim();
            if (userReplyGenre.equalsIgnoreCase("x")) {
               break;
            }
            correctGenre = readReplyGenre(userReplyGenre);

            if (!correctGenre) {
               System.out.println("Incorrect genre");
            } else {
               genresList.add(BookGenre.getGenre(Integer.parseInt(userReplyGenre)));
               System.out.println("Input one more genre? (y/n):");
               String userYesNo = scan.nextLine().trim();

               if( userYesNo.equalsIgnoreCase("n")  ){
                  enoughGenres = true;
               } else if( userYesNo.equalsIgnoreCase("y") ) {
                  enoughGenres = false;
               }
            }

         } while (!correctGenre);
      } while ( !enoughGenres ) ;

      return genresList;
   }

   public void changeGenre( TblBook book, Set<BookGenre> genres){
      book.setGenre( bookDaoUtils.convertSetToEnumSet( genres ) );
      bookDao.updateBook(book);
   }


//   public static void main(String[] args) {
//      BookServiceImpl bookService = new BookServiceImpl();
//      System.out.println(bookService.bookDao.findById(27));
//   }
}