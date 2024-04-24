package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

public class GerenciadorDeEstoqueView {
	public static String exibirMenuGerenciadorDeEstoque() {

		//adicionar locale
		StringBuilder menu = new StringBuilder();
		menu.append("Bem-vindo ao Controle de Estoque\n\n");
		menu.append("Menu:\n");
		menu.append("1. Cadastrar produto(s)\n");
		menu.append("2. Editar produto\n");
		menu.append("3. Listagem do estoque\n");
		menu.append("4. Remoção de produto\n");
		menu.append("5. Gerador de notas fiscal\n"); 
		menu.append("6. Listagem de Vendas\n");// por data // criar uma classe para o tratamento de data 
		menu.append("7. Detalhes da Venda\n"); // a impressão dos itens é algo em comum - melhorar a qualidade da impressão
		
		
		// adicionar uma opcao que alerte quando o estoque estiver baixo 
		
		menu.append("8. Voltar\n");
		menu.append("\nEscolha uma opção: ");
		return menu.toString();
	}
}


