package exercicio3;
import java.util.ArrayList;
import java.util.List;

public class MockServicoRemoto implements ServicoRemoto {

	private List<ContaCorrente> listaContas = new ArrayList<>();
	private ContaCorrente contaRecuperada;
	private boolean erroGerado = false;

	public void adicionarConta(ContaCorrente contaUsuario) {
		listaContas.add(contaUsuario);
	}

	@Override
	public void recuperarConta(String numContaRecebido, int senha) {
		if(erroGerado)
			throw new RuntimeException("Problema simulado pelo mock ao recuperar conta");
		for(ContaCorrente c : listaContas) {
			if(c.getNumConta().equals(numContaRecebido) &&
					c.getSenha() == senha)
				contaRecuperada = c;
		}
	}

	public ContaCorrente getContaRecuperada() {
		return contaRecuperada;
	}

	@Override
	public boolean persistirConta(int valorRecebido, int operacao) {
		boolean resultado = false;
		if(operacao == 1) {
			contaRecuperada.setSaldo(contaRecuperada.getSaldo() + valorRecebido);
			resultado = true;
		}
		if(operacao == 2) {
			if(valorRecebido < contaRecuperada.getSaldo()) {
				contaRecuperada.setSaldo(contaRecuperada.getSaldo() - valorRecebido);
				resultado = true;
			}
		}
		
		return resultado;
	}
	
	public void gerarErro() {
		erroGerado = true;
	}
	

}
