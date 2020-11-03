// < ------------ HEADER FILES   ------------ >

#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <err.h>

// < ------------ DECLARATION METHODS   ------------ >

int Exercise = 10;

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

typedef struct Celula
{
  Player elemento;     // Elemento inserido na celula.
  struct Celula *prox; // Aponta a celula prox.
} Celula;

// < ------------ UTILS   ------------ >

Celula *novaCelula(Player elemento)
{
  Celula *nova = (Celula *)malloc(sizeof(Celula));
  nova->elemento = elemento;
  nova->prox = NULL;
  return nova;
}

char *Split(char **word)
{
  return strsep(word, " ");
}
bool Equals(char *first, char *second)
{
  return strcmp(first, second) == 0;
}

// < ------------ DATA STRUCTURES   ------------ >

Player array[400]; // Elementos da pilha
int n;             // Quantidade de array.

/**
 * Inicializacoes
 */
void ListStart()
{
  n = 0;
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

void PrintPlayer(Player *player)
{
  printf("## %s ## %d ## %d ## %s ## %s ## %s ## %s\n", player->name, player->heigth, player->weigth, player->birthYear, player->university, player->birthCity, player->birthState);
}

/**
 * Insere um elemento na primeira posicao da lista e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 */
void ListInsertBegin(Player x)
{
  //levar elementos para o fim do array
  for (int i = n; i > 0; i--)
  {
    array[i] = array[i - 1];
  }
  array[0] = x;
  n++;
}

/**
 * Insere um elemento na ultima posicao da 
 * @param x int elemento a ser inserido.
 */
void ListInsertEnd(Player x)
{
  array[n] = x;
  n++;
}

/**
 * Insere um elemento em uma posicao especifica e move os demais
 * elementos para o fim da 
 * @param x int elemento a ser inserido.
 * @param pos Posicao de insercao.
 */
void ListInsert(Player x, int pos)
{
  //levar elementos para o fim do array
  for (int i = n; i > pos; i--)
  {
    array[i] = array[i - 1];
  }
  array[pos] = x;
  n++;
}

/**
 * Remove um elemento da primeira posicao da lista e movimenta 
 * os demais elementos para o inicio da mesma.
 * @return resp int elemento a ser removido.
 */
Player ListRemoveBegin()
{
  Player resp;
  resp = array[0];
  n--;
  for (int i = 0; i < n; i++)
  {
    array[i] = array[i + 1];
  }
  printf("(R) %s\n", resp.name);
  return resp;
}

/**
 * Remove um elemento da ultima posicao da 
 * @return resp int elemento a ser removido.
 */
Player ListRemoveEnd()
{
  Player resp = array[--n];
  printf("(R) %s\n", resp.name);
  return resp;
}

/**
 * Remove um elemento de uma posicao especifica da lista e 
 * movimenta os demais elementos para o inicio da mesma.
 * @param pos Posicao de remocao.
 * @return resp int elemento a ser removido.
 */
Player ListRemove(int pos)
{
  Player resp;
  resp = array[pos];
  n--;
  for (int i = pos; i < n; i++)
  {
    array[i] = array[i + 1];
  }
  printf("(R) %s\n", resp.name);
  return resp;
}

/**
 * Mostra os array separados por espacos.
 */
void ListShow()
{
  for (int i = 0; i < n; i++)
  {
    printf("[%i] ", i);
    PrintPlayer(&array[i]); // !Passivel de !Error
  }
}

// < ------------ FILA CIRCULAR   ------------ >
int MAXTAM;
int primeiro; // Remove do indice "primeiro".
int ultimo;   // Insere no indice "ultimo".

/**
 * Inicializacoes
 */
void QueueStart()
{
  primeiro = ultimo = 0;
  MAXTAM = 5;
}

void HeightAverage()
{
  float summation = 0.0f;
  int i = primeiro;
  while (i != ultimo)
  {
    summation += array[i].heigth;
    i = (i + 1) % 6;
  }
  int divider = (primeiro > ultimo || ultimo == 0) ? 5 : ultimo;
  printf("%.f\n", roundf(summation / divider));
}

/**
 * Remove um elemento da primeira posicao da fila e movimenta 
 * os demais elementos para o primeiro da mesma.
 * @return resp int elemento a ser removido.
 * @Se a fila estiver vazia.
 */
Player QueueRemove()
{
  Player resp = array[primeiro];
  primeiro = (primeiro + 1) % 6;
  return resp;
}

/**
 * Insere um elemento na ultima posicao da 
 * @param x int elemento a ser inserido.
 * @Se a fila estiver cheia.
 */
void QueueInsert(Player x)
{
  if (((ultimo + 1) % 6) == primeiro)
  {
    QueueRemove();
  }
  array[ultimo] = x;
  ultimo = (ultimo + 1) % 6;
  HeightAverage();
}

/**
 * Mostra os array separados por espacos.
 */
void QueueShow()
{
  for (int i = primeiro - 1, j = 0; i < ultimo; i++, j++)
  {
    printf("[%i] ", j);
    PrintPlayer(&array[i]);
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

// < ------------ DATA STRUCTURES   ------------ >

//PILHAFLEX
Celula *topo; // Sem celula cabeca.

/**
 * Cria uma fila sem elementos.
 */
void FlexStackStart()
{
  topo = NULL;
}
int FlexStackSize()
{
  int valueToReturn = 0;
  for (Celula *i = topo; i != NULL; i = i->prox, valueToReturn++)
    ;
  return valueToReturn;
}
/**
 * Insere elemento na pilha (politica FILO).
 * @param x int elemento a inserir.
 */
void InsertStack(Player x)
{
  Celula *tmp = novaCelula(x);
  tmp->prox = topo;
  topo = tmp;
  tmp = NULL;
}

/**
 * Remove elemento da pilha (politica FILO).
 * @return Elemento removido.
 */
Player RemoveStack()
{
  Player resp = topo->elemento;
  Celula *tmp = topo;
  topo = topo->prox;
  tmp->prox = NULL;
  free(tmp);
  tmp = NULL;
  printf("(R) %s\n", resp.name);
  return resp;
}
void FlexShowStack(Celula *i, int j)
{
  j -= 1;
  if (i != NULL)
  {
    FlexShowStack(i->prox, j);
    printf("[%i] ", j);
    PrintPlayer(&i->elemento); // ! Passivel de erro CHECK LATER
  }
}


/**
 * Mostra os elementos separados por espacos, comecando do topo.
 */
void ShowStack()
{
  FlexShowStack(topo, FlexStackSize());
}
// < ------------ SORTING    ------------ >
Celula *firstInLine;
Celula *lastInLine;

int FlexQueuSize()
{
  int valueToReturn = 0;
  for (Celula *i = firstInLine; i != lastInLine; i = i->prox, valueToReturn++)
    ;
  return valueToReturn;
}
/**
 * Cria uma fila sem elementos (somente no cabeca).
 */
void FlexQueueStart()
{
  Player nothing;
  nothing.id = -5;
  firstInLine = novaCelula(nothing);
  lastInLine = firstInLine;
}

void HeightAverageFlex()
{
  float summation = 0.0f;
  Celula *i = firstInLine->prox;
  while (i != lastInLine->prox)
  {
    summation += i->elemento.heigth;
    i = i->prox;
  }
  int divider = FlexQueuSize();
  printf("%.f\n", roundf(summation / divider));
}
/**
 * Remove elemento da fila (politica FIFO).
 * @return Elemento removido.
 */
Player FlexQueueRemove()
{
  Celula *tmp = firstInLine;
  lastInLine->prox = firstInLine->prox;
  firstInLine = firstInLine->prox;
  Player resp = firstInLine->elemento;
  tmp->prox = NULL;
  free(tmp);
  tmp = NULL;
  return resp;
}

/**
 * Insere elemento na fila (politica FIFO).
 * @param x int Elemento a inserir.
 */
void FlexQueueInsert(Player x)
{
  if (firstInLine == NULL)
  {
    FlexQueueStart();
  }
  else
  {
    if (FlexQueuSize() == 5)
    {
      FlexQueueRemove();
    }
    lastInLine->prox = novaCelula(x);
    lastInLine = lastInLine->prox;
    lastInLine->prox = firstInLine;
  }
  HeightAverageFlex();
}

/**
 * Mostra os elementos separados por espacos.
 */
void FlexQueueShow()
{
  int index = 0;
  for (Celula *i = firstInLine->prox; i != lastInLine->prox; i = i->prox, index++)
  {
    printf("[%i] ", index);
    PrintPlayer(&i->elemento);
  }
}

// < ------------ SORTING    ------------ >

/**
 * Método utilizado para operar os comandos.
 * @param  char* String que contem os comandos a serem executados
*/
void HandleComand(char *word)
{ //So vou ter Fila e Lista Flex ou Static
  char *command = Split(&word);
  if (command[0] == 'I' && command[1] == 'I')
  {
    //LISta
    ListInsertBegin(GetPlayerFromLine(Split(&word)));
  }
  else if (command[0] == 'I' && command[1] == 'F')
  {
    //LISta
    ListInsertEnd(GetPlayerFromLine(Split(&word)));
  }
  else if (command[0] == 'I' && command[1] == '*')
  {
    //LISta
    ListInsert(GetPlayerFromLine(Split(&word)), atoi(Split(&word)));
  }
  else if (command[0] == 'R' && command[1] == 'I')
  {
    //LISta
    ListRemoveBegin();
  }
  else if (command[0] == 'R' && command[1] == 'F')
  {
    //LISta
    ListRemoveEnd();
  }
  else if (command[0] == 'R' && command[1] == '*')
  {
    //LISta
    ListRemove(atoi(Split(&word)));
  }
  else if (command[0] == 'I')
  {
    //FILA
    if (Exercise == 10)
    { //Pilha Flexivel
      InsertStack(GetPlayerFromLine(Split(&word)));
    }
    else if (Exercise == 7)
    {
      FlexQueueInsert(GetPlayerFromLine(Split(&word)));
    }
    else
    {
      QueueInsert(GetPlayerFromLine(Split(&word)));
    }
  }
  else if (command[0] == 'R')
  {
    //FILA
    if (Exercise == 10)
    { //Pilha Flexivel
      RemoveStack();
    }
    else if (Exercise == 7)
    {
      Player resp = FlexQueueRemove();
      printf("(R) %s\n", resp.name);
    }
    else
    {
      Player resp = QueueRemove();
      printf("(R) %s\n", resp.name);
    }
  }
  else
  {
    printf("O comando %s não foi entendido!\n", command);
  }
}

void InitStructure()
{
  ListStart();
  QueueStart();
  FlexQueueStart();
  FlexStackStart();
}

void Insert(bool isStatic, Player x)
{
  if (isStatic)
  {
    if (Exercise == 2)
    {
      array[n] = x;
      n++;
    }
    else
    {
      QueueInsert(x);
    }
  }
  else
  { //PILHA (10) || FILA
    if (Exercise == 10)
    {
      InsertStack(x);
    }
    else if (Exercise == 7)
    {
      FlexQueueInsert(x);
    }
    else
    {
      QueueInsert(x);
    }
  }
}

void ShowExercise(int Ex)
{
  if (Ex == 2)
  {
    ListShow();
  }
  else if (Ex == 4)
  {
    QueueShow();
  }
  else if (Ex == 7)
  {
    FlexQueueShow();
  }
  else if (Ex == 10)
  {
    ShowStack();
  }
}
// < ------------ MAIN   ------------ >

int main()
{
  char word[1024];
  bool controller = false;
  scanf("%[^\n]%*c", word);
  bool isStatic = Exercise == 2 || Exercise == 4;
  QueueStart();
  FlexQueueStart();
  FlexStackStart();
  do
  {
    controller = IsAble(word);
    if (!controller)
    {
      Insert(isStatic, GetPlayerFromLine(word));
    }
    scanf("%[^\n]%*c", word);
  } while (!controller); //END OF FIRST ITERATION
  // scanf("%[^\n]%*c", word);
  int amount = atoi(word);
  for (int i = 0; i < amount; i++)
  {
    scanf("%[^\n]%*c", word);
    HandleComand(word);
  }
  ShowExercise(Exercise);
  return 0;
}