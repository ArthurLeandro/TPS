

#include <math.h>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

typedef struct Book
{
  char code[4];
} book;

int main()
{

  char word[1024];

  Book array[1000];
  do
  {
    scanf("%[^\n]%*c", word);
    printf("%s\n", word);
    if ((int)word[0] > 0)
    {
      int amountOfTimes = atoi(word);
      int pointer = 0;
      for (int i = 0; i < amountOfTimes; i++)
      {
        char newC[4];
        scanf("%[^\n]%*c", newC);
        strcpy(array[i].code, newC);
        //printf("%s\n", array[i].code);
        pointer++;
      }
      for (int i = 1; i < pointer; i++)
      {
        Book tmp = array[i];
        int j = i - 1;

        while ((j >= 0) && (strcmp(array[j].code, tmp.code) > 0))
        {
          array[j + 1] = array[j];
          j--;
        }
        array[j + 1] = tmp;
      }
      for (int i = 0; i < pointer; i++)
      {
        printf("%c%c%c%c\n", array[i].code[0], array[i].code[1], array[i].code[2], array[i].code[3]);
      }
    }
  } while ((int)word[0] >= 0);
  return 0;
}
