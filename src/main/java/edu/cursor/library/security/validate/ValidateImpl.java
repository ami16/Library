package edu.cursor.library.security.validate;

import java.util.Scanner;
import edu.cursor.library.security.service.SecurityServiceImpl;
import edu.cursor.library.security.auth.AuthImpl;

public class ValidateImpl implements Validate{

   public ValidateImpl() {
   }

   public void validateUser() {
      Scanner scan = new Scanner(System.in);
      String userLogin, userPassword, verifiedLog = "";
      boolean is = false;
      SecurityServiceImpl srv = new SecurityServiceImpl();
      AuthImpl auth = AuthImpl.getInstance();

      System.out.println("Enter your login:");
      do {
         userLogin = scan.nextLine().trim();
         if (userLogin.equals("")) {
            System.out.print("Enter smth: ");
         } else {
            is = srv.userExists(userLogin);
            if (is)
               verifiedLog = userLogin;
         }
         if (!is) {
            System.out.println("No such login (3)");
         }
      } while (!is);

      // PASS
      System.out.println("Enter your pass:");
      do {
         userPassword = scan.nextLine().trim();
         if (userPassword.equals("")) {
            System.out.print("Enter smth: ");
         } else {
            is = srv.passIsCorrect(verifiedLog, userPassword);
            if (is) {
               // USER->getUser by ID
//               auth.logIn(user);
            }
         }
         if (!is) {
            System.out.println("Wrong password (3)");
         }
      } while (!is);
   }

}