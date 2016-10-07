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

<space><space>L-- main

<space><space><space><space>L-- java

<space><space><space><space><space><space>L-- edu

<space><space><space><space><space><space><space>L-- cursor

<space><space><space><space><space><space><space><space><space>L-- library

<space><space><space><space><space><space><space><space><space><space><space>+-- application

<space><space><space><space><space><space><space><space><space><space><space>+-- domain

<space><space><space><space><space><space><space><space><space><space><space>¦<space><space><space>L-- model

<space><space><space><space><space><space><space><space><space><space><space>¦<space><space><space><space><space><space>+-- BookCategory.java

<space><space><space><space><space><space><space><space><space><space><space>¦<space><space><space><space><space><space>+-- Book.java

<space><space><space><space><space><space><space><space><space><space><space>¦<space><space><space><space><space><space>+-- Comparator.java

<space><space><space><space><space><space><space><space><space><space><space>¦<space><space><space><space><space><space>L-- Service.java

<space><space><space><space><space><space><space><space><space><space><space>+-- factory

<space><space><space><space><space><space><space><space><space><space><space>+-- infrastructure

<space><space><space><space><space><space><space><space><space><space><space>¦<space><space><space>L-- ServiceImpl.java

<space><space><space><space><space><space><space><space><space><space><space>L-- util

<space><space><space><space><space><space><space><space><space><space><space>L-- security
