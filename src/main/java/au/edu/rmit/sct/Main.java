package au.edu.rmit.sct;

import java.util.HashMap;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // ! temporary tests.
        Person Test_Person1 = new Person(
                "565_d%&fAB",
                "John",
                "Doe",
                "32|Highland Street |Melbourne|Victoria|Australia",
                "15-11-1990",
                new HashMap<Date, Integer>(),
                false
        );
        System.out.println("Expected Output: true - Actual Output: " + Test_Person1.addPerson());
    }
}