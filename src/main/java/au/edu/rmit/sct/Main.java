package au.edu.rmit.sct;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Date;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
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
        Test1.clearTXTFile(); // ! just clears txt file before doing anything else
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
        Map<Date, Integer> record3 = new HashMap<>();
        Date now = new Date();
        Date later = new Date(now.getTime() + 1000);
        record3.put(now, 8);
        record3.put(later, 1);
        System.out.println(record3);
        Person Test3 = new Person(
                "123_d%&fCD",
                "Jane",
                "Doe",
                "12|Yomama Street |Melbourne|Victoria|Australia",
                "29-05-1922",
                (HashMap<Date, Integer>) record3,
                true
        );
        System.out.println("Expected Output: true - Actual Output: " + Test3.addPerson());

        System.out.println("Expected Output: Success - Actual Output: " + Test3.addDemeritPoints("03-12-2022", 5));

    }
}