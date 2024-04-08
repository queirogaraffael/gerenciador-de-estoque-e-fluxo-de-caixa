package com.gerenciadorDeEstoqueEFluxoDeCaixa.application;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers.MenuPrincipalController;

public class Main {

	public static void main(String[] args) {

		MenuPrincipalController controller = new MenuPrincipalController();
		controller.exibirMenuPrincipal();
	}

}
