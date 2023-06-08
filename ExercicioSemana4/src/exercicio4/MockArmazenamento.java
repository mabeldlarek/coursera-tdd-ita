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
		Gson gson = new Gson();
		List<Placar> placaresRecuperados = new ArrayList<>();
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
	
	public Map<Integer, Integer> obterPontosPorTipo(int tipoPonto, String nomeUsuario) {
		List<Placar> recuperados = recuperarPlacar();
		Map<Integer, Integer> mapaPontoTipoUsuario = new HashMap<>();
		for(Placar p : recuperados) {
			if(p.getUsuario().getNome().equals(nomeUsuario) && p.getPontuacao().getTipoPonto() == tipoPonto) {
				if(mapaPontoTipoUsuario.containsKey(p.getPontuacao().getTipoPonto())) {
					int novoValor = mapaPontoTipoUsuario.get(p.getPontuacao().getTipoPonto()).intValue() + p.getPontuacao().getQtdPontos();
					mapaPontoTipoUsuario.put(p.getPontuacao().getTipoPonto(), novoValor);
				} else
					mapaPontoTipoUsuario.put(p.getPontuacao().getTipoPonto(), p.getPontuacao().getQtdPontos());
			}
		}
		
		return mapaPontoTipoUsuario;			
	}
	
	public Set<String> getUsuarios() {
		List<Placar> recuperados = recuperarPlacar();
		Set<String> usuarios = new HashSet<>();
		for(Placar p : recuperados) {
			usuarios.add(p.getUsuario().getNome());
		}
		return usuarios;
	}

	public String getTipoPontoUsuario(String nomeUsuario) {
		List<Placar> recuperados = recuperarPlacar();
		Set<Integer> tipoPontos = new HashSet<>();
		
		Gson gson = new Gson();
		for(Placar p : recuperados) {
			if(p.getUsuario().getNome().equals(nomeUsuario)) {
				tipoPontos.add(p.getPontuacao().getTipoPonto());
			}
		}
		
		JsonArray jsonArray = new JsonArray();
		for (Integer tipoPonto : tipoPontos) {
		    JsonObject objetoPontuacao = new JsonObject();
		    objetoPontuacao.addProperty("tipoPonto", tipoPonto);
		    jsonArray.add(objetoPontuacao);
		 }
		
		String json = gson.toJson(jsonArray);
		return json;
	}
	
}
