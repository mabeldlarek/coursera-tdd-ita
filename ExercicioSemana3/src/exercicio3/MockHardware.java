package exercicio3;

public class MockHardware implements Hardware {

	private Cartao cartao;
	private boolean isReceptorDinheiroAberto;
	private boolean isEnvelopeLido;
	private boolean erroGerado = false;
	
	public void adicionarCartao(Cartao cartaoInserido) {
		cartao = cartaoInserido;
	}
	
	@Override
	public String pegarNumeroDaContaCartao() {
		if(erroGerado)
			throw new RuntimeException("Problema simulado pelo mock ao ler cartao");
		return cartao.getNumCartao();
	}

	@Override
	public void entregarDinheiro() {
		if(erroGerado)
			throw new RuntimeException("Problema simulado pelo mock ao entregarDinheiro");
		isReceptorDinheiroAberto = true;
	}

	@Override
	public void lerEnvelope() {
		if(erroGerado)
			throw new RuntimeException("Problema simulado pelo mock ao ler envelope");
		isEnvelopeLido = true;
	}

	public boolean isReceptorDinheiroAberto() {
		return isReceptorDinheiroAberto;
	}

	public boolean isEnvelopeLido() {
		return isEnvelopeLido;
	}

	public void gerarErro() {
		erroGerado = true;
	}
	

}
