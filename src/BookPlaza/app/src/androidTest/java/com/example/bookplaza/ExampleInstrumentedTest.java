//package com.example.bookplaza;
//
//import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
//
//import static com.example.bookplaza.trees.BooksAttributes.addBookToDict;
//
//import android.content.Context;
//
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.*;
//
//import com.example.bookplaza.data.Book;
//import com.example.bookplaza.data.Books;
//import com.example.bookplaza.data.User;
//import com.example.bookplaza.file.BookFileOperation;
//import com.example.bookplaza.file.FileOperation;
//import com.example.bookplaza.file.FileOperationFactory;
//import com.example.bookplaza.file.UserFileOperation;
//import com.example.bookplaza.trees.AttributesLoader;
//import com.example.bookplaza.trees.BooksAttributes;
//import com.example.bookplaza.trees.TreeData;
//import com.example.bookplaza.user.UserOperation;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.jar.Attributes;
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.bookplaza", appContext.getPackageName());
//    }
//
//    @Test
//    public void saveAndLoadUser() {
//        User user = new User("comp21006442@gmail.com","comp21006442");
//
//        FileOperation userFileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER, getApplicationContext());
//
//        userFileOperation.save(user);
//        User returnUser = (User) userFileOperation.load("comp21006442@gmail.com");
//        assertEquals(user.getUsername(), returnUser.getUsername());
//        assertEquals(true, user.equals(returnUser));
//        assertEquals(true, returnUser.checkPassword("comp21006442"));
//        assertEquals(user, returnUser);
//
//        assertEquals(true, UserOperation.getInstance(getApplicationContext()).login("comp21006442@gmail.com", "comp21006442"));
//
//
//        userFileOperation.delete("comp21006442@gmail.com");
//        returnUser = (User) userFileOperation.load("comp21006442@gmail.com");
//        assertEquals(null, returnUser);
//
//    }
//
//    @Test
//    public void dataCheck() {
//        FileOperation userFileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.USER, getApplicationContext());
//        userFileOperation.delete("comp2100@anu.edu.au");
//        userFileOperation.delete("comp6442@anu.edu.au");
//
//        BookFileOperation bookFileOperation = (BookFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS, getApplicationContext());
//        bookFileOperation.clearBookAssets();
//
//        UserOperation userOperation = UserOperation.getInstance(getApplicationContext());
//        userOperation.dataCheck();
//
//        FileOperation fileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS, getApplicationContext());
//        Book book = (Book) fileOperation.load("1");
//        System.out.println(book.toString());
//        assertEquals("Fifty Shades of Grey", book.getName());
//
//        book = (Book) fileOperation.load("5");
//        System.out.println(book.toString());
//        assertEquals("Slammed", book.getName());
//
//        book.setName("More Than Forever");
//
//        fileOperation.save(book);
//        Book book2 = (Book) fileOperation.load("5");
//        assertEquals("More Than Forever", book2.getName());
//    }
//
//    @Test
//    public void parseBook() {
//        Book book = new Book("1,Fifty Shades of Grey,E.L. James,3.66,2483536,2.5,Horror,Ebay,https://www.ebay.com");
//        System.out.println(book.toString());
//        assertEquals("Fifty Shades of Grey", book.getName());
//        Book book2 = new Book("1,Fifty Shades of Grey,E.L. James,null,null,null,null,null,null");
//        System.out.println(book2.toString());
//        assertEquals("E.L. James", book2.getAuthor());
//    }
//
//    @Test
//    public void saveBook() {
//        FileOperation fileOperation = FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS, getApplicationContext());
//        Book book = new Book("5,Slammed,Colleen Hoover,4.19,320926,5.5,Drama,Ebay,https://www.ebay.com");
//        fileOperation.save(book);
//
//        book = new Book("1,Fifty Shades of Grey,E.L. James,3.66,2483536,2.5,Horror,Ebay,https://www.ebay.com");
//        fileOperation.save(book);
//
//        book = (Book) fileOperation.load("1");
//        System.out.println(book.toString());
//        assertEquals("Fifty Shades of Grey", book.getName());
//
//        book = (Book) fileOperation.load("5");
//        System.out.println(book.toString());
//        assertEquals("Slammed", book.getName());
//    }
//
//    @Test
//    public void bookFileOperation() {
//        BookFileOperation fileOperation = (BookFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS, getApplicationContext());
//        UserOperation userOperation = UserOperation.getInstance(getApplicationContext());
//        fileOperation.clearBookAssets();
//        userOperation.dataCheck();
//
//
//        Book book = (Book) fileOperation.load("5");
//        System.out.println(book.toString());
//        assertEquals("Slammed", book.getName());
//        assertEquals(true, book.getPrice()== 5.5);
//
//        fileOperation.delete("5");
//        book = (Book) fileOperation.load("5");
//        assertEquals(null, book);
//
//        List<Integer> list = new ArrayList<>();
//        list.add(3);
//        list.add(7);
//        Books books = (Books) fileOperation.load(list);
//        book = books.getBook(3);
//        System.out.println(book.toString());
//        assertEquals("The Fault in Our Stars", book.getName());
//        book = books.getBook(7);
//        System.out.println(book.toString());
//        assertEquals("Easy", book.getName());
//
//        fileOperation.delete(list);
//        books = (Books) fileOperation.load(list);
//        assertEquals(true, books.isEmpty());
//    }
//
//    @Test
//    public void timeCompare() {
//        long time1 = System.nanoTime();
//        AttributesLoader attributesLoader = new AttributesLoader();
//        try {
//            attributesLoader.readBooksToAttributes(getApplicationContext(),"test_book_list_3000.csv");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        long time2 = System.nanoTime();
//        List<Integer> list = new ArrayList<>();
//        for(int i =0;i<3000;i++){
//            list.add(i);
//        }
//        BookFileOperation  bookFileOperation= (BookFileOperation) FileOperationFactory.createFileOperate(FileOperationFactory.FileOperationType.BOOKS,getApplicationContext());
//        Books books= bookFileOperation.load(list);
//        BooksAttributes attributes = new BooksAttributes();
//        Integer id;
//        for(Book book:books.getBooksList()){
//            id = book.getIndex();
//            addBookToDict(attributes.getAuthors(), book.getAuthor(), id); // author
//            addBookToDict(attributes.getTitles(), book.getName(), id); // title
//            attributes.getRatings().insert(new TreeData<>(Float.parseFloat(book.getPrice().toString()), id)); // rating
//            addBookToDict(attributes.getGenres(), book.getAuthor(), id); // genre
//            attributes.getBestPrices().insert(new TreeData<>(Float.parseFloat(book.getPrice().toString()), id)); // best price
//            addBookToDict(attributes.getSellers(), book.getName(), id); // seller
//            addBookToDict(attributes.getUrls(), book.getName(), id); // url
//        }
//        long time3 = System.nanoTime();
//
//        System.out.println("csv time   : "+ (time2- time1));
//        System.out.println("binary time: "+ (time3- time2));
//
//    }
//
//}