package no.ntnu.iir.olavval.oblig2.test;

import no.ntnu.iir.olavval.oblig2.model.Personals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalsTest {

  private Personals ole;

  @BeforeEach
  void setUp()
  {
    this.ole = new Personals("Ole", "Olsen",
        "ole.olsen@dot.com", "ole");

  }

  @Test
  void testOle()
  {
    System.out.println("1: Check first name of Ole.");
    assertEquals("Ole", ole.getFirstname());
    System.out.println("2: Check surname of Ole.");
    assertEquals("Olsen", ole.getSurname());
    System.out.println("3: Check email address of Ole.");
    assertEquals("ole.olsen@dot.com", ole.getEMailAddress());
    System.out.println("4: Test Ole password ok.");
    assertTrue(ole.okPassword("ole"));
    System.out.println("5: Test Ole wrong password.");
    assertFalse(ole.okPassword("tove"));
    assertFalse(ole.okPassword(null));
    System.out.println("6: Test password change for Ole.");
      // Wrong current password.
    assertFalse(ole.changePassword("ole1", "newPass"));
      // Same password but with added whitespace. New password must be different from old.
    assertFalse(ole.changePassword("ole", " ole "));
      // New password.
    assertTrue(ole.changePassword("ole ", "newPass"));

  }


}
