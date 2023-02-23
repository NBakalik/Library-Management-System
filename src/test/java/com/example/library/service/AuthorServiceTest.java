package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.exeptions.AuthorAlreadyExistException;
import com.example.library.exeptions.AuthorNotFoundException;
import com.example.library.exeptions.BookNotFoundException;
import com.example.library.repo.AuthorRepository;
import com.example.library.repo.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(AuthorService.class)
public class AuthorServiceTest {

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    @InjectMocks
    private AuthorService authorService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeAll
    public static void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void getAllAuthorsWhenExistTest() throws ParseException {
        when(authorRepository.findAll())
                .thenReturn(List.of(new Author(1, "John", sdf.parse("2022-02-19"), "USA"),
                        new Author(2, "Jack", sdf.parse("2022-08-04"), "France")));

        List<Author> authorList = authorService.getAllAuthor();
        assertThat(authorList.get(0).getId()).isEqualTo(1);
        assertThat(authorList.get(0).getName()).isEqualTo("John");
        assertThat(authorList.get(0).getBirthDate()).isEqualTo("2022-02-19T00:00:00.000+00:00");
        assertThat(authorList.get(0).getCountry()).isEqualTo("USA");
        assertThat(authorList.get(1).getId()).isEqualTo(2);
        assertThat(authorList.get(1).getName()).isEqualTo("Jack");
        assertThat(authorList.get(1).getBirthDate()).isEqualTo("2022-08-04T00:00:00.000+00:00");
        assertThat(authorList.get(1).getCountry()).isEqualTo("France");
    }

    @Test
    public void getAllAuthorsWhenNotExistTest() {
        when(authorRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> authorService.getAllAuthor())
                .isInstanceOf(AuthorNotFoundException.class);
    }

    @Test
    public void getAuthorByIdWhenExistTest() throws ParseException {
        when(authorRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Author(1, "John", sdf.parse("2022-02-19"), "USA")));

        Author author = authorService.getAuthor(1);
        assertThat(author.getId()).isEqualTo(1);
        assertThat(author.getName()).isEqualTo("John");
        assertThat(author.getBirthDate()).isEqualTo("2022-02-19T00:00:00.000+00:00");
        assertThat(author.getCountry()).isEqualTo("USA");
    }

    @Test
    public void getAuthorByIdWhenNotExistTest() {
        when(authorRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.getAuthor(anyInt()))
                .isInstanceOf(AuthorNotFoundException.class);
    }

    @Test
    public void createAuthorWithoutIdTest() throws ParseException {
        Author author = new Author("John", sdf.parse("2022-02-19"), "USA");
        authorService.addAuthor(author);

        verify(authorRepository).save(author);
    }

    @Test
    public void createAuthorWithIdTest() throws ParseException {
        Author author = new Author(1, "John", sdf.parse("2022-02-19"), "USA");

        assertThatThrownBy(() -> authorService.addAuthor(author))
                .isInstanceOf(AuthorAlreadyExistException.class);

        verifyNoInteractions(authorRepository);
    }

    @Test
    public void updateAuthorWhenExistTest() throws ParseException {
        Author author = new Author(1, "John", sdf.parse("2022-02-19"), "USA");

        when(authorRepository.findById(1))
                .thenReturn(Optional.of(author));
        when(authorRepository.save(author))
                .thenReturn(author);

        author = authorService.updateAuthor(1, author);
        assertThat(author.getId()).isEqualTo(1);
        assertThat(author.getName()).isEqualTo("John");
        assertThat(author.getBirthDate()).isEqualTo("2022-02-19T00:00:00.000+00:00");
        assertThat(author.getCountry()).isEqualTo("USA");
    }

    @Test
    public void updateAuthorWhenNotExistTest() throws ParseException {
        Author author = new Author(1, "John", sdf.parse("2022-02-19"), "USA");

        when(authorRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.updateAuthor(1, author))
                .isInstanceOf(AuthorNotFoundException.class);
        verify(authorRepository).findById(1);
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    public void deleteAuthorWhenExistTest() throws ParseException {
        Author author = new Author(1, "John", sdf.parse("2022-02-19"), "USA");

        when(authorRepository.findById(1))
                .thenReturn(Optional.of(author));
        authorService.deleteAuthor(1);

        verify(authorRepository).deleteById(anyInt());
    }

    @Test
    public void deleteAuthorWhenNotExistTest() {
        when(authorRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.deleteAuthor(1))
                .isInstanceOf(AuthorNotFoundException.class);

        verify(authorRepository).findById(anyInt());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    public void deleteAllCategoriesTest() {
        authorService.deleteAll();
        verify(authorRepository).deleteAll();
    }

    @Test
    public void deleteBookFromAuthorWhenAuthorExistTest() throws ParseException {
        Author author = new Author(1, "John", sdf.parse("2022-02-19"), "USA");

        when(authorRepository.findById(1))
                .thenReturn(Optional.of(author));
        authorService.deleteBookFromAuthor(1, 1);

        verify(authorRepository).save(author);
    }

    @Test
    public void deleteBookFromAuthorWhenAuthorNotExistTest() {
        when(authorRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.deleteBookFromAuthor(1, 1))
                .isInstanceOf(AuthorNotFoundException.class);

        verify(authorRepository).findById(anyInt());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    public void findAuthorsByBooksIdWhenBookExistTest() throws ParseException {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Book(1, "Java 8", new Category("Programming"))));

        when(authorRepository.findAuthorsByBooksId(anyInt()))
                .thenReturn(List.of(new Author(1, "John", sdf.parse("2022-02-19"), "USA"),
                        new Author(2, "Jack", sdf.parse("2022-08-04"), "France")));

        List<Author> authorList = authorService.findAuthorsByBooksId(1);
        assertThat(authorList.get(0).getId()).isEqualTo(1);
        assertThat(authorList.get(0).getName()).isEqualTo("John");
        assertThat(authorList.get(0).getBirthDate()).isEqualTo("2022-02-19T00:00:00.000+00:00");
        assertThat(authorList.get(0).getCountry()).isEqualTo("USA");
        assertThat(authorList.get(1).getId()).isEqualTo(2);
        assertThat(authorList.get(1).getName()).isEqualTo("Jack");
        assertThat(authorList.get(1).getBirthDate()).isEqualTo("2022-08-04T00:00:00.000+00:00");
        assertThat(authorList.get(1).getCountry()).isEqualTo("France");
    }

    @Test
    public void findAuthorsByBooksIdWhenBookNotExistTest() {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findAuthorsByBooksId(anyInt()))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository).findById(anyInt());
        verifyNoInteractions(authorRepository);
    }
}
