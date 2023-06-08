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
	
	/***
	 * Retornar ranking de um tipo de ponto, com a lista de usuário que possuem aquele ponto ordenados
	 *  do que possui mais para o que possui menos. Por exemplo: ao pedir o ranking de "estrela",
	 *   seria retornado "guerra" com "25", "fernandes" com "19" e "rodrigo" com "17". Um usuário
	 *    que não possui pontos daquele tipo não seria incluído no ranking. Por exemplo, o usuário
	 *     "toco" sem pontos do tipo "estrela" não seria incluído. 
	 * @return
	 */
	public String obterRankingOrdenado(int tipoPonto){
		Gson gson = new Gson();
		JsonArray jsonArray = new JsonArray();
		List<JsonObject> jsonList = new ArrayList<>();
		for(String usuarioNome : mockArmazenamento.getUsuarios()) {
			Map<Integer, Integer> mapPontuacao = mockArmazenamento.obterPontosPorTipo(tipoPonto, usuarioNome);
			 for (Map.Entry<Integer, Integer> entry : mapPontuacao.entrySet()) {
			    	JsonObject objetoPontuacao = new JsonObject();
			    	objetoPontuacao.addProperty("tipoPonto", entry.getKey());
			    	objetoPontuacao.addProperty("qtdPontos", entry.getValue());
			    	objetoPontuacao.addProperty("usuario", usuarioNome);
			        jsonArray.add(objetoPontuacao);    
			        jsonList.add(objetoPontuacao);
			}
		}
		
		Collections.sort(jsonList, new Comparator<JsonObject>() {
            @Override
            public int compare(JsonObject obj1, JsonObject obj2) {
                int qtdPontos = obj1.getAsJsonObject().get("qtdPontos").getAsInt();
                int qtdPontos2 = obj2.getAsJsonObject().get("qtdPontos").getAsInt();
                return Integer.compare(qtdPontos, qtdPontos2);
            }
        });

		
		String json = gson.toJson(jsonList);
		return json;
	}
}


