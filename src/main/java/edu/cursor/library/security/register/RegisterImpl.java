package edu.cursor.library.security.register;

import edu.cursor.library.security.credentials.service.CredentialsImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;
import edu.cursor.library.user.service.UserServiceImpl;
import org.joda.time.LocalDate ;

import java.sql.Timestamp;
import java.util.Scanner;
import edu.cursor.library.security.service.SecurityServiceImpl;

public class RegisterImpl implements Register{

   public RegisterImpl() {
   }

   @Override
   public void registerUser() {

      Scanner scan = new Scanner(System.in);
      String desiredMail;
      boolean loginAllowed = false;
      UserServiceImpl userService = new UserServiceImpl();
      CredentialsImpl credentials = CredentialsImpl.getInstance();
      SecurityServiceImpl securityService = new SecurityServiceImpl();

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
               int desiredMobile;
               do {
                  System.out.print("Your mobile # (9 numbers): ");
                  desiredMobile = scan.nextInt();
                  // X
                  if ( Integer.toString(desiredMobile).equalsIgnoreCase("x"))
                     break outer;
                  if (securityService.validateMobile(desiredMobile))
                     correctMobile = true;
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
//               addUser( newId, desiredName, desiredName2, desiredMail, desiredMobile, desiredAddr, new LocalDate().toString(), pass1 ) ;
               if(
                  userService.addUser( new TblUser(
                      newId,
                      desiredName,
                      desiredName2,
                      desiredMail,
                      desiredMobile,
                      desiredAddr,
                      LocalDate.now(),
                      Role.USER
                  ) )
                ){
                  credentials.addCredentials(newId, pass1);
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