# E-Commerce Console Application

## Overview
This project is an interactive shopping system where users can browse through various categories of products like Laptops, Phones, TVs, and Fashion items, add them to their cart, and simulate a checkout process.
## Features

### User Authentication
- A login/logout system.
- Define roles and manage access levels (customer, admin).
- The application supports multiple admins.
- The application supports multiple customers.
- The user will be asked to choose his profile type at first (Admin or Customer), then he will be asked whether to sign up or login.
- Sign up: The user will be asked to enter his username and password and confirm it, The username and password will be saved in a file for future logins(admin_login.txt or customer_login.txt).
- Login: The user will be asked to enter his username and password, if the username and password are correct(The program will search in the file for a matching username/password), the user will be logged in, otherwise, an error message will be printed.

### ProductManagement
- When an admin logs in, he will be able to add a new product, update an existing product, view the products, or delete a product.
- Added products will be saved in a file (TV.txt/Phone.txt/....) and will be loaded when the program starts.
- Note: When adding a new product or modifying an existing one, the Product ID should be unique and TV ID: from 0 to 999 Phone ID: from 1000 to 1999 Laptop ID: from 2000 to 2999 Fashion ID: from 3000 to 3999. Of course, the isUniqueId and isIdInRange methods will be used to check the validity of the ID. Just make sure not to change the ID of an existing product manually in the files to ensure the stability of the program.

### Dynamic Product Search and Filtering
- When a Customer logs in, he will be able to view the products by category, search for a product by name, or filter the products by price range.

### Shopping Cart
- When a customer logs in, and search for the products he wants, he will be able to add the product to the cart, update the quantity of the product in the cart, or remove the product from the cart.
- A customer will be able to see the products in his cart and can choose to check out or continue shopping.

### Shipping Options
- When a customer chooses to check out, he will be asked to choose the shipping method (Home Delivery or Pick-up in Store).
- Then the customer will be asked to enter his personnel information (Fullname/Address/Phone number/...).
- Note: The Home Delivery option will add 10$ to the total price.

### Discounts
- The application supports discounts.
- When a customer chooses to check out, he will be asked if he has a discount code, if yes, he will be asked to enter the discount code, if the discount code is valid, the discount will be applied to the total price, otherwise, an error message will be printed.
- Note: The discount code is a 8-digit number, and the discount amount depends on the code itself.
- Note: The discount codes are saved in a file (Codes.txt) in this format: Code,DiscountAmount. You can pick the code you want and try it.

### Payment Processing
- When a customer chooses to check out, he will be asked to choose the payment method (Payment upon Delivery/Receiving or Credit Card).
- If the customer chooses to pay by Credit Card, he will be asked to enter his Credit Card information (Card Number/Expiration Date/...).

### Order Processing
- After a successful checkout, the order details will be saved in a file (transactions.txt).

### Inventory Management
- The inventory class role is to decrease the quantity of purchased products in the files (TV.txt/Phone.txt/....) after a successful checkout.
- When the customer adds a product to the cart, there will be a check to make sure that the quantity of the product is available in the inventory, otherwise, an error message will be printed.




