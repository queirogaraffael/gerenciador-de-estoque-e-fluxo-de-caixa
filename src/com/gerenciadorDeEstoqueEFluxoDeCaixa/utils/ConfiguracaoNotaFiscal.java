package com.gerenciadorDeEstoqueEFluxoDeCaixa.utils;

public class ConfiguracaoNotaFiscal {

	private static Boolean statusNotaFiscal = false;
	private static String caminhoNotaFiscal = "";

	public static Boolean getStatusNotaFiscal() {
		return statusNotaFiscal;
	}

	public static void setStatusNotaFiscal(Boolean statusNotaFiscal) {
		ConfiguracaoNotaFiscal.statusNotaFiscal = statusNotaFiscal;
	}

	public static String getCaminhoNotaFiscal() {
		return caminhoNotaFiscal;
	}

	public static void setCaminhoNotaFiscal(String caminhoNotaFiscal) {
		ConfiguracaoNotaFiscal.caminhoNotaFiscal = caminhoNotaFiscal;
	}

}
