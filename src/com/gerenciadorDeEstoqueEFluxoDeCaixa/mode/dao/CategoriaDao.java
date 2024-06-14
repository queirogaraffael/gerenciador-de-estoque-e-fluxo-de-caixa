package com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao;

public interface CategoriaDao {
	Object[] categorias();

	void adicionarCategoriasSeNaoTiverAinda();

	Integer retornaIdCategoria();
}
