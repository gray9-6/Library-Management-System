package com.example.LibraryManagementSystem.services.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.exception.AuthorNotFoundException;
import com.example.LibraryManagementSystem.exception.BookNotFoundException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.services.rental.RentalServiceImpl;
import com.example.LibraryManagementSystem.transformers.BookTransformer;
import com.example.LibraryManagementSystem.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    RentalServiceImpl rentalService;

    public BookResponseDto createBook(BookRequestDto bookRequestDto){
        // convert the RequestDto to Entity
        Book createdBook = BookTransformer.bookRequestDtoToBook(bookRequestDto);
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).orElseThrow(() -> new UsernameNotFoundException("Author Not found Exception"));
        createdBook.setAuthor(author);
        String isbn = String.valueOf(UUID.randomUUID()).substring(0,13);
        createdBook.setIsbn(isbn);

        // save the book in db
        Book savedBook = bookRepository.save(createdBook);

        // after setting the book to author, the bookList in author needs to be updated as well
        author.getBooks_written().add(createdBook);
        authorRepository.save(author);

        // prepare the responseDto
        return BookTransformer.bookToBookResponseDto(savedBook);
    }

    public BookResponseDto getBookById(Long authorId){
        Book book = bookRepository.findById(authorId).orElseThrow(() -> new BookNotFoundException(Messages.BOOK_NOT_FOUND));
        return BookTransformer.bookToBookResponseDto(book);
    }

    public List<BookResponseDto> getAllBooks(){
        List<Book> bookList =  bookRepository.findAll();
        return bookList.stream().map(BookTransformer::bookToBookResponseDto).collect(Collectors.toList());
    }

    public String updateBookById(Long bookId,BookRequestDto bookRequestDto){
       try {
           Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(Messages.BOOK_NOT_FOUND));

           // update the changed fields
           book.setTitle(bookRequestDto.getTitle());
           book.setPublicationYear(bookRequestDto.getPublicationYear());
           if(bookRequestDto.getAuthorId() != null){  // change the author
               // check if the author exists or not
               Author newAuthor = authorRepository.findById(bookRequestDto.getAuthorId()).orElseThrow(()-> new AuthorNotFoundException(Messages.AUTHOR_NOT_FOUND));

               // remove the previous author book from the bookList
               Author prevAuthor = authorRepository.findById(book.getAuthor().getId()).orElseThrow(()-> new AuthorNotFoundException(Messages.AUTHOR_NOT_FOUND));
               List<Book> filteredBookList = prevAuthor.getBooks_written().stream()
                       .filter(book1 -> !book1.getId().equals(bookId))
                       .collect(Collectors.toList());
               prevAuthor.setBooks_written(filteredBookList);
               authorRepository.save(prevAuthor);  // save the author

               // now changed the author as well
               book.setAuthor(newAuthor);
           }


           bookRepository.save(book);  // and then save the book
           return Messages.UPDATED;
       }catch (Exception e){
           return e.getMessage();
       }

    }

    public String deleteBookById(Long bookId){
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(Messages.BOOK_NOT_FOUND));

            //before remove the book, get the author of the book , and remove this book from its bookList
            Author author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(()-> new AuthorNotFoundException(Messages.AUTHOR_NOT_FOUND));
            List<Book> filteredBookList = author.getBooks_written().stream()
                    .filter(book1 -> !book1.getId().equals(bookId))
                    .collect(Collectors.toList());
            author.setBooks_written(filteredBookList);
            authorRepository.save(author);  // save the author

            // now remove the book
            bookRepository.deleteById(book.getId());

            return Messages.DELETED_SUCCESSFULLY;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<BookResponseDto> getBooksAvailableForRent(){
        Set<Long> booksOnRent = rentalService.retrieveAllRentalRecords().stream().map(rentBookResponseDto -> rentBookResponseDto.getBookResponseDto().getId()).collect(Collectors.toSet());
        List<Book> bookList =  bookRepository.findAll();

        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for (Book book:bookList) {
            if(!booksOnRent.contains(book.getId())){
                BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(book);
                bookResponseDtoList.add(bookResponseDto);
            }
        }

        return bookResponseDtoList;
    }

    public List<BookResponseDto> getAllRentedBooks(){
        Set<Long> booksOnRent = rentalService.retrieveAllRentalRecords().stream().map(rentBookResponseDto -> rentBookResponseDto.getBookResponseDto().getId()).collect(Collectors.toSet());
        List<Book> bookList =  bookRepository.findAll();

        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for (Book book:bookList) {
            if(booksOnRent.contains(book.getId())){
                BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(book);
                bookResponseDtoList.add(bookResponseDto);
            }
        }

        return bookResponseDtoList;
    }

    public List<BookResponseDto> getBooksByAuthor(String authorName){
        List<Book> bookList =  bookRepository.findAll();
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for (Book book:bookList) {
            if(book.getAuthor().getName().equals(authorName)){
                BookResponseDto bookResponseDto = BookTransformer.bookToBookResponseDto(book);
                bookResponseDtoList.add(bookResponseDto);
            }
        }

        return bookResponseDtoList;
    }


}
