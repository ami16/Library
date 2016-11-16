package edu.cursor.library.infrastructure.persistance;


import edu.cursor.library.model.DAO.UserBooksRegistryDAO;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.model.TblCredentials;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.TblUserBooksRegistry;
import static edu.cursor.library.infrastructure.Constants.*;

import edu.cursor.library.service.BookServiceImpl;
import org.joda.time.LocalDate;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class MySqlUserBooksRegistryDao implements UserBooksRegistryDAO {

   private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?autoReconnect=true&useSSL=false";
   private static final String DB_USER = "root";
   private static final String DB_PASS = "";
   private BookServiceImpl bookService = BookServiceImpl.getInstance();

   public MySqlUserBooksRegistryDao() {
   }

   @Override
   public List<TblUserBooksRegistry> findAll() {
      return getUserBooksRegistryList();
   }

   public List<TblBook> findTakenBooksByUserId( int userId ) {
      List<TblBook> takenBooks = new ArrayList<>();
      ResultSet rs = null ;
      Connection conn = null;
      try{
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement ps = conn.prepareStatement("SELECT book_isbn FROM registry WHERE user_id = ?");
         ps.setInt(1, userId);
         rs = ps.executeQuery();
         while( rs.next() ){
            takenBooks.add( bookService.getBookById( rs.getInt("book_isbn") ) );
         }
         conn.close();
      }catch (Exception e){
         e.printStackTrace();
      }
      return takenBooks;
   }

   public List<TblBook> findTakenBooksByIsbn( int isbn ){
      List<TblBook> takenBooks = new ArrayList<>() ;

      ResultSet rs = null ;
      Connection conn = null;
      try{
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM registry WHERE book_isbn = ?");
         ps.setInt(1, isbn);
         rs = ps.executeQuery();
         while( rs.next() ){
            takenBooks.add( bookService.getBookById( rs.getInt("book_isbn") ) );
         }
         conn.close();
      }catch (Exception e){
         e.printStackTrace();
      }
      return takenBooks;
   }


   /**
    * <b>ADDITIONAL FUNCTIONALITY</b> <br>
    * for MySQL UserBooksRegistryDAO.
    */
   private List<TblUserBooksRegistry> getUserBooksRegistryList(){
      ResultSet rs = null ;
      Connection conn = null;
      List<TblUserBooksRegistry> regList = new ArrayList<>();
      try{
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM registry");
         rs = statement.executeQuery();
         while(rs.next()){
            regList.add( new TblUserBooksRegistry(
                rs.getInt("user_id"),
                rs.getInt("book_isbn")
            ) );
         }
         conn.close();
      } catch (Exception e){
         e.printStackTrace();
      }
      return regList;
   }


   public void saveRegistry(TblUser user, TblBook book){

      List<TblBook> userBooksRegistered = findTakenBooksByUserId( user.getId() );
      Set<TblBook> userBooksRegisteredSet = new HashSet<>(userBooksRegistered);
      userBooksRegisteredSet.add( book );

      String q1 = "DELETE FROM registry WHERE user_id = ?";

      int rs = 0;
      Connection conn = null;
      try{
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement statement1 = conn.prepareStatement(q1);
         statement1.setInt(1, user.getId() );
         statement1.executeUpdate();

         int userBooksRegisteredSetCount = userBooksRegisteredSet.size();
         int i = 1;
         StringBuilder q2 = new StringBuilder();
         q2.append("INSERT INTO registry VALUES ");
             for( TblBook b : userBooksRegisteredSet ){
                q2.append("(");
                q2.append( user.getId() );
                q2.append( ", ");
                q2.append( b.getISBN());

                q2.append(")");
                if( i != userBooksRegisteredSetCount ){
                   q2.append(", ");
                }
                i++;
             }
         Statement st = conn.createStatement();
//         System.out.println(q2.toString());
         rs = st.executeUpdate(q2.toString());
         if( rs > 0 ){
            System.out.println("Inserted successfully!!!...");
         } else {
            System.out.println("SHiiiiiiittttttt..........");
         }
         conn.close();
      } catch(Exception e){
         e.printStackTrace();
      }
   }

   public void deleteEntry(TblUser user, TblBook book){
      try{
         Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         PreparedStatement pst = conn.prepareStatement("DELETE FROM registry WHERE user_id = ? AND book_isbn = ?");
         pst.setInt(1, user.getId());
         pst.setInt(2, book.getISBN());
         pst.executeUpdate();
         conn.close();
      } catch (Exception e){
         e.printStackTrace();
      }
   }


   public static void main(String[] args) {
//      MySqlUserBooksRegistryDao murd = new MySqlUserBooksRegistryDao();
//      System.out.println(Arrays.toString( murd.findAll().toArray() ) );
   }
}