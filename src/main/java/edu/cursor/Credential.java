package edu.cursor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Credential {

   private Map<Integer, String> credList = new HashMap<>();
   public Map<Integer, String> getCredList() { return credList; }

   private static Credential instance ;

   private Credential() {

   }

   public static Credential getInstance(){
      if( instance == null ){
         synchronized (Credential.class){
            // Double check
            if (instance == null) {
               instance = new Credential() ;
            }
         }
      }
      return instance ;
   }

   // 1. CREDENTIALS LIST
   public Map<Integer, String> createCredList() {
      credList.put(1, "peter") ;
      credList.put(2, "lois") ;
      credList.put(3, "doughnut") ;
      credList.put(4, "shit") ;
      credList.put(5, "brave") ;
      credList.put(6, "1234") ;
      credList.put(7, "admin7") ;
      return credList;
   }

   public Map<Integer, String> addCred( int id, String pass ) {
      credList.put(id, pass) ;
      return credList;
   }

   public String getPass( int id ){
      return credList.get(id);
   }
}