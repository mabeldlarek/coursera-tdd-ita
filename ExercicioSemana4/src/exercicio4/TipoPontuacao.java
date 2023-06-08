package exercicio4;

public enum TipoPontuacao {
	 ESTRELA(1),
	 ENERGIA(2),
	 MOEDA(3),
	 CORACOES(4),
	 MEDALHA(5),
	 TROFEU(6);

	 private final int valor;

	 TipoPontuacao(int valor) {
	    this.valor = valor;
	 }

	 public int getValor() {
	    return valor;
	 }
	 
	 public int getQtdTipos() {
		return TipoPontuacao.values().length;
	 }
}
