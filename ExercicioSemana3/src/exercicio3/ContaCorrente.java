package exercicio3;

public class ContaCorrente {
	private String numConta;
	private int senha;
	private int saldo;

	public ContaCorrente(String numConta, int senha) {
		super();
		this.numConta = numConta;
		this.senha = senha;
	}

	public String getNumConta() {
		return numConta;
	}

	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}


	public int getSenha() {
		return senha;
	}


	public void setSenha(int senha) {
		this.senha = senha;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
	public void adicionarValor(int valor) {
		setSaldo(getSaldo() + valor);
	}
	
}
