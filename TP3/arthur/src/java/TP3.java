
// #region IMPORTS
import java.io.*;
import java.util.*;
import java.time.*;

// #endregion
interface AED {
  public void inserirInicio(Player x);

  public void inserirFim(Player x);

  public void inserir(Player x, int pos);

  public Player removerInicio();

  public Player removerFim();

  public Player remover(int pos);

  public void mostrar();
}

// #region MAIN
public class TP3 {
  public static void main(String[] args) {
    int EXERCISE_NUMBER = 3;
    boolean controller = false;
    String word = "";
    Utilitary util = new Utilitary(EXERCISE_NUMBER == 1 || EXERCISE_NUMBER == 3);
    Scanner reader = new Scanner(System.in);
    do {
      word = reader.nextLine();
      controller = util.IsAble(word);
      if (!controller) {
        util.Insert(util.CreateNewPlayerFromFile(word));
      }
    } while (!controller);
    for (Player s : util.array) {
      s.PrintPlayer();
    }
    int amount = reader.nextInt();
    util.InitValuesOnStructure(EXERCISE_NUMBER);
    for (int i = 0; i < amount; i++) {
      util.HandleExercise(EXERCISE_NUMBER, reader.nextLine());
    }
    util.ShowStructure(EXERCISE_NUMBER);
    reader.close();
  }
}
// #endregion

// #region UTILS
class Utilitary {
  Player[] array;
  Celula structure, last;
  boolean isStatic;
  int lastValidPosition;

  Lista list;
  Pilha stack;
  ListaFlex listFlex;
  PilhaFlex stackFlex;

  public Utilitary(boolean isStatic) {
    lastValidPosition = 0;
    this.isStatic = isStatic;
    if (isStatic)
      array = new Player[600];
    else
      last = structure = new Celula(null);

  }

  public Player CreateNewPlayerFromFile(String valueToRead) {
    Player valueToReturn = null;
    try {

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
    } catch (Exception e) {
      System.out.println("Erro ao criar o jogador: ");
    }
    return valueToReturn;
  }

  public boolean IsAble(String _word) {
    return _word.charAt(0) == ('F') && _word.charAt(1) == ('I') && _word.charAt(2) == ('M');
  }

  public String[] FixEntry(String[] word) {
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

  public void Insert(Player playerToInsert) {
    if (this.isStatic) {
      this.array[lastValidPosition] = playerToInsert;
      lastValidPosition++;
    } else {
      last = new Celula(playerToInsert);
      last = last.prox;
    }
  }

  public void HandleExercise(int exercise, String word) {
    if (exercise == 1) {
      HandleCommands(word.split(" "), list);
    } else if (exercise == 3) {
      HandleCommands(word.split(" "), stack);
    } else if (exercise == 5) {
      HandleCommands(word.split(" "), listFlex);
    } else if (exercise == 6) {
      HandleCommands(word.split(" "), stackFlex);
    }
  }

  public void HandleCommands(String[] splitted, AED dataStructure) {
    // dataStructure.mostrar();
    switch (splitted[0]) {
      case "II":
        dataStructure.inserirInicio(CreateNewPlayerFromFile(splitted[1]));
        break;
      case "I*":
        dataStructure.inserir(CreateNewPlayerFromFile(splitted[2]), Integer.parseInt(splitted[1]));
        break;
      case "IF":
        dataStructure.inserirFim(CreateNewPlayerFromFile(splitted[1]));
        break;
      case "RI":
        dataStructure.removerInicio();
        break;
      case "R*":
        dataStructure.remover(Integer.parseInt(splitted[1]));
        break;
      case "RF":
        dataStructure.removerFim();
        break;
      case "I":
        dataStructure.inserirInicio(CreateNewPlayerFromFile(splitted[1]));
        break;
      case "R":
        dataStructure.removerFim();
        break;
      default:
        System.out.println("Não foi possível concluir a operação requisitada em questão");
        break;
    }
  }

  public void InitValuesOnStructure(int exercise) {
    if (exercise == 1) {
      list = new Lista(array, lastValidPosition);
    } else if (exercise == 3) {
      stack = new Pilha(array, lastValidPosition);
    } else if (exercise == 5) {
      listFlex = new ListaFlex(this.structure);
    } else if (exercise == 6) {
      stackFlex = new PilhaFlex(this.structure);
    }
  }

  public void ShowStructure(int exercise) {
    if (exercise == 1) {
      list.mostrar();
    } else if (exercise == 3) {
      stack.mostrar();
    } else if (exercise == 5) {
      listFlex.mostrar();
    } else if (exercise == 6) {
      stackFlex.mostrar();
    }
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
    stringBuilder.append("##");
    System.out.println(stringBuilder.toString());
  }

}

class Lista implements AED {
  private Player[] array;
  private int n;

  /**
   * Construtor da classe.
   */
  public Lista() {
    this(6);
  }

  public Lista(Player[] alreadyDefinedList, int last) {
    array = alreadyDefinedList;
    n = last;
  }

  /**
   * Construtor da classe.
   * 
   * @param tamanho Tamanho da lista.
   */
  public Lista(int tamanho) {
    array = new Player[tamanho];
    n = 0;
  }

  /**
   * Insere um elemento na primeira posicao da lista e move os demais elementos
   * para o fim da lista.
   * 
   * @param x int elemento a ser inserido.
   * @throws Exception Se a lista estiver cheia.
   */
  public void inserirInicio(Player x) {

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
  public void inserirFim(Player x) {
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
  public void inserir(Player x, int pos) {
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
  public Player removerInicio() {
    Player resp = array[0];
    n--;
    for (int i = 0; i < n; i++) {
      array[i] = array[i + 1];
    }
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Remove um elemento da ultima posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista estiver vazia.
   */
  public Player removerFim() {
    Player resp = array[--n];
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Remove um elemento de uma posicao especifica da lista e movimenta os demais
   * elementos para o inicio da mesma.
   * 
   * @param pos Posicao de remocao.
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista estiver vazia ou a posicao for invalida.
   */
  public Player remover(int pos) {
    Player resp = array[pos];
    n--;
    for (int i = pos; i < n; i++) {
      array[i] = array[i + 1];
    }
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Mostra os elementos da lista separados por espacos.
   */
  public void mostrar() { // TODO olhar se este método sofrerá alguma alteração na entrega do verde
    for (int i = 0; i < n; i++) {
      System.out.print("[" + i + "] ");
      array[i].PrintPlayer();
    }
  }

  /**
   * Procura um elemento e retorna se ele existe.
   * 
   * @param x int elemento a ser pesquisado.
   * @return <code>true</code> se o array existir, <code>false</code> em caso
   *         contrario.
   */
  public boolean pesquisar(Player x) {
    boolean retorno = false;
    for (int i = 0; i < n && retorno == false; i++) {
      retorno = (array[i] == x);
    }
    return retorno;
  }
}

class Pilha implements AED {
  private Player[] array;
  private int primeiro; // Remove do indice "primeiro".
  private int ultimo; // Insere no indice "ultimo".

  /**
   * Construtor da classe.
   */
  public Pilha() {
    this(6);
  }

  public Pilha(Player[] alreadyDefinedStack, int last) {
    array = alreadyDefinedStack;
    primeiro = 0;
    ultimo = last;
  }

  /**
   * Construtor da classe.
   * 
   * @param tamanho Tamanho da fila.
   */
  public Pilha(int tamanho) {
    array = new Player[tamanho + 1];
    primeiro = ultimo = array.length - 1;// acho que tem que ser menos 1
  }

  /**
   * Insere um elemento na ultima posicao da fila.
   * 
   * @param x int elemento a ser inserido.
   * @throws Exception Se a fila estiver cheia.
   */
  public void inserirInicio(Player x) {
    array[ultimo] = x;
    ultimo++;
  }

  /**
   * Remove um elemento da primeira posicao da fila e movimenta os demais
   * elementos para o primeiro da mesma.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a fila estiver vazia.
   */
  public Player remover() {
    Player resp = array[--ultimo];
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Mostra os array separados por espacos.
   */
  public void mostrar() {
    for (int i = ultimo; i < array.length; i++) {
      System.out.print("[" + i + "]");
      // array[i].PrintPlayer();
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

  @Override
  public void inserirFim(Player x) {
    // TODO Auto-generated method stub

  }

  @Override
  public void inserir(Player x, int pos) {
    // TODO Auto-generated method stub

  }

  @Override
  public Player removerInicio() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Player removerFim() {
    Player resp = array[primeiro];
    primeiro = (primeiro + 1) % array.length;
    return resp;
  }

  @Override
  public Player remover(int pos) {
    // TODO Auto-generated method stub
    return null;
  }
}

class Celula {
  public Player elemento; // Elemento inserido na celula.
  public Celula prox; // Aponta a celula prox.

  /**
   * Construtor da classe.
   * 
   * @param elemento int inserido na celula.
   */
  public Celula(Player elemento) {
    this.elemento = elemento;
    this.prox = null;
  }

}

class ListaFlex implements AED {
  private Celula primeiro;
  private Celula ultimo;

  /**
   * Construtor da classe que cria uma lista sem elementos (somente no cabeca).
   */
  public ListaFlex() {
    primeiro = new Celula(null);
    ultimo = primeiro;
  }

  public ListaFlex(Celula fullList) {
    primeiro = fullList;
    ultimo = fullList.prox;
    while (ultimo.prox != null) {
      ultimo = fullList.prox;
    }

  }

  /**
   * Insere um elemento na primeira posicao da lista.
   * 
   * @param x int elemento a ser inserido.
   */
  public void inserirInicio(Player x) {
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
  public void inserirFim(Player x) {
    ultimo.prox = new Celula(x);
    ultimo = ultimo.prox;
  }

  /**
   * Remove um elemento da primeira posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista nao contiver elementos.
   */
  public Player removerInicio() {
    Celula tmp = primeiro;
    primeiro = primeiro.prox;
    Player resp = primeiro.elemento;
    tmp.prox = null;
    tmp = null;
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Remove um elemento da ultima posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista nao contiver elementos.
   */
  public Player removerFim() {
    // Caminhar ate a penultima celula:
    Celula i;
    for (i = primeiro; i.prox != ultimo; i = i.prox)
      ;
    Player resp = ultimo.elemento;
    ultimo = i;
    i = ultimo.prox = null;
    System.out.println("(R) " + resp.getName());
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
  public void inserir(Player x, int pos) {
    int tamanho = tamanho();
    if (pos == 0) {
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
  public Player remover(int pos) {
    Player resp;
    int tamanho = tamanho();
    if (pos == 0) {
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
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Mostra os elementos da lista separados por espacos.
   */
  public void mostrar() {
    int j = 0;
    for (Celula i = primeiro.prox; i != null; i = i.prox, j++) {
      System.out.println("[" + j + "]");
      i.elemento.PrintPlayer();
    }
  }

  /**
   * Procura um elemento e retorna se ele existe.
   * 
   * @param x Elemento a pesquisar.
   * @return <code>true</code> se o elemento existir, <code>false</code> em caso
   *         contrario.
   */
  public boolean pesquisar(Player x) {
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

class PilhaFlex implements AED {
  private Celula topo;

  /**
   * Construtor da classe que cria uma fila sem elementos.
   */
  public PilhaFlex() {
    topo = null;
  }

  public PilhaFlex(Celula alreadyDefinedStack) {
    topo = alreadyDefinedStack;
  }

  /**
   * Insere elemento na pilha (politica FILO).
   * 
   * @param x int elemento a inserir.
   */
  public void inserirInicio(Player x) {
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
  public Player remover() {
    Player resp = topo.elemento;
    Celula tmp = topo;
    topo = topo.prox;
    tmp.prox = null;
    tmp = null;
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  /**
   * Mostra os elementos separados por espacos, comecando do topo.
   */
  public void mostrar() {
    int j = 0;
    for (Celula i = topo; i != null; i = i.prox, j++) {
      System.out.print("[" + j + "] ");
      i.elemento.PrintPlayer();
    }
  }

  @Override
  public void inserirFim(Player x) {
    // TODO Auto-generated method stub

  }

  @Override
  public void inserir(Player x, int pos) {
    // TODO Auto-generated method stub

  }

  @Override
  public Player removerInicio() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Player removerFim() {
    Player resp = topo.elemento;
    Celula tmp = topo;
    topo = topo.prox;
    tmp.prox = null;
    tmp = null;
    System.out.println("(R) " + resp.getName());
    return resp;
  }

  @Override
  public Player remover(int pos) {
    // TODO Auto-generated method stub
    return null;
  }
}

// MATRIZ DINAMICA

// QUICK SORT COM LISTA DUPLA
// #endregion

// #region SORTING
// #endregion
