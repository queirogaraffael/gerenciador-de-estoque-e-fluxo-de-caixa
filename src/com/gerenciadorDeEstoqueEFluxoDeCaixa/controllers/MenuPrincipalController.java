package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuPrincipal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorDeSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.MenuPrincipalView;

public class MenuPrincipalController {

	private EstoqueController estoqueController;
	private CaixaController caixaController;

	protected static Boolean statusNotaFiscal;
	protected static String caminhoNotaFiscal;

	public MenuPrincipalController() {
		estoqueController = new EstoqueController();
		caixaController = new CaixaController();
		statusNotaFiscal = false;
		caminhoNotaFiscal = "";
	}

	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;

		do {
			try {
				opcaoMenuPrincipal = Integer
						.parseInt(JOptionPane.showInputDialog(MenuPrincipalView.exibirMenuPrincipal()));

				switch (opcaoMenuPrincipal) {
				case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
					String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
					boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

					if (autenticacao) {
						estoqueController.gerenciadorEstoque(statusNotaFiscal, caminhoNotaFiscal);
					} else {
						JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
					}

					break;
				case (ConstantesMenuPrincipal.FLUXO_CAIXA):
					caixaController.fluxoDeCaixa(statusNotaFiscal, caminhoNotaFiscal);
					break;
				case (ConstantesMenuPrincipal.SAIR):

					EntityManagerFactoryService.fecharEntityManagerFactory();

					JOptionPane.showMessageDialog(null, "Fim do programa");
					System.exit(0);
				default:
					JOptionPane.showMessageDialog(null, "Opção invalida. Tente novamente!");
				}

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Entrada invalida. Por favor, insira um numero correspondente a opcao desejada.");
			}

		} while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);
	}

}
