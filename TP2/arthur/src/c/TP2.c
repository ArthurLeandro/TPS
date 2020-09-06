#include <stdio.h>
#include <math.h>
#include <time.h>
#include <stdlib.h>

typedef struct Players
{

} Player;
void SwapInt(const void *elementI, const void *elementJ)
{
	int temp = (*(int *)elementI);
	(*(int *)elementI) = (*(int *)elementJ);
	(*(int *)elementJ) = temp;
}
void PrintArrayInt(void *array, int amountOfElement)
{
	for (int i = 0; i < amountOfElement; i++)
	{
		PrintElement((Player *)array[i]);
	}
}
void PrintElement(Player *_player)
{
	printf("\n");
}
void SelectionSort(int *array, int n, void (*swap)(const void *, const void *))
{
	for (int i = 0; i < (n - 1); i++)
	{
		int menor = i;
		for (int j = (i + 1); j < n; j++)
		{
			if (array[menor] > array[j])
			{
				menor = j;
			}
		}
		swap(&array[menor], &array[i]);
	}
}