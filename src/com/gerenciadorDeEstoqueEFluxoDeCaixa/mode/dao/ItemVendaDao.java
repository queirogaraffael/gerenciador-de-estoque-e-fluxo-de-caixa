package com.gerenciadorDeEstoqueEFluxoDeCaixa.mode.dao;

import java.util.Set;

import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.ItemVenda;
import com.gerenciadorDeEstoqueEFluxoDeCaixa.model.entities.Venda;

public interface ItemVendaDao {
	void adicionaItemVenda(ItemVenda itemVenda);

	Set<ItemVenda> retornaItensVenda(Venda venda);

}
