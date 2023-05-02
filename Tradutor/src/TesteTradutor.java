import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TesteTradutor {

	private Tradutor t;
	
	@Before
	public void criarTradutor() {
		t = new Tradutor();
	}
	
	
	@Test
	public void tradutorSemPalavras() {
		assertTrue(t.estaVazio());
	}
	
	@Test
	public void umaTraducao() {
		t.adicionaTraducao("bom", "good");
		assertFalse(t.estaVazio());
		assertEquals("good", t.traduzir("bom"));
	}
	
	@Test
	public void duasTraducoes() {
		t.adicionaTraducao("bom", "good");
		t.adicionaTraducao("mau", "bad");
		assertFalse(t.estaVazio());
		assertEquals("good", t.traduzir("bom"));
		assertEquals("bad", t.traduzir("mau"));
	}
	
	@Test
	public void duasTraducoesMesmaPalavra() {
		t.adicionaTraducao("bom", "good");
		t.adicionaTraducao("bom", "nice");
		assertFalse(t.estaVazio());
		assertEquals("good, nice", t.traduzir("bom"));
	}
	
	@Test
	public void traduzirFrase() {
		t.adicionaTraducao("guerra", "war");
		t.adicionaTraducao("é", "is");
		t.adicionaTraducao("ruim", "bad");
		assertEquals("war is bad", t.traduzirFrase("guerra é ruim"));
	}
	
	@Test
	public void traduzirFraseComDuasTraducoesMesmaPalavras() {
		t.adicionaTraducao("paz", "peace");
		t.adicionaTraducao("é", "is");
		t.adicionaTraducao("bom", "good");
		t.adicionaTraducao("bom", "nice");
		assertEquals("peace is good", t.traduzirFrase("paz é bom"));
	}
}
