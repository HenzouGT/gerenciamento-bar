package ufmt.br.com.gerenciamentobar.seguranca;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptSenhaHasher implements SenhaHasher {
    @Override
    public String gerarHash(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }

        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    @Override
    public boolean verificar(String senha, String hash) {
        if (senha == null || hash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
