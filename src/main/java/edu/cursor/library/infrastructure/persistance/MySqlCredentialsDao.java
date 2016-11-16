package edu.cursor.library.infrastructure.persistance;

import edu.cursor.library.model.DAO.CredentialsDAO;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.security.SingleUserAuthImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import static edu.cursor.library.infrastructure.Constants.*;

public class MySqlCredentialsDao implements CredentialsDAO {

   private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?autoReconnect=true&useSSL=false";
   private static final String USER = "root";
   private static final String PASS = "";
   private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();

   public MySqlCredentialsDao() {
   }


   @Override
   public String getPassword(int userId) {
      ResultSet rs = null ;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement("SELECT PASS FROM CREDENTIALS WHERE ID = ?");
         stmt.setInt(1, userId);
         rs = stmt.executeQuery();

         if( rs != null ){
            while (rs.next()){
               return rs.getString("pass");
            }
         }
      } catch (Exception e){
         System.out.println("PartZ1: " + e.getMessage());
         e.printStackTrace();
      }
      return "";
   }

   @Override
   public String getPassword(String login) {
      ResultSet rs = null ;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement("SELECT PASS FROM CREDENTIALS WHERE ID = " +
             "(SELECT ID FROM USERS WHERE e_mail = ? ) ");
         stmt.setString(1, login);
         rs = stmt.executeQuery();

         if( rs != null ){
            while (rs.next()){
               return rs.getString("pass");
            }
         }
      } catch (Exception e){
         System.out.println("PartZX1: " + e.getMessage());
         e.printStackTrace();
      }
      return "";
   }

   @Override
   public void setPassword(int userId, String pass) {
      int rs = 0 ;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement("UPDATE CREDENTIALS SET PASS = ? WHERE ID = ?");
         stmt.setString(1, pass);
         stmt.setInt(2, userId);
         stmt.executeUpdate();
      } catch (Exception e){
         System.out.println("PartZ1: " + e.getMessage());
         e.printStackTrace();
      }
   }

   @Override
   public void addCredentials(int userId, String pass) {
      int rs = 0;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement("INSERT INTO CREDENTIALS " +
             "(id, pass) VALUES " +
             "(?, ?)");
         stmt.setInt(1, userId);
         stmt.setString(2, pass);
         stmt.executeUpdate();
      } catch (Exception e){
         System.out.println("PartZ2: " + e.getMessage());
         e.printStackTrace();
      }
   }

   @Override
   public void deleteCredentials(int userId) {
      int rs = 0;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM CREDENTIALS WHERE ID = ?");
         stmt.setInt(1, userId);
         stmt.executeUpdate();
      } catch (Exception e){
         System.out.println("PartZ3: " + e.getMessage());
         e.printStackTrace();
      }
   }

   public static void main(String[] args) {
      MySqlCredentialsDao cred = new MySqlCredentialsDao();
//      System.out.println(cred.getPassword(13));
//      cred.addCredentials(100, "500asdf");
      cred.deleteCredentials(100);
   }
}