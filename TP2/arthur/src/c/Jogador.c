#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct
{

    int id;
    int peso;
    int altura;
    char nome[40];
    char universidade[40];
    char anoNascimento[7];
    char cidadeNascimento[40];
    char estadoNascimento[40];

} Jogador;

Jogador Clone(Jogador j)
{
    Jogador retorno;

    retorno.id = j.id;
    retorno.peso = j.peso;
    retorno.altura = j.altura;
    strcpy(retorno.nome, j.nome);
    strcpy(retorno.universidade, j.universidade);
    strcpy(retorno.cidadeNascimento, j.cidadeNascimento);
    strcpy(retorno.estadoNascimento, j.estadoNascimento);

    return retorno;
}

void Imprimir(Jogador j)
{
    for (int i = 0; i < 40; i++)
    {
        if ((int)j.estadoNascimento[i] == 0)
        {
            int index = 0;
            if (strcmp(j.estadoNascimento, "nao informado") != 0)
                index = i - 1;
            else
                index = i;
            i += 40;
            j.estadoNascimento[index] = ']';
        }
    }
    printf("[%d ## %s ## %d ## %d ## %s ## %s ## %s ## %s", j.id, j.nome, j.peso, j.altura, j.anoNascimento, j.universidade, j.cidadeNascimento, j.estadoNascimento);
    printf("\n");
}

Jogador JogadorConstrutor(int cont, Jogador *j, char str[])
{

    switch (cont)
    {

    case 0:
        j->id = atoi(str); //converte string em inteiro
        break;

    case 1:
        strcpy(j->nome, str);
        break;

    case 2:
        j->altura = atoi(str);
        break;

    case 3:
        j->peso = atoi(str);
        break;

    case 4:
        strcpy(j->universidade, str);
        break;

    case 5:
        strcpy(j->anoNascimento, str);
        break;

    case 6:
        strcpy(j->cidadeNascimento, str);
        break;

    case 7:
        strcpy(j->estadoNascimento, str);
        break;
    }

    return *j;
}

Jogador *StringToJogador(char *str)
{

    Jogador *J = (Jogador *)malloc(sizeof(Jogador));

    char *strAux = strtok(str, ",");
    int cont = 0;

    while (strAux)
    {
        if ((int)strAux[0] < 48)
        {

            JogadorConstrutor(cont, J, "nao informado");
        }
        else
        {

            JogadorConstrutor(cont, J, strAux);
        }
        cont++;
        strAux = strtok(NULL, ",");
    }
    if ((int)J->estadoNascimento[0] < 48)
    {

        strcpy(J->estadoNascimento, "nao informado");
    }

    return J;
}

int main()
{

    char str[1024];
    char strAux[1024];

    scanf(" %[^\n]s", str);

    while (str[0] != 'F' || str[1] != 'I' || str[2] != 'M')
    {

        FILE *ler = fopen("players.csv", "r");

        for (int i = 0; i <= atoi(str) + 1; i++)
        {
            fgets(strAux, 1024, ler);
        }
        Jogador *j = StringToJogador(strAux);

        Imprimir(*(j));

        scanf(" %[^\n]s", str);
    }
}