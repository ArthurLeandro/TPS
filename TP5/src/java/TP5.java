
// #region IMPORTS
import java.io.*;
import java.util.*;
import java.time.*;
// #endregion

interface AED {
  public boolean inserir(Player pos);

  public boolean pesquisar(String toSearch);
}

public class TP5 {
  public static void main(String[] args) {
    int EXERCISE_NUMBER = 2;
    AED structure = GetStructureBasedOnExerciseNumber(EXERCISE_NUMBER);
    boolean controller = false;
    String word = "";
    Scanner reader = new Scanner(System.in);
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        Player util = CreateNewPlayerFromFile(word);
        if (!structure.pesquisar(util.getName()))
          structure.inserir(util);
      }
    } while (!controller);
    boolean resp = false;
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        System.out.print(word + " ");
        resp = structure.pesquisar(word);
        if (resp)
          System.out.println("SIM");
        else
          System.out.println("NAO");
      }
    } while (!controller);
    reader.close();
  }

  public static AED GetStructureBasedOnExerciseNumber(int ex) {
    if (ex == 1)
      return new HashReserva();
    else
      return new HashRehash();
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

class HashReserva implements AED {
  Player tabela[];
  int m1, m2, m, reserva;
  int NULO = -1;

  public HashReserva() {
    this(21, 9);
  }

  public HashReserva(int m1, int m2) {
    this.m1 = m1;
    this.m2 = m2;
    this.m = m1 + m2;
    this.tabela = new Player[this.m];
    for (int i = 0; i < m; i++) {
      tabela[i] = null;
    }
    reserva = 0;
  }

  public int h(Player elemento) {
    return elemento.getHeigth() % m1;
  }

  public boolean inserir(Player elemento) {
    boolean resp = false;
    if (elemento != null) {
      int pos = h(elemento);
      if (tabela[pos] == null) {
        tabela[pos] = elemento;
        resp = true;
      } else if (reserva < m2) {
        tabela[m1 + reserva] = elemento;
        reserva++;
        resp = true;
      }
    }
    return resp;
  }

  public boolean pesquisar(String elemento) {
    boolean resp = false;
    for (int i = 0; i < m; i++) {
      if (tabela[i] != null && elemento.compareTo(tabela[i].getName()) == 0) {
        resp = true;
        i += m;
      }
    }
    return resp;
  }
}

class HashRehash implements AED {
  Player tabela[];
  int m;

  public HashRehash() {
    this(25);
  }

  public HashRehash(int m) {
    this.m = m;
    this.tabela = new Player[this.m];
    for (int i = 0; i < m; i++) {
      tabela[i] = null;
    }
  }

  public int h(Player elemento) {
    return elemento.getHeigth() % m;
  }

  public int reh(Player elemento) {
    return (elemento.getHeigth() + 1) % m;
  }

  public boolean inserir(Player elemento) {
    boolean resp = false;
    if (elemento != null) {
      int pos = h(elemento);
      if (tabela[pos] == null) {
        tabela[pos] = elemento;
        resp = true;
      } else {
        pos = reh(elemento);
        if (tabela[pos] == null) {
          tabela[pos] = elemento;
          resp = true;
        }
      }
    }
    return resp;
  }

  public boolean pesquisar(String elemento) {
    boolean resp = false;
    for (int i = 0; i < m; i++) {
      if (tabela[i] != null && elemento.compareTo(tabela[i].getName()) == 0) {
        resp = true;
        i += m;
      }
    }
    return resp;
  }
}
