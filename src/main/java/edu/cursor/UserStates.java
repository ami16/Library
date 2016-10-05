package edu.cursor;

public enum UserStates {
   USER(0),
   ADMIN(1) ;

   private int id;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   UserStates(int _id) {
      id = _id ;
   }
}