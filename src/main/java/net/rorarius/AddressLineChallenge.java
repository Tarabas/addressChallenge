package net.rorarius;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressLineChallenge {

    // Get all Numbers that are 1-5 chars long and that may contain chars after them but not with a .
    private String regexGetNumber = "(\\d{1,5}\\w{0,5}+)(?!\\.)";

    // Allow all Numbers, Chars and spaces including some special characters
    private String regexStreet = "[^\\d\\w\\säöüÄÖÜß´`'().-]+";

    /**
     * Returns an Address String from a Street
     *
     * @param street Street and number String
     * @return street and streetnumber in json format
     */
    public String getAddressFromStreet(String street) {
        Address address = new Address();

        if (street == null) {
            return address.toString();
        }

        Pattern numberPattern = Pattern.compile(regexGetNumber);
        Matcher numberMatcher = numberPattern.matcher(street);

        int numberCount = getNumberCountAndResetMatcher(numberMatcher);

        if (numberCount == 0) {
            address.setStreet(street);
        } else if (numberCount == 1) {
            numberMatcher.find();

            address.setStreetNumber(parseNumberSingle(street, numberMatcher));
            address.setStreet(parseStreetnameSingle(street, numberMatcher));
        } else if (numberCount > 1) {
            numberMatcher.find();

            address.setStreetNumber(parseNumberMultiple(street, numberMatcher));
            address.setStreet(parseStreetnameMultipleNumbers(street, numberMatcher));
        }

        return address.toString();
    }

    /**
     * Get the count of numbers in the address string and reset matcher to first match
     *
     * @param numberMatcher Matcher for regexGetNumber
     * @return count
     */
    private int getNumberCountAndResetMatcher(Matcher numberMatcher) {
        int numberCount = 0;

        while (numberMatcher.find()) {
            numberCount++;
        }
        numberMatcher.reset();

        return numberCount;
    }

    /**
     * Parses Streets with only one number in them and returns the streetnumber
     *
     * @param street        Street and number String
     * @param numberMatcher Matcher for regexGetNumber
     * @return Streetnumber
     */
    private String parseNumberSingle(String street, Matcher numberMatcher) {
        int startIdx = numberMatcher.start();

        if (startIdx == 0) {
            return numberMatcher.group().trim();
        } else {
            return street.substring(startIdx).trim();
        }
    }

    /**
     * Parses Streets with only one number in them and returns the streetname
     *
     * @param street        Street and number String
     * @param numberMatcher Matcher for regexGetNumber
     * @return streetname
     */
    private String parseStreetnameSingle(String street, Matcher numberMatcher) {
        int startIdx = numberMatcher.start();
        int endIdx = numberMatcher.end();

        if (startIdx == 0) {
            return cleanStreetname(street.substring(endIdx));
        } else {
            return cleanStreetname(street.substring(0, startIdx));
        }
    }

    /**
     * Parses Streets with more than one number in them and returns the streetnumber
     *
     * @param street        Street and number String
     * @param numberMatcher Matcher for regexGetNumber
     * @return streetnumber
     */
    private String parseNumberMultiple(String street, Matcher numberMatcher) {
        int numberStartIdx = numberMatcher.end();
        return street.substring(numberStartIdx).trim();
    }

    /**
     * Parses Streets with more than one number in them and returns the streetname
     *
     * @param street        Street and number String
     * @param numberMatcher Matcher for regexGetNumber
     * @return streetname
     */
    private String parseStreetnameMultipleNumbers(String street, Matcher numberMatcher) {
        int streetEndIndex = numberMatcher.end(0);
        return cleanStreetname(street.substring(0, streetEndIndex));
    }

    /**
     * Removes any chars from a streetname that are not wanted (like comma)
     *
     * @param street Street and number String
     * @return cleaned streetname
     */
    private String cleanStreetname(String street) {
        if (street != null) {
            return street.replaceAll(regexStreet, "").trim();
        } else {
            return null;
        }
    }
}
