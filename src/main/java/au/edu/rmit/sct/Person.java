package au.edu.rmit.sct;

import java.util.HashMap;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;

    // addPerson returns true on successful creation, false otherwise (fails conditions)
    public boolean addPerson (String personID, String address, String birthdate) {
        //TODO: This method adds information about a person to a TXT file.
        System.out.println("Method - addPerson()");
        int n = personID.length();

        // ! Condition 1: PersonID should be exactly 10 characters long;
        if (n != 10) return false;

        // * the first two characters should be numbers between 2 and 9, there should be at least two special characters between characters 3 and 8,
        if (!Character.isDigit(personID.charAt(0)) || !Character.isDigit(personID.charAt(1))) return false;
        int num_special = 0;
        String specialCharsRegex = "[^a-zA-Z0-9\\s]";
        for (int i = 2; i < n - 2; i++) {
            char curr_char = personID.charAt(i);
            if (String.valueOf(curr_char).matches(specialCharsRegex)) num_special++;
        }
        if (!(num_special >= 2)) return false;

        // * and the last two characters should be upper case letters (A - Z). Example: "565_d%&fAB"
        if (!Character.isUpperCase(personID.charAt(8)) || !Character.isUpperCase(personID.charAt(9))) return false;
        System.out.println("Passed Condition 1");

        // ! Condition 2: The address of the Person should follow the following format: Street Number|Street|City|State|Country.
        // * The State should be only Victoria. Example: 32|Highland Street |Melbourne|Victoria|Australia.
        String[] inputs = address.split("\\|");
        if (!valid_address(inputs)) return false;
        System.out.println("Passed Condition 2");

        // ! Condition 3: The format of the birthdate of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        inputs = birthdate.split("-");
        if (!valid_birthdate(inputs)) return false;
        System.out.println("Passed Condition 3");

        // ! Instruction: If the Person's information meets the above conditions and any other conditions you may want to consider,
        // * the information should be inserted into a TXT file, and the addPerson function should return true.
        // * Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.
        String file_path = "src/main/java/au/edu/rmit/sct/test.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path))) {
            writer.write("ID: "+ personID + "\n");
            writer.write("Address: " + address+ "\n");
            writer.write("DOB: " + birthdate+ "\n");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
        System.out.println("Write to .txt file Successful!\n");

        return true;
    }
    public boolean updatePersonalDetails () {
        //TODO: This method allows updating a given person's ID, firstName, lastName, address and birthday in a TXT file.
        // * Changing personal details will not affect their demerit points or the suspension status.
        // * All relevant conditions discussed for the addPerson function also need to be considered and checked in the updatPerson function.

        // ! Condition 1: If a person is under 18, their address cannot be changed.

        // ! Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.

        // ! Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.

        // ! Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
        // * the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true.
        // * Otherwise, the Person's updated information should not be updated in the TXT file, and the updatePersonalDetails function should return false.

        return true;
    }

    public String addDemeritPoints () {
        //TODO: This method adds demerit points for a given person in a TXT file.

        // ! Condition 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990

        // ! Condition 2: The demerit points must be a whole number between 1-6

        // ! Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        // * If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.

        // ! Instruction: If the above condiations and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
        // * and the addDemeritPoints function should return "Sucess". Otherwise, the addDemeritPoints function should return "Failed".

        return "Success";
    }

    public boolean valid_address(String[] inputs) {
        if (inputs.length != 5) return false;
        String number = inputs[0];

        // first input should always be a number for address number
        // so if not a number doesn't follow format
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) return false;
        }

        return true;
    }

    // ! Condition 3: The format of the birthdate of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990
    public boolean valid_birthdate(String[] inputs) {
        if (inputs.length != 3) return false;
        String date = inputs[0], month = inputs[1], year = inputs[2];

        // checking if vals are valid length
        if (
                (date.isEmpty() || date.length() > 2)
                || (month.isEmpty() || month.length() > 2)
                || (year.isEmpty() || year.length() > 4)
        )
            return false;

        // checking if all vals are numbers
        for (int i = 0; i < 2; i++) {
            char digit = date.charAt(i);
            if (!Character.isDigit(digit)) return false;
        }
        for (int i = 0; i < 2; i++) {
            char digit = month.charAt(i);
            if (!Character.isDigit(digit)) return false;
        }
        for (int i = 0; i < 4; i++) {
            char digit = year.charAt(i);
            if (!Character.isDigit(digit)) return false;
        }

        return true;
    }
}
