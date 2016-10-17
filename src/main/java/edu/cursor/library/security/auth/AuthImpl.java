package edu.cursor.library.security.auth;

import edu.cursor.library.security.credentials.service.Credentials;
import edu.cursor.library.security.credentials.service.CredentialsImpl;
import edu.cursor.library.user.entity.TblUser;

public class AuthImpl implements Auth
{
   Credentials cred = CredentialsImpl.getInstance();
   private boolean userIsLogged = false;
   private TblUser loggedUser;
//   private List<TblUser> userList = new ArrayList<>();

   public boolean isUserLogged() {
      return userIsLogged;
   }

   public void setUserLogged(boolean userIsLogged) {
      this.userIsLogged = userIsLogged;
   }

   public TblUser getLoggedUser() {
      return loggedUser;
   }

   public void setLoggedUser(TblUser loggedUser) {
      this.loggedUser = loggedUser;
   }

//   public List<TblUser> getUserList() {
//      return userList;
//   }
//
//   public void setUserList(List<TblUser> userList) {
//      this.userList = userList;
//   }

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

   @Override
   public void logIn(TblUser user) {
      setLoggedUser(user);
      setUserLogged(true);
   }

   @Override
   public void logOut() {
      setLoggedUser(null);
      setUserLogged(false);
   }
}