package com.gerenciadorDeEstoqueEFluxoDeCaixa.application;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers.MenuPrincipalController;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.hibernateConnection.EntityManagerFactoryService;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactoryService.inicializarEntityManagerFactory();

		// CategoriaDaoHibernate.adicionarCategoriasSeNaoTiverAinda();

		MenuPrincipalController controller = new MenuPrincipalController();
		controller.exibirMenuPrincipal();
	}

}
