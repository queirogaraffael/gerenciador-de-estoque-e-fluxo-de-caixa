package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuEstoque;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ProdutoService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.VendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.VerificaDiretorio;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.GerenciadorDeEstoqueView;

public class EstoqueController {

	public void gerenciadorEstoque(Boolean statusNotaFiscal, String caminhoNotaFiscal) {

		int opcao = 0;

		do {
			try {
				opcao = Integer.parseInt(
						JOptionPane.showInputDialog(GerenciadorDeEstoqueView.exibirMenuGerenciadorDeEstoque()));

				switch (opcao) {

				case (ConstantesMenuEstoque.CADASTRAR):

					cadastrarProduto();
					break;

				case (ConstantesMenuEstoque.EDITAR):

					editarProduto();
					break;

				case (ConstantesMenuEstoque.LISTAGEM):

					listarProdutos();
					break;

				case (ConstantesMenuEstoque.REMOVER):

					removerProduto();
					break;

				case (ConstantesMenuEstoque.ATIVAR_NOTA_FICAL):

					ativadorNotaFiscal(caminhoNotaFiscal, statusNotaFiscal);
					break;

				case (ConstantesMenuEstoque.LISTAGEM_VENDAS):

					listarVendas();
					break;

				case (ConstantesMenuEstoque.DETALHES_VENDA):

					detalharVenda();
					break;

				case (ConstantesMenuEstoque.VOLTAR):
					break;

				default:
					JOptionPane.showMessageDialog(null, "Opcao invalida. Tente outra!");
					break;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Entrada invalida. Por favor, insira um numero correspondente a opção desejada.");
			}

		} while (opcao != ConstantesMenuEstoque.VOLTAR);
	}

	private void cadastrarProduto() {

		String codigoBarra = JOptionPane.showInputDialog("Digite o codigo de barra do produto: ");

		if (ProdutoService.contemProduto(codigoBarra)) {
			JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
		} else {

			String nome = JOptionPane.showInputDialog("Digite o nome do produto: ");
			Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
			Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));
			Integer categoria = Integer.parseInt(JOptionPane.showInputDialog("Categoria do produto: "));

			ProdutoService.adicionaProduto(new Produto(codigoBarra, nome, valor, quantidade, categoria));

		}

	}

	private void editarProduto() {

		int opcaoEditar = Integer.parseInt(JOptionPane.showInputDialog(
				"1. Modificar o nome\n2. Modificar o preco\n3. Modificar a quantidade\n4. Modificar a categoria"));

		String codigo = JOptionPane.showInputDialog("Digite o codigo do produto: ");

		Produto produto = ProdutoService.retornaProdutoPorCodigo(codigo);

		if (ProdutoService.contemProduto(codigo)) {
			if (opcaoEditar == 1) {
				String novoNome = JOptionPane.showInputDialog("Digite o novo nome: ");
				produto.setNome(novoNome);
				ProdutoService.atualizaProduto(produto);
			} else if (opcaoEditar == 2) {
				Double novoPreco = Double.valueOf(JOptionPane.showInputDialog("Digite o novo preco: "));
				produto.setpreco(novoPreco);
				ProdutoService.atualizaProduto(produto);
			} else if (opcaoEditar == 3) {
				Integer novaQuantidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade: "));
				produto.setQuantidade(novaQuantidade);
				ProdutoService.atualizaProduto(produto);
			} else if (opcaoEditar == 4) {
				Integer novaCategoria = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova categoria: "));
				produto.setCategoria(novaCategoria);
				ProdutoService.atualizaProduto(produto);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Produto nao cadastrado ainda. Tente outro!");
		}

	}

	private void listarProdutos() {

		if (ProdutoService.tabelaProdutoEstaVazia()) {
			JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
		} else {

			Integer categoria_id = Integer.parseInt(JOptionPane.showInputDialog("Digite a categoria: "));

			String resultado = ProdutoService.imprimeProdutos(categoria_id);

			JOptionPane.showMessageDialog(null, resultado);
		}

	}

	private void removerProduto() {

		String codigoProdutoParaRemover = JOptionPane
				.showInputDialog("Digite o codigo do produto que voce deseja remover:");

		ProdutoService.removeProduto(codigoProdutoParaRemover);
	}

	private void ativadorNotaFiscal(String caminhoNotaFiscal, Boolean statusNotaFiscal) {
		int status = Integer
				.parseInt(JOptionPane.showInputDialog("Deseja ativar o gerador de notas fiscais ?\n1. Sim\n2. NÃ£o"));
		if (status == 1) {

			String path = JOptionPane
					.showInputDialog("Entre com o caminho do diretorio que vocÃª deseja salvar as notas fiscais: ");

			boolean autoriza = VerificaDiretorio.verificarDiretorio(path);

			if (autoriza) {
				MenuPrincipalController.caminhoNotaFiscal = path;
				MenuPrincipalController.statusNotaFiscal = true;

				JOptionPane.showMessageDialog(null, "Gerador de notas fiscais ativado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao tentar ativar gerador de notas fiscais.");
			}

		}
	}

	private void listarVendas() {

		if (!VendaService.tabelaVendaEstaVazia()) {

			String resultadoListagemVendas = VendaService.imprimeVendas();
			JOptionPane.showMessageDialog(null, resultadoListagemVendas);

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada.");
		}
	}

//botar para imprimir os itens
	private void detalharVenda() {
		if (!VendaService.tabelaVendaEstaVazia()) {

			Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
			Venda venda = VendaService.retornaVendaPorCodigo(codigo);

			if (venda != null) {
				JOptionPane.showMessageDialog(null, venda);

			} else {
				JOptionPane.showMessageDialog(null, "Venda invalida. Tente outra!");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada ainda.");
		}
	}
}
