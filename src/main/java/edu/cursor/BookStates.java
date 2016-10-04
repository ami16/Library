package edu.cursor;

public enum BookStates {

   TAKEN(0) ,
   AVAILBALE(1);

   private int id ;
   private BookStates( int _id ) {
      id = _id ;
   }

   public int getId() { return id; }
   public void setId(int id) { this.id = id; }

   public static BookStates getBookState( int _id ){
      BookStates bookState = null ;

      for( BookStates bState : BookStates.values() ){
         if(bState.getId() == _id){
            bookState = bState;
            break;
         }
      }
      return bookState ;
   }

   public static void main(String[] args) {
      System.out.println( getBookState(0) );
   }
}