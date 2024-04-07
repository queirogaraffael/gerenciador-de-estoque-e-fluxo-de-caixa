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
		menu.append("1. Cadastrar produto(s)\n");
		menu.append("2. Editar produto\n");
		menu.append("3. Listagem do estoque\n");
		menu.append("4. Remoção de produto\n");
		menu.append("5. Gerador de notas fiscal\n");
		menu.append("6. Listagem de Vendas\n");
		menu.append("7. Detalhes da Venda\n");
		menu.append("8. Voltar\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}

	public static String exibirMenuFluxoCaixa() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Fluxo de Caixa\n\n");
		menu.append("Menu:\n");
		menu.append("1. Adicionar\n");
		menu.append("2. Listar sacola de compras\n");
		menu.append("3. Listar produtos em estoque\n");
		menu.append("4. Remover produto da sacola\n");
		menu.append("5. Modificar quantidade de um produto\n");
		menu.append("6. Limpar carrinho\n");
		menu.append("7. Finalizar compra\n");
		menu.append("8. Sair\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}

}
