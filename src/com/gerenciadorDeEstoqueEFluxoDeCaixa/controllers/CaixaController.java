package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuFluxoCaixa;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.ItemVenda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CategoriaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ItemVendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ProdutoService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.VendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.AutenticadorDeSenha;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.FluxoDeCaixaView;

public class CaixaController {

	public void fluxoDeCaixa(Boolean statusNotaFiscal, String caminhoNotaFiscal) {
		int opcaoMenuFluxoDeCaixa = 0;

		Set<ItemVenda> listaCompras = new HashSet<>();

		do {
			try {
				opcaoMenuFluxoDeCaixa = Integer
						.parseInt(JOptionPane.showInputDialog(FluxoDeCaixaView.exibirMenuFluxoDeCaixa()));

				switch (opcaoMenuFluxoDeCaixa) {

				case (ConstantesMenuFluxoCaixa.ADICIONAR):
					adicionaProduto(listaCompras);
					break;

				case (ConstantesMenuFluxoCaixa.LISTAR_SACOLA):
					listarSacola(listaCompras);

					break;

				case (ConstantesMenuFluxoCaixa.LISTAR_ESTOQUE):
					listarEstoque();

					break;

				case (ConstantesMenuFluxoCaixa.REMOVER_PRODUTO):

					removerProduto(listaCompras);

					break;

				case (ConstantesMenuFluxoCaixa.MODIFICAR_QUANTIDADE):

					modificarQuantidade(listaCompras);

					break;

				case (ConstantesMenuFluxoCaixa.FINALIZAR_COMPRA):

					finalizarCompra(listaCompras, statusNotaFiscal, caminhoNotaFiscal);
					break;

				case (ConstantesMenuFluxoCaixa.LIMPAR):

					limparCarrinho(listaCompras);
					break;

				case (ConstantesMenuFluxoCaixa.SAIR):
					opcaoMenuFluxoDeCaixa = sair(opcaoMenuFluxoDeCaixa);

					break;
				default:
					JOptionPane.showMessageDialog(null, "Opcao invalida. Tente novamente!");
					break;

				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Entrada invalida. Por favor, insira um numero correspondente a� opção desejada.");
			}

		} while (opcaoMenuFluxoDeCaixa != ConstantesMenuFluxoCaixa.SAIR);
	}

	// melhorar a qualidade// ta muito grande
	private void adicionaProduto(Set<ItemVenda> listaCompras) {
		String codigoProduto = JOptionPane
				.showInputDialog("Digite o codigo do produto que voce deseja adicionar a lista de compras");

		if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {

			Integer novaQuantidade = Integer.parseInt(JOptionPane.showInputDialog(
					"Produto ja adicionado anteriormente, digite a nova quantidade que voce deseja: "));

			ItemVenda prod = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);

			Produto produtoEstoque = ProdutoService.retornaProdutoPorCodigo(codigoProduto);

			int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

			if (quantidadeRealProduto >= novaQuantidade) {

				prod.setQuantidade(novaQuantidade);
				produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

				ProdutoService.atualizaProduto(produtoEstoque);

			} else if (quantidadeRealProduto <= 0) {
				JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
			}

			else {

				Object[] opcoes = { "Sim", "Nao" };

				int opcao = JOptionPane.showOptionDialog(null, "Deseja adicionar todos os itens restantes ?",
						"Quantidade desejada menor do que em estoque.", JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

				if (opcao == 0) {

					prod.setQuantidade(quantidadeRealProduto);
					produtoEstoque.setQuantidade(0);

					ProdutoService.atualizaProduto(produtoEstoque);

				} else {
					JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
				}
			}

		} else {

			Produto produto = ProdutoService.retornaProdutoPorCodigo(codigoProduto);

			if (produto != null) {
				Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade: "));

				if (produto.getQuantidade() >= quantidade) {

					ItemVenda item = new ItemVenda(produto, quantidade);

					listaCompras.add(item);

					produto.setQuantidade(produto.getQuantidade() - quantidade);

					ProdutoService.atualizaProduto(produto);

				} else if (produto.getQuantidade() == 0) {
					JOptionPane.showMessageDialog(null, "Quantidade em estoque do produto igual a 0. Tente outro!");
				}

				else {

					Object[] opcoes = { "Sim", "Nao" };

					int opcao = JOptionPane.showOptionDialog(null, "Deseja adicionar todos os itens restantes ?",
							"Quantidade desejada menor do que em estoque.", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

					if (opcao == 0) {

						ItemVenda item = new ItemVenda(produto, produto.getQuantidade());

						listaCompras.add(item);

						produto.setQuantidade(0);
						ProdutoService.atualizaProduto(produto);

					} else {
						JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Produto nao consta no estoque. Tente outro!");

			}
		}
	}

	private void listarSacola(Set<ItemVenda> listaCompras) {

		String lista = ItemVendaService.imprime(listaCompras);

		double subtotal = ItemVendaService.somaPrecos(listaCompras);
		String subtotalFormatado = String.format("Subtotal: %.2f R$", subtotal);
		String resultado = "Produtos da sacola de compras: \n" + lista + "\n" + subtotalFormatado;

		JOptionPane.showMessageDialog(null, resultado);

	}

	private void listarEstoque() {

		if (ProdutoService.tabelaProdutoEstaVazia()) {
			JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
		} else {

			int categoria = CategoriaService.retornaIdCategoria();

			String resultado = ProdutoService.imprimeProdutos(categoria);

			JOptionPane.showMessageDialog(null, resultado);
		}

	}
// olhar se n da pra simplificar
	private void removerProduto(Set<ItemVenda> listaCompras) {

		if (listaCompras.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Adicione primeiro um produto a sua sacola para poder remover.");
		} else {
			String codigoProdutoParaRemover = JOptionPane
					.showInputDialog("Digite o codigo do produto que voce seja remover:");

			if (!ItemVendaService.contemProduto(listaCompras, codigoProdutoParaRemover)) {
				JOptionPane.showMessageDialog(null,
						"Produto ja nao constava na sacola. Tente novamente com uma produto existente.");

			} else {

				ItemVenda produtoListaCompras = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras,
						codigoProdutoParaRemover);

				Produto produtoDoEstoque = ProdutoService.retornaProdutoPorCodigo(codigoProdutoParaRemover);
				int quantidadeRealProduto = produtoListaCompras.getQuantidade() + produtoDoEstoque.getQuantidade();

				produtoDoEstoque.setQuantidade(quantidadeRealProduto);

				ProdutoService.atualizaProduto(produtoDoEstoque);
				listaCompras.remove(produtoListaCompras);

				JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
			}
		}
	}

	// melhorar a qualidade // da pra simplificar
	private void modificarQuantidade(Set<ItemVenda> listaCompras) {
		if (listaCompras.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
		} else {
			String codigo = JOptionPane.showInputDialog("Digite o codigo do produto: ");

			if (ItemVendaService.contemProduto(listaCompras, codigo)) {
				Integer novaQuantidade = Integer
						.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));

				ItemVenda prod = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);

				Produto produtoEstoque = ProdutoService.retornaProdutoPorCodigo(codigo);

				int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

				if (quantidadeRealProduto >= novaQuantidade) {

					prod.setQuantidade(novaQuantidade);
					produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

					ProdutoService.atualizaProduto(produtoEstoque);

				} else if (quantidadeRealProduto <= 0) {
					JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
				}

				else {

					Object[] opcoes = { "Sim", "Nao" };

					int opcao = JOptionPane.showOptionDialog(null, "Deseja adicionar todos os itens restantes ?",
							"Quantidade desejada menor do que em estoque.", JOptionPane.DEFAULT_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

					if (opcao == 0) {

						prod.setQuantidade(quantidadeRealProduto);
						produtoEstoque.setQuantidade(0);

						ProdutoService.atualizaProduto(produtoEstoque);

					} else {
						JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
					}
				}

				JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Produto invalido, tente outro!");
			}
		}
	}
// organizar mais
	private void finalizarCompra(Set<ItemVenda> listaCompras, Boolean statusNotaFiscal, String caminhoNotaFiscal) {
		if (!listaCompras.isEmpty()) {

			Venda venda = new Venda();
			venda.setInstant(Instant.now());

			VendaService.adicionaVenda(venda);

			Double total = 0.0;

			for (ItemVenda itemVenda : listaCompras) {
				itemVenda.setVenda(venda);
				total += itemVenda.subTotal();
				ItemVendaService.adicionaItemVenda(itemVenda);
			}

			venda.setTotal(total);

			VendaService.atualizaVenda(venda);

			if (statusNotaFiscal) {
				VendaService.geradorNotaFiscal(venda, caminhoNotaFiscal);
			}

			listaCompras.clear();
			JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");
		}
	}
// observar essa questao da quantidade real
	private void limparCarrinho(Set<ItemVenda> listaCompras) {
		if (!listaCompras.isEmpty()) {
			for (ItemVenda item : listaCompras) {

				Produto produtoEmEstoque = ProdutoService.retornaProdutoPorCodigo(item.getProduto().getcodigoDeBarra());

				int quantidadeReal = item.getQuantidade() + produtoEmEstoque.getQuantidade();

				produtoEmEstoque.setQuantidade(quantidadeReal);

				ProdutoService.atualizaProduto(produtoEmEstoque);

			}
			listaCompras.clear();
			JOptionPane.showMessageDialog(null, "Sacola de compras limpada com sucesso.");

		}
	}
// ve se isso esta em boas praticas
	private int sair(int opcaoMenuFluxoDeCaixa) {
		String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
		boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

		if (!autenticacao) {
			JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
			return 0;
		}
		return 8;
	}
}
