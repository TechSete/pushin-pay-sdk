package tech.techsete.pushin_pay_sdk.exceptions;

public class InvalidChargeRequestException extends RuntimeException {
    public InvalidChargeRequestException(String message) {
        super(message);
    }
}
