package ufmt.br.com.gerenciamentobar.seguranca;

public interface SenhaHasher {
    String gerarHash(String senha);

    boolean verificar(String senha, String hash);
}
