package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

public class FluxoDeCaixaView {

	// adicionar locale
	//adicionar leitor de qr code
	// testar tratamento de excecao personalizada
	// olhar direito essa questao da quantidade real e ve se faz sentido pro programa
	public static String exibirMenuFluxoDeCaixa() {
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Fluxo de Caixa\n\n");
		menu.append("Menu:\n");
		menu.append("1. Adicionar\n"); // melhorar o codigo se o produto ja exite
		menu.append("2. Listar sacola de compras\n");
		menu.append("3. Listar produtos em estoque\n");
		menu.append("4. Remover produto da sacola\n");
		menu.append("5. Modificar quantidade de um produto\n"); // melhorar junto com o 1
		menu.append("6. Limpar carrinho\n");
		menu.append("7. Finalizar compra\n");
		menu.append("8. Sair\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}
}
