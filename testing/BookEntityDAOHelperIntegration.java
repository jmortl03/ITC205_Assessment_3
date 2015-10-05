/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.entities.Book;
import library.entities.Member;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class BookEntityDAOHelperIntegration {

  IBookHelper realBookHelper;
  IBook realBook1;    
  IBook realBook2;

  BookMapDAO bookMapDao;
  private String name1 = "John Smith";
  private String title1 = "To Kill a Mockingbird";
  private String callNo1 = "TKAM";
  private int id = 1;

  @Before
  public void setUp()
  {
    realBookHelper = new BookHelper();

    realBook1 = new Book(name1, title1, callNo1, id);

    bookMapDao = new BookMapDAO(realBookHelper);
  }



  @After
  public void clearSetUp()
  {
    realBookHelper = null;
    realBook1 = null;
    bookMapDao = null;
  }



  @Test
  public void testCreateBookMapDAO() {

    //execute
    bookMapDao = new BookMapDAO(realBookHelper);

    //assert
    assertNotNull(bookMapDao);
  }



  @Test
  public void testAddBook() {

    //execute
    IBook actual = bookMapDao.addBook(name1, title1, callNo1);

    //assert
    assertNotNull(actual);
    assertEquals(name1, actual.getAuthor());
    assertEquals(title1, actual.getTitle());
    assertEquals(callNo1, actual.getCallNumber());
  }



  @Test
  public void testGetBookByID() {

    //execute
    IBook actual = bookMapDao.addBook(name1, title1, callNo1);

    IBook mapBook = bookMapDao.getBookByID(1);

    //assert
    assertEquals(mapBook, actual);
  }



  @Test
  public void testListBooks() {

    //execute adding books
    IBook book1 = bookMapDao.addBook(name1, title1, callNo1);
    IBook book2 = bookMapDao.addBook("Laura Carter", "Dark Storm", "DSDS");

    List<IBook> listBooks = bookMapDao.listBooks();

    //assert
    assertEquals(2, listBooks.size());
    assertTrue(listBooks.contains(book1));
    assertTrue(listBooks.contains(book2));
  }



  @Test
  public void testFindBooksByAuthor() {

    //execute adding books to list
    BookMapDAO bookDao = new BookMapDAO(realBookHelper);

    bookDao.addBook("John Smith", "To Kill a Mockingbird", "TKAM");
    bookDao.addBook("Laura Carter", "Dark Storm", "DSDS");

    List<IBook> listBooks = bookDao.findBooksByAuthor("John Smith");

    IBook toKillAMockingbird = listBooks.get(0);

    assertEquals(1, listBooks.size());
    assertEquals("John Smith", toKillAMockingbird.getAuthor());
  }



  @Test
  public void testFindBooksByTitle() {

    //execute
    BookMapDAO bookDao = new BookMapDAO(realBookHelper);

    bookDao.addBook("John Smith", "To Kill A Mockingbird", "TKAM");
    bookDao.addBook("Laura Carter", "Dark Storm", "DSDS");

    List<IBook> listBooks = bookDao.findBooksByTitle("Dark Storm");

    IBook darkStorm = listBooks.get(0);

    //assert
    assertEquals("Dark Storm", darkStorm.getTitle());
  }



  @Test
  public void testFindBooksByAuthorTitle() {

    //execute
    BookMapDAO bookDao = new BookMapDAO(realBookHelper);

    IBook book1 = bookDao.addBook("John Smith", "To Kill A Mockingbird", "TKAM");
    IBook book2 = bookDao.addBook("Laura Carter", "Dark Storm", "DSDS");

    List<IBook> listBooks = bookDao.findBooksByAuthorTitle("John Smith", "Dark Storm");

    //assert
    assertEquals(1, listBooks.size());
    assertTrue(listBooks.contains(book1));
  }
}
