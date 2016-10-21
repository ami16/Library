package edu.cursor.library.security.service;

import edu.cursor.library.user.entity.TblUser;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.cursor.library.security.credentials.service.CredentialsImpl;
import edu.cursor.library.user.service.UserServiceImpl;

public class SecurityServiceImpl implements SecurityService {


   CredentialsImpl cred = CredentialsImpl.getInstance();
   private static UserServiceImpl userService = new UserServiceImpl();
   private static List<TblUser> tempUserList = null ;

   public SecurityServiceImpl() {
//      System.out.println(tempUserList);
   }



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
   public boolean loginAvailable(String desiredLogin, boolean showMessage) {
      boolean is = false;
      tempUserList = userService.getUserList() ;

      long loginOccurrences = tempUserList.stream().filter(i -> i.geteMail().equalsIgnoreCase(desiredLogin)).count();
      if(loginOccurrences > 0){
         if(showMessage)
            System.out.println("Login NOT available.");
         is = false ;
      } else {
         is = true;
      }

      return is;
   }

   @Override
   public int getNewUserId(){
      tempUserList = userService.getUserList() ;

      Iterator<TblUser> itr = tempUserList.iterator();
      int currentId = 1 ;
      while (itr.hasNext()) {
         currentId = itr.next().getId();
      }
      return currentId + 1;
   }


   @Override
   public boolean userExists(String login) {
      return !loginAvailable(login, false);
   }

   @Override
   public boolean passIsCorrect(String login, String pass) {
      int id ;
      tempUserList = userService.getUserList() ;
      Iterator<TblUser> itr = tempUserList.iterator();
      while(itr.hasNext()){
         TblUser user = itr.next() ;

         // Find User by id
         if( user.geteMail().equals(login) ){
            id = user.getId();

            Map<Integer, String> credentials = cred.getCredentialsList();
            for( Map.Entry<Integer, String> c : credentials.entrySet()){
               // Find Pass of user
               if(c.getKey()==id && c.getValue().equals(pass)){
                  return true;
               }
            }
         }
      }
      return false;
   }

   @Override
   public TblUser getUser(int id) {
      tempUserList = userService.getUserList();

      for( TblUser user : tempUserList ){
         if(user.getId() == id){
            return user ;
         }
      }
      return null;
   }

   @Override
   public TblUser getUser(String mail) {
      tempUserList = userService.getUserList();

      for( TblUser user : tempUserList ){
         if(user.geteMail().equals( mail)){
            return user ;
         }
      }
      return null;
   }

   //   public static void main(String[] args) {
//      SecurityServiceImpl service = new SecurityServiceImpl();
//      System.out.println(service.getUser(1));
//   }
}