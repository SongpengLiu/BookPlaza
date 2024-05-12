package com.example.bookplaza.file;

import android.content.Context;

import com.example.bookplaza.data.Book;
import com.example.bookplaza.data.Books;
import com.example.bookplaza.user.UserOperation;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Songpeng Liu
 * @date 9/29/2023
 * @description BookFileOperation
 */
public class BookFileOperation implements FileOperation {
    private static BookFileOperation instance = new BookFileOperation();
    private static RandomAccessFile randomAccessFile;
    private static File file;

    private BookFileOperation() {
    }

    /**
     * @return com.example.bookplaza.file.BookFileOperation
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description getInstance
     */
    public static BookFileOperation getInstance() {
        try {
            file = new File(UserOperation.APPLICATION_DIR, "library.books");
            randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * @param object
     * @return void
     * @throws RuntimeException
     * @throws FileNotFoundException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description save
     */
    @Override
    public void save(Object object) {
        Book book = null;
        Books books = null;
        if (object instanceof Book) {
            book = (Book) object;
            try {
                BookFileOperation.randomAccessFile.seek(book.getIndex() * Book.MAX_BOOK_STRING_LENGTH);
                BookFileOperation.randomAccessFile.write(StringUtils.rightPad(book.toString(), Book.MAX_BOOK_STRING_LENGTH).getBytes());
                randomAccessFile.getFD().sync();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;

        } else if (object instanceof Books) {
            books = (Books) object;
            try {
                List<Book> bookList = books.getBooksList();
                for (Book bookElement : bookList) {
                    randomAccessFile.seek(bookElement.getIndex() * Book.MAX_BOOK_STRING_LENGTH);
                    randomAccessFile.write(StringUtils.rightPad(bookElement.toString(), Book.MAX_BOOK_STRING_LENGTH).getBytes());
                }
                randomAccessFile.getFD().sync();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        throw new RuntimeException("Input type error");

    }

    /**
     * @param bookIndex
     * @return java.lang.Object
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description load
     */
    @Override
    public Object load(String bookIndex) {
        Book book = null;
        if (!file.exists()) {
            return null;
        }
        try {
            randomAccessFile.seek(Integer.valueOf(bookIndex) * Book.MAX_BOOK_STRING_LENGTH);
            byte[] bookByte = new byte[Book.MAX_BOOK_STRING_LENGTH];
            randomAccessFile.read(bookByte);
            String bookString = new String(bookByte).trim();
            if (bookString.equals("") || bookString.length() == 0) {
                return null;
            }
            book = new Book(bookString);
            if (book.getName() == null) {
                return null;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    /**
     * @param bookIndex
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description delete
     */
    @Override
    public void delete(String bookIndex) {
        Book book = new Book(Integer.valueOf(bookIndex), null, null, null, null, null, null, null,null);
        this.save(book);
    }

    /**
     * Read a book from file
     * O(1) complexity from disk
     * @author Yucheng Zhu
     * @param index Index of the book to load
     * @throws RuntimeException Cannot read the book
     * @return The book
     */
    public Book load(int index) {
        Book book = null;
            try {
                randomAccessFile.seek(index * Book.MAX_BOOK_STRING_LENGTH);
                byte[] bookByte = new byte[Book.MAX_BOOK_STRING_LENGTH];
                randomAccessFile.read(bookByte);
                String bookString = new String(bookByte).trim();
                book = new Book(bookString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return book;
    }

    /**
     * Read a book from file
     * O(n) complexity from disk for reading n books
     * @author Songpeng Liu
     * @param booksIndex Index of the books to load
     * @throws RuntimeException Cannot read the book file
     * @return The books
     */
    public Books load(Iterable<Integer> booksIndex) {
        Books books = new Books();
        Book book = null;
        if (!file.exists()) {
            return null;
        }
        for (Integer index : booksIndex) {
            try {
                randomAccessFile.seek(index * Book.MAX_BOOK_STRING_LENGTH);
                byte[] bookByte = new byte[Book.MAX_BOOK_STRING_LENGTH];
                randomAccessFile.read(bookByte);
                String bookString = new String(bookByte).trim();
                if (bookString.isEmpty()) {
                    continue;
                }
                book = new Book(bookString);
                if (book.getName() == null) {
                    continue;
                }
                books.addBook(book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return books;
    }

    /**
     * @param booksIndex
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description delete
     */
    public void delete(List<Integer> booksIndex) {
        Books books = new Books();
        Book book;
        for (Integer index : booksIndex) {
            book = new Book(index, null, null, null, null, null, null, null,null);
            books.addBook(book);
        }
        this.save(books);
    }

    /**
     * @return void
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description clearBookAssets
     * @deprecated only use for test
     */
    public void clearBookAssets() {
        File file = new File(UserOperation.APPLICATION_DIR, "library.books");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * @return void
     * @throws RuntimeException
     * @author Songpeng Liu
     * @date 9/29/2023
     * @description convertData
     */
    public void convertData(InputStream inputStream) {
        CSVParser csvParser = null;
        try {
            csvParser = new CSVParser(new BufferedReader(new InputStreamReader(inputStream)), CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int index = 0;
        Books books = new Books();
        for (CSVRecord csvRecord : csvParser.getRecords()) {
            Float rate = null;
            Integer ratePeople = null;
            Float price = null;
            try {
                rate = Float.parseFloat(csvRecord.get(2));
                ratePeople = Integer.valueOf(csvRecord.get(3));
                price= Float.parseFloat(csvRecord.get(5));
            } catch (RuntimeException e) {
                continue;
            }

            Book book = new Book(index, csvRecord.get(1), csvRecord.get(0), rate, ratePeople, price, csvRecord.get(4), csvRecord.get(6), csvRecord.get(7));
            books.addBook(book);
            //store to file per 3000 book
            if (index != 0 && index % 3000 == 0) {
                this.save(books);
                books.clearBooks();
            }
            index++;
        }
        if (!books.isEmpty()) {
            this.save(books);
        }
        UserOperation.MAX_INDEX = index;

    }
}
