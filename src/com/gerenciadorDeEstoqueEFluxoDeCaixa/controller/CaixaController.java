package com.gerenciadorDeEstoqueEFluxoDeCaixa.controller;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CaixaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.ConstantesMenuFluxoCaixa;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.view.MenusView;

public class CaixaController {

	public void fluxoDeCaixa(Set<Produto> produtos, Set<Venda> vendas, Set<Integer> codigosVendas) {
		int opcaoMenuFluxoDeCaixa = 0;
		Set<Produto> listaCompras = new HashSet<>();

		do {

			opcaoMenuFluxoDeCaixa = Integer.parseInt(JOptionPane.showInputDialog(MenusView.exibirMenuFluxoCaixa()));
			// Adicionar produto(s) na sacola

			switch (opcaoMenuFluxoDeCaixa) {

			case (ConstantesMenuFluxoCaixa.ADICIONAR):

				Integer codigoProduto = Integer.parseInt(JOptionPane
						.showInputDialog("Digite o código do produto que você deseja adicionar a lista de compras"));

				Produto p = CaixaService.retornaProdutoPeloCodigo(produtos, codigoProduto);

				if (p != null) {
					Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade: "));

					if (p.getQuantidade() >= quantidade) {
						listaCompras.add(new Produto(p.getCodigo(), p.getNome(), p.getValor(), quantidade));
						/// melhorar isso aqui
						p.setQuantidade(-quantidade);

					} else if (p.getQuantidade() == 0) {
						JOptionPane.showMessageDialog(null, "Quantidade em estoque do produto igual a 0. Tente outro!");
					}

					else {
						// melhorar isso aqui
						int escolha = Integer.parseInt(
								"Quantidade desejada menor do que em estoque. Deseja adicionar todos os itens restantes ?");

						if (escolha == 1) {
							listaCompras.add(new Produto(p.getCodigo(), p.getNome(), p.getValor(), p.getQuantidade()));
							p.setQuantidade(0);
						} else {
							JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Produto não consta no estoque. Tente outro!");

				}
				break;
			// Listar produto(s) da sacola
			case (ConstantesMenuFluxoCaixa.LISTAR):
				CaixaService.listarCompras(listaCompras);
				break;

			case (ConstantesMenuFluxoCaixa.FINALIZAR):

				int codigoVenda = CaixaService.geradorDeCodigoVenda(codigosVendas);

				codigosVendas.add(codigoVenda);
				Venda novaVenda = new Venda(codigoVenda, Instant.now());
				vendas.add(novaVenda);

				for (Produto p1 : listaCompras) {
					novaVenda.getProdutos().add(p1);
				}

				if (MenuController.statusNotaFiscal) {
					CaixaService.geradorNotaFiscal(novaVenda, MenuController.caminhoNotaFiscal);
				}

				listaCompras.clear();
				JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");
				break;
			case (ConstantesMenuFluxoCaixa.LIMPAR):
				listaCompras.clear();
				break;

			case (ConstantesMenuFluxoCaixa.SAIR):
				boolean autenticacao = AutenticadorSenha.autenticacaoSenha();

				if (!autenticacao) {
					JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
					opcaoMenuFluxoDeCaixa = 0;
				}

				break;

			default:

				JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente!");
				break;

			}
		} while (opcaoMenuFluxoDeCaixa != ConstantesMenuFluxoCaixa.SAIR);
	}
}
