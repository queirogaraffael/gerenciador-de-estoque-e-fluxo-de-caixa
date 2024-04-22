package com.gerenciadorDeEstoqueEFluxoDeCaixa.application;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers.MenuPrincipalController;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactoryService.inicializarEntityManagerFactory();

		MenuPrincipalController controller = new MenuPrincipalController();
		controller.exibirMenuPrincipal();
	}

}
