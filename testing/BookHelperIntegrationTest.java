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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import library.interfaces.daos.IBookHelper;
import library.daos.BookHelper;
import library.interfaces.entities.IBook;
import library.entities.Book;



public class BookHelperIntegrationTest {

  private final String author_ = "Rocky Hardwick";
  private final String title_ = "Little Charmers";
  private final String callNumber_ = "RHLCAA";
  private int id_ = 1;

  @Test
  public void testMakeBook() {

    //execute
    IBookHelper bookHelper = new BookHelper();

    IBook book = bookHelper.makeBook(author_, title_, callNumber_, id_);

    //asserts
    assertNotNull(book);
  assertEquals(book.getAuthor(), author_);
  assertEquals(book.getTitle(), title_);
  assertEquals(book.getCallNumber(), callNumber_);
  assertEquals(book.getID(), id_);


  }
}
