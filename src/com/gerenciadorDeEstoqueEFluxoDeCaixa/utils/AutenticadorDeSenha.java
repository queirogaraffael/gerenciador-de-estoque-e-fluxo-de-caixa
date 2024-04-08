package com.gerenciadorDeEstoqueEFluxoDeCaixa.utils;

public class AutenticadorDeSenha {
	private static final String SENHA = "senha123";

	public static boolean autenticacaoSenha(String senha) {

		if (senha.equals(SENHA)) {
			return true;
		} else {
			return false;
		}
	}

}
