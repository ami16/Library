package edu.cursor.library.ui;


import edu.cursor.library.util.BookDaoUtils;
import edu.cursor.library.infrastructure.persistence.MySqlBookDao;
import edu.cursor.library.model.BookGenre;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.security.SecurityServiceImpl;
import edu.cursor.library.security.SingleUserAuthImpl;
import edu.cursor.library.service.BookServiceImpl;
import edu.cursor.library.service.UserBooksRegistryServiceImpl;
import org.joda.time.LocalDate;

import java.util.*;


public class BookMenu {

   private BookServiceImpl bookService = BookServiceImpl.getInstance();
   private UserBooksRegistryServiceImpl userBooksRegistry = new UserBooksRegistryServiceImpl();
   private Scanner scan = new Scanner(System.in);
//   private CSVFileBookDao bookDao = new CSVFileBookDao();
   private MySqlBookDao bookDao = new MySqlBookDao();
   private BookDaoUtils bookDaoUtils = new BookDaoUtils();
   private SecurityServiceImpl security = SecurityServiceImpl.getInstance();

   public BookMenu() {
   }


   public void promptAddNewBook(){
      int userId = 0;
      boolean correctISBN = false;
      int userIsbn = 0;
      boolean correctAuthor = false;
      String userAuthor = "";
      boolean correctTitle = false;
      String userTitle = "";
      boolean correctWrittenDate = false;
      String userWrittenDate = "";
      boolean correctPublishedDate = false;
      String userPublishedDate = "";
//      List<BookGenre> genresList = new ArrayList<>();
      Set<BookGenre> genresList = new HashSet<>();
      TblBook sameBook = null;

      do{

         //0. Id
         userId = bookService.getNewBookId();

         // 1. ISBN
         System.out.println("1. Input ISBN");
         String userReplyBookIsbn = scan.nextLine().trim();
            if( userReplyBookIsbn.equalsIgnoreCase("x") ){ break; }
         correctISBN = bookService.readIsbn( userReplyBookIsbn );

         if(correctISBN ){
            userIsbn = Integer.parseInt(userReplyBookIsbn);
            if( bookService.bookExists( Integer.parseInt(userReplyBookIsbn) ) ){
               sameBook = bookService.getBookById( Integer.parseInt(userReplyBookIsbn) );
               bookDao.createBook( sameBook );
               System.out.println("Same book. So I've just copied that book for ya.");
               break;
            }


            do{
               // 2. Author
               System.out.println("2. Input Author's full name, min 3 chars (no spec symbols & numbers allowed)");
               String userReplyBookAuthor = scan.nextLine().trim();
                  if( userReplyBookAuthor.equalsIgnoreCase("x") ){ break; }
               correctAuthor = bookService.readAuthor( userReplyBookAuthor );

               if( !correctAuthor ){ System.out.println( "Incorrect input" ); } else {
                  userAuthor = userReplyBookAuthor;
               }

            } while( !correctAuthor ) ;


            do{
               // 3. Title
               System.out.println("3. Book Title please (2 chars min)... ");
               String userReplyBookTitle = scan.nextLine().trim();
                  if( userReplyBookTitle.equalsIgnoreCase("x") ){ break; }
               correctTitle = bookService.readTitle( userReplyBookTitle );

               if( !correctTitle ){ System.out.println("Incorrect title"); } else {
                  userTitle = userReplyBookTitle;
               }

            } while ( !correctTitle );


            do{
               // 4. Written date
               System.out.println( "4. Input written date (YYYY-MM-DD)" );
               String userReplyWrittenDate = scan.nextLine().trim();
                  if( userReplyWrittenDate.equalsIgnoreCase("x") ){ break; }
               correctWrittenDate = bookService.readDate( userReplyWrittenDate );

               if( !correctWrittenDate ){ System.out.println("Incorrect date"); } else {
                  userWrittenDate = userReplyWrittenDate;
               }

            } while( !correctWrittenDate );


            do{
               // 5. Published date
               System.out.println("5. Input published date (YYYY-MM-DD)");
               String userReplyPublishedDate = scan.nextLine().trim();
                  if( userReplyPublishedDate.equalsIgnoreCase("x") ){ break; }
               correctPublishedDate = bookService.readDate( userReplyPublishedDate );

               if( !correctPublishedDate ){ System.out.println("Incorrect date"); } else {
                  userPublishedDate = userReplyPublishedDate;
               }

            } while( !correctPublishedDate );

            genresList = bookService.inputGenre() ;

         }
         else {
            System.out.println( " -- Some trouble placeholder 1. ISBN -- " );
         }
      } while (!correctISBN);
      System.out.println("All done! : ");


      if( !bookService.bookExists( userIsbn ) && sameBook == null ){

         System.out.println("ISBN: " + userIsbn);
         System.out.println("Author: " + userAuthor);
         System.out.println("Title: " + userTitle);
         System.out.println("Written: " + userWrittenDate);
         System.out.println("Published: " + userPublishedDate);
         System.out.println("genre: " + genresList);

         bookDao.createBook(
             new TblBook(
                     userId,
                 userIsbn,
                 userAuthor,
                 userTitle,
                 LocalDate.parse(userPublishedDate),
                 LocalDate.parse(userWrittenDate),
                 bookDaoUtils.convertSetToEnumSet(genresList)
             )
         );
      }
   }


   public void promptEditBook(){
      SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
      if(!auth.userCanCrud()){
         System.out.println("U're not allowed...");
      } else {

         boolean is = false;
         do{
            System.out.println("Input book ISBN to edit: ");
            String userReplyBookIsbn = scan.nextLine().trim();
               if( userReplyBookIsbn.equalsIgnoreCase("x") ){ break; }
            is = bookService.readIsbn( userReplyBookIsbn );

            int userIsbn = Integer.parseInt(userReplyBookIsbn);

            if( bookService.bookExists( userIsbn ) ){
               showBookProfile( bookService.getBookById(userIsbn) );
            } else {
               System.out.println("Seems like no such book here...");
               is = false;
            }
         } while (!is);
      }
   }


   public void showBookProfile(TblBook book){

      System.out.println("---------- Book Profile ---------------");
      System.out.println("ISBN: " + book.getISBN() );
      System.out.println("Author:  " + book.getAuthor() );
      System.out.println("Title: " + book.getTitle() );
      System.out.println("Written date: " + book.getWritYear() );
      System.out.println("Published date: " + book.getPublYear() );
      System.out.println("Genre(s): " + book.getGenre() );
      System.out.println("----------------------------------------");


      Scanner scan = new Scanner(System.in);
      String userReply = "";
      boolean is = false;
      Set<BookGenre> genresList = new HashSet<>();

      System.out.print( "Edit book profile: \n" +
          "i: ISBN \n" +
          "a: Author \n" +
          "t: Title \n" +
          "w: Written date \n" +
          "p: Published date \n" +
          "g: Genre(s)\n"
      ) ;
      System.out.print("\nz: Main Menu \n");


      do{
         System.out.println("Your choice: ");
         userReply = scan.nextLine().trim();
         switch (userReply.toLowerCase()){
            case "i":
               bookService.changeIsbn(book);
               showBookProfile(book);
               is=true;
               break;
            case "a":
               bookService.changeAuthor(book);
               showBookProfile(book);
               is=true;
               break;
            case "t":
               bookService.changeTitle(book);
               showBookProfile(book);
               is=true;
               break;
            case "w":
               bookService.changeDate(book, 1);
               showBookProfile(book);
               is=true;
               break;
            case "p":
               bookService.changeDate(book, 2);
               showBookProfile(book);
               is=true;
               break;
            case "g":
               genresList = bookService.inputGenre();
               bookService.changeGenre(book, genresList);
               showBookProfile(book);
               is=true;
               break;
            case "z":
               is = true;
               break;
            default:
               System.out.println("Incorrect choice");
               is=false;
         }
      } while (!is);
   }

   public void promptDeleteBook(){
      SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
      boolean is = false;

      if(!auth.userCanCrud()){
         System.out.println("U're not allowed...");
      } else {

         do {
            System.out.println("Input book ISBN to delete: ");
            String userIsbn = scan.nextLine().trim();
               if( userIsbn.equalsIgnoreCase("x") ){ break; }
            boolean validIsbn = bookService.validateISBN( userIsbn );

            if( validIsbn ){
               int userIsbnInt = Integer.parseInt(userIsbn);
               int thisIsbnBooksCount = bookService.getBooksById( userIsbnInt ).size();
               int thisIsbnTakenBooksCount = userBooksRegistry.getTakenBooksCountByIsbn( userIsbnInt );

               int booksLeft = thisIsbnBooksCount - thisIsbnTakenBooksCount ;
               if( booksLeft > 0 ){
                  boolean validReply = false;
                  do{
                     System.out.println("Shure delete this book? (y/n)");
                     if( security.readYesNo( scan.nextLine().trim() ) ){
                        validReply = true;
                        bookDao.deleteBook( userIsbnInt );
                        is = true;
                     } else {
                        System.out.println("Y or N");
                        is = false;
                     }
                  } while ( !validReply );
               } else {
                  System.out.println("Can't delete the rest of 'em");
               }
            } else {
               System.out.println("Invalid isbn");
            }
         } while (!is);
      }
   }

}