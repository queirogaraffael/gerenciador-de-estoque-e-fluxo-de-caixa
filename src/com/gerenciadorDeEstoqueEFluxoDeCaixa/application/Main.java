package com.gerenciadorDeEstoqueEFluxoDeCaixa.application;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.controllers.MenuPrincipalController;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.services.CategoriaService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.utils.EntityManagerFactoryService;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactoryService.inicializarEntityManagerFactory();

		CategoriaService.adicionarCategoriasSeNaoTiverAinda();

		MenuPrincipalController controller = new MenuPrincipalController();
		controller.exibirMenuPrincipal();
	}

}
