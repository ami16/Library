package edu.cursor.library.security;

import edu.cursor.library.infrastructure.persistence.MySqlCredentialsDao;
import edu.cursor.library.infrastructure.persistence.MySqlUserDao;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.UserRole;
import edu.cursor.library.service.UserServiceImpl;
import org.joda.time.LocalDate ;

import java.util.Scanner;


public class RegisterImpl implements Register{

//   CSVFileUserDao userDao = new CSVFileUserDao();
   private MySqlUserDao userDao = new MySqlUserDao();

   public RegisterImpl() {
   }

   @Override
   public void registerUser() {

      Scanner scan = new Scanner(System.in);
      String desiredMail;
      boolean loginAllowed = false;
      UserServiceImpl userService = UserServiceImpl.getInstance();
//      CredentialsImpl credentialsDao = CredentialsImpl.getInstance();
      MySqlCredentialsDao credentialsDao = new MySqlCredentialsDao();
      SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();

      // LOGIN
      outer: do {
         System.out.println("Please enter your MAIL as a login: ");
         desiredMail = scan.nextLine().trim();
         // X
         if (desiredMail.equalsIgnoreCase("x"))
            break;
         if ( securityService.validateMail(desiredMail)) {

            if (!securityService.loginAvailable(desiredMail, true)) {
               loginAllowed = false;
            } else {

               // MAIL ok.
               // Now PASS
               boolean passCorrect = false;
               boolean passEqual = false;
               String pass1;
               System.out.print("OKAY NOW! Enter your password. ");
               do {
                  System.out.println("Password must be at least 4 chars:");
                  pass1 = scan.nextLine().trim();
                  // X
                  if (pass1.equalsIgnoreCase("x"))
                     break outer;
                  if (securityService.validatePass(pass1))
                     passCorrect = true;
               } while (!passCorrect);
               do {
                  System.out.println("Repeat pass ones more:");
                  String pass2 = scan.nextLine().trim();

                  // X
                  if (pass2.equalsIgnoreCase("x"))
                     break outer;
                  if (pass1.equals(pass2)) {
                     passEqual = true;
                  } else {
                     System.out.println("Your passwords doesn't match.");
                  }
               } while (!passEqual);

               // PASS ok. Now Name
               boolean correctName = false;
               String desiredName;
               do {
                  System.out.print("Pls give your name: ");
                  desiredName = scan.nextLine().trim();
                  // X
                  if (desiredName.equalsIgnoreCase("x"))
                     break outer;
                  if (securityService.validateName(desiredName))
                     correctName = true;
               } while (!correctName);

               // Name ok. Now name2
               boolean correctName2 = false;
               String desiredName2;
               do {
                  System.out.print("Pls provide your second name: ");
                  desiredName2 = scan.nextLine().trim();
                  // X
                  if (desiredName2.equalsIgnoreCase("x"))
                     break outer;
                  if (securityService.validateName(desiredName2))
                     correctName2 = true;
               } while (!correctName2);

               // name2 ok. Now MOBILE
               boolean correctMobile = false;
               String desiredMobile;
               do {
                  System.out.print("Your mobile # (9 numbers starting with NON-ZERO digit): ");
                  desiredMobile = scan.nextLine();
                  // X
                  if ( desiredMobile.equalsIgnoreCase("x"))
                     break outer;

                     correctMobile = securityService.validateMobile(desiredMobile) ;

               } while (!correctMobile);

               // MOBILE ok. Now ADDRESS
               boolean correctAddr = false;
               String desiredAddr;
               do {
                  System.out.print("Provide your address (10 chars min): ");
                  desiredAddr = scan.nextLine().trim();
                  // X
                  if ( desiredAddr.equalsIgnoreCase("x"))
                     break outer;
                  if ( desiredAddr.length() > 10 )
                     correctAddr = true;
               } while (!correctAddr);

               loginAllowed = true;

               int newId = securityService.getNewUserId() ;


               // userService
               long createdUserId = (long) userDao.createUser( new TblUser(
                   newId,
                   desiredName,
                   desiredName2,
                   desiredMail,
                   Integer.parseInt(desiredMobile),
                   desiredAddr,
                   LocalDate.now(),
                   UserRole.USER
               )) ;

               if( createdUserId != 0 ){
//                  credentialsDao.addCredentials(newId, pass1);
                  credentialsDao.addCredentials( (int) createdUserId, pass1 );
                  System.out.println("Now login using your credentials");
               } else {
                  System.out.println("Something went wrong. Try ones more or contact admin... Bla-bla..");
               }

            }

         } else {
            System.out.println("Not allowed");
         }
      } while (!loginAllowed);
   }
}