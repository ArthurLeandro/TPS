
// #region IMPORTS
import java.io.*;
import java.util.*;
import java.time.*;
// #endregion

interface AED {

  public void inserir(Player pos);

  public void remover(String pos);

  public boolean search(String toSearch);

  public boolean searchPrinting(String toSearch);

  public void mostrar();
}

public class TP4 {
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
        // if (!structure.search(util.getName()))
        structure.inserir(util);
      }
    } while (!controller);
    boolean resp = false;
    do {
      word = reader.nextLine();
      controller = IsAble(word);
      if (!controller) {
        if (EXERCISE_NUMBER == 5) {
          if (!structure.search(word)) {
            structure.search(word);
          }
        } else {
          System.out.print(word + " raiz");
          resp = structure.searchPrinting(word);
          if (!resp)
            System.out.println(" NAO");
          else
            System.out.println(" SIM");
        }
      }
    } while (!controller);
    if (EXERCISE_NUMBER == 5) {
      structure.mostrar();
    }
    reader.close();
  }

  public static AED GetStructureBasedOnExerciseNumber(int exercise) {
    AED valueToReturn;
    if (exercise == 1 || exercise == 5) {
      valueToReturn = new ArvoreBinaria();
    } else if (exercise == 4) {
      valueToReturn = new Alvinegra();
    } else if (exercise == 2) {
      valueToReturn = new BinariaDeBinaria();
    } else {
      valueToReturn = new Trie();
    }
    return valueToReturn;
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

// #region DATASTRUCTURES

// #region NODES

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

class BlackNo {
  ArvoreBinaria element;
  int height;
  BlackNo left, right;

  public BlackNo(int _h) {
    height = _h;
    element = new ArvoreBinaria();
    left = right = null;
  }
}

class NoAN {
  public boolean cor;
  public Player elemento;
  public NoAN esq, dir;

  public NoAN(Player elemento) {
    this(elemento, false, null, null);
  }

  public NoAN(Player elemento, boolean cor) {
    this(elemento, cor, null, null);
  }

  public NoAN(Player elemento, boolean cor, NoAN esq, NoAN dir) {
    this.cor = cor;
    this.elemento = elemento;
    this.esq = esq;
    this.dir = dir;
  }
}

// #endregion

// #region BST

class ArvoreBinaria implements AED {
  public No raiz; // Raiz da arvore.

  public ArvoreBinaria() {
    raiz = null;
  }

  @Override
  public boolean searchPrinting(String x) {
    return searchPrintingRec(x, raiz);
  }

  private boolean searchPrintingRec(String x, No i) {
    boolean resp = false;
    if (i != null) {
      resp = x.compareTo(i.elemento.getName()) == 0;
      if (!resp) {
        System.out.print(" esq");
        if (searchPrintingRec(x, i.esq)) {
          resp = true;
        } else {
          System.out.print(" dir");
          if (searchPrintingRec(x, i.dir)) {
            resp = true;
          }
        }
      }
    }
    return resp;
  }

  @Override
  public boolean search(String x) {
    return searchRec(x, raiz);
  }

  private boolean searchRec(String x, No i) {
    boolean resp = false;
    if (i != null) {
      int comparison = x.compareTo(i.elemento.getName());
      if (0 == comparison) {
        resp = true;
      } else if (0 > comparison) {
        resp = searchRec(x, i.esq);
      } else {
        resp = searchRec(x, i.dir);
      }
    }
    return resp;
  }

  public void mostrar() {
    caminharCentral(raiz);
  }

  private void caminharCentral(No i) {
    if (i != null) {
      caminharCentral(i.esq); // Elementos da esquerda.
      i.elemento.PrintPlayer();
      caminharCentral(i.dir); // Elementos da direita.
    }
  }

  public void inserir(Player x) {
    raiz = inserir(x, raiz);
  }

  private No inserir(Player x, No i) {
    int comparison = 0;
    if (i == null) {
      i = new No(x);

    } else {
      comparison = x.getName().compareTo(i.elemento.getName());
    }
    if (0 > comparison) {
      i.esq = inserir(x, i.esq);
    } else if (0 < comparison) {
      i.dir = inserir(x, i.dir);
    }
    return i;
  }

  public void remover(String x) {
    raiz = remover(x, raiz);
  }

  private No remover(String x, No i) {
    int comparison = 0;
    if (i == null) {
      System.out.println("ERRO AO REMOVER");
    } else {
      comparison = x.compareTo(i.elemento.getName());
    }
    if (0 > comparison) {
      i.esq = remover(x, i.esq);
    } else if (0 < comparison) {
      i.dir = remover(x, i.dir);
    } else if (i.dir == null) {
      i = i.esq;
    } else if (i.esq == null) {
      i = i.dir;
    } else {
      i.esq = antecessor(i, i.esq);
    }
    return i;
  }

  private No antecessor(No i, No j) {
    if (j.dir != null) {
      j.dir = antecessor(i, j.dir);
    } else {
      i.elemento = j.elemento;
      j = j.esq;
    }
    return j;
  }

}

// #endregion

// #region ALVINEGRA
class Alvinegra implements AED {
  public NoAN raiz; // Raiz da arvore.

  public Alvinegra() {
    raiz = null;
  }

  public void inserir(Player elemento) {
    if (raiz == null) {
      raiz = new NoAN(elemento, false);
    } else if (raiz.esq == null && raiz.dir == null) {
      if (raiz.elemento.getName().compareTo(elemento.getName()) > 0) {
        raiz.esq = new NoAN(elemento, true);
      } else {
        raiz.dir = new NoAN(elemento, true);
      }
    } else if (raiz.esq == null) {
      if (raiz.elemento.getName().compareTo(elemento.getName()) > 0) {
        raiz.esq = new NoAN(elemento);
      } else if (raiz.dir.elemento.getName().compareTo(elemento.getName()) > 0) {
        raiz.esq = new NoAN(raiz.elemento);
        raiz.elemento = elemento;
      } else {
        raiz.esq = new NoAN(raiz.elemento);
        raiz.elemento = raiz.dir.elemento;
        raiz.dir.elemento = elemento;
      }
      raiz.esq.cor = raiz.dir.cor = false;
    } else if (raiz.dir == null) {
      if (raiz.elemento.getName().compareTo(elemento.getName()) < 0) {
        raiz.dir = new NoAN(elemento);
      } else if (raiz.esq.elemento.getName().compareTo(elemento.getName()) < 0) {
        raiz.dir = new NoAN(raiz.elemento);
        raiz.elemento = elemento;
      } else {
        raiz.dir = new NoAN(raiz.elemento);
        raiz.elemento = raiz.esq.elemento;
        raiz.esq.elemento = elemento;
      }
      raiz.esq.cor = raiz.dir.cor = false;
    } else {
      insert(elemento, null, null, null, raiz);
    }
    raiz.cor = false;
  }

  private void insert(Player elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
    if (i == null) {
      if (elemento.getName().compareTo(pai.elemento.getName()) < 0) {
        i = pai.esq = new NoAN(elemento, true);
      } else {
        i = pai.dir = new NoAN(elemento, true);
      }
      if (pai.cor == true) {
        balancear(bisavo, avo, pai, i);
      }
    } else {
      if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
        i.cor = true;
        i.esq.cor = i.dir.cor = false;
        if (i == raiz) {
          i.cor = false;
        } else if (pai.cor == true) {
          balancear(bisavo, avo, pai, i);
        }
      }
      if (elemento.getName().compareTo(i.elemento.getName()) < 0) {
        insert(elemento, avo, pai, i, i.esq);
      } else if (elemento.getName().compareTo(i.elemento.getName()) > 0) {
        insert(elemento, avo, pai, i, i.dir);
      } else {
        System.out.println("Erro inserir (elemento repetido)!");
      }
    }
  }

  private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
    if (pai.cor == true) {
      if (pai.elemento.getName().compareTo(avo.elemento.getName()) > 0) {
        if (i.elemento.getName().compareTo(pai.elemento.getName()) > 0) {
          avo = rotacaoEsq(avo);
        } else {
          avo = rotacaoDirEsq(avo);
        }
      } else {
        if (i.elemento.getName().compareTo(pai.elemento.getName()) < 0) {
          avo = rotacaoDir(avo);
        } else {
          avo = rotacaoEsqDir(avo);
        }
      }
      if (bisavo == null) {
        raiz = avo;
      } else {
        if (avo.elemento.getName().compareTo(bisavo.elemento.getName()) < 0) {
          bisavo.esq = avo;
        } else {
          bisavo.dir = avo;
        }
      }
      avo.cor = false;
      avo.esq.cor = avo.dir.cor = true;
    }
  }

  private NoAN rotacaoDir(NoAN no) {
    NoAN noEsq = no.esq;
    NoAN noEsqDir = noEsq.dir;
    noEsq.dir = no;
    no.esq = noEsqDir;
    return noEsq;
  }

  private NoAN rotacaoEsq(NoAN no) {
    NoAN noDir = no.dir;
    NoAN noDirEsq = noDir.esq;
    noDir.esq = no;
    no.dir = noDirEsq;
    return noDir;
  }

  private NoAN rotacaoDirEsq(NoAN no) {
    no.dir = rotacaoDir(no.dir);
    return rotacaoEsq(no);
  }

  private NoAN rotacaoEsqDir(NoAN no) {
    no.esq = rotacaoEsq(no.esq);
    return rotacaoDir(no);
  }

  @Override
  public boolean search(String toSearch) {
    return pesquisarRec(toSearch, raiz);
  }

  private boolean pesquisarRec(String elemento, NoAN i) {
    boolean resp = false;
    int comparison = 0;
    if (i == null) {
      resp = false;
    } else {
      comparison = elemento.compareTo(i.elemento.getName());
    }
    if (i != null && comparison == 0) {
      resp = true;
    } else if (comparison < 0) {
      resp = pesquisarRec(elemento, i.esq);
    } else {
      try {
        resp = pesquisarRec(elemento, i.dir);
      } catch (Exception e) {
      }
    }
    return resp;
  }

  @Override
  public boolean searchPrinting(String toSearch) {
    return searchPrintingRec(toSearch, raiz);
  }

  private boolean searchPrintingRec(String elemento, NoAN i) {
    boolean resp = false;
    int comparison;
    if (i != null) {
      comparison = elemento.compareTo(i.elemento.getName());
      if (comparison == 0) {
        resp = true;
      } else if (comparison < 0) {
        System.out.print(" esq");
        resp = searchPrintingRec(elemento, i.esq);
      } else {
        try {
          System.out.print(" dir");
          resp = searchPrintingRec(elemento, i.dir);
        } catch (Exception e) {
        }
      }
    }
    return resp;
  }

  @Override
  public void mostrar() {
    // TODO Auto-generated method stub
  }

  @Override
  public void remover(String pos) {
    // TODO Auto-generated method stub

  }
}

// #endregion

// #region BINARIA DE BINARIA
class BinariaDeBinaria implements AED {

  public BlackNo raiz;

  public BinariaDeBinaria() {
    raiz = new BlackNo(7);
    raiz.right = new BlackNo(11);
    raiz.left = new BlackNo(3);
    raiz.right.right = new BlackNo(13);
    raiz.right.left = new BlackNo(9);
    raiz.left.right = new BlackNo(5);
    raiz.left.left = new BlackNo(1);
    raiz.left.left.right = new BlackNo(2);
    raiz.left.left.left = new BlackNo(0);
    raiz.left.right.right = new BlackNo(6);
    raiz.left.right.left = new BlackNo(4);
    raiz.right.left.right = new BlackNo(10);
    raiz.right.left.left = new BlackNo(8);
    raiz.right.right.right = new BlackNo(14);
    raiz.right.right.left = new BlackNo(12);
  }

  public BinariaDeBinaria(int n) {
    raiz = new BlackNo(n);
  }

  @Override
  public void inserir(Player pos) {
    int calc = pos.getHeigth() % 15;
    inserirRec(pos, raiz, calc);
  }

  public void inserirRec(Player p, BlackNo n, int calc) {
    if (n.height == calc) {
      n.element.inserir(p);
    } else if (n.height < calc) {
      inserirRec(p, n.right, calc);
    } else {
      inserirRec(p, n.left, calc);
    }
  }

  @Override
  public boolean search(String toSearch) {
    return searchRec(toSearch, raiz);
  }

  public boolean searchRec(String name, BlackNo i) {
    boolean response = false;
    if (i != null) {
      if (i.element.search(name)) {
        response = true;
      }
      if (!response) {
        if (searchRec(name, i.left)) {
          response = true;
        }
      }
      if (!response) {
        if (searchRec(name, i.right)) {
          response = true;
        }
      }
    }
    return response;
  }

  @Override
  public boolean searchPrinting(String toSearch) {
    return searchingPrinting(toSearch, raiz);
  }

  public boolean searchingPrinting(String name, BlackNo i) {
    boolean response = false;
    if (i != null) {
      response = (i.element.searchPrinting(name));
      if (!response) {
        System.out.print(" ESQ");
        if (searchingPrinting(name, i.left)) {
          response = true;
        } else {
          System.out.print(" DIR");
          if (searchingPrinting(name, i.right)) {
            response = true;
          }
        }
      }
    }
    return response;
  }

  @Override
  public void mostrar() {
    // TODO Auto-generated method stub

  }

  @Override
  public void remover(String pos) {
    // TODO Auto-generated method stub

  }
}

// #endregion

// #region TRIE
class Trie implements AED {

  @Override
  public void inserir(Player pos) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean search(String toSearch) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean searchPrinting(String toSearch) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void mostrar() {
    // TODO Auto-generated method stub

  }

  @Override
  public void remover(String pos) {
    // TODO Auto-generated method stub

  }
}

// #endregion

// #endregion
