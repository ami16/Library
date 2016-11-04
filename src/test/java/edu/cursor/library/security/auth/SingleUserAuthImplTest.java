package edu.cursor.library.security.auth;

import edu.cursor.library.user.entity.TblUser;
import org.junit.Test;
import static org.junit.Assert.*;

public class SingleUserAuthImplTest {

   SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();

   @Test
   public void isUserLoggedIn(){
      boolean logged = auth.isUserLoggedIn();
      if(!logged){
         auth.logIn( new TblUser() );
      }
      boolean result = auth.isUserLoggedIn();
      assertTrue("Should be true", result );
   }

   @Test
   public void shouldReturnLoggedUser(){
      boolean logged = auth.getLoggedInUser() != null;
      if(logged){
         auth.logOut();
         logged = auth.getLoggedInUser() != null;
      }
      assertFalse(logged);
      auth.logIn( new TblUser() );
      logged = auth.getLoggedInUser() != null;
      assertTrue( logged );
   }

   @Test
   public void shouldLogOut(){
      boolean logged = auth.isUserLoggedIn();
      if(!logged){
         auth.logIn( new TblUser() );
      }
      auth.logOut();
      boolean loggedIn = auth.getLoggedInUser() != null;
      assertFalse( loggedIn );
   }

}