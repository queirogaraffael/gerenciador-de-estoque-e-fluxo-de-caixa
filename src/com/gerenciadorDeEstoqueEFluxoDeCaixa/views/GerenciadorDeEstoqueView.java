package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

import javax.swing.JOptionPane;

public class GerenciadorDeEstoqueView {
	public static String exibirMenuGerenciadorDeEstoque() {

		Object[] opcoesMenu = { "Cadastrar produto(s)", "Editar produto", "Listagem do estoque",
				"Listar produtos com estoque baixo", "Listar Categorias", "Remoção de produto",
				"Gerador de notas fiscal", "Listagem de Vendas", "Detalhes da Venda", "Voltar para Menu Principal" };

		Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Escolha uma opcao", "Gerenciador de Estoque",
				JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

		if (opcaoSelecionada != null) {
			return opcaoSelecionada.toString();
		}
		return "";

	}
}
