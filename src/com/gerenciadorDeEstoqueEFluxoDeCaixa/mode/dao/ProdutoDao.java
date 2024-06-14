package com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.Produto;

public interface ProdutoDao {
	void adicionaProduto(Produto produto);

	void atualizaProduto(Produto produto);

	void removeProduto(String codigo);

	Produto retornaProdutoPorCodigo(String codigo);

	String geraRelatotioProdutos(Integer categoria);

	boolean tabelaProdutoEstaVazia();

	String geraRelatorioProdutosEstoqueBaixo();

}
