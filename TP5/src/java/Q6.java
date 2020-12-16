
// #region IMPORTS
import java.io.*;
import java.util.*;
import java.time.*;
// #endregion

public class Q6 {

  public static void main(String[] args) {
    int EXERCISE_NUMBER = 2;
    HashIndiretaLista structure = new HashIndiretaLista(25);
    boolean controller = false;
    String word = "";
    Scanner reader = new Scanner(System.in);
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        Player util = CreateNewPlayerFromFile(word);
        if (!structure.searchAll(util.getName()))
          structure.inserir(util);
      }
    } while (!controller);
    boolean resp = false;
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        System.out.print(word + " ");
        resp = structure.searchAll(word);
        if (resp)
          System.out.println("SIM");
        else
          System.out.println("NAO");
      }
    } while (!controller);
    reader.close();
  }

  public static Player CreateNewPlayerFromFile(String valueToRead) {
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
    // stringBuilder.append(" ## ");
    stringBuilder.append(getName());
    // stringBuilder.append(" ## ");
    // stringBuilder.append(getHeigth());
    // stringBuilder.append(" ## ");
    // stringBuilder.append(getWeigth());
    // stringBuilder.append(" ## ");
    // stringBuilder.append(getBirthYear());
    // stringBuilder.append(" ## ");
    // stringBuilder.append(getUniversity());
    // stringBuilder.append(" ## ");
    // stringBuilder.append(getBirthCity());
    // stringBuilder.append(" ## ");
    // stringBuilder.append(getBirthState());
    // stringBuilder.append(" ## ");
    System.out.println(stringBuilder.toString());
  }
}

class Lista {
  Player[] array;
  private int n;

  public Lista() {
    this(25);
  }

  public Lista(int tamanho) {
    array = new Player[tamanho];
    n = 0;
  }

  public void inserirFim(Player x) {
    array[n] = x;
    n++;
  }

  public boolean pesquisar(String x) {
    boolean retorno = false;
    for (int i = 0; i < n && retorno == false; i++) {
      retorno = (array[i].getName().equals(x));
    }
    return retorno;
  }
}

class HashIndiretaLista {
  Lista[] listas;

  public HashIndiretaLista(int tamanho) {
    listas = new Lista[tamanho];
    for (int i = 0; i < tamanho; i++) {
      listas[i] = new Lista(100);
    }
  }

  public void inserir(Player p) {
    int n = p.getHeigth() % 25;
    listas[n].inserirFim(p);
  }

  public boolean searchOne(Player p) {
    boolean resp = false;
    int n = p.getHeigth() % 25;
    resp = listas[n].pesquisar(p.getName());
    return resp;
  }

  public boolean searchAll(String p) {
    boolean resp = false;
    for (int i = 0; i < 25 && !resp; i++) {
      resp = listas[i].pesquisar(p);
    }
    return resp;
  }
}