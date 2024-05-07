package com.gerenciadorDeEstoqueEFluxoDeCaixa.views;

import javax.swing.JOptionPane;

public class FluxoDeCaixaView {
	public static String exibirMenuFluxoDeCaixa() {

		Object[] opcoesMenu = { "Adicionar", "Listar sacola de compras", "Listar produtos em estoque",
				"Remover produto da sacola", "Modificar quantidade de um produto", "Limpar carrinho",
				"Finalizar compra", "Sair" };

		Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Escolha uma opcao", "Opcao",
				JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

		return opcaoSelecionada.toString();

	}
}
