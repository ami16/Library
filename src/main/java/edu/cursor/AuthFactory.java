package edu.cursor;

import java.util.ArrayList;
import java.util.List;

public class AuthFactory {

   Credential cred = Credential.getInstance();

   private boolean userIsLogged = false;
   private String loggedUser;
   private List<User> uList = new ArrayList<>();

   public boolean getUserIsLogged() { return userIsLogged; }
   public String getLoggedUser() { return loggedUser; }
   public List<User> getuList() { return uList; }

   public void setUserIsLogged(boolean usrIsLogged) { userIsLogged = usrIsLogged; }
   public void setLoggedUser(String loggedUsr) { loggedUser = loggedUsr; }


   private static AuthFactory instance ;

   private AuthFactory() { }

   public static AuthFactory getInstance(){
      if( instance == null ){
         synchronized (AuthFactory.class){
            // Double check
            if (instance == null) {
               instance = new AuthFactory() ;
            }
         }
      }
      return instance ;
   }

   // 1. USERS LIST. Olesya
   public List<User> createUserList() {
      uList.add(new User(1, "Peter", "Griffin", "peter@gmail.com", 982545785, "Quahog, Spoon st., 34", "25-11-2016"));
      uList.add(new User(2, "Lois", "Griffin", "lois@gmail.com", 735458787, "Quahog, Spoon st.,34", "18-05-2014"));
      uList.add(
          new User(3, "Homer", "Simpson", "hommy@gmail.com", 598741547, "Springfield, Evergreeen st.,45", "13-04-2016"));
      uList.add(
          new User(4, "Eric", "Cartman", "eric@gmail.com", 857845175, "South Park, Jew st., 1845", "25-06-2014"));
         // Jew St. )))))))
      uList.add(
          new User(5, "Leopold", "Stoch", "butters@gmailm.com", 547845145, "South Park, Raisins st., 34", "18-07-2013"));
      return uList;
   }

   public List<User> addUser( int id, String firstName, String lastName, String email, int mobileNo, String address, String dateOfRegistration, String pass ) {
      uList.add( new User(id, firstName, lastName, email, mobileNo, address, dateOfRegistration) ) ;
      cred.addCred( id, pass ) ;
      return uList ;
   }

}