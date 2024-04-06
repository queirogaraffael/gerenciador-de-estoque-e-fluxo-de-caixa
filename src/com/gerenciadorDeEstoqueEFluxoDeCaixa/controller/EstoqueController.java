package com.gerenciadorDeEstoqueEFluxoDeCaixa.controller;

import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CaixaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ProdutoService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.ConstantesMenuEstoque;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.view.MenusView;

public class EstoqueController {

	public void gerenciarEstoque(Set<Produto> produtos, Set<Venda> vendas) {

		int opcao = 0;
		Produto produto;

		do {
			opcao = Integer.parseInt(JOptionPane.showInputDialog(MenusView.exibirMenuEstoque()));
			switch (opcao) {
			// cadastro de produto
			case (ConstantesMenuEstoque.CADASTRAR):

				int opcaoAdicionar = Integer.parseInt(
						JOptionPane.showInputDialog("1. Adicionar manualmente\n2. Adicionar por arquivo\n3. Voltar"));

				if (opcaoAdicionar == 1) {

					Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Código do produto: "));

					if (ProdutoService.jaContemProduto(produtos, codigo)) {
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
				} else if (opcaoAdicionar == 2) {
					String path = JOptionPane.showInputDialog("Digite o caminho do arquivo:");

					ProdutoService.adicionaProdutoPorArquivo(path, produtos);
				} else {

				}

				break;
			// edição de produto(s)
			case (ConstantesMenuEstoque.EDITAR):
				int opcaoEditar = Integer.parseInt(JOptionPane.showInputDialog(
						"1. Modificar o nome, o valor e a quantidade.\n2. Apenas a quantidade.\n3. Voltar"));

				if (opcaoEditar == 1) {

					Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do produto: "));

					if (!ProdutoService.jaContemProduto(produtos, codigo)) {
						System.out.println("Produto inválido. Tente outro!");
					} else {
						String novoNome = JOptionPane.showInputDialog("Digite o novo nome do produto: ");
						Double novoValor = Double
								.parseDouble(JOptionPane.showInputDialog("Digite o novo valor do produto: "));
						Integer novaQuantidade = Integer
								.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));
						ProdutoService.editarProdutoCompleto(produtos, codigo, novoNome, novoValor, novaQuantidade);
						JOptionPane.showMessageDialog(null, "Produto modificado com sucesso!");
					}

				} else {
					Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do produto: "));

					if (ProdutoService.jaContemProduto(produtos, codigo)) {
						Integer novaQuatidade = Integer
								.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));
						ProdutoService.editarProdutoQuantidade(produtos, codigo, novaQuatidade);
						JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null, "Produto não cadastrado ainda. Tente outro!");
					}

				}
				break;

			// Visualizar produto(s)
			case (ConstantesMenuEstoque.LISTAGEM):

				ProdutoService.visualizarProduto(produtos);
				break;

			// Remoção de produto(s)
			case (ConstantesMenuEstoque.REMOVER):

				Integer codigoProdutoParaRemover = Integer
						.parseInt(JOptionPane.showInputDialog("Digite o código do produto que você seja remover:"));

				if (produtos.isEmpty()) {

					JOptionPane.showMessageDialog(null, "Adicione primeiro um produto para poder remover.");
				} else if (!ProdutoService.jaContemProduto(produtos, codigoProdutoParaRemover)) {
					JOptionPane.showMessageDialog(null,
							"Produto já não constava no gerenciador de estoque. Tente novamente com uma produto existente.");

				} else {
					ProdutoService.removerProduto(produtos, codigoProdutoParaRemover);
					JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
				}
				break;

			case (ConstantesMenuEstoque.ATIVAR_NOTA_FICAL):
				int status = Integer.parseInt(
						JOptionPane.showInputDialog("Deseja ativar o gerador de notas fiscais ?\n1. Sim\n2. Não"));
				if (status == 1) {

					String path = JOptionPane
							.showInputDialog("Entre como o diretorio que você deseja salvar as notas fiscais: ");

					MenuController.caminhoNotaFiscal = path;
					MenuController.statusNotaFiscal = true;
					
					JOptionPane.showMessageDialog(null, "Gerador de notas fiscais ativado com sucesso!");

				}
				break;

			case (ConstantesMenuEstoque.LISTAGEM_VENDAS):

				/// implementar codigo de imprimir

				if (!vendas.isEmpty()) {
					System.out.println("Todas as vendas do estabelecimento");
					for (Venda v : vendas) {
						System.out.println(v);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Sem venda registrada.");
				}
				break;

			case (ConstantesMenuEstoque.DETALHES_VENDA):

				if (!vendas.isEmpty()) {

					Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
					Venda venda = CaixaService.retornaVendaPeloCodigo(vendas, codigo);
					JOptionPane.showMessageDialog(null, venda);
				} else {
					JOptionPane.showMessageDialog(null, "Sem venda registrada ainda.");
				}
				break;
			// Opção voltar
			case (ConstantesMenuEstoque.VOLTAR):

				break;

			default:
				JOptionPane.showMessageDialog(null, "Opção inválida. Tente outra!");
				break;
			}
		} while (opcao != ConstantesMenuEstoque.VOLTAR);
	}

}
