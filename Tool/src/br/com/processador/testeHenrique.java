package br.com.processador;

public class testeHenrique {
    public static void main(String[] args){
        Navegador n = new Navegador();
        String repositorio = "C:\\Users\\Henrique\\Downloads\\Repositorio\\";
        for (String s : n.navegaPorDiretorios(repositorio)){
            System.out.println(s);
        }
    }
}
