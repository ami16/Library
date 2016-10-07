#Library

---


1. Collection of users ( login, mail, name )

2. User Registration ability

3. User Authorization/authentication (?) ability

4. Books collection (title, author, ISBN)

5. Menu

5.1. CAN View available(!!) books (in same cat/type... & the same book v)

5.2. (how much are the same books available)

5.3. How much books are TAKEN / AVAILABLE

5.4. View Turn back period (date.. time.. String (?) ) == 7 days

6. Admin

6.1. CAN VIEW all users list

6.2. User Menu available

6.3. CAN VIEW what book each user is holding right now AND WHEN user must turn it back!



---
##Project structure
###For now


L-- src    
  L-- main
    L-- java            
      L-- edu                
       L-- cursor                    
         L-- library                        
           +-- application
           |   +-- App.java
           |
           +-- entity
           |   L-- book
           |   |   +-- BookCategory.java
           |   |   +-- Book.java
           |   |   +-- Service.java (so-called BookFunctions)
           |   |   +-- Comparator.java
           |   |
           |   L-- user
           |       +-- User.java (interface)
           |       +-- UserRoles.java
           |       +-- UserImpl.java
           |       +-- Service.java (so-called UserFunctions)
           |
           +-- factory                        
           |
           +-- service
           |   +-- Service.java (interface)
           |   +-- ServiceImpl.java (...LibFunctions)
           |
           +-- util
           |
           |
           +-- security
               L-- Auth
               |   +-- xxx
               |
               L-- Credential (? maybe remake)