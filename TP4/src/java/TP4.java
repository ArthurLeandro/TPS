
// #region IMPORTS
import java.io.*;
import java.util.*;
import java.time.*;
// #endregion

interface AED {

  public void inserir(Player pos);

  public void remover(Player pos);

  public boolean search(Object toSearch);

  public boolean searchPrinting(Object toSearch);

  public void mostrar();
}

public class TP4 {
  public static void main(String[] args) {
    AED structure = new ArvoreBinaria();

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
    stringBuilder.append(" ## ");
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
  private No raiz; // Raiz da arvore.

  public ArvoreBinaria() {
    raiz = null;
  }

  @Override
  public boolean searchPrinting(Object x) {
    Player aux = (Player) x;
    return searchPrintingRec(aux, raiz);
  }

  private boolean searchPrintingRec(Player x, No i) {
    boolean resp = false;
    if (i != null) {
      int comparison = x.getName().compareTo(i.elemento.getName());
      if (0 == comparison) {
        resp = true;
      } else if (0 > comparison) {
        resp = searchPrintingRec(x, i.esq);
      } else {
        resp = searchPrintingRec(x, i.dir);
      }
    }
    return resp;
  }

  @Override
  public boolean search(Object x) {
    Player aux = (Player) x;
    return searchRec(aux, raiz);
  }

  private boolean searchRec(Player x, No i) {
    boolean resp = false;
    if (i != null) {
      int comparison = x.getName().compareTo(i.elemento.getName());
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
    } else {
      System.out.println("Erro ao inserir!");
    }
    return i;
  }

  public void remover(Player x) {
    raiz = remover(x, raiz);
  }

  private No remover(Player x, No i) {
    int comparison = 0;
    if (i == null) {
      System.out.println("ERRO AO REMOVER");
    } else {
      comparison = x.getName().compareTo(i.elemento.getName());
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
  private NoAN raiz; // Raiz da arvore.

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
  public void remover(Player pos) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean search(Object toSearch) {
    String elemento = (String) toSearch;
    return pesquisarRec(elemento, raiz);
  }

  private boolean pesquisarRec(String elemento, NoAN i) {
    boolean resp = false;
    int comparison = 0;
    if (i != null) {
      resp = false;
    } else {
      comparison = elemento.compareTo(i.elemento.getName());
    }
    if (i != null && comparison == 0) {
      resp = true;
    } else if (comparison < 0) {
      resp = pesquisarRec(elemento, i.esq);
    } else {
      resp = pesquisarRec(elemento, i.dir);
    }
    return resp;
  }

  @Override
  public boolean searchPrinting(Object toSearch) {
    String elemento = (String) toSearch;
    return searchPrintingRec(elemento, raiz);
  }

  private boolean searchPrintingRec(String elemento, NoAN i) {
    boolean resp = false;
    int comparison = 0;
    if (i != null) {
      resp = false;
    } else {
      comparison = elemento.compareTo(i.elemento.getName());
    }
    if (i != null && comparison == 0) {
      resp = true;
    } else if (comparison < 0) {
      System.out.print("esq ");
      resp = pesquisarRec(elemento, i.esq);
    } else {
      System.out.print("dir ");
      resp = pesquisarRec(elemento, i.dir);
    }
    return resp;
  }

  @Override
  public void mostrar() {
    // TODO Auto-generated method stub
  }
}

// #endregion

// #region BINARIA DE BINARIA
class BinariaDeBinaria implements AED {

  public BlackNo raiz;

  public BinariaDeBinaria(int n) {
    raiz = new BlackNo(n);
  }

  @Override
  public void inserir(Player pos) {
    int calc = p.getHeigth() % 15;
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
  public void remover(Player pos) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean search(Object toSearch) {
    return searchRec((String) toSearch, raiz);
  }

  public boolean searchRec(String name, BlackNo i) {
    boolean response = false;
    if (i != null) {
      if (i.element.search(name)) {
        response = true;
      }
      if (!response && i.left.element.search(name)) {
        response = true;
      }
      if (!response && i.right.element.search(name)) {
        response = true;
      }
    }
    return response;
  }

  @Override
  public boolean searchPrinting(Object toSearch) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void mostrar() {
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
  public void remover(Player pos) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean search(Object toSearch) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean searchPrinting(Object toSearch) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void mostrar() {
    // TODO Auto-generated method stub

  }
}

// #endregion

// #endregion
