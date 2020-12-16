import java.io.*;
import java.time.*;
import java.util.*;

public class Q7 {

  public static void main(String[] args) {
    boolean controller = false;
    Doidona structure = new Doidona();
    String word = "";
    Scanner reader = new Scanner(System.in);
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        Player util = CreateNewPlayerFromFile(word);
        structure.Inserir(util);
      }
    } while (!controller);
    boolean resp = false;
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        System.out.print(word + " ");
        resp = structure.Search(word);
        if (resp)
          System.out.println(" SIM");
        else
          System.out.println(" NAO");
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

class Celula {
  public Player elemento;
  public Celula prox;

  public Celula() {
    this(null);
  }

  public Celula(Player elemento) {
    this.elemento = elemento;
    this.prox = null;
  }
}

class No {
  public Player elemento;
  public No esq, dir;

  public No(Player elemento) {
    this(elemento, null, null);
  }

  public No(Player elemento, No esq, No dir) {
    this.elemento = elemento;
    this.esq = esq;
    this.dir = dir;
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

  public void inserirFim(Player j) {
    ultimo.prox = new Celula(j);
    ultimo = ultimo.prox;
  }

  public boolean pesquisar(String j) {
    boolean resp = false;
    for (Celula i = primeiro.prox; i != null; i = i.prox) {
      if (i.elemento.getName().equals(j)) {
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

class ArvoreBinaria {
  public No raiz;

  public ArvoreBinaria() {
    raiz = null;
  }

  public boolean pesquisar(String x) {
    System.out.print(" raiz");
    return pesquisar(x, raiz);
  }

  private boolean pesquisar(String x, No i) {
    boolean resp;
    if (i == null) {
      resp = false;
    } else if (x.compareTo(i.elemento.getName()) == 0) {
      resp = true;
    } else if (x.compareTo(i.elemento.getName()) < 0) {
      System.out.print(" esq");
      resp = pesquisar(x, i.esq);
    } else {
      System.out.print(" dir");
      resp = pesquisar(x, i.dir);
    }
    return resp;
  }

  public void inserir(Player j) {
    raiz = inserir(j, raiz);
  }

  private No inserir(Player j, No i) {
    if (i == null) {
      i = new No(j);
    } else if (j.getName().compareTo(i.elemento.getName()) < 0) {
      i.esq = inserir(j, i.esq);
    } else if (j.getName().compareTo(i.elemento.getName()) > 0) {
      i.dir = inserir(j, i.dir);
    } else {
      System.out.println("Erro ao inserir!");
    }
    return i;
  }
}

class HashReserva {
  Player tabela[];
  int m1, m2, m, reserva;

  public HashReserva() {
    this(11, 0);
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

  public int h(int altura) {
    return altura % m1;
  }

  public boolean inserir(Player elemento) {
    boolean resp = false;
    if (elemento != null) {
      int pos = h(elemento.getHeigth());
      if (tabela[pos] == null) {
        tabela[pos] = elemento;
        resp = true;
      }
    }
    return resp;
  }

  public boolean pesquisar(String Player) {
    int pos = -1;
    for (int i = 0; i < m; i++) {
      if (tabela[i] != null && tabela[i].getName().equals(Player)) {
        pos = i;
        i = m;
      }
    }
    return pos != -1;
  }
}

class HashRehash {
  Player tabela[];
  int m;

  public HashRehash() {
    this(9);
  }

  public HashRehash(int m) { // inicializa todos cmo nulo
    this.m = m;
    this.tabela = new Player[this.m];
    for (int i = 0; i < m; i++) {
      tabela[i] = null;
    }
  }

  public int h(int altura) {
    return altura % m;
  }

  public int reh(int altura) {
    return ++altura % m;
  }

  public boolean inserir(Player elemento) {
    boolean resp = false;
    if (elemento != null) {
      int pos = h(elemento.getHeigth());
      if (tabela[pos] == null) {
        tabela[pos] = elemento;
        resp = true;
      } else {
        pos = reh(elemento.getHeigth());
        if (tabela[pos] == null) {
          tabela[pos] = elemento;
          resp = true;
        }
      }
    }
    return resp;
  }

  public boolean pesquisar(String Player) {
    int pos = -1;
    for (int i = 1; i < m; i++) {
      if (tabela[i] != null && tabela[i].getName().equals(Player)) {
        pos = i;
        i = m;
      }
    }
    return pos != -1;
  }
}

class Doidona {
  HashReserva T1;
  HashRehash T2;
  Lista T3;
  ArvoreBinaria T4;

  public Doidona() {
    T1 = new HashReserva();
    T2 = new HashRehash();
    T3 = new Lista();
    T4 = new ArvoreBinaria();
  }

  public void Inserir(Player x) {
    boolean wasInserted = T1.inserir(x);
    if (!wasInserted) {
      int hashValue = x.getHeigth() % 3;
      if (hashValue < 1) {
        T2.inserir(x);
      } else if (hashValue < 2) {
        T3.inserirFim(x);
      } else {
        T4.inserir(x);
      }
    }
  }

  public boolean Search(String x) {
    return T1.pesquisar(x) || T2.pesquisar(x) || T3.pesquisar(x) || T4.pesquisar(x);
  }

}