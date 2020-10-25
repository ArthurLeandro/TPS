class Node {
  Node left, right;
  char letter;
  BSTStringged otherTree;

  public Node(String _letter) {
    letter = _letter.charAt(0);
    left = right = null;
    otherTree = new BSTStringged(_letter);
  }
}

class StringNode {
  String letters;
  StringNode left, right;

  public StringNode(String letter) {
    letters = letter;
    left = right = null;
  }
}

class BST {
  Node raiz;

  void Insert(String name) {
    if (raiz == null) {
      raiz = new Node(name);
    } else if (name.charAt(0) > raiz.letter) {
      Insert(name, raiz.right);
    } else if (name.charAt(0) < raiz.letter) {
      Insert(name, raiz.left);
    }
  }

  void Insert(String name, Node nodes) {
    char aux = name.charAt(0);
    if (nodes == null) {
      nodes = new Node(name);
    } else if (aux == nodes.letter) {
      nodes.otherTree.Insert(name);
    } else if (aux < nodes.letter) {
      Insert(name, nodex.left);
    } else {
      Insert(name, nodes.right);
    }
  }

  boolean Search(String name) {
    boolean valueTOReturn = false;
    if (raiz != null) {
      char aux = name.charAt(0);
      if (aux == raiz.letter) {
        valueTOReturn = true;
      } else if (aux > raiz.letter) {
        Search(name, raiz.right);
      } else if (aux < raiz.letter) {
        Search(name, raiz.left);
      }
    }
    return valueTOReturn;
  }

  boolean Search(String name, Node nodes) {
    boolean value = false;
    char aux = name.charAt(0);
    if (nodes != null) {
      if (aux == nodes.letter) {
        nodes.otherTree.Search(name);
      } else if (aux < nodes.letter) {
        Search(name, nodex.left);
      } else {
        Search(name, nodes.right);
      }
    }
    return value;
  }

}

class BSTStringged {
  StringNode raiz;

  void Insert(String name) {
    if (raiz == null) {
      raiz = new StringNode(name);
    } else if (name.charAt(0) > raiz.letter) {
      Insert(name, raiz.right);
    } else if (name.charAt(0) < raiz.letter) {
      Insert(name, raiz.left);
    }
  }

  void Insert(String name, StringNode nodes) {
    char aux = name.charAt(0);
    if (nodes == null) {
      nodes = new StringNode(name);
    } else if (aux == nodes.letter) {
      nodes.otherTree.Insert(name);
    } else if (aux < nodes.letter) {
      Insert(name, nodex.left);
    } else {
      Insert(name, nodes.right);
    }
  }

}