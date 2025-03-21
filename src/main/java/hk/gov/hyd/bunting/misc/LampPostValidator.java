package hk.gov.hyd.bunting.misc;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class LampPostValidator {

    // Define the regex patterns for the valid lamp post numbers
    private static final Pattern FIVE_DIGIT_NUMBER = Pattern.compile("^\\d{5}$");
    private static final Pattern ONE_ALPHA_FOLLOWED_BY_NUMBERS = Pattern.compile("^[a-zA-Z]\\d+$");
    private static final Pattern TWO_ALPHAS_FOLLOWED_BY_NUMBERS = Pattern.compile("^[a-zA-Z]{2}\\d+$");

    public static boolean isValidLampPostNoList(List<String> lampPostList) {
        for (String lampPostNo : lampPostList) {
            if (!isValidLampPostNo(lampPostNo)) {
                return false; // Return false if any lamp post number is invalid
            }
        }
        return true; // All lamp post numbers are valid
    }

    private static boolean isValidLampPostNo(String lampPostNo) {
        return FIVE_DIGIT_NUMBER.matcher(lampPostNo).matches() ||
                ONE_ALPHA_FOLLOWED_BY_NUMBERS.matcher(lampPostNo).matches() ||
                TWO_ALPHAS_FOLLOWED_BY_NUMBERS.matcher(lampPostNo).matches();
    }

    public static boolean notValidDateRange(Date installationDate, Date removalDate) {
        if (isNull(installationDate)) {
            return true;
        } else if (isNull(removalDate)) {
            return false;
        } else {
            return installationDate.compareTo(removalDate) > 0;
        }
    }


    private static boolean isNull(Date inDate) {
        return inDate == null;
    }
    /*
    public static void main(String[] args) {
        // Example usage
        List<String> lampPostList = List.of("12345", "A123", "AB456", "12345", "CF23456"); // Example list
        boolean isValid = isValidLampPostNoList(lampPostList);
        System.out.println("Is valid: " + isValid); // Output: Is valid: false
    }

     */
}