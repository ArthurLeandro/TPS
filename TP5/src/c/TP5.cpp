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

#define  TAMANHO  25
Player tabela[TAMANHO][100];    
int n[25];               // Quantidade de array.
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

void start(){
  for (int i = 0; i < TAMANHO; i++)
  {
    for (int j = 0; j < 100; j++)
    {
      tabela[i][j] = NULL;
    }
  }
}

// < ------------ MAIN   ------------ >

int main()
{
  char word[1024];
  bool controller = false;
  scanf("%[^\n]%*c", word);
  do
  {
    controller = IsAble(word);
    if (!controller)
    {
      Player toInsert = GetPlayerFromLine(word);
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
        printf(" SIM");
      else
        printf(" NAO");
      printf("\n");
    }
    scanf("%[^\n]%*c", word);
  } while (!controller);
  return 0;
}