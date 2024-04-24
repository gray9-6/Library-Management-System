package com.example.LibraryManagementSystem.services.author;

import com.example.LibraryManagementSystem.dtos.author.AuthorRequestDto;
import com.example.LibraryManagementSystem.dtos.author.AuthorResponseDto;
import com.example.LibraryManagementSystem.exception.AuthorNotFoundException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.transformers.AuthorTransformer;
import com.example.LibraryManagementSystem.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Autowired
    AuthorRepository authorRepository;




    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto){
        // make the entity from the request dto
        Author author = AuthorTransformer.authorRequestDtoToAuthor(authorRequestDto);
        Author savedAuthor = authorRepository.save(author);

        // prepare the response dto from the saved author
        return AuthorTransformer.authorToAuthorResponseDto(savedAuthor);
    }

    public List<AuthorResponseDto> getAuthors(){
        List<Author> authorList =  authorRepository.findAll();

        // prepare the responseDto
        List<AuthorResponseDto> authorResponseDtoList = authorList.stream()
                .map(AuthorTransformer::authorToAuthorResponseDto)
                .collect(Collectors.toList());

        return authorResponseDtoList;
    }

    public String updateAuthorById(Long authorId,String name,String biography){
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if(optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();
            if(name != null){
                author.setName(name);
            }
            if(biography != null){
                author.setBiography(biography);
            }
            //save the author
            authorRepository.save(author);
            return Messages.UPDATED;
        }

        return Messages.AUTHOR_NOT_FOUND;
    }

    public AuthorResponseDto getAuthorById(Long authorId){
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if(optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();
            return AuthorTransformer.authorToAuthorResponseDto(author);
        }else{
            throw new AuthorNotFoundException(Messages.AUTHOR_NOT_FOUND);
        }
    }

    public String deleteAuthorById(Long authorId){
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new RuntimeException("Author Not Found"));
        authorRepository.deleteById(author.getId());
        return Messages.DELETED_SUCCESSFULLY;
    }

}
