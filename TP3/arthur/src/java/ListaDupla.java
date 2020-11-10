import java.io.*;
import java.util.*;
import java.time.*;

public class ListaDupla {

  public static void main(String[] args) {
    boolean controller = false;
    String word = "";
    Utilitary util = new Utilitary();
    Scanner reader = new Scanner(System.in);
    Dupla list = new Dupla(util.CreateNewPlayerFromFile(reader.nextLine()));
    do {
      word = reader.nextLine();
      controller = util.IsAble(word);
      if (!controller) {
        list.inserirFim(util.CreateNewPlayerFromFile(word));
      }
    } while (!controller);
    reader.close();
    list.indexEveryOne();
    util.quicksort(list);
    // list.mostrarI();
  }
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
    this.index = 0;
  }

}

class Dupla {
  CelulaDupla primeiro;
  CelulaDupla ultimo;

  /**
   * Construtor da classe que cria uma lista dupla sem elementos (somente no
   * cabeca).
   */
  public Dupla() {
    primeiro = new CelulaDupla();
    ultimo = primeiro;
  }

  public Dupla(Player x) {
    primeiro = new CelulaDupla(x);
    ultimo = primeiro;
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

  public void mostrar() {
    for (CelulaDupla i = primeiro; i != null; i = i.prox) {
      i.elemento.PrintPlayer();
    }
  }

  public void mostrarI() {
    for (CelulaDupla i = primeiro; i.prox != null; i = i.prox) {
      System.out.println(i.index);
    }
  }

  public void indexEveryOne() {
    int tamanho = 0;
    for (CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++) {
      i.index = tamanho;
    }
    ultimo.index = tamanho;// tamanho +1
  }

}

// #region UTILS
class Utilitary {
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

  Player GetHalfOfTwoEndPoints(CelulaDupla left, CelulaDupla right) {
    CelulaDupla aux = left.prox;
    for (int i = left.index; i < ((left.index + right.index) / 2); i++) {
      aux = aux.prox;
    }
    return aux.elemento;
  }

  private void quicksort(CelulaDupla esq, CelulaDupla dir) {
    CelulaDupla i = esq, j = dir;
    Player pivo = GetHalfOfTwoEndPoints(esq, dir);
    // System.out.println("i: " + i.index + "\tj: " + j.index);
    while (i != null && j != null && i.index <= j.index) {
      while (i.elemento.getBirthState().compareTo(pivo.getBirthState()) < 0
          || i.elemento.getBirthState().compareTo(pivo.getBirthState()) == 0
              && i.elemento.getName().compareTo(pivo.getName()) < 0) {
        if (i.prox != null) {
          i = i.prox;
        } else {
          break;
        }
      }
      while (j.elemento.getBirthState().compareTo(pivo.getBirthState()) > 0
          || j.elemento.getBirthState().compareTo(pivo.getBirthState()) == 0
              && j.elemento.getName().compareTo(pivo.getName()) > 0) {
        if (j.ant != null) {
          j = j.ant;
        } else {
          break;
        }
      }
      if (i.index <= j.index) {
        swap(i, j);
        i = i.prox;
        j = j.ant;
      }
    }
    if (esq != null && j != null && esq.index < j.index)
      quicksort(esq, j);
    if (i != null && dir != null && i.index < dir.index)
      quicksort(i, dir);
  }

  public void quicksort(Dupla list) {
    quicksort(list.primeiro, list.ultimo);
    list.mostrar();
  }

  public void swap(CelulaDupla i, CelulaDupla j) {
    if (i != null && j != null && i.index != j.index) {
      Player temp = i.elemento;
      i.elemento = j.elemento;
      j.elemento = temp;
    }
  }

}