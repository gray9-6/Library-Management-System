# Library Management System

## Description

This Library Management System is a web application developed to efficiently manage library resources, including books, authors, and rental records. It provides a range of APIs to perform various operations such as creating, updating, deleting, and retrieving information related to books, authors, and rentals. Additionally, the system features user authentication using JSON Web Tokens (JWT) to ensure secure access to its functionalities.

## Initial SetUp :-  please change the username and password  as per your sql configuration , in application.properties


## Project Overview

The Library Management System offers a comprehensive solution for libraries or educational institutions to manage their collections and rental records effectively. With its user-friendly APIs, administrators can seamlessly perform administrative tasks, while users can conveniently browse and borrow books.

## Available APIs

### Authentication

- **Sign Up User**: `/sign-up` [POST]
  - Allows creating a new user.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/c873cc29-b72c-47ff-8883-3a65e00534a5)

- **Authenticate Token**: `/authenticate` [POST]
  - Allows authenticating the user and generating a JSON web token.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/5c5b0428-fe56-4918-9713-1f85956a5a42)

 

/**
 * Note:
 * Before accessing any authorized API (i.e., those starting from `/api/**`), you must provide an authorization token.
 * This token is obtained from the response when authenticating the user.
 */
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/66568df9-41a4-4546-898e-cc29e684674f)
   ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/6af1c402-a147-4515-b99d-0f0436553352)



### Author

- **Create Author**: `/api/author/create` [POST]
  - Creates a new author.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/aa6e23d6-a2b3-4d9e-af53-65b29c56a8e1)


    
- **Get All Authors**: `/api/author/getAll` [GET]
  - Retrieves information about all authors.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/40a7fb98-7297-4958-9111-5c9ed69a5397)

 
  
- **Update Author By Id**: `/api/author/updateById/{id}` [PUT]
  - Updates author information by its ID.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/ca33e0e8-b65d-4b8d-bf79-4d9b6b6a2648)

    

- **Get Author By Id**: `/api/author/getById/{id}` [GET]
  - Retrieves author information by its ID.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/be153320-9b37-4524-ae5a-a790348fd1e7)

    
- **Delete Author By Id**: `/api/author/deleteById/{id}` [DELETE]
  - Deletes an author by its ID.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/9c247fc8-1be0-48f1-a464-1dffec7b4502)


### Book

- **Create Book**: `/api/book/create` [POST]
  - Creates a new book.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/2543094c-e737-40a3-a16d-188df9047a4b)


- **Get Book By Id**: `/api/book/getById/{id}` [GET]
  - Retrieves book information by its ID.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/01d01ddb-2c31-4924-8cad-969d8e214189)


- **Get All Books**: `/api/book/getBooks` [GET]
  - Retrieves information about all books.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/7c85dd23-e403-45a0-8ebe-625412a3af99)


- **Update Book By Id**: `/api/book/updateById/{id}` [UPDATE]
  - Updates book information by its ID.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/6da06630-507c-4c28-bb2b-373eb72ceac6)


- **Delete Book By Id**: `/api/book/deleteById/{id}` [DELETE]
  - Deletes a book by its ID.
 ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/0032cf26-39ec-40c7-b1f7-21144b3bac1f)

    
- **Get Books Available for Rents**: `/api/book/availableForRents` [GET]
  - Retrieves information about books available for rent.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/39acf504-fc3d-4b72-9e89-28b39db7686a)

    
- **Get All Rented Books**: `/api/book/rentedBooks` [GET]
  - Retrieves information about all rented books.
  ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/7f398f80-1e18-4674-8cb1-3816d6138e06)

    
- **Get Books by Author**: `/api/book/getByAuthor/{authorName}` [GET]
  - Retrieves books by the author's name.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/52ca2178-6798-4976-8911-bd8728ff62f3)



### Rental

- **Create a Rental Record**: `/api/rent/book` [POST]
  - Creates a rental record.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/de7010af-db8f-48d3-9e5b-f25c4af63079)


- **Retrieve All Rental Records**: `/api/rent/retrieveAll` [GET]
  - Retrieves all rental records.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/f17239ce-833b-4a24-8995-df881293844e)


- **Retrieve Rental Records by Author Name**: `/api/rent/retrieveBy/{authorName}` [GET]
  - Retrieves rental records by author name.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/4b8c915c-9084-4c34-9fbc-a7c2bd7ecedb)

    
- **Return Book**: `/api/rent/return` [POST]
  - Allows returning a book.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/59e56d3a-566f-4359-a577-0c0cae8ac15f)
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/191d3cdf-0fa7-4ddf-8969-2480b41302e4)

    
- **Get Overdue Rental Records**: `/api/rent/overDue` [GET]
  - Retrieves overdue rental records.
    ![image](https://github.com/gray9-6/Library-Management-System/assets/123147364/d596be6f-bd83-4b41-b372-bb88e8a8a101)


## Technical Choices

- **Java**: Employed for its robustness and extensive ecosystem.
- **Spring Boot**: Utilized for rapid development and easy integration.
- **SQL Database**: Utilized for data storage and management, providing relational database capabilities.
- **JWT Security**: Implemented for secure authentication and authorization.

## Future Improvements

- Implementation of advanced search and filtering capabilities.
- Integration with external book databases for automatic book information retrieval.
- Implementation of fine-grained access control based on user roles and permissions.
- Enhancement of user interface for better user experience.
