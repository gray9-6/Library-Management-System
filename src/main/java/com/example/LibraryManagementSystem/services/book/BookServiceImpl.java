package com.example.LibraryManagementSystem.services.book;

import com.example.LibraryManagementSystem.dtos.book.BookRequestDto;
import com.example.LibraryManagementSystem.dtos.book.BookResponseDto;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.BookRepository;
import com.example.LibraryManagementSystem.transformers.BookTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public BookResponseDto createBook(BookRequestDto bookRequestDto){
        // convert the RequestDto to Entity
        Book createdBook = BookTransformer.bookRequestDtoToBook(bookRequestDto);
        Author author = authorRepository.findById(bookRequestDto.getAuthorId()).orElseThrow(() -> new UsernameNotFoundException("Author Not found Exception"));
        createdBook.setAuthor(author);

        // save the book in db
        Book savedBook = bookRepository.save(createdBook);

        // after setting the book to author, the bookList in author needs to be updated as well
        author.getBooks_written().add(createdBook);
        authorRepository.save(author);

        // prepare the responseDto
        return BookTransformer.bookToBookResponseDto(savedBook);
    }
}
