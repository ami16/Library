package edu.cursor;

public enum BookCat {

   BIOGRAPHY(1),
   LOVE_STORY(2),
   FICTION(3),
   TALES(4),
   FANTASY(5) ;

   private int id ;
   private BookCat(int _id) {
      id = _id;
   }

   public int getId() { return id; }

   public static BookCat getCat(int id){
      BookCat cat = null ;
      for( BookCat c : BookCat.values() ){
         if(id == c.getId()){
            cat = c ;
            break;
         }
      }
      return cat ;
   }

   public static void main(String[] args) {
      System.out.println( getCat(3) );
   }

}