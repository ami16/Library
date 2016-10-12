package edu.cursor.library.security;

import edu.cursor.library.user.entity.TblUser;

import java.util.Map;

public interface Credential {

   public Map<Integer, String> createCredentialList();
   public Map<Integer, String> addCredential( int id, String pass );
   public String getPassword(TblUser user) ;

}