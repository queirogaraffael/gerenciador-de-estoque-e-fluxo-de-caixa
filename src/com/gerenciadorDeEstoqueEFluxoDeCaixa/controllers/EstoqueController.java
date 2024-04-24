package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.constantes.ConstantesMenuEstoque;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.ItemVenda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CategoriaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ItemVendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.ProdutoService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.VendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.VerificaDiretorio;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.GerenciadorDeEstoqueView;

public class EstoqueController {

	public void gerenciadorEstoque(String caminhoNotaFiscal) {

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

					ativadorNotaFiscal(caminhoNotaFiscal);
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

		if (ProdutoService.retornaProdutoPorCodigo(codigoBarra) != null) {
			JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
		} else {

			String nome = JOptionPane.showInputDialog("Digite o nome do produto: ");
			Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
			Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));

			int categoria = CategoriaService.retornaIdCategoria();

			ProdutoService.adicionaProduto(new Produto(codigoBarra, nome, valor, quantidade, categoria));

		}

	}

	private void editarProduto() {

		Object[] opcoes = { "Nome", "Preco", "Quantidade", "Categoria", "Voltar" };

		int opcaoEditar = JOptionPane.showOptionDialog(null, "Escolha uma opcao para modificar: ", "Modificar",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		if (opcaoEditar != 4) {
			String codigo = JOptionPane.showInputDialog("Digite o codigo do produto: ");

			Produto produto = ProdutoService.retornaProdutoPorCodigo(codigo);

			if (ProdutoService.retornaProdutoPorCodigo(codigo) != null) {
				if (opcaoEditar == 0) {
					String novoNome = JOptionPane.showInputDialog("Digite o novo nome: ");
					produto.setNome(novoNome);
					ProdutoService.atualizaProduto(produto);
				} else if (opcaoEditar == 1) {
					Double novoPreco = Double.valueOf(JOptionPane.showInputDialog("Digite o novo preco: "));
					produto.setpreco(novoPreco);
					ProdutoService.atualizaProduto(produto);
				} else if (opcaoEditar == 2) {
					Integer novaQuantidade = Integer
							.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade: "));
					produto.setQuantidade(novaQuantidade);
					ProdutoService.atualizaProduto(produto);
				} else if (opcaoEditar == 3) {
					Integer novaCategoria = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova categoria: "));
					produto.setCategoria(novaCategoria);
					ProdutoService.atualizaProduto(produto);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Produto nao cadastrado ainda. Tente outro!");
			}
		}

	}

	private void listarProdutos() {

		if (ProdutoService.tabelaProdutoEstaVazia()) {
			JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
		} else {

			int categoria = CategoriaService.retornaIdCategoria();

			String resultado = ProdutoService.imprimeProdutos(categoria);

			JOptionPane.showMessageDialog(null, resultado);
		}

	}

	private void removerProduto() {

		String codigoProdutoParaRemover = JOptionPane
				.showInputDialog("Digite o codigo do produto que voce deseja remover:");

		ProdutoService.removeProduto(codigoProdutoParaRemover);
	}

	private void ativadorNotaFiscal(String caminhoNotaFiscal) {
		Object[] opcoes = { "Sim", "Nao" };

		String mensagem = MenuPrincipalController.statusNotaFiscal ? "Deseja modificar o diretório?"
				: "Ativar gerador de nota fiscal ?";

		int opcao = JOptionPane.showOptionDialog(null, mensagem, "Opcoes", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		if (opcao == 0) {
			String path = JOptionPane.showInputDialog("Caminho do diretorio: ");

			if (VerificaDiretorio.verificarDiretorio(path)) {

				MenuPrincipalController.caminhoNotaFiscal = path;
				MenuPrincipalController.statusNotaFiscal = true;

				String msg = MenuPrincipalController.statusNotaFiscal
						? "Gerador de notas fiscais com novo diretório ativado com sucesso!"
						: "Gerador de notas fiscais ativado com sucesso!";
				JOptionPane.showMessageDialog(null, msg);
			} else {

				String msg = MenuPrincipalController.statusNotaFiscal
						? "Falha ao tentar ativar o novo diretorio do gerador de notas fiscais."
						: "Falha ao tentar ativar gerador de notas fiscais.";
				JOptionPane.showMessageDialog(null, msg);

			}

		}

	}
// adicionar pra ver apenas por data
	private void listarVendas() {

		if (!VendaService.tabelaVendaEstaVazia()) {

			String resultadoListagemVendas = VendaService.imprimeVendas();
			JOptionPane.showMessageDialog(null, resultadoListagemVendas);

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada.");
		}
	}

	// melhorar aqui
	private void detalharVenda() {
		if (!VendaService.tabelaVendaEstaVazia()) {

			Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
			Venda venda = VendaService.retornaVendaPorCodigo(codigo);

			if (venda != null) {

				Set<ItemVenda> itens = ItemVendaService.retornaItensVenda(venda);

				String itensVenda = ItemVendaService.imprime(itens);

				String resultado = itensVenda + "\n" + venda.toString();

				JOptionPane.showMessageDialog(null, resultado);

			} else {
				JOptionPane.showMessageDialog(null, "Venda invalida. Tente outra!");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada ainda.");
		}
	}
}
