package edu.cursor.library.security.credentials.service;

import edu.cursor.library.user.entity.TblUser;

import java.util.Map;

public interface Credentials {

   //  Map<Integer, String> createCredentialsList();
   Map<Integer, String> getCredentialsList(String file);
   String getPassword(TblUser user) ;
   void addCredentials( int id, String pass );

}