// < ------------ HEADER FILES   ------------ >

#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <err.h>

typedef struct Players
{
  int id;
  char *name;
  int heigth;
  int weigth;
  char *university;
  char *birthYear;
  char *birthCity;
  char *birthState;
} Player;

typedef struct CelulaDupla
{
  Player elemento;          // Elemento inserido na celula.
  int index;                // indice da célula dentro da lista
  struct CelulaDupla *prox; // Aponta a celula prox.
  struct CelulaDupla *ant;  // Aponta a celula anterior.
} CelulaDupla;

CelulaDupla *novaCelulaDupla(Player elemento)
{
  CelulaDupla *nova = (CelulaDupla *)malloc(sizeof(CelulaDupla));
  nova->elemento = elemento;
  nova->ant = nova->prox = NULL;
  nova->index = 0;
  return nova;
}

CelulaDupla *primeiro;
CelulaDupla *ultimo;

/**
 * Cria uma lista dupla sem elementos (somente no cabeca).
 */
void start(Player first)
{
  primeiro = novaCelulaDupla(first);
  ultimo = primeiro;
}

/**
 * Insere um elemento na ultima posicao da lista.
 * @param x int elemento a ser inserido.
 */
void inserirFim(Player x)
{
  ultimo->prox = novaCelulaDupla(x);
  ultimo->prox->ant = ultimo;
  ultimo = ultimo->prox;
}

/**
 *  Calcula e retorna o tamanho, em numero de elementos, da lista.
 *  @return resp int tamanho
 */
int tamanho()
{
  int tamanho = 0;
  CelulaDupla *i;
  for (i = primeiro; i != ultimo; i = i->prox, tamanho++)
    ;
  return tamanho;
}

void PrintPlayer(Player *player)
{
  printf("[%d ## %s ## %d ## %d ## %s ## %s ## %s ## %s]\n", player->id, player->name, player->heigth, player->weigth, player->birthYear, player->university, player->birthCity, player->birthState);
}

/**
 * Mostra os elementos da lista separados por espacos.
 */
void mostrar()
{
  CelulaDupla *i;
  for (i = primeiro->prox; i != NULL; i = i->prox)
  {
    PrintPlayer(&(i->elemento));
  }
}

/**
 * Gera um indice para cada uma das células
 */
void IndexEveryCell()
{
  int j = 0;
  CelulaDupla *i;
  for (j = 0, i = primeiro->prox; i != NULL; i = i->prox, j++)
  {
    i->index = j;
  }
}

char *Replace(char const *const original, char const *const pattern, char const *const replacement)
{
  size_t const replen = strlen(replacement);
  size_t const patlen = strlen(pattern);
  size_t const orilen = strlen(original);
  size_t patcnt = 0;
  const char *oriptr;
  const char *patloc;
  for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
  {
    patcnt++;
  }
  {
    size_t const retlen = orilen + patcnt * (replen - patlen);
    char *const returned = (char *)malloc(sizeof(char) * (retlen + 1));
    if (returned != NULL)
    {
      char *retptr = returned;
      for (oriptr = original; patloc = strstr(oriptr, pattern); oriptr = patloc + patlen)
      {
        size_t const skplen = patloc - oriptr;
        strncpy(retptr, oriptr, skplen);
        retptr += skplen;
        strncpy(retptr, replacement, replen);
        retptr += replen;
      }
      strcpy(retptr, oriptr);
    }
    return returned;
  }
}
void PlayerSet(int checker, Player *playerToReturn, char *field)
{
  if (checker == 0)
  {
    playerToReturn->id = atoi(field);
  }
  else if (checker == 1)
  {
    playerToReturn->name = field;
  }
  else if (checker == 2)
  {
    playerToReturn->heigth = atoi(field);
  }
  else if (checker == 3)
  {
    playerToReturn->weigth = atoi(field);
  }
  else if (checker == 4)
  {
    playerToReturn->university = field;
  }
  else if (checker == 5)
  {
    playerToReturn->birthYear = field;
  }
  else if (checker == 6)
  {
    //FIX \n at the end
    int place = strlen(field);
    if (field[place - 1] == '\n')
    {
      field[place - 1] = field[place];
      // printf("%s\n", field);
    }
    playerToReturn->birthCity = field;
  }
  else if (checker == 7)
  {
    int place = strlen(field);
    if (field[place - 1] == '\n')
    {
      field[place - 1] = field[place];
    }
    playerToReturn->birthState = field;
  }
}

Player GetPlayerFromFile(char *word)
{
  // printf("%s\n", word);
  Player playerToReturn;
  char *field = strtok(word, ",");
  int checker = 0;
  while (field)
  {
    PlayerSet(checker, &playerToReturn, field);
    checker++;
    field = strtok(NULL, ",");
  }
  return playerToReturn;
}

char *GetLineFromFile(int lineToRead)
{
  FILE *csvReader = fopen("/tmp/players.csv", "r");
  static char buffer[1024];
  fgets(buffer, 1024, csvReader);
  for (int i = 0; i < lineToRead; i++)
  {
    fgets(buffer, 1024, csvReader);
  }
  fgets(buffer, 1024, csvReader);
  fclose(csvReader);
  return buffer;
}

Player GetPlayerFromLine(char *player)
{
  char *buffer;
  buffer = GetLineFromFile(atoi(player)); //blocao contendo todos os dados da linha que representa um jogador
  char *test = Replace(buffer, ",,", ",nao informado,");
  char *final = Replace(test, ",\n", ",nao informado\n");
  free(test);
  return GetPlayerFromFile(final);
}

bool IsAble(char *_word)
{
  bool valueToReturn = false;
  if (_word[0] == ('F') && _word[1] == ('I') && _word[2] == ('M'))
  {
    valueToReturn = true;
  }
  return valueToReturn;
}

void SwapPlayer(CelulaDupla *elementI, CelulaDupla *elementJ)
{
  if (elementI != NULL && elementJ != NULL && elementI->index != elementJ->index)
  {
    Player temp = elementI->elemento;
    elementI->elemento = elementJ->elemento;
    elementJ->elemento = temp;
  }
}

Player GetHalfOfTwoEndPoints(CelulaDupla *left, CelulaDupla *right)
{
  CelulaDupla *aux = left->prox;
  for (int i = left->index; i < ((left->index + right->index) / 2); i++)
  {
    aux = aux->prox;
  }
  Player valueToReturn = aux->elemento;
  //TODO free aux
  return valueToReturn;
}

void RecursiveQuicksort(CelulaDupla *left, CelulaDupla *right)
{
  CelulaDupla *i = left, *j = right;
  Player pivo = GetHalfOfTwoEndPoints(left, right);
  while (i != NULL && j != NULL && i->index <= j->index)
  {
    while (strcmp(i->elemento.birthState, pivo.birthState) < 0 || strcmp(i->elemento.birthState, pivo.birthState) == 0 && strcmp(i->elemento.name, pivo.name) < 0)
    {
      if (i->prox != NULL)
        i = i->prox;
      else
        break;
    }
    while (strcmp(j->elemento.birthState, pivo.birthState) > 0 || strcmp(j->elemento.birthState, pivo.birthState) == 0 && strcmp(j->elemento.name, pivo.name) > 0)
    {
      if (j->ant != NULL)
        j = j->ant;
      else
        break;
    }
    if (i->index <= j->index)
    {
      SwapPlayer(i, j);
      i = i->prox;
      j = j->ant;
    }
  }
  if (left != NULL && j != NULL && left->index < j->index)
    RecursiveQuicksort(left, j);
  if (i != NULL && right != NULL && i->index < right->index)
    RecursiveQuicksort(i, right);
}

void Quicksort()
{
  RecursiveQuicksort(primeiro, ultimo);
}

int main()
{
  char word[1024];
  bool controller = false;
  scanf("%[^\n]%*c", word);
  start(GetPlayerFromLine(word));
  scanf("%[^\n]%*c", word);
  do
  {
    controller = IsAble(word);
    if (!controller)
    {
      inserirFim(GetPlayerFromLine(word));
    }
    scanf("%[^\n]%*c", word);
  } while (!controller); //END OF FIRST ITERATION
  IndexEveryCell();
  Quicksort();
  mostrar();
  return 0;
}