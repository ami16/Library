package edu.cursor.library.security.service;

import edu.cursor.library.user.entity.TblUser;

public interface SecurityService {

   boolean validateLogin(String val);
   boolean validatePass(String val);
   boolean validateMail(String val);
   boolean validateName(String val);
   boolean validateMobile( int val );

   boolean loginAvailable(String desiredLogin, boolean showMessage);
   int getNewUserId();

   boolean userExists(String login);
   boolean passIsCorrect(String login, String pass);
   TblUser getUser(int id) ;
   TblUser getUser(String mail) ;
}