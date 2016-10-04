package edu.cursor;

public enum BookCategories {

   BIOGRAPHY(1),
   LOVE_STORY(2),
   FICTION(3),
   TALES(4),
   FANTASY(5) ;

   private int id ;
   private BookCategories(int _id) {
      id = _id;
   }

   public int getId() { return id; }
   public void setId(int id) { this.id = id; }

   public static BookCategories getCategory(int id){
      BookCategories category = null ;
      for( BookCategories c : BookCategories.values() ){
         if(id == c.getId()){
            category = c ;
            break;
         }
      }
      return category ;
   }

   public static void main(String[] args) {
      System.out.println( getCategory(2) );
   }

}