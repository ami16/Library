package edu.cursor.library.security;

import edu.cursor.library.model.UserRole;
import edu.cursor.library.model.TblUser;


public class SingleUserAuthImpl implements Auth{

   private boolean userIsLogged = false;
   private TblUser loggedUser;

   private static SingleUserAuthImpl instance ;
   private SingleUserAuthImpl() {}

   public static SingleUserAuthImpl getInstance(){
      if( instance == null ){
         synchronized (SingleUserAuthImpl.class){
            // Double check
            if (instance == null) {
               instance = new SingleUserAuthImpl() ;
            }
         }
      }
      return instance ;
   }

   public boolean isUserLoggedIn() {
      return this.userIsLogged;
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

   public boolean userCanCrud(){
      return isUserLoggedIn() && (getLoggedInUser().getRole() == UserRole.ADMIN) ;
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