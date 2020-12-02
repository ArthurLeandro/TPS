// < ------------ HEADER FILES   ------------ >

#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <err.h>

// < ------------ DECLARATION METHODS   ------------ >

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

typedef struct Node
{
  Player data;        //element in Node
  int ht;             //height of this Node
  struct Node *left;  //left Node
  struct Node *right; // Right Node
} node;

// < ------------ UTILS   ------------ >

char *Split(char **word)
{
  return strsep(word, " ");
}
bool Equals(char *first, char *second)
{
  return strcmp(first, second) == 0;
}

// < ------------ DATA STRUCTURES   ------------ >

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
  printf("## %s ## %d ## %d ## %s ## %s ## %s ## %s ##\n", player->name, player->heigth, player->weigth, player->birthYear, player->university, player->birthCity, player->birthState);
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

int height(node *T)
{
  int lh, rh;
  if (T == NULL)
    return (0);
  if (T->left == NULL)
    lh = 0;
  else
    lh = 1 + T->left->ht;
  if (T->right == NULL)
    rh = 0;
  else
    rh = 1 + T->right->ht;
  if (lh > rh)
    return (lh);
  return (rh);
}

int BF(node *T)
{
  int lh, rh;
  if (T == NULL)
    return (0);
  if (T->left == NULL)
    lh = 0;
  else
    lh = 1 + T->left->ht;
  if (T->right == NULL)
    rh = 0;
  else
    rh = 1 + T->right->ht;
  return (lh - rh);
}

node *rotateright(node *x)
{
  node *y;
  y = x->left;
  x->left = y->right;
  y->right = x;
  x->ht = height(x);
  y->ht = height(y);
  return (y);
}

node *rotateleft(node *x)
{
  node *y;
  y = x->right;
  x->right = y->left;
  y->left = x;
  x->ht = height(x);
  y->ht = height(y);
  return (y);
}

node *RR(node *T)
{
  T = rotateleft(T);
  return (T);
}

node *LL(node *T)
{
  T = rotateright(T);
  return (T);
}

node *LR(node *T)
{
  T->left = rotateleft(T->left);
  T = rotateright(T);
  return (T);
}

node *RL(node *T)
{
  T->right = rotateright(T->right);
  T = rotateleft(T);
  return (T);
}

void preorder(node *T)
{
  if (T != NULL)
  {
    PrintPlayer(&T->data);
    preorder(T->left);
    preorder(T->right);
  }
}

node *insert(node *T, Player x)
{
  if (T == NULL)
  {
    T = (node *)malloc(sizeof(node));
    T->data = x;
    T->left = NULL;
    T->right = NULL;
  }
  else if (strcmp(x.name, T->data.name) > 0) // insert in right subtree
  {
    T->right = insert(T->right, x);
    if (BF(T) == -2)
      if (strcmp(x.name, T->right->data.name) > 0)
        T = RR(T);
      else
        T = RL(T);
  }
  else if (strcmp(x.name, T->data.name) < 0) // insert in right subtree
  {
    T->left = insert(T->left, x);
    if (BF(T) == 2)
      if (strcmp(x.name, T->left->data.name) < 0)
        T = LL(T);
      else
        T = LR(T);
  }
  T->ht = height(T);
  return (T);
}

bool search(node *T, char *str)
{
  bool response = false;
  if (T != NULL)
  {
    int comparisonValue = strcmp(str, T->data.name);
    if (comparisonValue == 0)
    {
      response = true;
    }
    else if (comparisonValue == -1)
    {
      response = search(T->left, str);
    }
    else if (comparisonValue == 1)
    {
      response = search(T->right, str);
    }
  }
  return response;
}

bool searchPrinting(node *T, char *str)
{
  bool response = false;
  if (T != NULL)
  {
    int comparisonValue = strcmp(str, T->data.name);
    if (comparisonValue == 0)
    {
      response = true;
    }
    else if (comparisonValue < 0)
    {
      printf(" esq");
      response = searchPrinting(T->left, str);
    }
    else if (comparisonValue >0)
    {
      printf(" dir");
      response = searchPrinting(T->right, str);
    }
  }
  return response;
}

// < ------------ MAIN   ------------ >

int main()
{
  char word[1024];
  bool controller = false;
  scanf("%[^\n]%*c", word);
  node *root = NULL;
  do
  {
    controller = IsAble(word);
    if (!controller)
    {
      Player toInsert = GetPlayerFromLine(word);
      root = insert(root, toInsert);
    }
    scanf("%[^\n]%*c", word);
  } while (!controller);
  do
  {
    controller = IsAble(word);
    if (!controller)
    {
      printf("%s raiz", word);
      bool val = searchPrinting(root, word);
      if (val)
      {
        printf(" SIM");
      }
      else
      {
        printf(" NAO");
      }
      printf("\n");
    }
    scanf("%[^\n]%*c", word);
  } while (!controller);
  return 0;
}