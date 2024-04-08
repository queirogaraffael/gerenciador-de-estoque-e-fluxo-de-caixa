package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

public class MenuPrincipalView {
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
}
