# Library Management System

## Description

This Library Management System is a web application developed to efficiently manage library resources, including books, authors, and rental records. It provides a range of APIs to perform various operations such as creating, updating, deleting, and retrieving information related to books, authors, and rentals. Additionally, the system features user authentication using JSON Web Tokens (JWT) to ensure secure access to its functionalities.

## Project Overview

The Library Management System offers a comprehensive solution for libraries or educational institutions to manage their collections and rental records effectively. With its user-friendly APIs, administrators can seamlessly perform administrative tasks, while users can conveniently browse and borrow books.

## Available APIs

### Authentication

- **Sign Up User**: `/sign-up` [POST]
  - Allows creating a new user.
- **Authenticate Token**: `/authenticate` [POST]
  - Allows authenticating the user and generating a JSON web token.

### Author

- **Create Author**: `/api/author/create` [POST]
  - Creates a new author.
- **Get All Authors**: `/api/author/getAll` [GET]
  - Retrieves information about all authors.
- **Update Author By Id**: `/api/author/updateById/{id}` [PUT]
  - Updates author information by its ID.
- **Get Author By Id**: `/api/author/getById/{id}` [GET]
  - Retrieves author information by its ID.
- **Delete Author By Id**: `/api/author/deleteById/{id}` [DELETE]
  - Deletes an author by its ID.

### Book

- **Create Book**: `/api/book/create` [POST]
  - Creates a new book.
- **Get Book By Id**: `/api/book/getById/{id}` [GET]
  - Retrieves book information by its ID.
- **Get All Books**: `/api/book/getBooks` [GET]
  - Retrieves information about all books.
- **Update Book By Id**: `/api/book/updateById/{id}` [UPDATE]
  - Updates book information by its ID.
- **Delete Book By Id**: `/api/book/deleteById/{id}` [DELETE]
  - Deletes a book by its ID.
- **Get Books Available for Rents**: `/api/book/availableForRents` [GET]
  - Retrieves information about books available for rent.
- **Get All Rented Books**: `/api/book/rentedBooks` [GET]
  - Retrieves information about all rented books.
- **Get Books by Author**: `/api/book/getByAuthor/{authorName}` [GET]
  - Retrieves books by the author's name.

### Rental

- **Create a Rental Record**: `/api/rent/book` [POST]
  - Creates a rental record.
- **Retrieve All Rental Records**: `/api/rent/retrieveAll` [GET]
  - Retrieves all rental records.
- **Retrieve Rental Records by Author Name**: `/api/rent/retrieveBy/{authorName}` [GET]
  - Retrieves rental records by author name.
- **Return Book**: `/api/rent/return` [POST]
  - Allows returning a book.
- **Get Overdue Rental Records**: `/api/rent/overDue` [GET]
  - Retrieves overdue rental records.

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
