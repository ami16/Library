package edu.cursor.library.model;


public class TblCredentials {

   private int id;
   private String pass ;


   public TblCredentials() {
   }

   public TblCredentials(int id, String pass) {
      this.id = id;
      this.pass = pass;
   }

   public int getId() {
      return id;
   }

   public String getPass() {
      return pass;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setPass(String pass) {
      this.pass = pass;
   }
}