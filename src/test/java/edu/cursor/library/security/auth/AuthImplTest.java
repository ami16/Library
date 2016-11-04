package edu.cursor.library.security.auth;

import edu.cursor.library.user.entity.TblUser;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthImplTest {

   private boolean userIsLogged = true;

   @Test
   public void isUserLoggedIn(){
      boolean result = AuthImpl.getInstance().isUserLoggedIn();
      assertTrue("Should be true", result );
   }

   @Test
   public void shouldReturnLoggedUser(){
      boolean logged = AuthImpl.getInstance().getLoggedInUser() != null;
      assertFalse(logged);
      AuthImpl.getInstance().logIn( new TblUser() );
      logged = AuthImpl.getInstance().getLoggedInUser() != null;
      assertTrue( logged );
   }

   @Test
   public void shouldLogOut(){

   }

}