package dataOperationTest;

import com.example.bookplaza.data.Book;
import com.example.bookplaza.data.Books;
import com.example.bookplaza.data.User;
import com.example.bookplaza.file.BookFileOperation;
import com.example.bookplaza.file.FileOperationFactory;
import com.example.bookplaza.file.UserFileOperation;
import com.example.bookplaza.user.UserOperation;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataOperationTest {
    @Test
    public void userDataOperation(){
        UserOperation.setApplicationDir(new File("TestResources/"));
        UserOperation userOperation = UserOperation.getInstance();
        InputStream inputStream = null;
        InputStream imageStream = null;
        InputStream userStream = null;
        try {
            inputStream = new FileInputStream(new File("TestResources/test_books_list_3000_realistic.csv"));
            imageStream = new FileInputStream("TestResources/icon.png");
            userStream = new FileInputStream("TestResources/userData.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        UserFileOperation userFileOperation= (UserFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER);
        userFileOperation.delete("123");
        userFileOperation.delete("comp2100@anu.edu.au");
        userFileOperation.delete("comp6442@anu.edu.au");

        userOperation.getUsername();
        userOperation.getUerImageUri();
        userOperation.saveSearchHistory("price>30");
        userOperation.getSearchHistory();
        userOperation.logout();
        userOperation.dataCheck(userStream,inputStream);

        userOperation.register("123","123");
        userOperation.login("123","456");
        userOperation.login("123","123");
        userOperation.getUerImageUri();
        userOperation.changeImage(imageStream);
        userOperation.register("123","123");
        userOperation.login("123","123");
        userOperation.getUsername();
        userOperation.saveSearchHistory("price>30");
        userOperation.getSearchHistory();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        userOperation.searchBooks(list);
        userOperation.logout();

        User user = new User("123", "123");
        user.setIsCustomImage(true);
        user.addSearchHistory("price>30");
        user.getSearchHistory();
        assertEquals(true,user.checkPassword("123"));
    }

    @Test
    public void BookDataOperation(){
        UserOperation.setApplicationDir(new File("TestResources/"));
        UserOperation userOperation = UserOperation.getInstance();
        InputStream inputStream = null;
        InputStream imageStream = null;
        InputStream userStream = null;
        try {
            inputStream = new FileInputStream(new File("TestResources/test_books_list_3000_realistic.csv"));
            imageStream = new FileInputStream("TestResources/icon.png");
            userStream = new FileInputStream("TestResources/userData.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BookFileOperation bookFileOperation = (BookFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS);
        bookFileOperation.clearBookAssets();
        bookFileOperation.convertData(inputStream);

        Book book =(Book)bookFileOperation.load("10");
        book =(Book)bookFileOperation.load(10);
        assertEquals("The Hunger Games",book.getName());

        bookFileOperation.delete("10");
        bookFileOperation.save(book);

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(13);
        Books books = bookFileOperation.load(list);
        bookFileOperation.delete(list);
        bookFileOperation.save(books);

        books.getBooksList();
        books.isEmpty();
        books.clearBooks();
        books.isEmpty();
        books.addBook(book);
        books.getBook(10);
        books.getBook( 13);
        books.sortByPriceHighToLow();
        books.sortByPriceLowToHigh();
        books.sortByName();
        books.sortDefault();
        books.sortByRating();
        Books books2 = new Books(books);
        book.getName();
        book.getIndex();
        book.getAuthor();
        book.getPrice();
        book.getReviewPeopleNumber();
        book.getReviewScore();
        book.getSeller();
        book.getGenre();
        book.getUrl();
        book.setName("123");

        book =(Book)bookFileOperation.load("10");
        assertEquals("The Hunger Games",book.getName());
    }


}
