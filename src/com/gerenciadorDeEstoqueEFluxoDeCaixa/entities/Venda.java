package com.gerenciadorDeEstoqueEFluxoDeCaixa.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendas")
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	private Instant instant;
	private Double total;

	@OneToMany(mappedBy = "id.venda")
	private Set<ItemVenda> itens = new HashSet<>();

	public Venda() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public String getInstant() {
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
		return formatoDataHora.format(zonedDateTime);
	}

	public Set<ItemVenda> getProdutosVendidos() {
		return itens;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Venda: Codigo = " + codigo + ", Data = " + getInstant() + ", Total: "
				+ String.format("%.2f R$", getTotal());
	}

}
