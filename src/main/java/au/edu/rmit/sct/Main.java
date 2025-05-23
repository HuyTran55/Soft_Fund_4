package au.edu.rmit.sct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        // ! temporary tests.
        // * checking if adding person works
        Person Test1 = new Person(
                "565_d%&fAB",
                "John",
                "Doe",
                "32|Highland Street |Melbourne|Victoria|Australia",
                "15-11-1990",
                new HashMap<Date, Integer>(),
                false
        );
        System.out.println("Expected Output: true - Actual Output: " + Test1.addPerson());

        // * checking if update details work
        Person Test2 = new Person(
                "565_d%&fAB",
                "John",
                "Smith",
                "32|Highland Street |Melbourne|Victoria|Australia",
                "15-11-1990",
                new HashMap<Date, Integer>(),
                false
        );
        System.out.println("Expected Output: true - Actual Output: " + Test1.updatePersonalDetails(Test2));

        // * checking if can add multiple ppl to same txt file
        Person Test3 = new Person(
                "123_d%&fCD",
                "Jane",
                "Doe",
                "12|Yomama Street |Melbourne|Victoria|Australia",
                "29-05-1922",
                new HashMap<Date, Integer>(),
                true
        );
        System.out.println("Expected Output: true - Actual Output: " + Test3.addPerson());
    }
}