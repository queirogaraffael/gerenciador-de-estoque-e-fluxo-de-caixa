package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

import javax.swing.JOptionPane;

public class FluxoDeCaixaView {
	public static String exibirMenuFluxoDeCaixa() {

		Object[] opcoesMenu = { "Adicionar", "Listar sacola de compras", "Listar produtos em estoque",
				"Remover produto da sacola", "Modificar quantidade de um produto", "Limpar carrinho",
				"Finalizar compra", "Voltar para Menu Principal" };

		Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Escolha uma opcao", "Fluxo De Caixa",
				JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

		if (opcaoSelecionada != null) {
			return opcaoSelecionada.toString();
		}
		return "";

	}
}
