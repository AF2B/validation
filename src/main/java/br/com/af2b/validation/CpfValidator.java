package br.com.af2b.validation;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class CpfValidator implements Validator {
    private static final int CPF_LENGTH = 11;
    private static final int[] PESOS_PRIMEIRO_DIGITO = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
    private static final int[] PESOS_SEGUNDO_DIGITO = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    @Override
    public boolean validate(String cpf)
            throws ValidationException,
            CpfDigitException,
            CpfLengthException,
            CpfInvalidFormatException {
        final String cpfNumeros = cpf.replaceAll("\\D", "");

        if (cpfNumeros.length() != CPF_LENGTH) {
            throw new CpfLengthException("CPF deve conter exatamente 11 dígitos.");
        }

        if (cpfNumeros.chars().allMatch(ch -> ch == cpfNumeros.charAt(0))) {
            throw new CpfDigitException("CPF não pode conter dígitos iguais.");
        }

        BiFunction<String, int[], Integer> calculaDigito = (str, pesos) -> {
            int soma = IntStream.range(0, str.length())
                    .map(i -> Integer.parseInt(str.substring(i, i + 1)) * pesos[i])
                    .sum();
            int resto = soma % 11;
            return (resto < 2) ? 0 : 11 - resto;
        };

        int primeiroDigito = calculaDigito.apply(cpfNumeros.substring(0, 9), PESOS_PRIMEIRO_DIGITO);
        int segundoDigito = calculaDigito.apply(cpfNumeros.substring(0, 9) + primeiroDigito, PESOS_SEGUNDO_DIGITO);

        if (!cpfNumeros.equals(cpfNumeros.substring(0, 9) + primeiroDigito + segundoDigito)) {
            throw new CpfInvalidFormatException("Dígitos verificadores não conferem.");
        }
        return true;
    }
}
