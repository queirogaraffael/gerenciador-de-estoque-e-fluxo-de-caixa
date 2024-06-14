package com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao;

import java.time.LocalDate;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.Venda;

public interface VendaDao {
	void adicionaVenda(Venda vendaInterface);

	void atualizaVenda(Venda vendaInterface);

	Venda retornaVendaPorCodigo(Integer codigo);

	String geraRelatioVendas();

	boolean tabelaVendaEstaVazia();

	String geraRelatiorioVendasPorData(LocalDate data);

}
