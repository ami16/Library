package edu.cursor;

import java.util.HashMap;
import java.util.Map;

public class Credential {

   private Map<Integer, String> cList = new HashMap<>();
   public Map<Integer, String> getcList() { return cList; }

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
      cList.put(1, "peter") ;
      cList.put(2, "lois") ;
      cList.put(3, "doughnut") ;
      cList.put(4, "shit") ;
      cList.put(5, "brave") ;
      cList.put(6, "1234") ;
      return cList;
   }

   public Map<Integer, String> addCred( int id, String pass ) {
      cList.put(id, pass) ;
      return cList ;
   }

   public String getPass( int id ){
      return cList.get(id);
   }

}