package au.edu.rmit.sct;

public class Main {
    public static void main(String[] args) {
        // ! temporary tests.
        Person person = new Person();
        System.out.println("Expected Output: true - Actual Output: "
                + person.addPerson("565_d%&fAB",
                "32|Highland Street |Melbourne|Victoria|Australia",
                "15-11-1990"));
    }
}