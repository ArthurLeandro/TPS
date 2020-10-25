import java.io.*;
import java.util.*;
import java.time.*;

public class ListaDupla {

}

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

class CelulaDupla {
  public Player elemento;
  public CelulaDupla ant;
  public CelulaDupla prox;
  int index;

  /**
   * Construtor da classe.
   */
  public CelulaDupla() {
    this(null);
  }

  /**
   * Construtor da classe.
   * 
   * @param elemento int inserido na celula.
   */
  public CelulaDupla(Player elemento) {
    this.elemento = elemento;
    this.ant = this.prox = null;
    this.index = index;
  }

}

class Dupla {
  private CelulaDupla primeiro;
  private CelulaDupla ultimo;

  /**
   * Construtor da classe que cria uma lista dupla sem elementos (somente no
   * cabeca).
   */
  public Dupla() {
    primeiro = new CelulaDupla();
    ultimo = primeiro;
  }

  /**
   * Insere um elemento na primeira posicao da lista.
   * 
   * @param x int elemento a ser inserido.
   */
  public void inserirInicio(Player x) {
    CelulaDupla tmp = new CelulaDupla(x);
    tmp.ant = primeiro;
    tmp.prox = primeiro.prox;
    primeiro.prox = tmp;
    if (primeiro == ultimo) {
      ultimo = tmp;
    } else {
      tmp.prox.ant = tmp;
    }
    tmp = null;
  }

  /**
   * Insere um elemento na ultima posicao da lista.
   * 
   * @param x int elemento a ser inserido.
   */
  public void inserirFim(Player x) {
    ultimo.prox = new CelulaDupla(x);
    ultimo.prox.ant = ultimo;
    ultimo = ultimo.prox;
  }

  /**
   * Remove um elemento da primeira posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista nao contiver elementos.
   */
  public Player removerInicio() {
    CelulaDupla tmp = primeiro;
    primeiro = primeiro.prox;
    Player resp = primeiro.elemento;
    tmp.prox = primeiro.ant = null;
    tmp = null;
    return resp;
  }

  /**
   * Remove um elemento da ultima posicao da lista.
   * 
   * @return resp int elemento a ser removido.
   * @throws Exception Se a lista nao contiver elementos.
   */
  public Player removerFim() {
    Player resp = ultimo.elemento;
    ultimo = ultimo.ant;
    ultimo.prox.ant = null;
    ultimo.prox = null;
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
  public void inserir(Player x, int pos) throws Exception {
    int tamanho = tamanho();
    if (pos < 0 || pos > tamanho) {
      throw new Exception("Erro ao inserir posicao (" + pos + " / tamanho = " + tamanho + ") invalida!");
    } else if (pos == 0) {
      inserirInicio(x);
    } else if (pos == tamanho) {
      inserirFim(x);
    } else {
      // Caminhar ate a posicao anterior a insercao
      CelulaDupla i = primeiro;
      for (int j = 0; j < pos; j++, i = i.prox)
        ;
      CelulaDupla tmp = new CelulaDupla(x);
      tmp.ant = i;
      tmp.prox = i.prox;
      tmp.ant.prox = tmp.prox.ant = tmp;
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
  public Player remover(int pos) throws Exception {
    Player resp;
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
      CelulaDupla i = primeiro.prox;
      for (int j = 0; j < pos; j++, i = i.prox)
        ;
      i.ant.prox = i.prox;
      i.prox.ant = i.ant;
      resp = i.elemento;
      i.prox = i.ant = null;
      i = null;
    }

    return resp;
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
    for (CelulaDupla i = primeiro.prox; i != null; i = i.prox) {
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
    for (CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++)
      ;
    return tamanho;
  }

}

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

  private void quicksort(CelulaDupla esq, CelulaDupla dir) {
    CelulaDupla i = esq, j = dir;
    int pivoPos = (dir.index + esq.index) / 2;
    CelulaDupla pivo = esq;
    for (int k = esq.index; k < pivoPos; k++) {
      pivo = pivo.prox;
    }
    while (i.index <= j.index) {
      while (i.elemento.getBirthState().compareTo(pivo.elemento.getBirthState()) < 0)
        i = i.prox;
      while (j.elemento.getBirthState().compareTo(pivo.elemento.getBirthState()) > 0)
        j = j.ant;
      if (i.index <= j.index) {
        swap(i, j);
        i = i.prox;
        j = j.ant;
      }
    }
    if (esq.index < j.index)
      quicksort(esq, j);
    if (i.index < dir.index)
      quicksort(i, dir);
  }

  public void swap(CelulaDupla i, CelulaDupla j) {
    Player temp = i.elemento;
    i.elemento = j.elemento;
    j.elemento = temp;
  }

}