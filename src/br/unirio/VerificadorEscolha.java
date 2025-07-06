package br.unirio;

public class VerificadorEscolha {
    public static String VerificarEscolha(String escolha, String max) {
        if ((escolha.compareTo("1") >= 0 && escolha.compareTo(max) <= 0)) {
            return escolha;
        } else {
            throw new EscolhaInvalException("Digite um valor válido!");
        }

    }
}