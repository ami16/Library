package edu.cursor.library.infrastructure.persistence;

import edu.cursor.library.model.DAO.UserDAO;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.UserRole;
import edu.cursor.library.security.SingleUserAuthImpl;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CSVFileUserDao implements UserDAO{

   private static String projPath = System.getProperty("user.dir"),
       dbPath = "/src/main/resources/",
       fileName = "userList.csv",
       file = projPath + dbPath + fileName ;
   private static final String DELIMITER = "," ;

   private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();

   public CSVFileUserDao() {
   }

   @Override
   public List<TblUser> findAll() {
      return getUserList();
   }

   @Override
   public TblUser findById( int id ) {
      List<TblUser> userList = findAll();
      for( TblUser u : userList ){
         if( u.getId() == id ){
            return u;
         }
      }
      return null;
   }

   @Override
   public long createUser(TblUser user) {
      List<TblUser> tempUserList = getUserList() ;
      tempUserList.add(user) ;

      putUserList( tempUserList );
//      return true ;
      return user.getId() ;
   }

   @Override
   public void updateUser(TblUser user) {
      boolean loggedUserIsAdmin = auth.getLoggedInUser().getRole() == UserRole.valueOf("ADMIN");
      List<TblUser> tempUserList = getUserList() ;

      for( TblUser u : tempUserList ){
//         if( u.getId().equals(user.getId()) ){
         if( u.getId() == user.getId() ){
            u.setFirstName( user.getFirstName() );
            u.setLastName( user.getLastName() );
            u.setMobileNum( user.getMobileNum() );
            if( loggedUserIsAdmin ){
               u.setRole( user.getRole() );
            }
            break;
         }
      }
      putUserList( tempUserList );
   }

   @Override
   public void deleteUser(int userId) {
      List<TblUser> tempUserList = getUserList() ;

      int counter = 0;
      for( TblUser u : tempUserList ){
//         if( u.getId().equals(userId) ){
         if( u.getId() == userId ){
            tempUserList.remove(counter);
            break;
         }
         counter++;
      }
      putUserList( tempUserList );
   }


   /**
    * <b>ADDITIONAL FUNCTIONALITY</b> <br>
    * for CSV UserDAO.
    */
   private void putUserList( List<TblUser> userList ){
      try( FileWriter fw = new FileWriter( new File(file) ) ){
         fw.write("Id,firstName,lastName,eMail,mobileNum,address,dateOfRegistration,role\n");
         for( TblUser u : userList ){
//            fw.append( u.getId().toString() ); fw.append(DELIMITER);
            fw.append( String.valueOf( u.getId()) ); fw.append(DELIMITER);
            fw.append( u.getFirstName() ); fw.append(DELIMITER);
            fw.append( u.getLastName() ); fw.append(DELIMITER);
            fw.append( u.getEmail() ); fw.append(DELIMITER);
//            fw.append( u.getMobileNum().toString() ); fw.append(DELIMITER);
            fw.append( String.valueOf( u.getMobileNum()) ); fw.append(DELIMITER);
            fw.append( u.getAddress() ); fw.append(DELIMITER);
            fw.append( u.getDateOfRegistration().toString() ); fw.append(DELIMITER);
            fw.append( u.getRole().toString() );
            fw.append("\n");
         }
         fw.flush();
      } catch (Exception e){
         e.printStackTrace();
      }
   }

   private List<TblUser> getUserList( ){
      List<TblUser> userList = new ArrayList<>();
      try( Scanner scan = new Scanner(new File(file)) ){
         String[] fields;
         while (scan.hasNext()){
            fields = scan.nextLine().split(",");
            if(fields[0].trim().contains("Id")) continue;
            userList.add( new TblUser(
                Integer.parseInt(fields[0]),
                fields[1],
                fields[2],
                fields[3],
                Integer.parseInt(fields[4]),
                fields[5],
                LocalDate.parse(fields[6]),
                UserRole.valueOf(fields[7])
            ));
         }
      } catch(Exception e){
         e.printStackTrace();
      }
      return userList;
   }


}