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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import library.BorrowUC_CTL;
import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.daos.LoanHelper;
import library.daos.LoanMapDAO;
import library.daos.MemberHelper;
import library.daos.MemberMapDAO;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

public class ScanBookOperationsIntegrationTests {
  BorrowUC_CTL ctl_;

  ICardReader reader;
  IScanner scanner;
  IPrinter printer;
  IDisplay display;
  IBorrowUI ui;

  IBookDAO bookDAO;
  IMemberDAO memberDAO;
  ILoanDAO loanDAO;

  Date borrowDate_, dueDate_;
  Calendar cal_;

  @Before
  public void setUp() throws Exception {
    reader = mock(ICardReader.class);
    scanner = mock(IScanner.class);
    printer = mock(IPrinter.class);
    display = mock(IDisplay.class);
    ui = mock(IBorrowUI.class);

    bookDAO = new BookMapDAO(new BookHelper());
    memberDAO = new MemberMapDAO(new MemberHelper());
    loanDAO = new LoanMapDAO(new LoanHelper());

    ctl_ = new BorrowUC_CTL(reader, scanner, printer, display, bookDAO, loanDAO, memberDAO, ui );

    //setupTestData from Main.java
    IBook[] book = new IBook[15];
    IMember[] member = new IMember[6];

    book[0]  = bookDAO.addBook("author1", "title1", "callNo1");
    book[1]  = bookDAO.addBook("author1", "title2", "callNo2");
    book[2]  = bookDAO.addBook("author5", "title3", "callNo3");
    book[3]  = bookDAO.addBook("author1", "title4", "callNo4");
    book[4]  = bookDAO.addBook("author2", "title5", "callNo5");
    book[5]  = bookDAO.addBook("author2", "title6", "callNo6");
    book[6]  = bookDAO.addBook("author3", "title7", "callNo7");
    book[7]  = bookDAO.addBook("author2", "title8", "callNo8");
    book[8]  = bookDAO.addBook("author2", "title9", "callNo9");
    book[9]  = bookDAO.addBook("author3", "title10", "callNo10");
    book[10] = bookDAO.addBook("author4", "title11", "callNo11");
    book[11] = bookDAO.addBook("author4", "title12", "callNo12");
    book[12] = bookDAO.addBook("author4", "title13", "callNo13");
    book[13] = bookDAO.addBook("author1", "title14", "callNo14");
    book[14] = bookDAO.addBook("author5", "title15", "callNo15");
    
    member[0] = memberDAO.addMember("fName0", "lName0", "0001", "email0");
    member[1] = memberDAO.addMember("fName1", "lName1", "0002", "email1");
    member[2] = memberDAO.addMember("fName2", "lName2", "0003", "email2");
    member[3] = memberDAO.addMember("fName3", "lName3", "0004", "email3");
    member[4] = memberDAO.addMember("fName4", "lName4", "0005", "email4");
    member[5] = memberDAO.addMember("fName5", "lName5", "0006", "email5");

    Calendar cal = Calendar.getInstance();
    Date now = cal.getTime();

    //create a member with overdue loans		
    for (int i=0; i<2; i++) {
	ILoan loan = loanDAO.createLoan(member[1], book[i]);
	loanDAO.commitLoan(loan);
    }
    cal.setTime(now);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD + 1);
    Date checkDate = cal.getTime();		
    loanDAO.updateOverDueStatus(checkDate);

    //create a member with max unpaid fines
    member[2].addFine(10.0f);
  
    //create a member with loans maxed out
    for (int i=2; i<7; i++) {
	ILoan loan = loanDAO.createLoan(member[3], book[i]);
	loanDAO.commitLoan(loan);
    }
  }



  @After
  public void tearDown() throws Exception {
  }
	
	
	
  @Test
  public void testScanBook() {
    //setup to test
    ctl_.setState(EBorrowState.INITIALIZED);
    
    //execute card swipe
    ctl_.cardSwiped(1);

    //verify the reader and scanner
    verify(reader).setEnabled(false);
    verify(scanner).setEnabled(true);
    verify(ui).setState(EBorrowState.SCANNING_BOOKS);
    verify(ui).displayMemberDetails(1, "fName0 lName0", "0001");
    verify(ui).displayExistingLoan(any(String.class));    

    //assert
    assertEquals(EBorrowState.SCANNING_BOOKS, ctl_.getState());

    //execute
    ctl_.bookScanned(9);

    //verifies scanner and reader
    verify(reader).setEnabled(true);
    verify(scanner).setEnabled(true);
    verify(ui).setState(EBorrowState.SCANNING_BOOKS);
    verify(ui).displayScannedBookDetails("");
    verify(ui).displayPendingLoan("");

    //assert
    assertEquals(EBorrowState.SCANNING_BOOKS, ctl_.getState());
  }
}
