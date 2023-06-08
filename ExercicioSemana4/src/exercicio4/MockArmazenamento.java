package exercicio4;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MockArmazenamento {
	
	List<Placar> placaresRecuperados = new ArrayList<>();

	public void armazenarPlacar(String dadoRecebido) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("placares.txt", true));
			bw.write(dadoRecebido);
			bw.newLine();
			bw.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Placar> recuperarPlacar() {
		List<Placar> placaresRecuperados = new ArrayList<>();
		Gson gson = new Gson();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("placares.txt"));
            String linha;
            while ((linha = reader.readLine()) != null) {
            	Placar placar = gson.fromJson(linha, Placar.class);
                placaresRecuperados.add(placar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return placaresRecuperados;
	}

	
	public List<Usuario> recuperarUsuario() {
		
		Gson gson = new Gson();
		List<Usuario> usuariosRecuperados = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("placares.txt"));
            String linha;
            while ((linha = reader.readLine()) != null) {
            	Usuario usuario = gson.fromJson(linha, Usuario.class);
            	usuariosRecuperados.add(usuario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return usuariosRecuperados;
	}
	
	public Map<Integer, Integer> obterPontosDoUsuarioPorTipo(int tipoPonto, String nomeUsuario) {
		placaresRecuperados = recuperarPlacar();
		Map<Integer, Integer> mapPontosDoUsuario = new HashMap<>();
		for(Placar p : placaresRecuperados) {
			if(p.getUsuario().getNome().equals(nomeUsuario) && p.getPontuacao().getTipoPonto() == tipoPonto) {
				if(mapPontosDoUsuario.containsKey(p.getPontuacao().getTipoPonto())) {
					int novoValor = mapPontosDoUsuario.get(p.getPontuacao().getTipoPonto()).intValue() + p.getPontuacao().getQtdPontos();
					mapPontosDoUsuario.put(p.getPontuacao().getTipoPonto(), novoValor);
				} else
					mapPontosDoUsuario.put(p.getPontuacao().getTipoPonto(), p.getPontuacao().getQtdPontos());
			}
		}
		
		return mapPontosDoUsuario;			
	}

	public Set<String> getUsuarios() {
		List<Placar> placaresRecuperados = new ArrayList<>();
		placaresRecuperados = recuperarPlacar();
		Set<String> usuarios = new HashSet<>();
		for(Placar p : placaresRecuperados) {
			usuarios.add(p.getUsuario().getNome());
		}
		return usuarios;
	}

	public String getTipoPontosPorUsuario(String nomeUsuario) {
		placaresRecuperados = recuperarPlacar();
		Set<Integer> setListTipoPontos = new HashSet<>();
		
		for(Placar p : placaresRecuperados) {
			if(p.getUsuario().getNome().equals(nomeUsuario)) {
				setListTipoPontos.add(p.getPontuacao().getTipoPonto());
			}
		}

		String json = converterDadoEmJson(setListTipoPontos);
		return json;
	}
	
	private String converterDadoEmJson(Set<Integer> tipoPontos) {
		Gson gson = new Gson();
		JsonArray jsonArray = new JsonArray();
		for (Integer tipoPonto : tipoPontos) {
		    JsonObject objetoPontuacao = new JsonObject();
		    objetoPontuacao.addProperty("tipoPonto", tipoPonto);
		    jsonArray.add(objetoPontuacao);
		 }
		
		return gson.toJson(jsonArray);
	}
}
