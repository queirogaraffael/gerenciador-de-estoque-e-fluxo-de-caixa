package com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers;

import java.time.LocalDate;
import java.util.Set;

import javax.swing.JOptionPane;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.commons.constantes.ConstantesMenuEstoque;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.commons.utils.ManipulacaoData;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.commons.utils.VerificaDiretorio;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.CategoriaDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.ItemVendaDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.ProdutoDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.VendaDao;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.domain.NotaFiscal;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.ItemVenda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.Produto;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.Venda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.service.ItemVendaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.views.GerenciadorDeEstoqueView;

public class EstoqueController {
	private NotaFiscal notaFiscal;
	private CategoriaDao categoriaDao;
	private ItemVendaDao itemVendaDao;
	private ProdutoDao produtoDao;
	private VendaDao vendaDao;

	public EstoqueController(NotaFiscal notaFiscal, CategoriaDao categoriaDao, ItemVendaDao itemVendaDao,
			ProdutoDao produtoDao, VendaDao vendaDao) {
		this.notaFiscal = notaFiscal;
		this.categoriaDao = categoriaDao;
		this.itemVendaDao = itemVendaDao;
		this.produtoDao = produtoDao;
		this.vendaDao = vendaDao;
	}

	public void gerenciadorEstoque() {
		String opcao = "";
		do {
			try {
				opcao = GerenciadorDeEstoqueView.exibirMenuGerenciadorDeEstoque();

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

				case (ConstantesMenuEstoque.LISTAGEM_ESTOQUE_BAIXO):
					listaProdutosEstoqueBaixo();
					break;

				case (ConstantesMenuEstoque.LISTAGEM_CATEGORIAS):
					listarCategorias();
					break;

				case (ConstantesMenuEstoque.REMOVER):

					removerProduto();
					break;

				case (ConstantesMenuEstoque.ATIVAR_NOTA_FICAL):

					ativadorNotaFiscal(notaFiscal);
					break;

				case (ConstantesMenuEstoque.LISTAGEM_VENDAS):

					listarVendas();
					break;

				case (ConstantesMenuEstoque.DETALHES_VENDA):

					detalharVenda();
					break;

				case (ConstantesMenuEstoque.VOLTAR):
					break;

				}
			} catch (NumberFormatException erro) {
				JOptionPane.showMessageDialog(null,
						"Entrada invalida. Por favor, insira um numero correspondente a�op�ao desejada.");
			}

		} while (!opcao.equals(ConstantesMenuEstoque.VOLTAR));
	}

	private void cadastrarProduto() {

		String codigoBarra = JOptionPane.showInputDialog("Digite o codigo de barra do produto: ");

		if (produtoDao.retornaProdutoPorCodigo(codigoBarra) != null) {
			JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
		} else {

			String nome = JOptionPane.showInputDialog("Digite o nome do produto: ");
			Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
			Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));

			int categoria = categoriaDao.retornaIdCategoria();

			produtoDao.adicionaProduto(new Produto(codigoBarra, nome, valor, quantidade, categoria));

		}

	}

	private void editarProduto() {

		Object[] opcoes = { "Nome", "Preco", "Quantidade", "Categoria", "Voltar" };

		int opcaoEditar = JOptionPane.showOptionDialog(null, "Escolha uma opcao para modificar: ", "Modificar",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		if (opcaoEditar != 4) {
			String codigo = JOptionPane.showInputDialog("Digite o codigo do produto: ");

			Produto produto = produtoDao.retornaProdutoPorCodigo(codigo);

			if (produtoDao.retornaProdutoPorCodigo(codigo) != null) {
				if (opcaoEditar == 0) {
					String novoNome = JOptionPane.showInputDialog("Digite o novo nome: ");
					produto.setNome(novoNome);
					produtoDao.atualizaProduto(produto);
				} else if (opcaoEditar == 1) {
					Double novoPreco = Double.valueOf(JOptionPane.showInputDialog("Digite o novo preco: "));
					produto.setpreco(novoPreco);
					produtoDao.atualizaProduto(produto);
				} else if (opcaoEditar == 2) {
					Integer novaQuantidade = Integer
							.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade: "));
					produto.setQuantidade(novaQuantidade);
					produtoDao.atualizaProduto(produto);
				} else if (opcaoEditar == 3) {
					Integer novaCategoria = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova categoria: "));
					produto.setCategoria(novaCategoria);
					produtoDao.atualizaProduto(produto);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Produto nao cadastrado ainda. Tente outro!");
			}
		}

	}

	private void listarProdutos() {

		if (produtoDao.tabelaProdutoEstaVazia()) {
			JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
		} else {

			int categoria = categoriaDao.retornaIdCategoria();

			String resultado = produtoDao.geraRelatotioProdutos(categoria);

			JOptionPane.showMessageDialog(null, resultado);
		}

	}

	private void listaProdutosEstoqueBaixo() {

		String resultado = produtoDao.geraRelatorioProdutosEstoqueBaixo();

		if (resultado.equals("")) {
			JOptionPane.showMessageDialog(null, "Sem produtos com baixo estoque!");
		} else {
			JOptionPane.showMessageDialog(null, resultado);
		}

	}

	private void listarCategorias() {

		Object resultado = categoriaDao.categorias();

		JOptionPane.showMessageDialog(null, resultado);

	}

	private void removerProduto() {

		String codigoProdutoParaRemover = JOptionPane
				.showInputDialog("Digite o codigo do produto que voce deseja remover:");

		produtoDao.removeProduto(codigoProdutoParaRemover);
	}

	private void ativadorNotaFiscal(NotaFiscal notaFiscal) {
		Object[] opcoes = { "Sim", "Nao" };

		String mensagem = notaFiscal.getStatusNotaFiscal() ? "Deseja modificar o diret�rio?"
				: "Ativar gerador de nota fiscal ?";

		int opcao = JOptionPane.showOptionDialog(null, mensagem, "Opcoes", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		if (opcao == 0) {
			String path = JOptionPane.showInputDialog("Caminho do diretorio: ");

			if (VerificaDiretorio.verificarDiretorio(path)) {

				notaFiscal.setCaminhoNotaFiscal(path);
				notaFiscal.setStatusNotaFiscal(true);

				String msg = notaFiscal.getStatusNotaFiscal()
						? "Gerador de notas fiscais com novo diret�rio ativado com sucesso!"
						: "Gerador de notas fiscais ativado com sucesso!";
				JOptionPane.showMessageDialog(null, msg);
			} else {

				String msg = notaFiscal.getStatusNotaFiscal()
						? "Falha ao tentar ativar o novo diretorio do gerador de notas fiscais."
						: "Falha ao tentar ativar gerador de notas fiscais.";
				JOptionPane.showMessageDialog(null, msg);

			}

		}

	}

	private void listarVendas() {

		if (!vendaDao.tabelaVendaEstaVazia()) {

			Object[] opcoes = { "Listar todas as vendas", "Listar venda por data especifica", "Voltar" };

			int opcaoListagem = JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Listagem de vendas",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			if (opcaoListagem == 0) {

				String resultadoListagemVendas = vendaDao.geraRelatioVendas();
				JOptionPane.showMessageDialog(null, resultadoListagemVendas);

			} else if (opcaoListagem == 1) {
				String formatoData = "dd/MM/yyyy";
				String dataString = JOptionPane.showInputDialog("Digite uma data no formato dd/MM/yyyy");

				boolean formatoAprovado = ManipulacaoData.verificaFormatoData(dataString, formatoData);

				if (formatoAprovado) {

					LocalDate data = ManipulacaoData.retornaLocalDate(dataString);
					if (!ManipulacaoData.verificaSeADataEPosterior(dataString)) {

						String resultadoListagemVendasPorData = vendaDao.geraRelatiorioVendasPorData(data);

						if (resultadoListagemVendasPorData.equals("")) {
							JOptionPane.showMessageDialog(null, "Sem resultado de vendas para esta data");
						} else {
							JOptionPane.showMessageDialog(null, resultadoListagemVendasPorData);
						}

					} else {
						JOptionPane.showMessageDialog(null, "Data posterior a data atual. Tente novamente!");
					}

				} else {
					JOptionPane.showMessageDialog(null, "Problema no formato da data. Tente novamente!");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Sem venda registrada.");
			}
		}
	}

	private void detalharVenda() {
		if (!vendaDao.tabelaVendaEstaVazia()) {

			Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
			Venda venda = vendaDao.retornaVendaPorCodigo(codigo);

			if (venda != null) {

				Set<ItemVenda> itens = itemVendaDao.retornaItensVenda(venda);

				String itensVenda = ItemVendaService.geraRelatorioItemVenda(itens);

				String resultado = venda.toStringSemPreco() + "\n" + itensVenda
						+ String.format("Total: %.2f", venda.getTotal());

				JOptionPane.showMessageDialog(null, resultado);

			} else {
				JOptionPane.showMessageDialog(null, "Venda invalida. Tente outra!");
			}

		} else {
			JOptionPane.showMessageDialog(null, "Sem venda registrada ainda.");
		}
	}
}
