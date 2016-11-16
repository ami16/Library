package edu.cursor.library.security;

import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.UserRole;

public class AuthImpl implements Auth
{
//   Credentials cred = CredentialsImpl.getInstance();
   private boolean userIsLogged = false;
   private TblUser user;

   private static AuthImpl instance ;
   private AuthImpl() {}

   public static AuthImpl getInstance(){
      if( instance == null ){
         synchronized (AuthImpl.class){
            // Double check
            if (instance == null) {
               instance = new AuthImpl() ;
            }
         }
      }
      return instance ;
   }

   public boolean isUserLoggedIn() {
      return userIsLogged;
   }

   public void setUserIsLogged(boolean userIsLogged) {
      this.userIsLogged = userIsLogged;
   }

   public TblUser getLoggedInUser() {
      return user;
   }

   public void setLoggedInUser(TblUser loggedUser) {
      this.user = loggedUser;
   }


   @Override
   public void logIn(TblUser user) {
      setLoggedInUser(user);
      setUserIsLogged(true);
   }

   @Override
   public void logOut() {
      setLoggedInUser(null);
      setUserIsLogged(false);
   }
}