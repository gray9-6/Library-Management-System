package com.example.LibraryManagementSystem.services.author;

import com.example.LibraryManagementSystem.dtos.author.AuthorRequestDto;
import com.example.LibraryManagementSystem.dtos.author.AuthorResponseDto;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.utils.Messages;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    public AuthorServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAuthor() {
        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("Test Author");
        requestDto.setBiography("Test Biography");

        Author savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setName("Test Author");
        savedAuthor.setBiography("Test Biography");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        AuthorResponseDto responseDto = authorService.createAuthor(requestDto);

        assertEquals(savedAuthor.getId(), responseDto.getId());
        assertEquals(savedAuthor.getName(), responseDto.getName());
        assertEquals(savedAuthor.getBiography(), responseDto.getBiography());
    }

    @Test
    public void testGetAuthors() {
        List<Author> authorList = new ArrayList<>();
        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("Author 1");
        author1.setBiography("Biography 1");
        authorList.add(author1);

        // Add more authors if needed for testing different scenarios

        when(authorRepository.findAll()).thenReturn(authorList);

        List<AuthorResponseDto> responseDtoList = authorService.getAuthors();

        assertEquals(authorList.size(), responseDtoList.size());
        // Add more assertions if needed to compare individual elements
    }

    @Test
    public void testUpdateAuthorById() {
        Long authorId = 1L;
        String newName = "New Author Name";
        String newBiography = "New Biography";

        Author existingAuthor = new Author();
        existingAuthor.setId(authorId);
        existingAuthor.setName("Old Name");
        existingAuthor.setBiography("Old Biography");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(existingAuthor);

        String result = authorService.updateAuthorById(authorId, newName, newBiography);

        assertEquals(Messages.UPDATED, result);
        assertEquals(newName, existingAuthor.getName());
        assertEquals(newBiography, existingAuthor.getBiography());
    }

    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        author.setName("Test Author");
        author.setBiography("Test Biography");

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        AuthorResponseDto responseDto = authorService.getAuthorById(authorId);

        assertEquals(author.getId(), responseDto.getId());
        assertEquals(author.getName(), responseDto.getName());
        assertEquals(author.getBiography(), responseDto.getBiography());
    }

    @Test
    void testDeleteAuthorById_SuccessfulDeletion() {
        // Arrange
        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.of(author));

        // Act
        String result = authorService.deleteAuthorById(authorId);

        // Assert
        assertEquals(Messages.DELETED_SUCCESSFULLY, result);
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    void testDeleteAuthorById_AuthorNotFound() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            authorService.deleteAuthorById(authorId);
        });
        verify(authorRepository, times(1)).findById(authorId);
        verify(authorRepository, never()).deleteById(any());
    }

}
