package exercicio4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Placar{
	private Pontuacao pontuacao;
	private Usuario usuario;
	private String dadosRegistrado;
	private MockArmazenamento mockArmazenamento;
	
	public Placar() {
		
	}
	
	public Placar(Pontuacao pontuacao, Usuario usuario) {
		this.pontuacao = pontuacao;
		this.usuario = usuario;
	}
	
	public void adicionarMockArmazenamento(MockArmazenamento mockArmazenamento) {
		this.mockArmazenamento = mockArmazenamento;
	}
	
	public void registrarPlacar(Pontuacao pontuacao, Usuario usuario) {
		Placar placar = new Placar(pontuacao, usuario);
		setDadoRegistrado(placar);
	}
	
	private void setDadoRegistrado(Placar placar) {
		Gson gson = new Gson();
		this.dadosRegistrado = gson.toJson(placar);
	}

	public String getDadoRegistrado() {
		return this.dadosRegistrado;
	}

	public Pontuacao getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Pontuacao pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String obterPontosUsuario(String nomeUsuario) {
		Gson gson = new Gson();
		List<Map<Integer, Integer>> listaPontuacoes = new ArrayList<>();
		for(TipoPontuacao t : TipoPontuacao.values()) {
			listaPontuacoes.add(mockArmazenamento.obterPontosPorTipo(t.getValor(), nomeUsuario));
		}
		
		JsonArray jsonArray = new JsonArray();
		for (Map<Integer, Integer> pontuacao : listaPontuacoes) {
		    Map<String, Integer> jsonPontuacao = new HashMap<>();
		    for (Map.Entry<Integer, Integer> entry : pontuacao.entrySet()) {
		    	JsonObject objetoPontuacao = new JsonObject();
		    	objetoPontuacao.addProperty("tipoPonto", entry.getKey());
		    	objetoPontuacao.addProperty("qtdPontos", entry.getValue());
		        jsonArray.add(objetoPontuacao);
		    }
		}

		String json = gson.toJson(jsonArray);
		return json;
	}
	
}


