package exercicio3;

public interface ServicoRemoto {

	public void recuperarConta(String numContaRecebido, int senha);
	public boolean persistirConta(int valor, int operacao);
}
