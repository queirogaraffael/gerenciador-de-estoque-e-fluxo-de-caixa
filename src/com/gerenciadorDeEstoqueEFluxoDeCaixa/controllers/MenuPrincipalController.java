package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuPrincipal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorDeSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.MenuPrincipalView;

public class MenuPrincipalController {
	private Set<Produto> produtos;
	private Set<Venda> vendas;
	private Set<Integer> codigosVendas;

	private EstoqueController estoqueController;
	private CaixaController caixaController;

	private Boolean statusNotaFiscal;
	private String caminhoNotaFiscal;

	public MenuPrincipalController() {
		produtos = new HashSet<>();
		vendas = new HashSet<>();
		codigosVendas = new HashSet<>();
		estoqueController = new EstoqueController();
		caixaController = new CaixaController();
		this.statusNotaFiscal = false;
		this.caminhoNotaFiscal = "";
	}

	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;

		do {
			try {
				opcaoMenuPrincipal = Integer.parseInt(JOptionPane.showInputDialog(MenuPrincipalView.exibirMenuPrincipal()));

				switch (opcaoMenuPrincipal) {
				case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
					String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
					boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

					if (autenticacao) {
						estoqueController.gerenciadorEstoque(produtos, vendas, statusNotaFiscal, caminhoNotaFiscal);
					} else {
						JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
					}

					break;
				case (ConstantesMenuPrincipal.FLUXO_CAIXA):
					caixaController.fluxoDeCaixa(produtos, vendas, codigosVendas, statusNotaFiscal, caminhoNotaFiscal);
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
