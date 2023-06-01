package testes;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exercicio3.CaixaEletronico;
import exercicio3.Cartao;
import exercicio3.ContaCorrente;
import exercicio3.MockHardware;
import exercicio3.MockServicoRemoto;

public class TesteCaixaEletronico {
	
	private CaixaEletronico cxEle;
	private MockHardware mockHardware;
	private MockServicoRemoto mockServRemoto;
	private ContaCorrente contaUsuario;
	
	@Before
	public void inicializaComponentes() {
		mockHardware = new MockHardware();
		mockServRemoto = new MockServicoRemoto();
		cxEle = new CaixaEletronico();
		cxEle.adicionarMockHardware(mockHardware);
		cxEle.adicionarMockServRemoto(mockServRemoto);
		contaUsuario = new ContaCorrente("15023600", 1122);
		mockServRemoto.adicionarConta(contaUsuario);
		mockHardware.adicionarCartao(new Cartao("15023600"));
	}

	@Test
	public void logar() {
		assertEquals(cxEle.logar(1122), "Usuário Autenticado");
	}
	
	@Test
	public void depositar() {
		logar();
		assertEquals(cxEle.depositar(500), "Depósito recebido com sucesso");
	}
	
	@Test
	public void sacar() {
		logar();
		depositar();
		assertEquals(cxEle.sacar(100), "Retire seu dinheiro");
	}
	
	@Test
	public void obterSaldo() {
		logar();
		depositar();
		assertEquals(cxEle.saldo(), "O saldo é 500");
	}

}
