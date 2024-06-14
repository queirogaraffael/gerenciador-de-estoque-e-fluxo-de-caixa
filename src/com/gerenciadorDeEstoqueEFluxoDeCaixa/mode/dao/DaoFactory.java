package com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.imp.CategoriaDaoHibernate;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.imp.ItemVendaDaoHibernate;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.imp.ProdutoDaoHibernate;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao.imp.VendaDaoHibernate;

public class DaoFactory {

	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

	public static ItemVendaDao createItemVendaDao() {
		return new ItemVendaDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

	public static VendaDao createVendaDao() {
		return new VendaDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

}
