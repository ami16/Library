package edu.cursor.library.security;

import edu.cursor.library.model.TblUser;

public interface SecurityService {

   boolean validateLogin(String val);
   boolean validatePass(String val);
   boolean validateMail(String val);
   boolean validateName(String val);
   boolean validateMobile(String val);

   boolean loginAvailable(String desiredLogin, boolean showMessage);
   int getNewUserId();

   boolean userExists(String login);
   boolean userExists(int userId);
   boolean passIsCorrect(String login, String pass);
   TblUser getUser(int id) ;
   TblUser getUser(String mail) ;
}