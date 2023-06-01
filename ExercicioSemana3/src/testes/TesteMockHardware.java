package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exercicio3.MockHardware;
import exercicio3.MockServicoRemoto;
import exercicio3.CaixaEletronico;
import exercicio3.Cartao;
import exercicio3.ContaCorrente;

public class TesteMockHardware {
	
	private MockHardware mockHardware;
	private MockServicoRemoto mockServRemoto;
	private CaixaEletronico cxEle;
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
	public void lerCartao() {
		Cartao cartao = new Cartao("15023600");
		mockHardware.adicionarCartao(cartao);
		assertEquals(cartao.getNumCartao(), mockHardware.pegarNumeroDaContaCartao());
	}
	
	@Test
	public void entregarDinheiro() {
		cxEle.logar(1122);
		cxEle.depositar(100);
		cxEle.sacar(10);
		assertEquals(true, mockHardware.isReceptorDinheiroAberto());
	}
	
	@Test
	public void lerEnvelope() {
		cxEle.logar(1122);
		cxEle.depositar(100);
		assertEquals(true, mockHardware.isEnvelopeLido());
	}
	
	@Test(expected = RuntimeException.class)
	public void erroAoLerEnvelope() {
		cxEle.logar(1122);
		mockHardware.gerarErro();
		cxEle.depositar(100);
	}
	
	@Test(expected = RuntimeException.class)
	public void erroEntregarDinheiro() {
		cxEle.logar(1122);
		cxEle.logar(1122);
		cxEle.depositar(100);
		mockHardware.gerarErro();
		cxEle.sacar(10);
		cxEle.depositar(100);
	}
	
	@Test(expected = RuntimeException.class)
	public void erroAoLerCartao() {
		cxEle.logar(1122);
		Cartao cartao = new Cartao("15023600");
		mockHardware.adicionarCartao(cartao);
		mockHardware.gerarErro();
		mockHardware.pegarNumeroDaContaCartao();
		cxEle.depositar(100);
	}
	
}
