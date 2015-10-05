/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

/**
 *
 * @author Jason
 */
import static org.junit.Assert.*;

import java.util.List;

import library.daos.BookMapDAO;
import library.daos.BookHelper;
import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BookMapDAOTest {

  IBookHelper mockBookHelper_;
  IBook mockBook1;
  IBook mockBook2;
  BookMapDAO bookMapDao_;
  String name1 = "John Smith";
  String title1 = "To Kill A Mockingbird";
  String callNo1 = "TKAM";
  String name2 = "Jorge Thurston";
  String title2 = "Point Blank";
  String callNo2 = "JTPB";

  @Before
  public void setUp() {
    mockBookHelper_ = mock(IBookHelper.class);
    mockBook1 = mock(IBook.class);
    mockBook2 = mock(IBook.class);

    when(mockBook1.getID()).thenReturn(1);
    when(mockBook1.getAuthor()).thenReturn(name1);
    when(mockBook1.getTitle()).thenReturn(title1);

    when(mockBook2.getID()).thenReturn(2);
    when(mockBook2.getAuthor()).thenReturn("Jorge Thurston");
    when(mockBook2.getTitle()).thenReturn("Point Blank");

    when(mockBookHelper_.makeBook(name1, title1, callNo1, 1)).thenReturn(
                    mockBook1);
    when(mockBookHelper_.makeBook(name2, title2, callNo2, 2)).thenReturn(
                    mockBook2);

    bookMapDao_ = new BookMapDAO(mockBookHelper_);
  }



  @After
  public void clearSetUp() {
    mockBookHelper_ = null;
    mockBook1 = null;
    mockBook2 = null;

    bookMapDao_ = null;
  }



  @Test
  public void testCreateBookMapDAO() {

    //execute
    bookMapDao_ = new BookMapDAO(mockBookHelper_);

    assertNotNull(bookMapDao_);

  }



  @Test(expected = IllegalArgumentException.class)
  public void testCreateBookMapDAOHelperNull() {

    //execute
    bookMapDao_ = new BookMapDAO(null);

    fail("Should have thrown IllegalArgumentException");
  }



  @Test
  public void testAddBook() {

    //execute
    IBook actual = bookMapDao_.addBook(name1, title1, callNo1);

    //assert
    verify(mockBookHelper_).makeBook(name1, title1, callNo1, 1);

    assertEquals(actual, mockBook1);
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddBookAuthorIsNull() {

    // execute
    IBook actual = bookMapDao_.addBook(null, title1, callNo1);

    // assert
    verify(mockBookHelper_).makeBook(null, title1, callNo1, 1);

    assertNull(actual.getAuthor());

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddBookAuthorIsBlank() {

    // execute
    IBook actual = bookMapDao_.addBook("", title1, callNo1);

    // assert
    verify(mockBookHelper_).makeBook("", title1, callNo1, 1);

    assertNull(actual.getAuthor());

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddBookTitleIsNull() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, null, callNo1);

    // assert
    verify(mockBookHelper_).makeBook(name1, null, callNo1, 1);

    assertNull(actual.getTitle());

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddBookTitleIsBlank() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, "", callNo1);

    // assert
    verify(mockBookHelper_).makeBook(name1, "", callNo1, 1);

    assertNull(actual.getTitle());

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddBookCallNumberIsNull() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, title1, null);

    // assert
    verify(mockBookHelper_).makeBook(name1, title1, null, 1);

    assertNull(actual.getCallNumber());

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddBookCallNumberIsBlank() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, title1, "");

    // assert
    verify(mockBookHelper_).makeBook(name1, title1, "", 1);

    assertNotNull(actual.getCallNumber());

    fail("Should have thrown IllegalArgumentException");
  }



  @Test
  public void testGetBookByID() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, title1, callNo1);

    IBook mapBook = bookMapDao_.getBookByID(1);

    // assert
    assertEquals(mapBook, mockBook1);
  }



  @Test
  public void testListBooks() {

    //execute
    bookMapDao_.addBook(name1, title1, callNo1);
    bookMapDao_.addBook("Jorge Thurston", "Point Blank", "JTPB");

    List<IBook> listBooks = bookMapDao_.listBooks();

    // assert
    assertEquals(2, listBooks.size());
    assertTrue(listBooks.contains(mockBook1));
    assertTrue(listBooks.contains(mockBook2));
  }



  @Test
  public void testListBooksIsEmpty() {

    //execute
    List<IBook> listBooks = bookMapDao_.listBooks();

    // assert
    assertEquals(0, listBooks.size());
    assertTrue(listBooks.isEmpty());
  }



  @Test
  public void testFindBooksByAuthor() {

    //execute
    bookMapDao_.addBook("John Smith", "To Kill A Mockingbird", "TKAM");
    bookMapDao_.addBook("Jorge Thurston", "Point Blank", "JTPB");

    List<IBook> listBooks = bookMapDao_.findBooksByAuthor("John Smith");

    IBook toKillAMockingbird = listBooks.get(0);

    // assert
    assertEquals(1, listBooks.size());
    assertEquals("John Smith", toKillAMockingbird.getAuthor());
  }



  @Test(expected = IllegalArgumentException.class)
  public void testFindBooksByAuthorIsEmpty() {

    // execute
    IBook actual = bookMapDao_.addBook("", title1, callNo1);

    verify(mockBookHelper_).makeBook("", title1, callNo1, 1);

    List<IBook> listBooks = bookMapDao_.findBooksByAuthor("");

    // assert
    assertTrue(listBooks.contains(actual));
    assertNull(bookMapDao_.findBooksByAuthor(""));

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testFindBooksByAuthorIsNull() {

    // execute
    IBook actual = bookMapDao_.addBook(null, title1, callNo1);

    verify(mockBookHelper_).makeBook(null, title1, callNo1, 1);

    List<IBook> listBooks = bookMapDao_.findBooksByAuthor(null);

    // assert
    assertTrue(listBooks.contains(actual));
    assertNull(bookMapDao_.findBooksByAuthor(null));

    fail("Should have thrown IllegalArgumentException");
  }



  @Test
  public void testFindBooksByTitle() {

    //execute
    bookMapDao_.addBook("John Smith", "To Kill A Mockingbird", "TKAM");
    bookMapDao_.addBook("Jorge Thurston", "Point Blank", "JTPB");

    List<IBook> listBooks = bookMapDao_.findBooksByTitle("Point Blank");

    IBook pointBlank = listBooks.get(0);

    //assert
    assertEquals("Point Blank", pointBlank.getTitle());
  }



  @Test(expected = IllegalArgumentException.class)
  public void testFindBooksByTitleIsEmpty() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, "", callNo1);

    verify(mockBookHelper_).makeBook(name1, "", callNo1, 1);

    List<IBook> listBooks = bookMapDao_.findBooksByTitle("");

    // assert
    assertTrue(listBooks.contains(actual));
    assertNull(bookMapDao_.findBooksByTitle(""));

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testFindBooksByTitleIsNull() {

    // execute
    IBook actual = bookMapDao_.addBook(name1, null, callNo1);

    verify(mockBookHelper_).makeBook(name1, null, callNo1, 1);

    List<IBook> listBooks = bookMapDao_.findBooksByTitle(null);

    // assert
    assertTrue(listBooks.contains(actual));
    assertNull(bookMapDao_.findBooksByTitle(null));

    fail("Should have thrown IllegalArgumentException");
  }



  @Test
  public void testFindBooksByAuthorTitle() {

    //execute
    bookMapDao_.addBook("John Smith", "To Kill A Mockingbird", "TKAM");
    bookMapDao_.addBook("Jorge Thurston", "Point Blank", "JTPB");

    List<IBook> listBooks = bookMapDao_.findBooksByAuthorTitle("John Smith",
                    "To Kill A Mockingbird");

    //assert
    assertEquals(1, listBooks.size());
    assertTrue(listBooks.contains(mockBook1));
  }



  @Test(expected = IllegalArgumentException.class)
  public void testFindBooksByAuthorTitleIsEmpty() {

    // execute
    IBook actual = bookMapDao_.addBook("", "", callNo1);

    verify(mockBookHelper_).makeBook("", "", callNo1, 1);

    List<IBook> listBooks = bookMapDao_.findBooksByAuthorTitle("","");

    // assert
    assertTrue(listBooks.contains(actual));
    assertNull(bookMapDao_.findBooksByAuthorTitle("",""));

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testFindBooksByAuthorTitleIsNull() {

    // execute
    IBook actual = bookMapDao_.addBook(null, null, callNo1);

    verify(mockBookHelper_).makeBook(null, null, callNo1, 1);

    List<IBook> listBooks = bookMapDao_.findBooksByAuthorTitle(null, null);

    // assert
    assertTrue(listBooks.contains(actual));
    assertNull(bookMapDao_.findBooksByAuthorTitle(null, null));

    fail("Should have thrown IllegalArgumentException");
  }
}
