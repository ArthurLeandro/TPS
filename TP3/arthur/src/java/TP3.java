
// #region IMPORTS
import java.io.*;
import java.util.*;
import java.time.*;
// #endregion

// #region MAIN
public class TP3 {
  public static void main(String[] args) {
    // TODO implement main
  }
}
// #endregion

// #region UTILS
class Utilitary {

  public static Player CreateNewPlayerFromFile(String valueToRead) {
    Player valueToReturn = null;
    Scanner csvReader = new Scanner(new File("/tmp/players.csv"));
    int lineToread = Integer.parseInt(valueToRead);
    String auxWord = "";
    for (int i = 0; i < lineToread + 2; i++) {
      auxWord = csvReader.nextLine();
    }
    csvReader.close();
    String[] splitted = auxWord.split(",");
    splitted = FixEntry(splitted);
    valueToReturn = new Player(splitted);
    return valueToReturn;
  }

  public static boolean IsAble(String _word) {
    return _word.charAt(0) == ('F') && _word.charAt(1) == ('I') && _word.charAt(2) == ('M');
  }

  public static String[] FixEntry(String[] word) {
    if (word.length != 8) {
      String[] valueToReturn = new String[8];
      String NULO = "nao informado";
      for (int i = 0; i < word.length; i++) {
        if (!word[i].equals(""))
          valueToReturn[i] = word[i];
        else
          valueToReturn[i] = NULO;
      }
      if (word.length != 8) {
        for (int i = word.length; i < valueToReturn.length; i++) {
          valueToReturn[i] = NULO;
        }
      }
      return valueToReturn;
    } else {
      return word;
    }
  }

  public static void HandleExercise() {
    // TODO Handle Input from pub in
  }

  public static void HandleCommands() {
    // TODO Handle Input from pub in
  }

}
// #endregion

// #region DATA STRUCTURES

class Player {
  private int id, heigth, weigth;
  private String name, university, birthYear, birthCity, birthState;
  private final String NULO = "nao informado";

  // #region <----------- CTOR & GETTERS & SETTERS ------------------>
  public Player(int id, int heigth, int weigth, String name, String university, String birthYear, String birthCity,
      String birthState) {
    this.id = id;
    this.heigth = heigth;
    this.weigth = weigth;
    this.name = name;
    this.university = university;
    this.birthYear = birthYear;
    this.birthCity = birthCity;
    this.birthState = birthState;
  }

  public Player(String[] splitted) {
    setId(Integer.parseInt(splitted[0]));
    setName(splitted[1]);
    setHeigth(Integer.parseInt(splitted[2]));
    setWeigth(Integer.parseInt(splitted[3]));
    setUniversity(splitted[4]);
    setBirthYear(splitted[5]);
    setBirthCity(splitted[6]);
    setBirthState(splitted[7]);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getHeigth() {
    return heigth;
  }

  public void setHeigth(int heigth) {
    this.heigth = heigth;
  }

  public int getWeigth() {
    return weigth;
  }

  public void setWeigth(int weigth) {
    this.weigth = weigth;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = (!name.equals("")) ? name : NULO;
  }

  public String getUniversity() {
    return university;
  }

  public void setUniversity(String university) {
    this.university = (!university.equals("")) ? university : NULO;
  }

  public String getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(String birthYear) {
    this.birthYear = (!birthYear.equals("")) ? birthYear : NULO;
  }

  public String getBirthCity() {
    return birthCity;
  }

  public void setBirthCity(String birthCity) {
    this.birthCity = (!birthCity.equals("")) ? birthCity : NULO;
  }

  public String getBirthState() {
    return birthState;
  }

  public void setBirthState(String birthState) {
    this.birthState = (!birthState.equals("")) ? birthState : NULO;
  }
  // #endregion

  public void PrintPlayer() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(getId());
    stringBuilder.append(" ## ");
    stringBuilder.append(getName());
    stringBuilder.append(" ## ");
    stringBuilder.append(getHeigth());
    stringBuilder.append(" ## ");
    stringBuilder.append(getWeigth());
    stringBuilder.append(" ## ");
    stringBuilder.append(getBirthYear());
    stringBuilder.append(" ## ");
    stringBuilder.append(getUniversity());
    stringBuilder.append(" ## ");
    stringBuilder.append(getBirthCity());
    stringBuilder.append(" ## ");
    stringBuilder.append(getBirthState());
    stringBuilder.append("]");
    System.out.println(stringBuilder.toString());
  }

}

class Lista {
  private int[] array;
  private int n;

  /**
   * Construtor da classe.
   */
  public Lista() {
    this(6);
  }

  /**
   * Construtor da classe.
   * 
   * @param tamanho Tamanho da lista.
   */
  public Lista(int tamanho) {
    array = new int[tamanho];
    n = 0;
  }

  /**
   * Insere um elemento na primeira posicao da lista e move os demais elementos
   * para o fim da lista.
   * 
   * @param x int elemento a ser inserido.
   * @throws Exception Se a lista estiver cheia.
   */
  public void inserirInicio(int x) throws Exception {

    // validar insercao
    if (n >= array.length) {
      throw new Exception("Erro ao inserir!");
    }

    // levar elementos para o fim do array
    for (int i = n; i > 0; i--) {
      array[i] = array[i - 1];
    }

    array[0] = x;
    n++;
  }

  /**
   * Insere um elemento na ultima posicao da lista.
   * 
   * @param x int elemento a ser inserido.
   * @throws Exception Se a lista estiver cheia.
   */
  public void inserirFim(int x) throws Exception {

    // validar insercao
    if (n >= array.length) {
      throw new Exception("Erro ao inserir!");
    }

    array[n] = x;
    n++;
  }

  /**
   * Insere um elemento em uma posicao especifica e move os demais elementos para
   * o fim da lista.
   * 
   * @param x   int elemento a ser inserido.
   * @param pos Posicao de insercao.
   * @throws Exception Se a lista estiver cheia ou a posicao invalida.
   */
  public void inserir(int x, int pos) throws Exception {

    // validar insercao
    if (n >= array.length || pos < 0 || pos > n) {
      throw new Exception("Erro ao inserir!");
    }

    // levar elementos para o fim do array
    for (int i = n; i > pos; i--) {
      array[i] = array[i - 1];
    }

    array[pos] = x;
    n++;
  }

  /**
   * Remove um elemento da primeira posicao da lista e movimenta os demais
   * elementos para o inicio da mesma.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista estiver vazia.
   */
  public int removerInicio() throws Exception {

    // validar remocao
    if (n == 0) {
      throw new Exception("Erro ao remover!");
    }

    int resp = array[0];
    n--;

    for (int i = 0; i < n; i++) {
      array[i] = array[i + 1];
    }

    return resp;
  }

  /**
   * Remove um elemento da ultima posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista estiver vazia.
   */
  public int removerFim() throws Exception {

    // validar remocao
    if (n == 0) {
      throw new Exception("Erro ao remover!");
    }

    return array[--n];
  }

  /**
   * Remove um elemento de uma posicao especifica da lista e movimenta os demais
   * elementos para o inicio da mesma.
   * 
   * @param pos Posicao de remocao.
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
   */
  public int remover(int pos) throws Exception {

    // validar remocao
    if (n == 0 || pos < 0 || pos >= n) {
      throw new Exception("Erro ao remover!");
    }

    int resp = array[pos];
    n--;

    for (int i = pos; i < n; i++) {
      array[i] = array[i + 1];
    }

    return resp;
  }

  /**
   * Mostra os elementos da lista separados por espacos.
   */
  public void mostrar() {
    System.out.print("[ ");
    for (int i = 0; i < n; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println("]");
  }

  /**
   * Procura um elemento e retorna se ele existe.
   * 
   * @param x int elemento a ser pesquisado.
   * @return <code>true</code> se o array existir, <code>false</code> em caso
   *         contrario.
   */
  public boolean pesquisar(int x) {
    boolean retorno = false;
    for (int i = 0; i < n && retorno == false; i++) {
      retorno = (array[i] == x);
    }
    return retorno;
  }
}

class Pilha {
  //! NOT IMPLEMENTED CORRECTLY
  private int[] array;
  private int primeiro; // Remove do indice "primeiro".
  private int ultimo; // Insere no indice "ultimo".

  /**
   * Construtor da classe.
   */
  public Pilha() {
    this(6);
  }

  /**
   * Construtor da classe.
   * 
   * @param tamanho Tamanho da fila.
   */
  public Pilha(int tamanho) {
    array = new int[tamanho + 1];
    primeiro = ultimo = 0;
  }

  /**
   * Insere um elemento na ultima posicao da fila.
   * 
   * @param x int elemento a ser inserido.
   * @throws Exception Se a fila estiver cheia.
   */
  public void inserir(int x) throws Exception {

    // validar insercao
    if (((ultimo + 1) % array.length) == primeiro) {
      throw new Exception("Erro ao inserir!");
    }

    array[ultimo] = x;
    ultimo = (ultimo + 1) % array.length;
  }

  /**
   * Remove um elemento da primeira posicao da fila e movimenta os demais
   * elementos para o primeiro da mesma.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a fila estiver vazia.
   */
  public int remover() throws Exception {

    // validar remocao
    if (primeiro == ultimo) {
      throw new Exception("Erro ao remover!");
    }

    int resp = array[primeiro];
    primeiro = (primeiro + 1) % array.length;
    return resp;
  }

  /**
   * Mostra os array separados por espacos.
   */
  public void mostrar() {
    System.out.print("[ ");

    for (int i = primeiro; i != ultimo; i = ((i + 1) % array.length)) {
      System.out.print(array[i] + " ");
    }

    System.out.println("]");
  }

  public void mostrarRec() {
    System.out.print("[ ");
    mostrarRec(primeiro);
    System.out.println("]");
  }

  public void mostrarRec(int i) {
    if (i != ultimo) {
      System.out.print(array[i] + " ");
      mostrarRec((i + 1) % array.length);
    }
  }

  /**
   * Retorna um boolean indicando se a fila esta vazia
   * 
   * @return boolean indicando se a fila esta vazia
   */
  public boolean isVazia() {
    return (primeiro == ultimo);
  }
}

class Celula {
  public int elemento; // Elemento inserido na celula.
  public Celula prox; // Aponta a celula prox.

  /**
   * Construtor da classe.
   */
  public Celula() {
    this(0);
  }

  /**
   * Construtor da classe.
   * 
   * @param elemento int inserido na celula.
   */
  public Celula(int elemento) {
    this.elemento = elemento;
    this.prox = null;
  }
}

class ListaFlex {
  private Celula primeiro;
  private Celula ultimo;

  /**
   * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
   */
  public ListaFlex() {
    primeiro = new Celula();
    ultimo = primeiro;
  }

  /**
   * Insere um elemento na primeira posicao da lista.
   * 
   * @param x int elemento a ser inserido.
   */
  public void inserirInicio(int x) {
    Celula tmp = new Celula(x);
    tmp.prox = primeiro.prox;
    primeiro.prox = tmp;
    if (primeiro == ultimo) {
      ultimo = tmp;
    }
    tmp = null;
  }

  /**
   * Insere um elemento na ultima posicao da lista.
   * 
   * @param x int elemento a ser inserido.
   */
  public void inserirFim(int x) {
    ultimo.prox = new Celula(x);
    ultimo = ultimo.prox;
  }

  /**
   * Remove um elemento da primeira posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista nao contiver elementos.
   */
  public int removerInicio() throws Exception {
    if (primeiro == ultimo) {
      throw new Exception("Erro ao remover (vazia)!");
    }

    Celula tmp = primeiro;
    primeiro = primeiro.prox;
    int resp = primeiro.elemento;
    tmp.prox = null;
    tmp = null;
    return resp;
  }

  /**
   * Remove um elemento da ultima posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista nao contiver elementos.
   */
  public int removerFim() throws Exception {
    if (primeiro == ultimo) {
      throw new Exception("Erro ao remover (vazia)!");
    }

    // Caminhar ate a penultima celula:
    Celula i;
    for (i = primeiro; i.prox != ultimo; i = i.prox)
      ;

    int resp = ultimo.elemento;
    ultimo = i;
    i = ultimo.prox = null;

    return resp;
  }

  /**
   * Insere um elemento em uma posicao especifica considerando que o primeiro
   * elemento valido esta na posicao 0.
   * 
   * @param x   int elemento a ser inserido.
   * @param pos int posicao da insercao.
   * @throws Exception Se <code>posicao</code> invalida.
   */
  public void inserir(int x, int pos) throws Exception {

    int tamanho = tamanho();

    if (pos < 0 || pos > tamanho) {
      throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
    } else if (pos == 0) {
      inserirInicio(x);
    } else if (pos == tamanho) {
      inserirFim(x);
    } else {
      // Caminhar ate a posicao anterior a insercao
      Celula i = primeiro;
      for (int j = 0; j < pos; j++, i = i.prox)
        ;

      Celula tmp = new Celula(x);
      tmp.prox = i.prox;
      i.prox = tmp;
      tmp = i = null;
    }
  }

  /**
   * Remove um elemento de uma posicao especifica da lista considerando que o
   * primeiro elemento valido esta na posicao 0.
   * 
   * @param posicao Meio da remocao.
   * @return resp int elemento a ser removido.
   * @throws Exception Se <code>posicao</code> invalida.
   */
  public int remover(int pos) throws Exception {
    int resp;
    int tamanho = tamanho();

    if (primeiro == ultimo) {
      throw new Exception("Erro ao remover (vazia)!");

    } else if (pos < 0 || pos >= tamanho) {
      throw new Exception("Erro ao remover (posicao " + pos + " / " + tamanho + " invalida!");
    } else if (pos == 0) {
      resp = removerInicio();
    } else if (pos == tamanho - 1) {
      resp = removerFim();
    } else {
      // Caminhar ate a posicao anterior a insercao
      Celula i = primeiro;
      for (int j = 0; j < pos; j++, i = i.prox)
        ;

      Celula tmp = i.prox;
      resp = tmp.elemento;
      i.prox = tmp.prox;
      tmp.prox = null;
      i = tmp = null;
    }

    return resp;
  }

  /**
   * Mostra os elementos da lista separados por espacos.
   */
  public void mostrar() {
    System.out.print("[ ");
    for (Celula i = primeiro.prox; i != null; i = i.prox) {
      System.out.print(i.elemento + " ");
    }
    System.out.println("] ");
  }

  /**
   * Procura um elemento e retorna se ele existe.
   * 
   * @param x Elemento a pesquisar.
   * @return <code>true</code> se o elemento existir, <code>false</code> em caso
   *         contrario.
   */
  public boolean pesquisar(int x) {
    boolean resp = false;
    for (Celula i = primeiro.prox; i != null; i = i.prox) {
      if (i.elemento == x) {
        resp = true;
        i = ultimo;
      }
    }
    return resp;
  }

  /**
   * Calcula e retorna o tamanho, em numero de elementos, da lista.
   * 
   * @return resp int tamanho
   */
  public int tamanho() {
    int tamanho = 0;
    for (Celula i = primeiro; i != ultimo; i = i.prox, tamanho++)
      ;
    return tamanho;
  }
}

public class PilhaFlex {
  private Celula topo;

  /**
   * Construtor da classe que cria uma fila sem elementos.
   */
  public PilhaFlex() {
    topo = null;
  }

  /**
   * Insere elemento na pilha (politica FILO).
   * 
   * @param x int elemento a inserir.
   */
  public void inserir(int x) {
    Celula tmp = new Celula(x);
    tmp.prox = topo;
    topo = tmp;
    tmp = null;
  }

  /**
   * Remove elemento da pilha (politica FILO).
   * 
   * @return Elemento removido.
   * @trhows Exception Se a sequencia nao contiver elementos.
   */
  public int remover() throws Exception {
    if (topo == null) {
      throw new Exception("Erro ao remover!");
    }
    int resp = topo.elemento;
    Celula tmp = topo;
    topo = topo.prox;
    tmp.prox = null;
    tmp = null;
    return resp;
  }

  /**
   * Mostra os elementos separados por espacos, comecando do topo.
   */
  public void mostrar() {
    System.out.print("[ ");
    for (Celula i = topo; i != null; i = i.prox) {
      System.out.print(i.elemento + " ");
    }
    System.out.println("] ");
  }

  public int getSoma() {
    return getSoma(topo);
  }

  private int getSoma(Celula i) {
    int resp = 0;
    if (i != null) {
      resp += i.elemento + getSoma(i.prox);
    }
    return resp;
  }

  public int getMax() {
    int max = topo.elemento;
    for (Celula i = topo.prox; i != null; i = i.prox) {
      if (i.elemento > max)
        max = i.elemento;
    }
    return max;
  }

  public void mostraPilha() {
    mostraPilha(topo);
  }

  private void mostraPilha(Celula i) {
    if (i != null) {
      mostraPilha(i.prox);
      System.out.println("" + i.elemento);
    }
  }

}
// #endregion

// #region SORTING
// #endregion
