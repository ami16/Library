package edu.cursor.library.infrastructure.persistence;

import edu.cursor.library.model.DAO.BookDAO;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.security.SingleUserAuthImpl;
import edu.cursor.library.util.BookDaoUtils;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import static edu.cursor.library.infrastructure.Constants.*;


public class CSVFileBookDao implements BookDAO {

   private static List<TblBook> bookList = new ArrayList<>();
   private static String projPath = System.getProperty("user.dir"),
       dbPath = "/src/main/resources/",
       fileName = "bookList.csv",
       file = projPath + dbPath + fileName ;
   private BookDaoUtils bookDaoUtils = new BookDaoUtils();


   public CSVFileBookDao() {
   }

   @Override
   public List<TblBook> findAll() {
      return getBookList();
   }

   @Override
   public List<TblBook> findById( int id ) {
      List<TblBook> bookList = findAll();
      List<TblBook> foundBookList = new ArrayList<>();
      for( TblBook b : bookList ){
//         if( b.getISBN().equals(id) ){
         if( b.getISBN() == id ){
            foundBookList.add( b );
         }
      }
//      return foundBookList.get(0);
      return foundBookList;
   }

   @Override
   public boolean createBook(TblBook book) {
      List<TblBook> tempUserList = getBookList() ;
      tempUserList.add(book) ;

      putBookList( tempUserList );
      return true ;
   }

   @Override
   public void updateBook(TblBook book) {
      SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
      List<TblBook> tempBookList = getBookList() ;

      for( TblBook b : tempBookList ){
//         if( b.getISBN().equals(book.getISBN()) ){
         if( b.getISBN() == book.getISBN() ){

            if( auth.userCanCrud() ){
               b.setISBN( book.getISBN() );
               b.setAuthor( book.getAuthor() );
               b.setTitle( book.getTitle() );
               b.setWritYear( book.getWritYear() );
               b.setPublYear( book.getPublYear() );
               b.setGenre( book.getGenre() );
            }
            break;
         }
      }
      putBookList( tempBookList );
   }

   @Override
   public void deleteBook(int ISBN) {
      List<TblBook> tempBookList = getBookList() ;

      int counter = 0;
      for( TblBook b : tempBookList ){
//         if( b.getISBN().equals(ISBN) ){
         if( b.getISBN() == ISBN ){
            tempBookList.remove(counter);
            break;
         }
         counter++;
      }
      putBookList( tempBookList );
   }


   /**
    * <b>ADDITIONAL FUNCTIONALITY</b> <br>
    * for CSV BookDAO.
    */
   private void putBookList( List<TblBook> bookList ){
      try( FileWriter fw = new FileWriter( new File(file) ) ){
         fw.write("iId,ISBN,Author,Title,publYear,writYear,Genre\n");
         for( TblBook b : bookList ){
            fw.append(String.valueOf(b.getId()));fw.append(DEFAULT_SEPARATOR);
            fw.append( String.valueOf( b.getISBN() ) ); fw.append(DEFAULT_SEPARATOR);
            fw.append( b.getAuthor() ); fw.append(DEFAULT_SEPARATOR);
            fw.append( b.getTitle() ); fw.append(DEFAULT_SEPARATOR);
            fw.append( b.getPublYear().toString() ); fw.append(DEFAULT_SEPARATOR);
            fw.append( b.getWritYear().toString() ); fw.append(DEFAULT_SEPARATOR);
            fw.append( bookDaoUtils.convertSetToString( b.getGenre() ));
            fw.append("\n");
         }
         fw.flush();
      } catch (Exception e){
         e.printStackTrace();
      }
   }

   private List<TblBook> getBookList( ){
      bookList.clear();
      try( Scanner scan = new Scanner(new File(file)) ){
         String[] fields;
         while (scan.hasNext()){
            fields = scan.nextLine().split(",");
            if(fields[1].trim().contains("ISBN")) continue;
            bookList.add( new TblBook(
                Integer.parseInt(fields[0]),
                    Integer.parseInt(fields[1]),
                fields[2],
                fields[3],
                LocalDate.parse(fields[4]),
                LocalDate.parse(fields[5]),
                bookDaoUtils.convertStringToEnumSet( fields[6] )
                )
            );
         }
      } catch(Exception e){
         e.printStackTrace();
      }
      return bookList;
   }

}