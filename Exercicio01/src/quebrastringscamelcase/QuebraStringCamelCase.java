package quebrastringscamelcase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QuebraStringCamelCase {

	public boolean stringEstaVazia;
	public static int tamanhoLista;
	private static String stringOriginal;

	private static List<String> palavras;
	
	public QuebraStringCamelCase() {
		super();
		this.palavras = new ArrayList<>();
		this.stringOriginal = "";
		this.stringEstaVazia = true;
	}


	public void lerString(String string) {
		if(string!="") {
			stringOriginal = string;
			stringEstaVazia = false;
		}
	}

	public void adicionaPalavraNaLista() {
		if(!stringEstaVazia) {
			tamanhoLista++;
		}
	}
	
	public static int getTamanhoLista() {
		return palavras.size();
	}

	public void validarString() {
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(stringOriginal);
		boolean contemCaracterEspecial = m.find();  
		if(contemCaracterEspecial)
			throw new StringPossuiCaracterEspecialException("String possui caracter(es) especial (is).");

		if(stringOriginal.substring(0,1).matches("[0-9]"));
			throw new StringIniciaComNumeroException("String inicia com número");
	}

	public static void separarString(String palavra, int indInicialCaracter) {
		for (int i = 1; i <= palavra.length(); i++) {
				if(adicionarPalavraSeparada(palavra, indInicialCaracter, i)) {
					indInicialCaracter = i - 1;  
					if(i + 1 < palavra.length()) {
						if(adicionarPalavraInteiraMaiuscula(palavra, indInicialCaracter, i))
							break;
					}
				}
		}
	}
	
	public static boolean adicionarPalavraInteiraMaiuscula(String palavraAlterada, int indInicial, int indAtual) {
		String proxCaracter = palavraAlterada.substring(indInicial+1, indAtual+1);
		int indFinal = palavraAlterada.length();
		if(proxCaracter.equals(".")) {
			String palavraInteiraMaiuscula = stringOriginal.substring(indInicial, indFinal);
			palavras.add( palavraInteiraMaiuscula);
			return true;
		}
		return false;
	}
	
	public static boolean adicionarPalavraSeparada(String palavraAlterada, int indInicial, int indFinal) {
		String caracterAtual = palavraAlterada.substring(indFinal-1, indFinal);
		if(indFinal == stringOriginal.length())
			palavras.add(stringOriginal.substring(indInicial, indFinal));
		if(caracterAtual.equals(".") || caracterAtual.matches("!")) {
			   palavras.add(stringOriginal.substring(indInicial, indFinal-1));
			   return true;
		}
		return false;
	}


	public static String converterInicialMaiuscula(String palavra) {
		if(!palavra.substring(palavra.length() -1, palavra.length()).matches("[A-Z]"))
			palavra = palavra.toLowerCase();
		return palavra;
	}


	public static List<String> converterCamelCase(String string) {
		setStringOriginal(string);
		int indInicialCaracter = 0;
		string = stringOriginal.replaceAll("[A-Z]", ".");
		string = string.replaceFirst("[0-9]", "!");
		separarString(string, indInicialCaracter);
		for(int i=0; i < getPalavras().size(); i++ ) {
			getPalavras().set(i, converterInicialMaiuscula(getPalavras().get(i)));
		}
		
		return palavras;
	}
	

	public static List<String> getPalavras() {
		return palavras;
	}

	
	public static String getStringOriginal() {
		return stringOriginal;
	}


	public static void setStringOriginal(String stringOriginal) {
		QuebraStringCamelCase.stringOriginal = stringOriginal;
	}

}
