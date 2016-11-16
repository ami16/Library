package edu.cursor.library.model.DAO;

import edu.cursor.library.model.TblBook;
import java.util.List;


public interface BookDAO {

   List<TblBook> findAll();
   List<TblBook> findById( int id );
   boolean createBook(TblBook book);
   void updateBook(TblBook book);
   void deleteBook(int ISBN);

}