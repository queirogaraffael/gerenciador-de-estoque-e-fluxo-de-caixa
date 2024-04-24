package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuPrincipal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorDeSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class MenuPrincipalController {

	
	private EstoqueController estoqueController;
	private CaixaController caixaController;

	// melhorar a comunicao daqui
	protected static Boolean statusNotaFiscal;
	protected static String caminhoNotaFiscal;

	public MenuPrincipalController() {
		estoqueController = new EstoqueController();
		caixaController = new CaixaController();
		statusNotaFiscal = false;
		caminhoNotaFiscal = "";
	}

//ADICIONAR LOCALE
	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;
		Object[] opcoes = { "Gerenciador de Estoque", "Fluxo de Caixa", "Encerrar" };

		do {

			opcaoMenuPrincipal = JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Menu Principal",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			switch (opcaoMenuPrincipal) {
			case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
				String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
				boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

				if (autenticacao) {
					estoqueController.gerenciadorEstoque(caminhoNotaFiscal);
				} else {
					JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
				}

				break;
			case (ConstantesMenuPrincipal.FLUXO_CAIXA):
				caixaController.fluxoDeCaixa(statusNotaFiscal, caminhoNotaFiscal);
				break;
			default:

				EntityManagerFactoryService.fecharEntityManagerFactory();

				JOptionPane.showMessageDialog(null, "Fim do programa");
				System.exit(0);
			}
		} while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);
	}
}
