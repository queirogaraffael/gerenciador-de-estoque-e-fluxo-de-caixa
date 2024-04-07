package com.gerenciadorDeEstoqueEFluxoDeCaixa.controller;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuFluxoCaixa;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CaixaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ComumService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ProdutoService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.view.MenusView;

public class CaixaController {

	public void fluxoDeCaixa(Set<Produto> produtos, Set<Venda> vendas, Set<Integer> codigosVendas,
			Boolean statusNotaFiscal, String caminhoNotaFiscal) {
		int opcaoMenuFluxoDeCaixa = 0;
		Set<Produto> listaCompras = new HashSet<>();

		do {

			opcaoMenuFluxoDeCaixa = Integer.parseInt(JOptionPane.showInputDialog(MenusView.exibirMenuFluxoCaixa()));
			// Adicionar produto(s) na sacola

			switch (opcaoMenuFluxoDeCaixa) {

			case (ConstantesMenuFluxoCaixa.ADICIONAR):

				Integer codigoProduto = Integer.parseInt(JOptionPane
						.showInputDialog("Digite o código do produto que você deseja adicionar a lista de compras"));

				if (ComumService.jaContem(listaCompras, codigoProduto)) {
					JOptionPane.showMessageDialog(null,
							"Se você deseja modificar a quantidade desse produto, vá na opção 5. Modificar quantidade de um produto");
				} else {
					Produto p = ComumService.retornaPeloCodigo(produtos, codigoProduto);
					if (p != null) {
						Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade: "));

						if (p.getQuantidade() >= quantidade) {
							listaCompras.add(new Produto(p.getCodigo(), p.getNome(), p.getValor(), quantidade));
							p.setQuantidade(p.getQuantidade() - quantidade);

						} else if (p.getQuantidade() == 0) {
							JOptionPane.showMessageDialog(null,
									"Quantidade em estoque do produto igual a 0. Tente outro!");
						}

						else {
							int escolha = Integer.parseInt(JOptionPane.showInputDialog(
									"Quantidade desejada menor do que em estoque. Deseja adicionar todos os itens restantes ?\n1. Sim\n2. Não"));

							if (escolha == 1) {
								listaCompras
										.add(new Produto(p.getCodigo(), p.getNome(), p.getValor(), p.getQuantidade()));
								p.setQuantidade(0);
							} else {
								JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Produto não consta no estoque. Tente outro!");

					}
				}

				break;

			// Listar produto(s) da sacola
			case (ConstantesMenuFluxoCaixa.LISTAR_SACOLA):
				CaixaService.listarCompras(listaCompras);
				break;

			case (ConstantesMenuFluxoCaixa.REMOVER_PRODUTO):

				if (listaCompras.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
				} else {
					Integer codigoProdutoParaRemover = Integer
							.parseInt(JOptionPane.showInputDialog("Digite o código do produto que você seja remover:"));

					if (listaCompras.isEmpty()) {

						JOptionPane.showMessageDialog(null,
								"Adicione primeiro um produto a sua sacola para poder remover.");
					} else if (!ComumService.jaContem(listaCompras, codigoProdutoParaRemover)) {
						JOptionPane.showMessageDialog(null,
								"Produto já não constava no gerenciador de estoque. Tente novamente com uma produto existente.");

					} else {

						int quantidade = CaixaService.quantidadeRealProduto(listaCompras, produtos,
								codigoProdutoParaRemover);
						Produto prodrem = ComumService.retornaPeloCodigo(produtos, codigoProdutoParaRemover);
						prodrem.setQuantidade(quantidade);

						ProdutoService.removerProduto(listaCompras, codigoProdutoParaRemover);
						JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
					}
				}

				break;

			case (ConstantesMenuFluxoCaixa.LISTAR_ESTOQUE):
				ComumService.imprimir(produtos);
				break;

			case (ConstantesMenuFluxoCaixa.MODIFICAR_QUANTIDADE):

				if (listaCompras.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
				} else {
					Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do produto: "));

					if (ComumService.jaContem(listaCompras, codigo)) {
						Integer novaQuantidade = Integer
								.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));

						Produto prod = ComumService.retornaPeloCodigo(listaCompras, codigo);
						Produto produtoEstoque = ComumService.retornaPeloCodigo(produtos, codigo);
						int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

						if (quantidadeRealProduto >= novaQuantidade) {
							ProdutoService.editarProdutoQuantidade(listaCompras, codigo, novaQuantidade);
							produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

						} else if (quantidadeRealProduto <= 0) {
							JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
						}

						else {
							int escolha = Integer.parseInt(JOptionPane.showInputDialog(
									"Quantidade desejada menor do que em estoque. Deseja adicionar todos os itens restantes ?\n1. Sim\n2. Não"));

							if (escolha == 1) {

								ProdutoService.editarProdutoQuantidade(listaCompras, codigo, quantidadeRealProduto);
								produtoEstoque.setQuantidade(0);
							} else {
								JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
							}
						}

						JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null, "Produto inválido, tente outro!");
					}
				}

				break;

			case (ConstantesMenuFluxoCaixa.FINALIZAR):

				if (!listaCompras.isEmpty()) {
					Integer codigoVenda = CaixaService.geradorDeCodigo(codigosVendas);

					Venda novaVenda = new Venda(codigoVenda, Instant.now());
					vendas.add(novaVenda);

					for (Produto p1 : listaCompras) {
						novaVenda.getProdutos().add(p1);
					}

					if (statusNotaFiscal) {
						CaixaService.geradorNotaFiscal(novaVenda, caminhoNotaFiscal);
					}

					listaCompras.clear();
					JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");
				}
				break;

			case (ConstantesMenuFluxoCaixa.LIMPAR):

				if (!listaCompras.isEmpty()) {
					for (Produto compra : listaCompras) {

						int quantidade = CaixaService.quantidadeRealProduto(listaCompras, produtos, compra.getCodigo());

						Produto prodrem = ComumService.retornaPeloCodigo(produtos, compra.getCodigo());

						prodrem.setQuantidade(quantidade);

						ProdutoService.removerProduto(listaCompras, compra.getCodigo());
						JOptionPane.showMessageDialog(null, "Sacola de compras limpada com sucesso.");
					}
				}
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
