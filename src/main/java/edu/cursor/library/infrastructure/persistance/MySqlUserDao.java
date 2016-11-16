package edu.cursor.library.infrastructure.persistance;

import edu.cursor.library.model.DAO.UserDAO;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.UserRole;
import edu.cursor.library.security.SingleUserAuthImpl;
import org.joda.time.LocalDate;
import static edu.cursor.library.infrastructure.Constants.*;

import java.sql.*;
import java.util.*;


public class MySqlUserDao implements UserDAO {

   private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?autoReconnect=true&useSSL=false";
   private static final String USER = "root";
   private static final String PASS = "";
   private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();

   public MySqlUserDao() {
   }

   private Map<ResultSet, Connection> getResultSet(String q, int paramsCount, int id){
      ResultSet rs = null;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(q);

         // ID only (yet)
         if( paramsCount == 1 && id != 0 ) {
            stmt.setInt(1, id);
         }
         rs = stmt.executeQuery();

      } catch (Exception e){
         System.out.println("Part1: " + e.getMessage());
         e.printStackTrace();
      }
      Map<ResultSet, Connection> map = new HashMap<>();
      map.put(rs, conn);
      return map;
   }

   private Map<Long, Connection> getResultUpdate(String q, int paramsCount, Object... params){
      long rs = 0;
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(URL, USER, PASS);
//         PreparedStatement stmt = conn.prepareStatement(q);
         PreparedStatement stmt = conn.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
         if( paramsCount == 1 ){
            stmt.setInt(1, (int) params[0]);
         }
         if( paramsCount == 7 ){
            stmt.setString(1, (String) params[0]);
            stmt.setString(2, (String) params[1]);
            stmt.setString(3, (String) params[2]);
            stmt.setInt(4, (int) params[3]);
            stmt.setString(5, (String) params[4]);
            stmt.setDate(6, java.sql.Date.valueOf((String) params[5]) );
            UserRole ur = UserRole.valueOf(params[6].toString());
            stmt.setString(7, ur.toString());
         }
         if( paramsCount == 8 ){
            stmt.setString(1, (String) params[0]);
            stmt.setString(2, (String) params[1]);
            stmt.setString(3, (String) params[2]);
            stmt.setInt(4, (int) params[3]);
            stmt.setString(5, (String) params[4]);
            stmt.setDate(6, java.sql.Date.valueOf((String) params[5]) );
            UserRole ur = UserRole.valueOf(params[6].toString());
            stmt.setString(7, ur.toString());
            stmt.setInt(8, (int) params[7] );
         }
         rs = stmt.executeUpdate();

         try( ResultSet generatedKeys = stmt.getGeneratedKeys() ){
            if( generatedKeys.next() ){
               rs = generatedKeys.getLong(1);
               System.out.println(rs);
            } else {
               System.out.println("No generated keys..");
            }
         }
      } catch (Exception e){
         System.out.println("Part2: " + e.getMessage());
         e.printStackTrace();
      }


      Map<Long, Connection> map = new HashMap<>();
      map.put(rs, conn);
      return map;
   }



   @Override
   public List<TblUser> findAll() {
      return getUserList();
   }

   @Override
   public TblUser findById( int id ) {
      List<TblUser> userList = new ArrayList<>();
      Map<ResultSet, Connection> map = getResultSet("SELECT * FROM USERS WHERE ID = ?", 1, id);
      if( map.values().stream().findFirst() != null ){
         try {
            ResultSet rs = map.entrySet().iterator().next().getKey();
            while (rs.next()) {
               userList.add( getUserObject(rs) );
            }
            Connection conn = map.entrySet().iterator().next().getValue();
            conn.close();
         } catch (Exception e){
            e.printStackTrace();
         }
      }
      return userList.get(0);
   }

   @Override
   public long createUser(TblUser user) {
      Object[] params = {
          user.getFirstName(),
          user.getLastName(),
          user.geteMail(),
          user.getMobileNum(),
          user.getAddress(),
          user.getDateOfRegistration().toString(),
          user.getRole()
      };
      Map<Long, Connection> map = getResultUpdate("INSERT INTO USERS " +
          "(first_name, last_name, e_mail, mobile_num, address, date_of_registration, role) VALUES" +
          "(?, ?, ?, ?, ?, ?, ?)", 7, params);

//      System.out.println( map.entrySet().iterator().next().getKey() );

//      return map.values().stream().findFirst() != null;
      return map.entrySet().iterator().next().getKey();
   }

   @Override
   public void updateUser(TblUser user) {
      Object[] params = {
          user.getFirstName(),
          user.getLastName(),
          user.geteMail(),
          user.getMobileNum(),
          user.getAddress(),
          user.getDateOfRegistration().toString(),
          user.getRole(),
          user.getId()
      };
//      Map<Long, Connection> map = getResultUpdate("UPDATE USERS " +
//          "SET first_name=?, last_name=?, e_mail=?, mobile_num=?, address=?, date_of_registration=?, role=? " +
//          "WHERE ID = ?", 8, params);
      getResultUpdate("UPDATE USERS " +
          "SET first_name=?, last_name=?, e_mail=?, mobile_num=?, address=?, date_of_registration=?, role=? " +
          "WHERE ID = ?", 8, params);

   }

   @Override
   public void deleteUser(int userId) {
      if( auth.userCanCrud() ) {
//         Map<Long, Connection> map = getResultUpdate("DELETE FROM USERS " +
//             "WHERE ID = ?", 1, userId);
         getResultUpdate("DELETE FROM USERS WHERE ID = ?", 1, userId);
      }
   }




   /**
    * <b>ADDITIONAL FUNCTIONALITY</b> <br>
    * for MySQL UserDAO.
    */
   private List<TblUser> getUserList(){
      List<TblUser> userList = new ArrayList<>();
      try{
         Map<ResultSet, Connection> resultMap = getResultSet("SELECT * FROM users", 0, 0);
         if( resultMap.values().stream().findFirst() != null ){
            ResultSet rs = resultMap.entrySet().iterator().next().getKey();
            while (rs.next()){
               userList.add( getUserObject( rs ) );
            }
            Connection conn = resultMap.entrySet().iterator().next().getValue();
            conn.close();
         }
      } catch (Exception e){
         e.printStackTrace();
      }
      return userList;
   }

   private TblUser getUserObject( ResultSet rs ){
      try {
            return new TblUser(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("e_mail"),
                rs.getInt("mobile_num"),
                rs.getString("address"),
                LocalDate.parse(rs.getString("date_of_registration")),
                UserRole.valueOf(rs.getString("role"))
            );
      } catch( Exception e ){
         e.printStackTrace();
      }
      return null;
   }


   public static void main(String[] args) {
      MySqlUserDao msud = new MySqlUserDao();
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

//      System.out.println( msud.getUserList() );
//      System.out.println(msud.findById(3));

//      msud.createUser(
//          new TblUser(
//              100,
//              "NewOne",
//              "JDBCovsky",
//              "j@j.j",
//              456456456,
//              "Some new address",
//              LocalDate.parse("2016-11-14"),
//              UserRole.USER
//          )
//      );

//      msud.updateUser(
//          new TblUser(
//              25,
//              "NewOneName",
//              "JDBCovsky222",
//              "j@j.jjj",
//              787878787,
//              "Some new address rafbiu qaefra daef bvafrd",
//              LocalDate.parse("2016-11-10"),
//              UserRole.USER
//          )
//      );

//      msud.deleteUser(25);

   }

}