package edu.cursor.library.user.registry;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.registry.entity.TblUserBooksRegistry;

import java.util.List;
import java.util.Map;

public interface UserBooksRegistry {

//   Map<Integer, List<Integer>> getRegistry();
//   void putRegistry(Map<Integer, List<Integer>> map);
   void putRegistry(List<TblUserBooksRegistry> list);

   void registerBook(TblUser user, TblBook book);
   void unregisterBook(TblUser user, TblBook book);

   public List<TblBook> getTakenBooksByIsbn(int book_id);
   List<TblBook> getTakenBooksByUser(int user_id );
   TblUser getUserByTakenBook(int book_id );

}