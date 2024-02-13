package br.com.af2b.validation;

public class ValidatorFactory {
    public static Validator getValidator(ValidatorType type) {
        switch (type) {
            case CPF:
                return new CpfValidator();
            case CNPJ:
                return new CnpjValidator();
            default:
                throw new IllegalArgumentException("Validator not supported.");
        }
    }
}
