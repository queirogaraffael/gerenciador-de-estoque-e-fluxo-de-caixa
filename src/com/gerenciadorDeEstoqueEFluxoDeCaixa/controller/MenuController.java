package com.gerenciadorDeEstoqueEFluxoDeCaixa.controller;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.ConstantesMenuPrincipal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.view.MenusView;

public class MenuController {
	private Set<Produto> produtos;
	private Set<Venda> vendas;
	private Set<Integer> codigosVendas;

	private EstoqueController estoqueController;
	private CaixaController caixaController;

	public static Boolean statusNotaFiscal = false;
	public static String caminhoNotaFiscal = "";

	public MenuController() {
		produtos = new HashSet<>();
		vendas = new HashSet<>();
		codigosVendas = new HashSet<>();
		estoqueController = new EstoqueController();
		caixaController = new CaixaController();

	}

	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;

		do {
			try {
				opcaoMenuPrincipal = Integer.parseInt(JOptionPane.showInputDialog(MenusView.exibirMenuPrincipal()));

				switch (opcaoMenuPrincipal) {
				case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
					boolean autenticacao = AutenticadorSenha.autenticacaoSenha();

					if (autenticacao) {
						estoqueController.gerenciarEstoque(produtos, vendas);
					} else {
						JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
					}

					break;
				case (ConstantesMenuPrincipal.FLUXO_CAIXA):
					caixaController.fluxoDeCaixa(produtos, vendas, codigosVendas);
					break;
				case (ConstantesMenuPrincipal.SAIR):
					JOptionPane.showMessageDialog(null, "Fim do programa");
					System.exit(0);
				default:
					JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente!");
				}

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Entrada inválida. Por favor, insira um número correspondente à opção desejada.");
			}

		} while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);
	}

}