package com.gerenciadorDeEstoqueEFluxoDeCaixa.application;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.controller.MenuController;

public class Main {

	public static void main(String[] args) {

		MenuController controller = new MenuController();
		controller.exibirMenuPrincipal();
	}

}
