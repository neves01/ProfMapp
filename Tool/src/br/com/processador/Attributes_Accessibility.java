package br.com.processador;

import java.util.List;

import com.github.javaparser.ast.CompilationUnit;

public class Attributes_Accessibility {

	public void mapearAccessibilityAttributes(Arquivos arqs, List<App> apps_map, App a, String listaDeAttAcc)
			throws Exception {

		Analisador analisador = new Analisador();
		String retorno = "";
		CompilationUnit cu = null;
		
		for (String caminhoClasse : arqs.getArqsJavaSRC()) {
			cu = analisador.mapearClasse(caminhoClasse);
			retorno = "";
			if (cu != null) {
				retorno = analisador.visitarConteudoAccessibility(analisador.mapearClasse(caminhoClasse), listaDeAttAcc,
						a);
			}
		}
	}
}
