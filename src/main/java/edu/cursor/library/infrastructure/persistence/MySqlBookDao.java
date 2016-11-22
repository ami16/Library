package edu.cursor.library.infrastructure.persistence;

import edu.cursor.library.model.DAO.BookDAO;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.security.SingleUserAuthImpl;
import edu.cursor.library.util.BookDaoUtils;
import org.joda.time.LocalDate;
import static edu.cursor.library.infrastructure.Constants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class MySqlBookDao implements BookDAO {
//public class MySqlBookDao {

   private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?autoReconnect=true&useSSL=false";
   private static final String DB_USER = "root";
   private static final String DB_PASS = "";
   private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
   private BookDaoUtils bookDaoUtils = new BookDaoUtils();

   public MySqlBookDao() {
   }

   private Map<ResultSet, Connection> getResultSet(String q, int paramsCount, int isbn){
      ResultSet rs = null;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement stmt = conn.prepareStatement(q);

         if( paramsCount == 1 && isbn != 0 ) {
            stmt.setInt(1, isbn);
         }
         rs = stmt.executeQuery();
//         System.out.println(q);
      } catch (Exception e){
         System.out.println("Part1: " + e.getMessage());
         e.printStackTrace();
      }
      Map<ResultSet, Connection> map = new HashMap<>();
      map.put(rs, conn);
      return map;
   }

   private Map<Integer, Connection> getResultUpdate(String q, int paramsCount, Object... params){
      int rs = 0;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement stmt = conn.prepareStatement(q);
         if( paramsCount == 1 ){
            stmt.setInt(1, (int) params[0]);
         }
         if( paramsCount == 6 ){
            stmt.setInt(1, (int) params[0]);
            stmt.setString(2, (String) params[1]);
            stmt.setString(3, (String) params[2]);
            stmt.setDate(4, java.sql.Date.valueOf((String) params[3]));
            stmt.setDate(5, java.sql.Date.valueOf((String) params[4]));
            stmt.setString(6, (String) params[5] );
         }
         rs = stmt.executeUpdate();

      } catch (Exception e){
         System.out.println("Part2: " + e.getMessage());
         e.printStackTrace();
      }
      Map<Integer, Connection> map = new HashMap<>();
      map.put(rs, conn);
      return map;
   }



   @Override
   public List<TblBook> findAll() {
      return getBookList();
   }

   @Override
   public List<TblBook> findById( int isbn ) {
      List<TblBook> bookList = new ArrayList<>();
      Map<ResultSet, Connection> map = getResultSet("SELECT * FROM BOOKS WHERE ISBN = ?", 1, isbn);
      if( map.values().stream().findFirst() != null ){
         try {
            ResultSet rs = map.entrySet().iterator().next().getKey();
            while (rs.next()) {
               bookList.add( getBookObject(rs) );
            }
            Connection conn = map.entrySet().iterator().next().getValue();
            conn.close();
         } catch (Exception e){
//            System.out.println(e.getStackTrace());
            e.printStackTrace();
         }
      }
      return bookList;
   }

   @Override
   public boolean createBook(TblBook book) {
      Object[] params = {
          book.getISBN(),
          book.getAuthor(),
          book.getTitle(),
          book.getPublYear().toString(),
          book.getWritYear().toString(),
          bookDaoUtils.convertEnumSetToString(book.getGenre())
      };
      Map<Integer, Connection> map = getResultUpdate("INSERT INTO BOOKS " +
          "(isbn, author, title, publ_year, writ_year, genre) VALUES" +
          "(?, ?, ?, ?, ?, ? )", 6, params);
      return map.values().stream().findFirst() != null;
   }

   @Override
   public void updateBook(TblBook book) {
      try{
         Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement pst = conn.prepareStatement("UPDATE BOOKS " +
             "SET author=?, title=?, publ_year=?, writ_year=?, genre=? " +
             "WHERE isbn = ?");
         pst.setString(1, book.getAuthor());
         pst.setString(2, book.getTitle());
         pst.setDate(3, java.sql.Date.valueOf( book.getPublYear().toString() ) );
         pst.setDate(4, java.sql.Date.valueOf( book.getWritYear().toString() ) );
         pst.setString(5, bookDaoUtils.convertEnumSetToString( book.getGenre() ) );
         pst.setInt(6, book.getISBN());
         pst.executeUpdate();
         conn.close();
      } catch (Exception e){
         e.printStackTrace();
      }
   }

   @Override
   public void deleteBook(int isbn) {
      if( auth.userCanCrud() ) {
//         Map<Integer, Connection> map = getResultUpdate("DELETE FROM BOOKS " +
//             "WHERE ISBN = ?", 1, isbn);

         try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement pst1 = conn.prepareStatement("SELECT MAX(id) AS id, isbn " +
                "FROM books WHERE isbn = ? " +
                "GROUP BY isbn");
            pst1.setInt(1, isbn);
            ResultSet rs = pst1.executeQuery();

            int maxId = 0;
            while( rs.next() ){
               maxId = rs.getInt("id");
            }

            PreparedStatement pst2 = conn.prepareStatement("DELETE FROM books " +
                "WHERE id = ?");
            pst2.setInt(1, maxId);
            pst2.executeUpdate();

            conn.close();
         } catch (Exception e){
            e.printStackTrace();
         }
      }
   }




   /**
    * <b>ADDITIONAL FUNCTIONALITY</b> <br>
    * for MySQL UserDAO.
    */
   private List<TblBook> getBookList(){
      List<TblBook> bookList = new ArrayList<>();
      try{
         Map<ResultSet, Connection> resultMap = getResultSet("SELECT * FROM books", 0, 0);
         if( resultMap.values().stream().findFirst() != null ){
            ResultSet rs = resultMap.entrySet().iterator().next().getKey();
            while (rs.next()){
               bookList.add( getBookObject( rs ) );
            }
            resultMap.entrySet().iterator().next().getValue().close();
         }
      } catch (Exception e){
         e.printStackTrace();
      }
      return bookList;
   }

   private TblBook getBookObject( ResultSet rs ){
      try {
            return new TblBook(
                    rs.getInt("id"),
                rs.getInt("isbn"),
                rs.getString("author"),
                rs.getString("title"),
                LocalDate.parse(rs.getString("publ_year")),
                LocalDate.parse(rs.getString("writ_year")),
                bookDaoUtils.convertStringToEnumSet( rs.getString("genre") )
            );
      } catch( Exception e ){
         e.printStackTrace();
      }
      return null;
   }


   public static void main(String[] args) {
//      MySqlBookDao msbd = new MySqlBookDao();
//      try{
//         Map<ResultSet, Connection> resultMap = msud.getResultSet("SELECT * FROM users");
//         if( resultMap.values().stream().findFirst() != null ){
//            ResultSet rs = resultMap.entrySet().iterator().next().getKey();
//            while (rs.next()){
//               System.out.println(rs.getString("first_name"));
//               System.out.println(rs.getString("last_name"));
//               System.out.println(rs.getString("address"));
//            }
//            Connection conn = resultMap.entrySet().iterator().next().getValue();
//            conn.close();
//         }
//      } catch (Exception e){
//         e.printStackTrace();
//      }

//      System.out.println( msbd.getBookList() );
//      System.out.println(msbd.findById(111));

//      msbd.createBook(
//          new TblBook(
//              777,
//              "Unavailable Author",
//              "Smooth Title",
//              LocalDate.parse("2016-10-25"),
//              LocalDate.parse("1988-01-30"),
//              msbd.bookDaoUtils.convertStringToEnumSet("FANTASY;MYSTERY;HORROR")
//          )
//      );

//      msbd.updateBook(
//          new TblBook(
//              777,
//              "ABSOLUTE NEW author",
//              "-----Another Title-----",
//              LocalDate.parse("1900-01-01"),
//              LocalDate.parse("1800-11-11"),
//              msbd.bookDaoUtils.convertStringToEnumSet("HISTORY;COMEDY;FANTASY")
//          )
//      );

//      msbd.deleteBook(777);

   }

}