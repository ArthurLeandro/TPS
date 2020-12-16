import java.io.*;
import java.time.*;
import java.util.*;

class Jogador {

      private int id, peso, altura;
      private String nome, universidade, anoNascimento, cidadeNascimento, estadoNascimento;

      public Jogador(int id, int peso, int altura, String nome, String universidade, String anoNascimento,
                  String cidadeNascimento, String estadoNascimento) {

            this.id = id;
            this.peso = peso;
            this.altura = altura;
            this.nome = nome;
            this.universidade = universidade;
            this.anoNascimento = anoNascimento;
            this.cidadeNascimento = cidadeNascimento;
            this.estadoNascimento = estadoNascimento;
      }

      public Jogador() {

            this.id = 0;
            this.peso = 0;
            this.altura = 0;
            this.nome = "";
            this.universidade = "";
            this.anoNascimento = "";
            this.cidadeNascimento = "";
            this.estadoNascimento = "";

      }

      public Jogador(String[] str) {

            setId(Integer.parseInt(str[0]));
            setNome(str[1]);
            setAltura(Integer.parseInt(str[2]));
            setPeso(Integer.parseInt(str[3]));
            setUniversidade(str[4]);
            setAnoNascimento(str[5]);
            setCidadeNascimento(str[6]);
            setEstadoNascimento(str[7]);
            JogadorNaoNulo();
      }

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      public int getPeso() {
            return peso;
      }

      public void setPeso(int peso) {
            this.peso = peso;
      }

      public int getAltura() {
            return altura;
      }

      public void setAltura(int altura) {
            this.altura = altura;
      }

      public String getNome() {
            return nome;
      }

      public void setNome(String nome) {
            this.nome = nome;
      }

      public String getUniversidade() {
            return universidade;
      }

      public void setUniversidade(String universidade) {
            this.universidade = universidade;
      }

      public String getAnoNascimento() {
            return anoNascimento;
      }

      public void setAnoNascimento(String anoNascimento) {
            this.anoNascimento = anoNascimento;
      }

      public String getCidadeNascimento() {
            return cidadeNascimento;
      }

      public void setCidadeNascimento(String cidadeNascimento) {
            this.cidadeNascimento = cidadeNascimento;
      }

      public String getEstadoNascimento() {
            return estadoNascimento;
      }

      public void setEstadoNascimento(String estadoNascimento) {
            this.estadoNascimento = estadoNascimento;
      }

      public Jogador Clone(Jogador j) {
            return new Jogador(j.id, j.peso, j.altura, j.nome, j.universidade, j.anoNascimento, j.cidadeNascimento,
                        j.estadoNascimento);
      }

      public void JogadorNaoNulo() {
            if (getNome() == null || getNome().equals("")) {
                  setNome("nao informado");
            }
            if (getUniversidade() == null || getUniversidade().equals("")) {
                  setUniversidade("nao informado");
            }
            if (getAnoNascimento() == null || getAnoNascimento().equals("")) {
                  setAnoNascimento("nao informado");
            }
            if (getCidadeNascimento() == null || getCidadeNascimento().equals("")) {
                  setCidadeNascimento("nao informado");
            }
            if (getEstadoNascimento() == null || getEstadoNascimento().equals("")) {
                  setEstadoNascimento("nao informado");
            }

      }
}
class No {
      public Jogador elemento;
      public No esq, dir;

      public No(Jogador elemento) {
            this(elemento, null, null);
      }

      public No(Jogador elemento, No esq, No dir) {
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
      }
}

class ArvoreBinaria {

      public No raiz;

      public ArvoreBinaria() {
            raiz = null;
      }

      public boolean pesquisar(String x, boolean print) {
            if (print) {
                  MyIO.print(" raiz");
                  return pesquisarPrint(x, raiz);
            } else {
                  return pesquisar(x, raiz);
            }
      }

      private boolean pesquisar(String x, No i) {
            boolean resp;
            if (i == null) {
                  resp = false;

            } else if (x.compareTo(i.elemento.getNome()) == 0) {
                  resp = true;

            } else if (x.compareTo(i.elemento.getNome()) < 0) {
                  resp = pesquisar(x, i.esq);

            } else {
                  resp = pesquisar(x, i.dir);
            }
            return resp;
      }

      private boolean pesquisarPrint(String x, No i) {
            boolean resp;
            if (i == null) {
                  resp = false;

            } else if (x.compareTo(i.elemento.getNome()) == 0) {
                  resp = true;

            } else if (x.compareTo(i.elemento.getNome()) < 0) {
                  MyIO.print(" esq");
                  resp = pesquisarPrint(x, i.esq);

            } else {
                  MyIO.print(" dir");
                  resp = pesquisarPrint(x, i.dir);
            }
            return resp;
      }

      public void inserir(Jogador j) {
            raiz = inserir(j, raiz);
      }

      private No inserir(Jogador j, No i) {
            if (i == null) {
                  i = new No(j);

            } else if (j.getNome().compareTo(i.elemento.getNome()) < 0) {
                  i.esq = inserir(j, i.esq);

            } else if (j.getNome().compareTo(i.elemento.getNome()) > 0) {
                  i.dir = inserir(j, i.dir);

            } else {
                  MyIO.println("Erro ao inserir!");
            }
            return i;
      }

}
class HashDireta {
   Jogador tabela[];
   int m1, m2, m, reserva;

   public HashDireta (){
      this(11, 0);
   }

   public HashDireta (int m1, int m2){
      this.m1 = m1;
      this.m2 =  m2;
      this.m = m1 + m2;
      this.tabela = new Jogador [this.m];
      for(int i = 0; i < m; i++){
         tabela[i] = null;
      }
      reserva  = 0;
   }

   public int h(int altura){
      return altura % m1;
   }

   public boolean inserir (Jogador elemento){
      boolean resp = false;

      if(elemento != null){

         int pos = h(elemento.getAltura());

         if(tabela[pos] == null){
            tabela[pos] = elemento;
            resp = true;
         } 
      }

      return resp;
   }

   public boolean pesquisar (String jogador){
      int pos = -1;
      for(int i = 0; i < m; i++){
            if(tabela[i] != null && tabela[i].getNome().compareTo(jogador) == 0){
                 pos = i;
                 i = m;
            }
       }
       return pos != -1;
   }
}
class HashRehash {
   Jogador tabela[];
   int m;

   public HashRehash(){
      this(9);
   }

   public HashRehash(int m){ //inicializa todos cmo nulo
      this.m = m;
      this.tabela = new Jogador[this.m];
      for(int i = 0; i < m; i++){
         tabela[i] = null;
      }
   }

   public int h(int altura){
      return altura % m;
   }

   public int reh(int altura){
      return ++altura % m;
   }

public boolean inserir (Jogador elemento){
      boolean resp = false;

      if(elemento != null){
         int pos = h(elemento.getAltura());

         if(tabela[pos] == null){
            tabela[pos] = elemento;
            resp = true;

         } else{

            pos = reh(elemento.getAltura());

            if(tabela[pos] == null){
               tabela[pos] = elemento;
               resp = true;
            }
         }
      }
      return resp;
}

public boolean pesquisar (String jogador){
      int pos = -1;
      for(int i = 1; i < m; i++){
            if(tabela[i] != null && tabela[i].getNome().compareTo(jogador) == 0){
                 pos = i;
                 i = m;
            }
      }
      return pos != -1;
}

}
class Celula {
      public Jogador elemento;
      public Celula prox;

      public Celula() {
            this(null);
      }

      public Celula(Jogador elemento) {
            this.elemento = elemento;
            this.prox = null;
      }
}
class Lista {

      public Celula primeiro;
      public Celula ultimo;

      public Lista() {
            primeiro = new Celula();
            ultimo = primeiro;
      }

      public int tamanho() {
            int tamanho = 0;
            for (Celula i = primeiro; i != ultimo; i = i.prox, tamanho++)
                  ;
            return tamanho;
      }

      public void inserirFim(Jogador j) {
            ultimo.prox = new Celula(j);
            ultimo = ultimo.prox;
      }
      public boolean pesquisar(String j) {
		boolean resp = false;
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
                  if(i.elemento.getNome().equals(j)){
                        resp = true;
                        i = ultimo;
                  }
	      }
		return resp;
      }

      public void mostrar() {
		for (Celula i = primeiro.prox; i != null; i = i.prox) {
			System.out.println(i.elemento + " ");
		}
	}

}

class Doidona {
   final int TAMT1 = 100;
   final int TAMT3 = 100;
   final int NULO = -0x7FFFFF;

   HashDireta t1;
   HashRehash t2;

   ArvoreBinaria t4;
   Lista t3;

   public Doidona(){
     
      t1 = new HashDireta();
      t2 = new HashRehash();
      t4 = new ArvoreBinaria();
      t3 = new Lista();
   }

   public void inserir(Jogador elemento){
      boolean controlador = t1.inserir(elemento);

      if(!controlador){
        int altura = elemento.getAltura() % 3;

         if(altura == 0){
          t2.inserir(elemento);
         }else if(altura == 1){
           t3.inserirFim(elemento);
         }else if(altura == 2){
            t4.inserir(elemento);
         }
      }
   }
  
   boolean pesquisar (String elemento){
      return t1.pesquisar(elemento) || t2.pesquisar(elemento) || t3.pesquisar(elemento) || t4.pesquisar(elemento, true);
   }

}
public class Doidao {

      public static String[] LerJogador(String in) {

            String auxIn = "";

            try {
                  Scanner ler = new Scanner(new File("/tmp/players.csv"));
                  int linha = Integer.parseInt(in);
                  for (int i = 0; i <= Integer.parseInt(in) + 1; i++) {
                        auxIn = ler.nextLine();
                  }
                  ler.close();
            } catch (Exception e) {

            }

            String[] enter = auxIn.split(",");

            if (enter.length < 8) {

                  String[] auxEnter = new String[8];
                  String NULO = "nao informado";
                  for (int i = 0; i < enter.length; i++) {
                        if (!(enter[i].equals("")))
                              auxEnter[i] = enter[i];
                        else {
                              auxEnter[i] = NULO;
                        }
                  }
                  if (enter.length < 8) {
                        for (int i = enter.length; i < auxEnter.length; i++) {
                              auxEnter[i] = NULO;
                        }
                  }
                  enter = auxEnter;
            }

            return enter;
      }

      public static void main(String[] args) throws Exception {
            Doidona tabela = new Doidona();
            String in = MyIO.readLine();

            while (in.charAt(0) != 'F' || in.charAt(1) != 'I' || in.charAt(2) != 'M') {
                  Jogador j = new Jogador(LerJogador(in));
                  tabela.inserir(j);
                  in = MyIO.readLine();
            }

            in = MyIO.readLine();

            while (in.charAt(0) != 'F' || in.charAt(1) != 'I' || in.charAt(2) != 'M') {
                  MyIO.print(in);
                  if(tabela.pesquisar(in)){
                        MyIO.println(" SIM");
                  } else {
                        MyIO.println(" NAO");
                  }
                  in = MyIO.readLine();
            }

      }

}