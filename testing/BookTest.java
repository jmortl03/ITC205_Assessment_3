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
import library.entities.Book;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BookTest {

  private static Book testBook_;
  private final String author_ = "Rocky Hardwick";
  private final String title_ = "Little Charmers";
  private final String callNumber_ = "RKLCAA";
  private int id_ = 1;

  ILoan mockLoan_;

  @Before
  public void setUp() throws Exception {

    testBook_ = new Book(author_, title_, callNumber_, id_);
    mockLoan_ = mock(ILoan.class);

}

  @Test
  public void testConstructorAllParamsOK() {
    Book testBook2 = new Book("Katie Massey", "Summer Dream", "KMSD", 2);

    // assert
    assertNotNull(testBook2);

    assertEquals("Katie Massey", testBook2.getAuthor());
    assertEquals("Summer Dream", testBook2.getTitle());
    assertEquals("KMSD", testBook2.getCallNumber());
    assertEquals(2, testBook2.getID());
    assertEquals(EBookState.AVAILABLE, testBook2.getState());
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamAuthorIsNull() {

    Book bookAuthorNull = new Book(null, "Hover Car Racer", "78SGB5", 11);

    // execute the function
    bookAuthorNull.getAuthor();

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamAuthorIsBlank() {

    Book bookAuthorBlank = new Book("", "Hover Car Racer", "78SGB5", 11);

    // execute the function
    bookAuthorBlank.getAuthor();

    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamTitleIsNull() {

    Book bookTitleNull = new Book("Greg Bell", null, "78SGB5", 11);

    // execute the function
    bookTitleNull.getTitle();

    // assert
    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamTitleIsBlank() {

    Book bookTitleBlank = new Book("Greg Bell", "", "78SGB5", 11);

    // execute the function
    bookTitleBlank.getTitle();

    // assert
    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamCallNumberIsNull() {

    Book bookCallNumberNull = new Book("Greg Bell", "Hover Car Racer", null,
                    11);

    // execute the function
    bookCallNumberNull.getCallNumber();

    // assert
    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamCallNumberIsBlank() {

    Book bookCallNumberBlank = new Book("Greg Bell", "Hover Car Racer", "", 11);

    //execute the function
    bookCallNumberBlank.getCallNumber();

    //assert
    fail("Should have thrown IllegalArgumentException");
  }



  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamIdLessThanZero() {

    Book bookIdLessThanZero = new Book("Greg Bell", "Hover Car Racer",
                    "78SGB5", -2);

    fail("Should have thrown IllegalArgumentException");
  }

	
	
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBadParamIdEqualsZero() {

    Book bookIdZero = new Book("Greg Bell", "Hover Car Racer", "78SGB5", 0);

    fail("Should have thrown IllegalArguementException");
  }



  @Test
  public void testBorrow() {

    Book book = new Book("Michael Horowitz", "Armageddon", "MHAMH1", 3);

    // execute the function
    when(mockLoan_.getBook()).thenReturn(book);
    when(mockLoan_.isOverDue()).thenReturn(false);

    // assert
    assertEquals(mockLoan_.getBook(), book);
  }



  @Test(expected = RuntimeException.class)
  public void testBorrowBookNotAvailable() {

    ILoan loan = null;
    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.borrow(loan);

    fail("Should have thrown RuntimeException");
  }



  @Test
  public void testGetLoan() {

    Book book = new Book("Michael Horowitz", "Linger", "AB4412", 4);

    // execute the function
    book.borrow(mockLoan_);

    // assert
    assertEquals(mockLoan_, book.getLoan());
  }



  @Test
  public void testReturnBookTrue() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    when(mockLoan_.getBook()).thenReturn(book);

    assertEquals(book.getState(), EBookState.AVAILABLE);

    // execute the function
    book.borrow(mockLoan_);
    book.returnBook(true);

    // assert
    assertEquals(book.getState(), EBookState.DAMAGED);
  }



  @Test
  public void testReturnBookFalse() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    when(mockLoan_.getBook()).thenReturn(book);

    assertEquals(book.getState(), EBookState.AVAILABLE);

    // execute the function
    book.borrow(mockLoan_);
    book.returnBook(false);

    // assert
    assertEquals(book.getState(), EBookState.AVAILABLE);
  }



  @Test(expected = RuntimeException.class)
  public void testReturnBookNotOnLoan() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.returnBook(true);

    // assert
    fail("Should have thrown RuntimeException");
  }



  @Test(expected = RuntimeException.class)
  public void testReturnBookNotLost() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.returnBook(true);

    // assert
    fail("Should have thrown RuntimeException");
  }



  @Test
  public void testLose() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.borrow(mockLoan_);
    book.lose();

    // assert
    assertEquals(book.getState(), EBookState.LOST);
  }



  @Test(expected = RuntimeException.class)
  public void testLoseThrowsRunTimeException() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.lose();

    fail("Should have thrown RuntimeException");

  }



  @Test
  public void testRepair() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // assert
    assertEquals(book.getState(), EBookState.AVAILABLE);
  }



  @Test(expected = RuntimeException.class)
  public void testRepairBookIsNotDamaged() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.repair();

    fail("Should have thrown RuntimeException");
  }



  @Test
  public void testDisposeWhenBookIsAvailable() {
    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.dispose();

    // assert
    assertEquals(book.getState(), EBookState.DISPOSED);
  }



  @Test
  public void testDisposeWhenBookIsDamaged() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);
    book.borrow(mockLoan_);
    book.returnBook(true);

    // execute the function
    book.dispose();

    // assert
    assertEquals(book.getState(), EBookState.DISPOSED);
  }



  @Test
  public void testDisposeWhenBookIsLost() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);
    book.borrow(mockLoan_);
    book.lose();

    // execute the function
    book.dispose();

    // assert
    assertEquals(book.getState(), EBookState.DISPOSED);
  }



  @Test(expected = RuntimeException.class)
  public void testDisposeWhenBookIsOnLoan() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.borrow(mockLoan_);
    book.dispose();

    fail("Should have thrown RuntimeException");
  }



  @Test
  public void testGetState() {

    Book book = new Book("Michael Horowitz", "Once In A Lifetime", "XY21AB", 9);

    // execute the function
    book.borrow(mockLoan_);

    // assert
    assertEquals(book.getState(), EBookState.ON_LOAN);
  }



  @Test
  public void testGetAuthor() {

    // assert
    assertEquals("Rocky Hardwick", testBook_.getAuthor());
  }



  @Test
  public void testGetTitle() {

    // assert
    assertEquals("Little Charmers", testBook_.getTitle());

  }



  @Test
  public void testGetCallNumber() {

    // assert
    assertEquals("RKLCAA", testBook_.getCallNumber());
  }



  @Test
  public void testGetId() {

    assertEquals(1, testBook_.getID());
  }
}
