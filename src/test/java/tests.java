import org.junit.jupiter.api.Test;

import au.edu.rmit.sct.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

public class tests {

    
    @Test
    void testValidPerson() throws IOException {
        Person p = new Person("56s_d%&fAB", "Alice", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990", new HashMap<>(), false);
        assertTrue(p.addPerson());
    }

    @Test
    void testInvalidBirthdateFormat() throws IOException {
        Person p = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "1990-11-15", new HashMap<>(), false);
        assertFalse(p.addPerson());
    }
    
    @Test
void testInvalidPersonIDTooShort() throws IOException {
    Person p = new Person(
        "12345", // too short
        "John",
        "Doe",
        "32|Highland Street|Melbourne|Victoria|Australia",
        "15-11-1990",
        new HashMap<>(),
        false
    );
    assertFalse(p.addPerson()); // expect false
}
 @Test
    void testInsufficientSpecialChars() throws IOException {
        Person p = new Person("56abcdefAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990", new HashMap<>(), false);
        assertFalse(p.addPerson());
    }
@Test
    void testUpdateFirstNameOnly() throws IOException {
        Person original = new Person("56s_d%&fAB", "Alice", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990", new HashMap<>(), false);
        original.addPerson();
        Person updated = new Person("56s_d%&fAB", "Alicia", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-1990", new HashMap<>(), false);
        assertTrue(original.updatePersonalDetails(updated));
    }
    @Test
    void testUnderageCannotChangeAddress() throws IOException {
        Person original = new Person("56s_d%&fAB", "Bob", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "15-11-2010", new HashMap<>(), false);
        original.addPerson();
        Person updated = new Person("56s_d%&fAB", "Bob", "Smith", "10|New Street|Melbourne|Victoria|Australia", "15-11-2010", new HashMap<>(), false);
        assertFalse(original.updatePersonalDetails(updated));
    }
      @Test
    void testChangeBirthdayAndOtherDetails() throws IOException {
        Person original = new Person("56s_d%&fAB", "Alice", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "01-01-1990", new HashMap<>(), false);
        original.addPerson();
        Person updated = new Person("78s_d%&fAB", "Alice", "Smith", "10|Other Street|Melbourne|Victoria|Australia", "01-01-2000", new HashMap<>(), false);
        assertFalse(original.updatePersonalDetails(updated));
    }
  @Test
    void testEvenIDChange() throws IOException {
        Person original = new Person("24a_d%&fYZ", "Charlie", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "01-01-1990", new HashMap<>(), false);
        original.addPerson();
        Person updated = new Person("56z_d%&fGH", "Charlie", "Smith", "32|Highland Street|Melbourne|Victoria|Australia", "01-01-1990", new HashMap<>(), false);
        assertFalse(original.updatePersonalDetails(updated));
    }
}
