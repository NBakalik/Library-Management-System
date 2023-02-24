package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.exeptions.AuthorNotFoundException;
import com.example.library.exeptions.BookNotFoundException;
import com.example.library.exeptions.CategoryNotFoundException;
import com.example.library.repo.AuthorRepository;
import com.example.library.repo.BookRepository;
import com.example.library.repo.CategoryRepository;
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

@WebMvcTest(BookService.class)
public class BookServiceTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    @InjectMocks
    private BookService bookService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeAll
    public static void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void getAllBooksWhenExistTest() {
        when(bookRepository.findAll())
                .thenReturn(List.of(new Book(1, "Java 8", new Category("Programming")),
                        new Book(2, "Romeo and Juliet", new Category("Tragedy"))));

        List<Book> bookList = bookService.getAllBook();
        assertThat(bookList.get(0).getId()).isEqualTo(1);
        assertThat(bookList.get(0).getName()).isEqualTo("Java 8");
        assertThat(bookList.get(0).getCategory().getName()).isEqualTo("Programming");
        assertThat(bookList.get(1).getId()).isEqualTo(2);
        assertThat(bookList.get(1).getName()).isEqualTo("Romeo and Juliet");
        assertThat(bookList.get(1).getCategory().getName()).isEqualTo("Tragedy");
    }

    @Test
    public void getAllBooksWhenNotExistTest() {
        when(bookRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> bookService.getAllBook())
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void getBookByIdWhenExistTest() {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Book(1, "Java 8", new Category("Programming"))));

        Book book = bookService.getBook(anyInt());
        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getName()).isEqualTo("Java 8");
        assertThat(book.getCategory().getName()).isEqualTo("Programming");
    }

    @Test
    public void getBookByIdWhenNotExistTest() {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBook(anyInt()))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    public void updateBookWhenExistTest() {
        Book book = new Book(1, "Java 8", new Category("Programming"));

        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.of(book));
        when(bookRepository.save(book))
                .thenReturn(book);

        book = bookService.updateBook(1, book);
        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getName()).isEqualTo("Java 8");
        assertThat(book.getCategory().getName()).isEqualTo("Programming");
    }

    @Test
    public void updateBookWhenNotExistTest() {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBook(anyInt(), new Book(1, "Java 8", new Category("Programming"))))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository).findById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void deleteBookWhenExistTest() {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Book(1, "Java 8", new Category("Programming"))));
        bookService.deleteBook(anyInt());

        verify(bookRepository).deleteById(anyInt());
    }

    @Test
    public void deleteBookWhenNotExistTest() {
        when(bookRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBook(anyInt()))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository).findById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void deleteAllBooksTest() {
        bookService.deleteAll();
        verify(bookRepository).deleteAll();
    }

    @Test
    public void findBooksByAuthorsIdWhenAuthorExistTest() throws ParseException {
        when(authorRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Author(1, "John", sdf.parse("2022-02-19"), "USA")));

        when(bookRepository.findBooksByAuthorsId(anyInt()))
                .thenReturn(List.of(new Book(1, "Java 8", new Category("Programming")),
                        new Book(2, "Romeo and Juliet", new Category("Tragedy"))));

        List<Book> bookList = bookService.findBooksByAuthorsId(anyInt());
        assertThat(bookList.get(0).getId()).isEqualTo(1);
        assertThat(bookList.get(0).getName()).isEqualTo("Java 8");
        assertThat(bookList.get(0).getCategory().getName()).isEqualTo("Programming");
        assertThat(bookList.get(1).getId()).isEqualTo(2);
        assertThat(bookList.get(1).getName()).isEqualTo("Romeo and Juliet");
        assertThat(bookList.get(1).getCategory().getName()).isEqualTo("Tragedy");
    }

    @Test
    public void findBooksByAuthorsIdWhenAuthorNotExistTest() {
        when(authorRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findBooksByAuthorsId(anyInt()))
                .isInstanceOf(AuthorNotFoundException.class);

        verify(authorRepository).findById(anyInt());
        verifyNoInteractions(bookRepository);
    }

    @Test
    public void findBooksByCategoryIdWhenCategoryExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Category(1, "Comedy")));

        when(bookRepository.findBooksByCategoryId(anyInt()))
                .thenReturn(List.of(new Book(1, "Java 8", new Category("Programming")),
                        new Book(2, "Java 11", new Category("Programming"))));

        List<Book> bookList = bookService.findBooksByCategoryId(anyInt());
        assertThat(bookList.get(0).getId()).isEqualTo(1);
        assertThat(bookList.get(0).getName()).isEqualTo("Java 8");
        assertThat(bookList.get(0).getCategory().getName()).isEqualTo("Programming");
        assertThat(bookList.get(1).getId()).isEqualTo(2);
        assertThat(bookList.get(1).getName()).isEqualTo("Java 11");
        assertThat(bookList.get(1).getCategory().getName()).isEqualTo("Programming");
    }

    @Test
    public void findBooksByCategoryIdWhenCategoryNotExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findBooksByCategoryId(anyInt()))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(anyInt());
        verifyNoInteractions(bookRepository);
    }

    @Test
    public void deleteBooksByCategoryIdWhenCategoryExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Category(1, "Comedy")));
        bookService.deleteBooksByCategoryId(anyInt());
        verify(bookRepository).deleteBooksByCategoryId(anyInt());
    }

    @Test
    public void deleteBooksByCategoryIdWhenCategoryNotExistTest() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBooksByCategoryId(anyInt()))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    public void addBookToCategoryWhenCategoryExist() {
        Category category = new Category("Programming");
        Book book = new Book(1, "Java 8", null);

        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.of(category));
        when(bookRepository.save(any()))
                .thenReturn(book);

        book = bookService.addBookToCategory(anyInt(), book);
        assertThat(book.getId()).isEqualTo(1);
        assertThat(book.getName()).isEqualTo("Java 8");
        assertThat(book.getCategory().getName()).isEqualTo("Programming");
    }

    @Test
    public void addBookToCategoryWhenCategoryNotExist() {
        when(categoryRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.addBookToCategory(anyInt(), new Book()))
                .isInstanceOf(CategoryNotFoundException.class);

        verify(categoryRepository).findById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }
}
