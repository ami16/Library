package edu.cursor.library.security;

import edu.cursor.library.user.entity.TblUser;

import java.util.HashMap;
import java.util.Map;

public class CredentialImpl implements Credential {

   private Map<Integer, String> credentialList = new HashMap<>();
   public Map<Integer, String> getCredentialList() { return credentialList; }

   private static CredentialImpl instance ;

   private CredentialImpl() {

   }

   public static CredentialImpl getInstance(){
      if( instance == null ){
         synchronized (CredentialImpl.class){
            // Double check
            if (instance == null) {
               instance = new CredentialImpl() ;
            }
         }
      }
      return instance ;
   }

   // 1. CREDENTIALS LIST
   @Override
   public Map<Integer, String> createCredentialList() {
      credentialList.put(1, "peter") ;
      credentialList.put(2, "lois") ;
      credentialList.put(3, "doughnut") ;
      credentialList.put(4, "shit") ;
      credentialList.put(5, "brave") ;
      credentialList.put(6, "1234") ;
      credentialList.put(7, "admin7") ;
      return credentialList;
   }

   @Override
   public Map<Integer, String> addCredential ( int id, String pass ) {
      credentialList.put(id, pass) ;
      return credentialList;
   }

   @Override
   public String getPassword(TblUser user) {
      return credentialList.get( user.getId() );
   }
}