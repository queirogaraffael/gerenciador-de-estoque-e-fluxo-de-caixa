package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

public class GerenciadorDeEstoqueView {
	public static String exibirMenuGerenciadorDeEstoque() {

		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Controle de Estoque\n\n");
		menu.append("Menu:\n");
		menu.append("1. Cadastrar produto(s)\n");
		menu.append("2. Editar produto\n");
		menu.append("3. Listagem do estoque\n");
		menu.append("4. Produtos com estoque baixo\n");
		menu.append("5. Remoção de produto\n");
		menu.append("6. Gerador de notas fiscal\n");
		menu.append("7. Listagem de Vendas\n");
		menu.append("8. Detalhes da Venda\n");
		menu.append("9. Voltar\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}
}
