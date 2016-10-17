package edu.cursor.library.security.service;

public interface SecurityService {

   boolean validateLogin(String val);
   boolean validatePass(String val);
   boolean validateMail(String val);
   boolean validateName(String val);
   boolean validateMobile( int val );

   boolean loginAvailable(String desiredLogin);
   int getNewUserId();

   boolean userExists(String login);
   boolean passIsCorrect(String login, String pass);

}