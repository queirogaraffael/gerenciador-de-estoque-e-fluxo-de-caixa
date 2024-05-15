package com.gerenciadorDeEstoqueEFluxoDeCaixa.entities;

public class NotaFiscal {

	private Boolean statusNotaFiscal;
	private String caminhoNotaFiscal;

	public NotaFiscal() {
		this.statusNotaFiscal = false;
		this.caminhoNotaFiscal = "";
	}

	public Boolean getStatusNotaFiscal() {
		return statusNotaFiscal;
	}

	public void setStatusNotaFiscal(Boolean statusNotaFiscal) {
		this.statusNotaFiscal = statusNotaFiscal;
	}

	public String getCaminhoNotaFiscal() {
		return caminhoNotaFiscal;
	}

	public void setCaminhoNotaFiscal(String caminhoNotaFiscal) {
		this.caminhoNotaFiscal = caminhoNotaFiscal;
	}

}
