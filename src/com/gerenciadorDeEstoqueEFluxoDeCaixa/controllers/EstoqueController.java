package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuEstoque;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ComumProdutoVendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ProdutoService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.GerenciadorDeEstoqueView;

public class EstoqueController {

	public void gerenciadorEstoque(Set<Produto> produtos, Set<Venda> vendas, Boolean statusNotaFiscal,
			String caminhoNotaFiscal) {

		int opcao = 0;

		do {
			try {
				opcao = Integer.parseInt(
						JOptionPane.showInputDialog(GerenciadorDeEstoqueView.exibirMenuGerenciadorDeEstoque()));

				switch (opcao) {

				case (ConstantesMenuEstoque.CADASTRAR):

					cadastrarProduto(produtos);
					break;

				case (ConstantesMenuEstoque.EDITAR):

					editarProduto(produtos);
					break;

				case (ConstantesMenuEstoque.LISTAGEM):

					listarProdutos(produtos);
					break;

				case (ConstantesMenuEstoque.REMOVER):

					removerProduto(produtos);
					break;

				case (ConstantesMenuEstoque.ATIVAR_NOTA_FICAL):

					ativadorNotaFiscal(caminhoNotaFiscal, statusNotaFiscal);
					break;

				case (ConstantesMenuEstoque.LISTAGEM_VENDAS):

					listarVendas(vendas);
					break;

				case (ConstantesMenuEstoque.DETALHES_VENDA):

					detalharVenda(vendas);
					break;

				case (ConstantesMenuEstoque.VOLTAR):
					break;

				default:
					JOptionPane.showMessageDialog(null, "Opção inválida. Tente outra!");
					break;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Entrada inválida. Por favor, insira um número correspondente à opção desejada.");
			}

		} while (opcao != ConstantesMenuEstoque.VOLTAR);
	}

	private void cadastrarProduto(Set<Produto> produtos) {
		Produto produto;
		int opcaoAdicionar = Integer.parseInt(JOptionPane
				.showInputDialog("1. Adicionar manualmente\n2. Adicionar por arquivo\n3. Desativar arquivo \n4. Voltar"));

		if (opcaoAdicionar == 1) {

			if (MenuPrincipalController.statusArquivoAdicionado == true) {
				JOptionPane.showMessageDialog(null, "Desative primeiro o arquivo para poder adicionar manualmente.");
			} else {
				Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Código do produto: "));

				if (ComumProdutoVendaService.jaContem(produtos, codigo)) {
					JOptionPane.showMessageDialog(null,
							"O produto já foi cadastrado anteriormente. Por favor, insira um novo produto!");
				} else {
					String nome = JOptionPane.showInputDialog("Digite o nome do produto: ");
					Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
					Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));

					produto = new Produto(codigo, nome, valor, quantidade);
					ProdutoService.adicionarProduto(produtos, produto);
					JOptionPane.showMessageDialog(null, "Produto adicionada com sucesso!");
				}
			}

		} else if (opcaoAdicionar == 2) {

			String path = JOptionPane.showInputDialog("Digite o caminho do arquivo:");
			ProdutoService.adicionaProdutoPorArquivo(path, produtos);
			
			MenuPrincipalController.caminhoArquivoAdicionado = path;
			MenuPrincipalController.statusArquivoAdicionado = true;
			
			JOptionPane.showMessageDialog(null, "Produtos por arquivo adicionado com sucesso!");

		} else if (opcaoAdicionar == 3) {

			if (MenuPrincipalController.statusArquivoAdicionado == false) {
				JOptionPane.showInternalMessageDialog(null, "Arquivo já estava desativado.");
			} else {
				MenuPrincipalController.statusArquivoAdicionado = false;
				JOptionPane.showInternalMessageDialog(null, "Arquivo desativado.");
			}

		}
	}

	private void editarProduto(Set<Produto> produtos) {
		int opcaoEditar = Integer.parseInt(JOptionPane
				.showInputDialog("1. Modificar o nome, o valor e a quantidade.\n2. Apenas a quantidade.\n3. Voltar"));

		if (opcaoEditar == 1) {

			Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do produto: "));

			if (!ComumProdutoVendaService.jaContem(produtos, codigo)) {
				JOptionPane.showMessageDialog(null, "Produto inválido. Tente outro!");
			} else {

				String novoNome = JOptionPane.showInputDialog("Digite o novo nome do produto: ");
				Double novoValor = Double.parseDouble(JOptionPane.showInputDialog("Digite o novo valor do produto: "));
				Integer novaQuantidade = Integer
						.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));

				Produto produtoModificar = ComumProdutoVendaService.retornaPeloCodigo(produtos, codigo);
				ProdutoService.editaProduto(produtoModificar, novoNome, novoValor, novaQuantidade);

				if (MenuPrincipalController.statusArquivoAdicionado == true) {
					ProdutoService.atualizaArquivoProdutos(MenuPrincipalController.caminhoArquivoAdicionado, produtos);
				}

				JOptionPane.showMessageDialog(null, "Produto modificado com sucesso!");
			}

		} else if (opcaoEditar == 2) {
			Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do produto: "));

			if (ComumProdutoVendaService.jaContem(produtos, codigo)) {
				Integer novaQuatidade = Integer
						.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));
				Produto produtoModificar = ComumProdutoVendaService.retornaPeloCodigo(produtos, codigo);
				ProdutoService.editaProduto(produtoModificar, novaQuatidade);

				if (MenuPrincipalController.statusArquivoAdicionado == true) {
					ProdutoService.atualizaArquivoProdutos(MenuPrincipalController.caminhoArquivoAdicionado, produtos);
				}

				JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Produto não cadastrado ainda. Tente outro!");
			}

		}
	}

	private void listarProdutos(Set<Produto> produtos) {

		if (produtos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
		} else {
			String resultado = ComumProdutoVendaService.imprimir(produtos);
			JOptionPane.showMessageDialog(null, resultado);
		}

	}

	private void removerProduto(Set<Produto> produtos) {

		if (produtos.isEmpty()) {

			JOptionPane.showMessageDialog(null, "Adicione primeiro um produto para poder remover.");
		} else {
			Integer codigoProdutoParaRemover = Integer
					.parseInt(JOptionPane.showInputDialog("Digite o código do produto que você seja remover:"));
			if (!ComumProdutoVendaService.jaContem(produtos, codigoProdutoParaRemover)) {
				JOptionPane.showMessageDialog(null,
						"Produto já não constava no gerenciador de estoque. Tente novamente com uma produto existente.");

			} else {
				ProdutoService.removeProduto(produtos, codigoProdutoParaRemover);

				if (MenuPrincipalController.statusArquivoAdicionado == true) {
					ProdutoService.atualizaArquivoProdutos(MenuPrincipalController.caminhoArquivoAdicionado, produtos);
				}

				JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");

			}

		}

	}

	private void ativadorNotaFiscal(String caminhoNotaFiscal, Boolean statusNotaFiscal) {
		int status = Integer
				.parseInt(JOptionPane.showInputDialog("Deseja ativar o gerador de notas fiscais ?\n1. Sim\n2. Não"));
		if (status == 1) {

			String path = JOptionPane
					.showInputDialog("Entre com o caminho do diretorio que você deseja salvar as notas fiscais: ");

			MenuPrincipalController.caminhoNotaFiscal = path;
			MenuPrincipalController.statusNotaFiscal = true;

			JOptionPane.showMessageDialog(null, "Gerador de notas fiscais ativado com sucesso!");

		}
	}

	private void listarVendas(Set<Venda> vendas) {

		if (!vendas.isEmpty()) {

			String resultadoListagemVendas = ComumProdutoVendaService.imprimir(vendas);
			JOptionPane.showMessageDialog(null, resultadoListagemVendas);

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada.");
		}
	}

	private void detalharVenda(Set<Venda> vendas) {
		if (!vendas.isEmpty()) {

			Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));

			if (ComumProdutoVendaService.jaContem(vendas, codigo)) {

				Venda venda = ComumProdutoVendaService.retornaPeloCodigo(vendas, codigo);

				JOptionPane.showMessageDialog(null, venda);

			} else {
				JOptionPane.showMessageDialog(null, "Venda inválida. Tente outra!");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada ainda.");
		}
	}
}
