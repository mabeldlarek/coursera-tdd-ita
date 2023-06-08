package exercicio4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
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
			listaPontuacoes.add(mockArmazenamento.obterPontosDoUsuarioPorTipo(t.getValor(), nomeUsuario));
		}
		
		String json = gson.toJson(montarArrayJsonPontosUsuario(listaPontuacoes));
		return json;
	}
	

	public String obterRankingOrdenado(int tipoPonto){
		Gson gson = new Gson();
		List<JsonObject> jsonList = montarListaRanking(tipoPonto);
		ordenarArray(jsonList);

		String json = gson.toJson(jsonList);
		return json;
	}
	
	private JsonArray montarArrayJsonPontosUsuario(List<Map<Integer, Integer>> listaPontuacoes){
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
		
		return jsonArray;
	}
	
	private List<JsonObject> montarListaRanking(int tipoPonto) {
		List<JsonObject> jsonList = new ArrayList<>();
		for(String usuarioNome : mockArmazenamento.getUsuarios()) {
		Map<Integer, Integer> mapPontuacao = mockArmazenamento.obterPontosDoUsuarioPorTipo(tipoPonto, usuarioNome);
		 for (Map.Entry<Integer, Integer> entry : mapPontuacao.entrySet()) {
		    	JsonObject objetoPontuacao = new JsonObject();
		    	objetoPontuacao.addProperty("tipoPonto", entry.getKey());
		    	objetoPontuacao.addProperty("qtdPontos", entry.getValue());
		    	objetoPontuacao.addProperty("usuario", usuarioNome);
		        jsonList.add(objetoPontuacao);
		 	}
		}
		
		return jsonList;
	}
	
	private void ordenarArray(List<JsonObject> jsonList) {
		Collections.sort(jsonList, new Comparator<JsonObject>() {
            @Override
            public int compare(JsonObject obj1, JsonObject obj2) {
                int qtdPontos = obj1.getAsJsonObject().get("qtdPontos").getAsInt();
                int qtdPontos2 = obj2.getAsJsonObject().get("qtdPontos").getAsInt();
                return Integer.compare(qtdPontos, qtdPontos2);
            }
        });
	}
}


