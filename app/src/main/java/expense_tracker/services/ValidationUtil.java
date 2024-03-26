package expense_tracker.services;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        if(email == null ) return false;
        return email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    }
    public static boolean isValidPassword(String password) {
        if(password == null ) return false;
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }


    public static void validateUser(String email, String password) {
        if (!isValidEmail(email) || !isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }
}
