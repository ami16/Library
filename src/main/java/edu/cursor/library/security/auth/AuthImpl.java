package edu.cursor.library.security.auth;

import edu.cursor.library.security.credentials.service.Credentials;
import edu.cursor.library.security.credentials.service.CredentialsImpl;
import edu.cursor.library.user.entity.TblUser;

public class AuthImpl implements Auth
{
   Credentials cred = CredentialsImpl.getInstance();
   private boolean userIsLogged = false;
   private TblUser loggedUser;

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
      return loggedUser;
   }

   public void setLoggedInUser(TblUser loggedUser) {
      this.loggedUser = loggedUser;
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