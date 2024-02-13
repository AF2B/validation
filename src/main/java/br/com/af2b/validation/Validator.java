package br.com.af2b.validation;

public interface Validator {
    boolean validate(String value)
            throws ValidationException,
            CpfDigitException,
            CpfLengthException,
            CpfInvalidFormatException;
}
