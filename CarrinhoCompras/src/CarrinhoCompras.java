import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras {
	private List<Produto> produtos = new ArrayList<>();
	private List<ObservadorCarrinho> observadores = new ArrayList<>();
	
	void adicionaProduto(Produto p) {
		produtos.add(p);
		for(ObservadorCarrinho obs : observadores )
			try {
			obs.produtoAdicionado(p.getNome(), p.getValor());
			} catch(Exception e) {
				
			}
	}
	
	public int total() {
		int total =0;
		for(Produto p : produtos) {
			total += p.getValor();
		}
		return total;
	}

	
	public void adicionarObservador(ObservadorCarrinho observador) {
		observadores.add(observador);
	}

}
