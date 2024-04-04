package Program;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import entities.Produto;
import entities.Venda;
import services.CaixaService;
import services.ProdutoService;

public class Program {

	public static void main(String[] args) {

		Set<Produto> produtos = new HashSet<>();
		Set<Venda> vendas = new HashSet<>();
		Set<String> codigosVendas = new HashSet<>();

		int opcaoMenuPrincipal = 0;

		do {

			opcaoMenuPrincipal = Integer.parseInt(JOptionPane.showInputDialog(menuPrincipal()));

			// Gerenciamento de estoque
			if (opcaoMenuPrincipal == 1) {
				int opcao = 0;
				Produto produto;

				do {

					opcao = Integer.parseInt(JOptionPane.showInputDialog(menuEstoque()));

					// cadastro de produto
					if (opcao == 1) {

						int opcaoAdicionar = Integer.parseInt(
								JOptionPane.showInputDialog("1. Adicionar manualmente\n2. Adicionar por arquivo"));

						if (opcaoAdicionar == 1) {
							JOptionPane.showMessageDialog(null, "Cadastro de produto");
							String codigo = JOptionPane.showInputDialog("Código do produto: ");
							String nome = JOptionPane.showInputDialog("Digite o nome do produto: ");
							Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
							Integer quantidade = Integer
									.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));

							produto = new Produto(codigo, nome, valor, quantidade);

							if (ProdutoService.jaContemProduto(produtos, codigo)) {
								JOptionPane.showMessageDialog(null,
										"O produto já foi cadastrado anteriormente. Por favor, insira um novo produto!");
							} else {
								ProdutoService.adicionarProduto(produtos, produto);
								JOptionPane.showMessageDialog(null, "Tarefa adicionada com sucesso!");
							}
						} else {
							String caminho = JOptionPane.showInputDialog("Digite o caminho do arquivo:");

							ProdutoService.adicionaProdutoPorArquivo(caminho, produtos);
						}
						// edição de produto(s)
					} else if (opcao == 2) {

						JOptionPane.showMessageDialog(null, "Edição de dados do produto");

						int opcaoEditar = Integer.parseInt(JOptionPane.showInputDialog(
								"1. Modificar o nome, o valor e a quantidade.\n2. Apenas a quantidade."));

						String codigo = JOptionPane.showInputDialog("Digite o código do produto: ");

						if (opcaoEditar == 1) {

							String novoNome = JOptionPane.showInputDialog("Digite o novo nome do produto: ");
							Double novoValor = Double
									.parseDouble(JOptionPane.showInputDialog("Digite o novo valor do produto: "));
							Integer novaQuatidade = Integer
									.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));

							if (ProdutoService.jaContemProduto(produtos, codigo)) {
								System.out.println("Produto inválido");
							} else {
								ProdutoService.editarProdutoCompleto(produtos, codigo, novoNome, novoValor,
										novaQuatidade);
								JOptionPane.showMessageDialog(null, "Produto modificado com sucesso!");
							}

						} else if (opcaoEditar == 2) {
							// esta modificando de arquivos inexistente
							Integer novaQuatidade = Integer
									.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade do produto: "));

							// implementar

							ProdutoService.editarProdutoQuantidade(produtos, codigo, novaQuatidade);
							JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");

						} else {

							JOptionPane.showMessageDialog(null, "Opção inválida. Tente Novamente!");
						}
						// Visualizar produto(s)
					} else if (opcao == 3) {
						ProdutoService.visualizarProduto(produtos);
						// Remoção de produto(s)
					} else if (opcao == 4) {

						String produtoParaRemover = JOptionPane
								.showInputDialog("Digite o código do produto que você seja remover:");

						if (produtos.isEmpty()) {

							JOptionPane.showMessageDialog(null, "Adicione primeiro um produto para poder remover.");
						} else if (!ProdutoService.jaContemProduto(produtos, produtoParaRemover)) {
							JOptionPane.showMessageDialog(null,
									"Produto já não constava no gerenciador de estoque. Tente novamente com uma produto existente.");

						} else {
							ProdutoService.removerProduto(produtos, produtoParaRemover.toLowerCase());
							JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
						}
					}

					else if (opcao == 5) {
						
						///implementar
						System.out.println("Todas as vendas do estabelecimento");
						for (Venda v : vendas) {
							System.out.println(v);
						}
					}

					else if (opcao == 6) {

						String codigo = JOptionPane.showInputDialog("Codigo de venda: ");
						Venda venda = CaixaService.retornaVendaPeloCodigo(vendas, codigo);
						JOptionPane.showMessageDialog(null, venda);
					}

					// Opção voltar
					else if (opcao == 7) {
						continue;
					}

					else {
						JOptionPane.showMessageDialog(null, "Opção inválida. Tente outra!");
					}

				} while (opcao != 7);

				// fluxo de caixa
			} else if (opcaoMenuPrincipal == 2) {
				int opcaoMenuFluxoDeCaixa = 0;
				Set<Produto> listaCompras = new HashSet<>();

				do {
					opcaoMenuFluxoDeCaixa = Integer.parseInt(JOptionPane.showInputDialog(menuFluxoDeCaixa()));
					// Adicionar produto(s) na sacola
					if (opcaoMenuFluxoDeCaixa == 1) {

						String codigoProduto = JOptionPane.showInputDialog(
								"Digite o código do produto que você deseja adicionar a lista de compras");
						Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade: "));

						Produto p = CaixaService.retornaProdutoPeloCodigo(produtos, codigoProduto);

						if (p != null) {

							if (p.getQuantidade() >= quantidade) {
								listaCompras.add(new Produto(p.getCodigo(), p.getNome(), p.getValor(), quantidade));
								p.setQuantidade(-quantidade);

							} else if (p.getQuantidade() == 0) {
								JOptionPane.showMessageDialog(null,
										"Quantidade em estoque do produto igual a 0. Tente outro!");
							}

							else {

								int escolha = Integer.parseInt(
										"Quantidade desejada menor do que em estoque. Deseja adicionar todos os itens restantes ?");

								if (escolha == 1) {
									listaCompras.add(
											new Produto(p.getCodigo(), p.getNome(), p.getValor(), p.getQuantidade()));
									p.setQuantidade(0);
								} else {
									JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");

								}
							}
						}

						else {
							JOptionPane.showMessageDialog(null, "Produto não consta no estoque. Tente outro!");

						}
						// Listar produto(s) da sacola
					} else if (opcaoMenuFluxoDeCaixa == 2) {
						
						
						
						CaixaService.listarCompras(listaCompras);
						
						

						
					}

					else if (opcaoMenuFluxoDeCaixa == 3) {

						String codigoVenda = CaixaService.geradorDeCodigoVenda(codigosVendas);
						codigosVendas.add(codigoVenda);
						Venda novaVenda = new Venda(codigoVenda);
						vendas.add(novaVenda);

						for (Produto p : listaCompras) {
							novaVenda.getProdutos().add(p);
						}

						CaixaService.geradorNotaFiscal(novaVenda);

						JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");

					} else if (opcaoMenuFluxoDeCaixa == 4) {
						continue;
					}

					else {

						JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente!");

					}

				} while (opcaoMenuFluxoDeCaixa != 4);

			} else if (opcaoMenuPrincipal == 3) {
				System.out.println("Fim do programa");
			} else {
				JOptionPane.showMessageDialog(null, "Opção inválida. Tente outra!");
			}
		} while (opcaoMenuPrincipal != 3);

	}

	public static String menuPrincipal() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Gerenciador de Estoque e Fluxo de Caixa\n\n");
		menu.append("Menu:\n");
		menu.append("1. Gerenciador de Estoque\n");
		menu.append("2. Fluxo de Caixa\n");
		menu.append("3. Sair\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}

	public static String menuEstoque() {

		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Controle de Estoque\n\n");
		menu.append("Menu:\n");
		menu.append("1. Cadastro\n");
		menu.append("2. Edição\n");
		menu.append("3. Listagem\n");
		menu.append("4. Remoção\n");
		menu.append("5. Listagem de Vendas\n");
		menu.append("6. Detalhes da Venda\n");
		menu.append("7. Voltar");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();

	}

	public static String menuFluxoDeCaixa() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Fluxo de Caixa\n\n");
		menu.append("Menu:\n");
		menu.append("1. Adicionar\n");
		menu.append("2. Listar\n");
		menu.append("3. Finalizar compra\n");
		menu.append("4. Voltar para o menu principal\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}
}
