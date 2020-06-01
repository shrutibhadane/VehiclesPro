package direction.com.vehiclespro.utility;

public class Validations {

    public static boolean isValidVehicleNo(String txt) {

        //if(txt.matches("")) return true;

        if(txt.matches("^[A-Z]{2}[ -]{0,1}[0-9]{2}[ -]{0,1}[A-Z]{1,2}[ -]{0,1}[0-9]{1,4}$\n"))
            return true;

        else return false;
    }


}
