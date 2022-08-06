package bg.softuni.autoTraderExperience.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    private String message;

    public UserAlreadyExistException(String message) {
        this.message = message;
    }
}
