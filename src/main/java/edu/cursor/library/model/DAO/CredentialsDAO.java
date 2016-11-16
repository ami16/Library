package edu.cursor.library.model.DAO;

import edu.cursor.library.model.TblUser;

import java.util.Map;


public interface CredentialsDAO {

   public String getPassword(int userId);
   public String getPassword(String login);
   public void setPassword(int userId, String pass);
   public void addCredentials ( int userId, String pass );
   public void deleteCredentials ( int userId );

}