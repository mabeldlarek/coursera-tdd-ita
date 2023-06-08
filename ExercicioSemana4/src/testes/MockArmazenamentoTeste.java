package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import exercicio4.MockArmazenamento;

public class MockArmazenamentoTeste {
	
	
	@Test
	public void retornarUsuarioComPontos() {
		MockArmazenamento mockArmazenamento = new MockArmazenamento();
		assertEquals(2, mockArmazenamento.getUsuarios().size());
	}
	
	@Test
	public void retornarTipoDePontoUsuario() {
		MockArmazenamento mockArmazenamento = new MockArmazenamento();
		assertEquals("[{\"tipoPonto\":1},{\"tipoPonto\":2}]", mockArmazenamento.getTipoPontosPorUsuario("Marcos"));
	}
	
	
}
