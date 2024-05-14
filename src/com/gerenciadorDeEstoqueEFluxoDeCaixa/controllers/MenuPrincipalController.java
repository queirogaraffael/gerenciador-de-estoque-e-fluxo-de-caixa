package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuPrincipal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorDeSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class MenuPrincipalController {
	private EstoqueController estoqueController;
	private CaixaController caixaController;

	public MenuPrincipalController() {
		estoqueController = new EstoqueController();
		caixaController = new CaixaController();
	}

	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;
		Object[] opcoes = { "Gerenciador de Estoque", "Fluxo de Caixa", "Encerrar programa" };

		do {

			opcaoMenuPrincipal = JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Menu Principal",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			switch (opcaoMenuPrincipal) {
			case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
				String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
				boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

				if (autenticacao) {
					estoqueController.gerenciadorEstoque();
				} else {
					JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
				}

				break;
			case (ConstantesMenuPrincipal.FLUXO_CAIXA):
				caixaController.fluxoDeCaixa();
				break;
			default:

				EntityManagerFactoryService.fechaEntityManagerFactory();
				System.exit(0);
			}
		} while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);
	}
}
