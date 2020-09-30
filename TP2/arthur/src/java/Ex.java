
import java.util.*;

public class Ex {

  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    String[] lista = new String[1000];
    int pointerTo = 0;
    int amount = reader.nextInt();
    String cache = "";
    for (String string : lista) {
      string = "";
    }
    for (int i = -1; i < amount; i++) {
      pointerTo = 0;
      cache = reader.nextLine();
      String[] splitted = cache.split(" ");
      for (int j = 0; j < splitted.length; j++) {
        if (!Pesquisa(lista, pointerTo, splitted[j])) {
          lista[j] = splitted[j];
          pointerTo++;
        }
      }
      if (i >= 0) {
        Sort(lista, pointerTo);
        PrintArray(lista, pointerTo);
      }
      for (String string : lista) {
        string = "";
      }
    }

    reader.close();
  }

  public static void Sort(String[] array, int tillWhere) {
    int n = tillWhere;
    for (int i = 1; i < n; i++) {
      String tmp = array[i];
      int j = i - 1;
      while ((j >= 0) && (array[j].compareTo(tmp) > 0)) {
        array[j + 1] = array[j];
        j--;
      }
      array[j + 1] = tmp;
    }
  }

  public static void PrintArray(String[] array, int pointer) {
    for (int i = 0; i < pointer; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }

  public static boolean Pesquisa(String[] array, int pointer, String search) {
    boolean valueToReturn = false;
    for (int i = 0; i < pointer; i++) {
      try {
        if (array[i].equals(search)) {
          valueToReturn = true;
          i += pointer;
        }
      } catch (Exception e) {
        // TODO: handle exception
      }

    }
    return valueToReturn;
  }
}
