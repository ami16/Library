package edu.cursor.library.util;

import edu.cursor.library.model.BookGenre;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.service.BookServiceImpl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import static edu.cursor.library.infrastructure.Constants.*;


public class BookDaoUtils {

   public BookDaoUtils() {
   }

   public EnumSet<BookGenre> convertStringToEnumSet (String genres) {
      String[] fields = genres.split(MULTI_PICKLIST_SEPARATOR);
      List<BookGenre> genreList = new ArrayList<>();
      for (String g : fields) {
         genreList.add( BookGenre.valueOf(g) );
      }
      return EnumSet.copyOf(genreList);
   }

   public EnumSet convertSetToEnumSet(Set genre) {
      return EnumSet.copyOf(genre);
   }


   public String convertSetToString(Set genre) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < genre.toArray().length; i++){
         sb.append(genre.toArray()[i].toString());
         if (i < genre.toArray().length - 1) {
            sb.append(MULTI_PICKLIST_SEPARATOR);
         }
      }
      return sb.toString();
   }

   public String convertEnumSetToString( EnumSet<BookGenre> genresEnumSet ){
      StringBuilder sb = new StringBuilder();
      int count = genresEnumSet.size();
      int i = 1;
      for( Enum<BookGenre> bGenres : genresEnumSet ){
         sb.append(bGenres);
         if( i != count ){
            sb.append(";");
         }
         i++;
      }
      return sb.toString();
   }


   public static void main(String[] args) {
//      BookServiceImpl bookService = BookServiceImpl.getInstance();
//      BookDaoUtils bdu = new BookDaoUtils();
//      TblBook book = bookService.getBookById(159);
   }
}