import org.junit.jupiter.api.Test;

import au.edu.rmit.sct.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class PersonTests {

        @Test
        void testValidPerson() throws IOException {
                Person p = new Person("56s_d%&fAB", "Alice", "Smith", "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-1990", new HashMap<>(), false);
                assertTrue(p.addPerson());
        }

        @Test
        void testInvalidBirthdateFormat() throws IOException {
                Person p = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia",
                                "1990-11-15", new HashMap<>(), false);
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
                                false);
                assertFalse(p.addPerson()); // expect false
        }

        @Test
        void testInsufficientSpecialChars() throws IOException {
                Person p = new Person("56abcdefAB", "John", "Doe", "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-1990", new HashMap<>(), false);
                assertFalse(p.addPerson());
        }

        @Test
        void testInvalidStateInAddress() throws IOException {
                Person p = new Person("56s_d%&fAB", "John", "Doe", "32|Highland Street|Melbourne|NSW|Australia",
                                "15-11-1990",
                                new HashMap<>(), false);
                assertFalse(p.addPerson());
        }

        @Test
        void testUpdateFirstNameOnly() throws IOException {
                Person original = new Person("56s_d%&fAB", "Alice", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-1990", new HashMap<>(), false);
                original.addPerson();
                Person updated = new Person("56s_d%&fAB", "Alicia", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-1990", new HashMap<>(), false);
                assertTrue(original.updatePersonalDetails(updated));
        }

        @Test
        void testUnderageCannotChangeAddress() throws IOException {
                Person original = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2010", new HashMap<>(), false);
                original.addPerson();
                Person updated = new Person("56s_d%&fAB", "Bob", "Smith", "10|New Street|Melbourne|Victoria|Australia",
                                "15-11-2010", new HashMap<>(), false);
                assertFalse(original.updatePersonalDetails(updated));
        }

        @Test
        void testChangeBirthdayAndOtherDetails() throws IOException {
                Person original = new Person("56s_d%&fAB", "Alice", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "01-01-1990", new HashMap<>(), false);
                original.addPerson();
                Person updated = new Person("78s_d%&fAB", "Alice", "Smith",
                                "10|Other Street|Melbourne|Victoria|Australia",
                                "01-01-2000", new HashMap<>(), false);
                assertFalse(original.updatePersonalDetails(updated));
        }

        @Test
        void testEvenIDChange() throws IOException {
                Person original = new Person("24a_d%&fYZ", "Charlie", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia", "01-01-1990", new HashMap<>(),
                                false);
                original.addPerson();
                Person updated = new Person("56z_d%&fGH", "Charlie", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "01-01-1990", new HashMap<>(), false);
                assertFalse(original.updatePersonalDetails(updated));
        }

        // checks condition that Suspended status cannot be updated
        @Test
        void testChangeSuspended() throws IOException {
                Person original = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2007", new HashMap<>(), false);
                original.addPerson();

                Person updated = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2010", new HashMap<>(), true);
                assertFalse(original.updatePersonalDetails(updated));
        }

        // checks able to add demerits given correct date
        @Test
        void testAddDemeritPoints() throws IOException, ParseException {
                Person p = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2007", new HashMap<>(), false);
                p.addPerson();

                assertEquals("Success", p.addDemeritPoints("20-1-2020", 5));
        }

        // checks whether the date passed is of the correct format
        @Test
        void testCorrectDateFormat() throws IOException, ParseException {
                Person p = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2007", new HashMap<>(), false);
                p.addPerson();

                assertEquals("Failed", p.addDemeritPoints("5-31-2020", 2)); // opposite format (mm-dd-yyyy)
        }

        // checks to see if trying to add too many demerits at once
        @Test
        void testDemeritMax() throws IOException, ParseException {
                Person p = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2007", new HashMap<>(), false);
                p.addPerson();

                assertEquals("Failed", p.addDemeritPoints("20-1-2020", 12));
        }

        // checks if under 21 and over 6 demerits, isSuspended should be set to true
        @Test
        void testUnder21Suspension() throws IOException, ParseException {
                Person p = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2007", new HashMap<>(), false);
                p.addPerson();

                assertEquals("Success", p.addDemeritPoints("20-1-2024", 3));
                assertEquals("Success", p.addDemeritPoints("20-2-2025", 4));
                assertTrue(p.getSuspended());
        }

        // checks if over 21 and over
        @Test
        void testOver21Suspension() throws IOException, ParseException {
                Person p = new Person("56s_d%&fAB", "Bob", "Smith",
                                "32|Highland Street|Melbourne|Victoria|Australia",
                                "15-11-2007", new HashMap<>(), false);
                p.addPerson();

                assertEquals("Success", p.addDemeritPoints("20-1-2024", 3));
                assertEquals("Success", p.addDemeritPoints("20-2-2025", 4));
                assertEquals("Success", p.addDemeritPoints("20-3-2025", 4));
                assertEquals("Success", p.addDemeritPoints("20-4-2025", 4));
                assertTrue(p.getSuspended());
        }

}
