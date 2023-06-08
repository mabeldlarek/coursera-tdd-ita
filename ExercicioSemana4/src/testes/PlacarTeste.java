package testes;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import exercicio4.MockArmazenamento;
import exercicio4.Placar;
import exercicio4.Pontuacao;
import exercicio4.Usuario;

public class PlacarTeste {

	@Test
	public void registrarPonto() {
		Usuario usuario = new Usuario("João");
		Pontuacao pontuacao = new Pontuacao(1,5);
		Placar placar = new Placar();
		placar.registrarPlacar(pontuacao, usuario);
		assertEquals(placar.getDadoRegistrado(), "{\"pontuacao\":{\"tipoPonto\":1,\"qtdPontos\":5},\"usuario\":{\"nome\":\"João\"}}");
	}
	
	@Test
	public void registrarPontoEmArquivo() {
		Usuario usuario = new Usuario("Maria");
		Pontuacao pontuacao = new Pontuacao(1,5);
		Placar placar = new Placar();
		placar.registrarPlacar(pontuacao, usuario);
		MockArmazenamento mockArmazenamento = new MockArmazenamento();
		mockArmazenamento.armazenarPlacar(placar.getDadoRegistrado());
		assertEquals("Marcos", mockArmazenamento.recuperarPlacar().get(0).getUsuario().getNome());
	}
	
	@Test
	public void retornarPontosUsuario() {
		Placar placar = new Placar();
		MockArmazenamento mockArmazenamento = new MockArmazenamento();
		placar.adicionarMockArmazenamento(mockArmazenamento);
		Usuario usuario = new Usuario("Marcos");
		Pontuacao pontuacao = new Pontuacao(1,5);
		
		placar.registrarPlacar(pontuacao, usuario);
		mockArmazenamento.armazenarPlacar(placar.getDadoRegistrado());
		
		Pontuacao pontuacao2 = new Pontuacao(2,6);
		placar.registrarPlacar(pontuacao2, usuario);
		mockArmazenamento.armazenarPlacar(placar.getDadoRegistrado());
		
		registrarInfoTeste("{\"numTeste\":" + (obterInfoTeste() + 1) + "}");
		int numTeste = obterInfoTeste();
		
		//"[{\"tipoPonto\":1,\"qtdPontos\":" + (numTeste * 5) + "},{\"tipoPonto\":2,\"qtdPontos\":" + (numTeste * 6 )+ "}]"
		//"[{\"qtdPontos\":" + (numTeste * 5) + "\"tipoPonto\":1},{\"qtdPontos\":" + (numTeste * 6 ) + "\"tipoPonto\":2}"
		assertEquals("[{\"tipoPonto\":1,\"qtdPontos\":" + (numTeste * 5) + "},{\"tipoPonto\":2,\"qtdPontos\":" + (numTeste * 6 )+ "}]", placar.obterPontosUsuario("Marcos"));
	}
	
	/*@Test
	public void retornarPontosUsuario() {
		Placar placar = new Placar();
		MockArmazenamento mockArmazenamento = new MockArmazenamento();
		
		Usuario usuario = new Usuario("Marcos");
		Pontuacao pontuacao = new Pontuacao(1,5);
		
		placar.registrarPlacar(pontuacao, usuario);
		mockArmazenamento.armazenarPlacar(placar.getDadoRegistrado());
		
		Pontuacao pontuacao2 = new Pontuacao(2,6);
		placar.registrarPlacar(pontuacao2, usuario);
		mockArmazenamento.armazenarPlacar(placar.getDadoRegistrado());
		
		registrarInfoTeste("{\"numTeste\":" + (obterInfoTeste() + 1) + "}");
		int numTeste = obterInfoTeste();
		
		assertEquals("[{\"tipoPonto\":1,\"qtdPontos\":" + (numTeste * 5) + "},{\"tipoPonto\":2,\"qtdPontos\":" + (numTeste * 6 )+ "}]", mockArmazenamento.obterPontosUsuario("Marcos"));
	}*/
	
	private void registrarInfoTeste(String infoTeste) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("infoTeste.txt"));
			bw.write(infoTeste);
			bw.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int obterInfoTeste() {
		Gson gson = new Gson();
		int numTeste = 0;
		
		try {
			String s = "";
			BufferedReader reader = new BufferedReader(new FileReader("infoTeste.txt"));
            String linha = reader.readLine();
            JsonObject jsonObject = gson.fromJson(linha, JsonObject.class);
            numTeste = jsonObject.get("numTeste").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return numTeste;
	}
	
}
