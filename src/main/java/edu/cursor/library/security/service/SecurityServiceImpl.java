package edu.cursor.library.security.service;

import edu.cursor.library.user.entity.TblUser;
import java.util.Iterator;
import edu.cursor.library.security.credentials.service.CredentialsImpl;

public class SecurityServiceImpl implements SecurityService {

   public SecurityServiceImpl() {
   }

   CredentialsImpl cred = CredentialsImpl.getInstance();

   @Override
   public boolean validateLogin(String val) {
      return val.matches("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
   }

   @Override
   public boolean validatePass(String val) {
      return (val.length() >= 4);
   }

   @Override
   public boolean validateMail(String val) {
      return val.matches("^(.+)@(.+)\\.(.+){1,3}$");
   }

   @Override
   public boolean validateName(String val) {
      return val.matches("^[a-zA-Z0-9_-]{2,16}$");
   }

   @Override
   public boolean validateMobile(int val) {
      return Integer.toString(val).matches("^(\\d{9})$");
   }

   @Override
   public boolean loginAvailable(String desiredLogin) {
      boolean loginAvailable = false;

      // userService
//      Iterator<TblUser> iterator = getUserList().iterator();
//      while (iterator.hasNext()) {
//         TblUser user = iterator.next();
//         if (user.geteMail().equals(desiredLogin)) {
//            loginAvailable = false;
//            System.out.println("Login NOT available.");
//            break;
//         } else {
//            loginAvailable = true;
//         }
//      }
      return loginAvailable;
   }

   @Override
   public int getNewUserId(){

      // userService
//      Iterator<TblUser> itr = getUserList().iterator();
//      int currentId = 1 ;
//      while (itr.hasNext()) {
//         currentId = itr.next().getId();
//      }
//      return currentId + 1;
      return 1;
   }


   @Override
   public boolean userExists(String login) {

      // userService
//      Iterator<TblUser> iterator = getUserList().iterator();
//      while (iterator.hasNext()) {
//         TblUser user = iterator.next();
//         if (user.geteMail().equals(login)) {
//            return true;
//         }
//      }
      return false;
   }

   @Override
   public boolean passIsCorrect(String login, String pass) {
//      Iterator<TblUser> iterator = getUserList().iterator();
//      while (iterator.hasNext()) {
//         TblUser user = iterator.next();
//         if ( user.geteMail().equals(login) ) {
//            if( cred.getPassword(user.getId(user.geteMail()).equals(pass) ){
//               return true;
////            }
//         }
//      }
      return false;
   }
}