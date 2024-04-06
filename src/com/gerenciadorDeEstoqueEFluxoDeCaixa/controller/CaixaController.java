package com.gerenciadorDeEstoqueEFluxoDeCaixa.controller;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CaixaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.view.MenuView;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.view.MenusView;

public class CaixaController {
	
	
	public void fluxoDeCaixa(Set<Produto> produtos, Set<Venda> vendas,  Set<Integer> codigosVendas) {
		int opcaoMenuFluxoDeCaixa = 0;
		Set<Produto> listaCompras = new HashSet<>();

		do {

			opcaoMenuFluxoDeCaixa = Integer.parseInt(JOptionPane.showInputDialog(MenusView.exibirMenuFluxoCaixa()));
			// Adicionar produto(s) na sacola

			switch (opcaoMenuFluxoDeCaixa) {

			case 1:

				String codigoProduto = JOptionPane
						.showInputDialog("Digite o código do produto que você deseja adicionar a lista de compras");
				Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade: "));

				Produto p = CaixaService.retornaProdutoPeloCodigo(produtos, codigoProduto);

				if (p != null) {

					if (p.getQuantidade() >= quantidade) {
						listaCompras.add(new Produto(p.getCodigo(), p.getNome(), p.getValor(), quantidade));
						p.setQuantidade(-quantidade);

					} else if (p.getQuantidade() == 0) {
						JOptionPane.showMessageDialog(null, "Quantidade em estoque do produto igual a 0. Tente outro!");
					}

					else {
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
				// Listar produto(s) da sacola
			case 2:
				CaixaService.listarCompras(listaCompras);

			case 3:

				int codigoVenda = CaixaService.geradorDeCodigoVenda(codigosVendas);

				codigosVendas.add(codigoVenda);
				Venda novaVenda = new Venda(codigoVenda);
				vendas.add(novaVenda);

				for (Produto p1 : listaCompras) {
					novaVenda.getProdutos().add(p1);
				}

				// nota fiscal
				// CaixaService.geradorNotaFiscal(novaVenda);

				listaCompras.clear();
				JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");

			case 4:
				break;

			default:

				JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente!");

			}
		} while (opcaoMenuFluxoDeCaixa != 4);
	}
}

