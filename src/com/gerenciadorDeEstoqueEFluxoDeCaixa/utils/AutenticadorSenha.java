package com.gerenciadorDeEstoqueEFluxoDeCaixa.utils;

import javax.swing.JOptionPane;

public class AutenticadorSenha {
	private static final String SENHA = "senha123";
	
	public static boolean autenticacaoSenha() {
		String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
		
		if (senhaDigitada.equals(SENHA)) {
			return true;
		}
		else {
			return false;
		}
	}

}
