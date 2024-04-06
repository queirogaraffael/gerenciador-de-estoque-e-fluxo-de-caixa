package com.gerenciadorDeEstoqueEFluxoDeCaixa.view;

public class MenusView {

	public static String exibirMenuPrincipal() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Gerenciador de Estoque e Fluxo de Caixa\n\n");
		menu.append("Menu:\n");
		menu.append("1. Gerenciador de Estoque\n");
		menu.append("2. Fluxo de Caixa\n");
		menu.append("3. Sair\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}

	public static String exibirMenuEstoque() {

		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Controle de Estoque\n\n");
		menu.append("Menu:\n");
		menu.append("1. Cadastro\n");
		menu.append("2. Edição\n");
		menu.append("3. Listagem\n");
		menu.append("4. Remoção\n");
		menu.append("5. Listagem de Vendas\n");
		menu.append("6. Detalhes da Venda\n");
		menu.append("7. Voltar\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}

	public static String exibirMenuFluxoCaixa() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Fluxo de Caixa\n\n");
		menu.append("Menu:\n");
		menu.append("1. Adicionar\n");
		menu.append("2. Listar\n");
		menu.append("3. Finalizar compra\n");
		menu.append("4. Sair sem finalizar compra\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}

}
