package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

public class GerenciadorDeEstoqueView {
	public static String exibirMenuGerenciadorDeEstoque() {

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
}
