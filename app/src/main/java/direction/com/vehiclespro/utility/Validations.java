package direction.com.vehiclespro.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    public static boolean isValidVehicleNo(String txt) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[a-zA-Z]{2}[ ]{0,1}[0-9]{2}[ ]{0,1}[a-zA-Z]{1,2}[ ]{0,1}[0-9]{1,4}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(txt);

        return matcher.matches();

    }


}
