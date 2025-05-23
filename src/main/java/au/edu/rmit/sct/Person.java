package au.edu.rmit.sct;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.text.SimpleDateFormat;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;

    // constructor
    public Person(
            String personID,
            String firstName,
            String lastName,
            String address,
            String birthdate,
            HashMap<Date, Integer> demeritPoints,
            boolean isSuspended
    ) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = demeritPoints;
        this.isSuspended = isSuspended;
    }

    public void clearTXTFile() {
        writeFile(new ArrayList<>(), "src/main/java/au/edu/rmit/sct/test.txt", false);
    }

    /**
     * this method appends a new person into the txt file
     * @return
     * @throws IOException
     */
    public boolean addPerson () throws IOException {
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
        if (!validAddress(inputs)) return false;
        System.out.println("Passed Condition 2");

        // ! Condition 3: The format of the birthdate of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        inputs = birthdate.split("-");
        if (!validBirthdate(inputs)) return false;
        System.out.println("Passed Condition 3");

        // ! Instruction: If the Person's information meets the above conditions and any other conditions you may want to consider,
        // * the information should be inserted into a TXT file, and the addPerson function should return true.
        // * Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.

        String file_path = "src/main/java/au/edu/rmit/sct/test.txt";
        List<String> lines = readFile(file_path, new ArrayList<>());
        lines.add("ID: " + personID);
        lines.add("Full Name: " + firstName + " " + lastName);
        lines.add("Address: " + address);
        lines.add("DOB: " + birthdate);
        lines.add("Demerit Points: " + demeritPoints);
        lines.add("Suspended License: " + isSuspended);

        writeFile(lines, file_path, true);

        System.out.println("Write to .txt file Successful!\n");

        return true;
    }

    static final int[] monthDays = {
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
    };

    // check if a year is a leap year
    static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // count number of days from 0000-00-00 to the given date
    static int countDays(int year, int month, int day) {
        int days = year * 365;
        days += year / 4 - year / 100 + year / 400;

        for (int i = 0; i < month - 1; i++) {
            days += monthDays[i];
        }

        if (month > 2 && isLeap(year)) {
            days += 1;
        }

        days += day;
        return days;
    }

    // get difference between two dates
    static int getDateDifference(int y1, int m1, int d1, int y2, int m2, int d2) {
        int total1 = countDays(y1, m1, d1);
        int total2 = countDays(y2, m2, d2);
        return Math.abs(total1 - total2);
    }

    /**
     * this method updates a Person object when given another Person object as a parameter
     * E.G you want to update Person1, you pass through Person1_Update which will presumably would have different values for some variables
     * Person1.updatePersonalDetails(Person1_Update)
     * @param updatedDetails
     * @return true for successful updates, false for conditions failing
     * @throws IOException
     */
    public boolean updatePersonalDetails (Person updatedDetails) throws IOException {
        //TODO: This method allows updating a given person's ID, firstName, lastName, address and birthday in a TXT file.
        // * Changing personal details will not affect their demerit points or the suspension status.
        // * All relevant conditions discussed for the addPerson function also need to be considered and checked in the updatPerson function.

        System.out.println("Method - updatePersonalDetails()");

        // ! Condition 1: If a person is under 18, their address cannot be changed.
        // ? if under 18 and address is being changed, return false?
        String[] inputs = birthdate.split("-");
        int difference = ageInDays( Integer.parseInt(inputs[2]), Integer.parseInt(inputs[1]), Integer.parseInt(inputs[0]));
        // theres 6575 days in 18 years (5 leap) years, but theres only a 1 day difference if theres 4 leap years in the 18 years
        boolean adult = difference >= 6575;
        if (!adult && !address.equalsIgnoreCase(updatedDetails.getAddress())) {
            return false;
        }
        System.out.println("Passed Condition 1");

        // ! Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.
        // ? if birthday changing, and other details being updated, return false?
        boolean changeBirthday = !birthdate.equalsIgnoreCase(updatedDetails.getBirthdate());
        boolean changeDetails = (
                (!personID.equalsIgnoreCase(updatedDetails.getID())) ||
                (!firstName.equalsIgnoreCase(updatedDetails.getFirstName())) ||
                (!lastName.equalsIgnoreCase(updatedDetails.getLastName())) ||
                (!address.equalsIgnoreCase(updatedDetails.getAddress()))
        );
        if (changeBirthday && changeDetails) {
            return false;
        }
        System.out.println("Passed Condition 2");

        // ! Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.
        // ? if even number, and id is being changed, return false?
        boolean evenID = (int)personID.charAt(0) % 2 == 0;
        if (evenID && !personID.equalsIgnoreCase(updatedDetails.getID())) {
            return false;
        }
        System.out.println("Passed Condition 3");

        // ! Condition 4 (Custom): suspension status cannot be changed
        if (isSuspended != updatedDetails.getSuspended()) {
            return false;
        }
        System.out.println("Passed Condition 4");

        // ! Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
        // * the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true.
        // * Otherwise, the Person's updated information should not be updated in the TXT file, and the updatePersonalDetails function should return false.
        String file_path = "src/main/java/au/edu/rmit/sct/test.txt";

        List<String> updatedLines = readFile(file_path, new ArrayList<>());
        String newID = updatedDetails.getID();
        if (!personID.equalsIgnoreCase(newID)) {
            updatedLines = changeLine(updatedLines, personID, ("ID: " + newID));
            setID(newID);
        }
        String newFirst = updatedDetails.getFirstName();
        if (!firstName.equalsIgnoreCase(newFirst)) {
            updatedLines = changeLine(updatedLines, (firstName + " " + lastName), ("Full Name: " + newFirst + " " + lastName));
            setFirstName(updatedDetails.getFirstName());
        }

        String newLast = updatedDetails.getLastName();
        if (!lastName.equalsIgnoreCase(newLast)) {
            updatedLines = changeLine(updatedLines, (firstName + " " + lastName), ("Full Name: " + firstName + " " + newLast));
            setLastName(updatedDetails.getLastName());
        }

        String newAddy = updatedDetails.getAddress();
        if (!address.equalsIgnoreCase(newAddy)) {
            updatedLines = changeLine(updatedLines, address, ("Address: " + newAddy));
            setAddress(updatedDetails.getAddress());
        }

        writeFile(updatedLines, file_path, false);

        System.out.println("Update to .txt file Successful!\n");
        return true;
    }

    /**
     * this method just add (issues) more demerit points when given a date (dd-mm-yyyy) and amount of points and updates the txt file
     * @param date
     * @param points
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public String addDemeritPoints (String date, int points) throws ParseException, IOException {
        //TODO: This method adds demerit points for a given person in a TXT file.

        // ! Condition 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990
        String[] inputs = date.split("-");
        if (inputs.length != 3) return "Failed";
        int day = Integer.parseInt(inputs[0]), month = Integer.parseInt(inputs[1]), year = Integer.parseInt(inputs[2]);
        if ((day < 1 || day > 31) || (month < 1 || month > 12) || (year < 1900 || year > 2025)) return "Failed";
        System.out.println("Passed Condition 1");

        // ! Condition 2: The demerit points must be a whole number between 1-6
        if (points < 1 || points > 6) return "Failed";
        System.out.println("Passed Condition 2");

        // ! Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
        // * If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
        int difference = ageInDays(year, month, day);
        boolean over21 = difference > 7665;

        int totalPoints = points;
        for (Map.Entry<Date, Integer> point : demeritPoints.entrySet()) {
            Date issued_date = point.getKey();
            Integer issued_points = point.getValue();
            difference = ageInDays(issued_date.getYear() + 1900, issued_date.getMonth() + 1, issued_date.getDate());
            if (difference < 730) totalPoints += issued_points;
        }

        System.out.println("Total Demerit Points: " + totalPoints);
        if (!over21 && totalPoints > 6) setSuspended(true);
        else if (over21 && totalPoints > 12) setSuspended(true);
        else setSuspended(false);
        System.out.println("Passed Condition 3");

        // ! Instruction: If the above condiations and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
        // * and the addDemeritPoints function should return "Sucess". Otherwise, the addDemeritPoints function should return "Failed".
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date issued_date = sdf.parse(date);
        HashMap<Date, Integer> copy = (HashMap<Date, Integer>) demeritPoints.clone();
        demeritPoints.put(issued_date, points);

        String file_path = "src/main/java/au/edu/rmit/sct/test.txt";
        List<String> lines = readFile(file_path, new ArrayList<>());
        lines = changeDemerit(lines, copy);
        writeFile(lines, file_path, false);
        System.out.println("Update Demerit in TXT successful");

        return "Success";
    }

    public List<String> changeDemerit(List<String> lines, HashMap<Date, Integer> old) {
        String old_line = "Demerit Points: " + old;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains(old_line)) {
                lines.set(i, "Demerit Points: " + demeritPoints);
                break;
            }
        }

        return lines;
    }

    public List<String> readFile(String file_path, List<String> lines) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file_path));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    public void writeFile(List<String> lines, String file_path, boolean extra_line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            if (extra_line) writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> changeLine(List<String> updatedLines, String oldDetails, String newDetails) throws IOException {
        for (int i = 0; i < updatedLines.size(); i++) {
            String line = updatedLines.get(i);
            if (line.contains(oldDetails)) {
                updatedLines.set(i, newDetails);
                break;
            }
        }

        return updatedLines;
    }

    public int ageInDays (int year, int month, int day) {
        Date current_date = new Date();
        int curr_year = current_date.getYear() + 1900;
        int curr_month = current_date.getMonth() + 1;
        int curr_day = current_date.getDate();

        return getDateDifference(
                curr_year, curr_month, curr_day,
                year, month, day
        );
    }

    public boolean validAddress(String[] inputs) {
        if (inputs.length != 5) return false;
        String number = inputs[0];

        // first input should always be a number for address number
        // so if not a number doesn't follow format
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) return false;
        }

        return true;
    }

    public boolean validBirthdate(String[] inputs) {
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

    public String getID() {
        return personID;
    }

    public void setID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public HashMap<Date, Integer> getDemeritPoints() {
        return demeritPoints;
    }

    public void setDemeritPoints(HashMap<Date, Integer> demeritPoints) {
        this.demeritPoints = demeritPoints;
    }

    public boolean getSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }
}
