package quebrastringscamelcase;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class QuebraStringCamelCaseTeste {
	
	private QuebraStringCamelCase qs;
	
	@Before
	public void inicializQuebraString() {
			qs = new QuebraStringCamelCase();
	}

	@Test
	public void stringVazia() {
		assertTrue(qs.stringEstaVazia);
		assertEquals(0, qs.tamanhoLista);
		;
	}

	@Test
	public void informaString() {
		qs.lerString("aprendendoTDD");
		qs.adicionaPalavraNaLista();
		assertFalse(qs.stringEstaVazia);
		assertEquals(1, qs.tamanhoLista);
		;
	}
	
	@Test(expected = StringIniciaComNumeroException.class)
	public void stringIniciaComNumero() {
		qs.lerString("10aprendendoTDD");
		qs.validarString();
	}
	
	@Test(expected = StringPossuiCaracterEspecialException.class)
	public void stringPossuiCaracterEspecial() {
		qs.lerString("aprendendo#TDD");
		qs.validarString();
	}
	
	@Test
	public void adicionaStringMaiuscula() {
		qs.lerString("aprendendoTDD");
		assertTrue(qs.adicionarPalavraInteiraMaiuscula("aprendendo...", 9, 10));
		assertEquals(1, qs.getTamanhoLista());
		;
	}
	
	
	@Test
	public void adicionaStringNormal() {
		qs.lerString("aprendendoTDD");
		assertTrue(qs.adicionarPalavraSeparada("aprendendo...", 0, 11));
		assertEquals(1, qs.getTamanhoLista());
		;
	}
	
	@Test
	public void alteraInicialMaiuscula() {
		qs.lerString("aprendendoComTDD");
		assertTrue(qs.adicionarPalavraSeparada("aprendendo.om...", 10, 14));
		assertEquals("com", qs.converterInicialMaiuscula(qs.getPalavras().get(0)));
	}
	
	@Test
	public void converterCamelCase() {
		List<String> resultado = qs.converterCamelCase("aprendendoComTDD");
		assertEquals(3, resultado.size());
		assertEquals("aprendendo", resultado.get(0));
		assertEquals("com", resultado.get(1));
		assertEquals("TDD", resultado.get(2));
	}
	
	@Test
	public void converterCamelCaseNumero() {
		List<String> resultado = qs.converterCamelCase("aprendendo10ComTDD");
		assertEquals(4, resultado.size());
		assertEquals("aprendendo", resultado.get(0));
		assertEquals("10",  resultado.get(1));
		assertEquals("com", resultado.get(2));
		assertEquals("TDD", resultado.get(3));
	}
}
