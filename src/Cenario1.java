import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cenario1 {
    static File arquivo = new File("src/grafo01.txt");
    static int INFINITO = 9999;
    static int[] vertice_central = {0, INFINITO, INFINITO};

    public static void main(String[] args) throws FileNotFoundException {
        int linhas = 0;

        Scanner scan1 = new Scanner(arquivo);
        Scanner scan2 = new Scanner(arquivo);

        while (scan1.hasNextLine()) {
            scan1.nextLine();
            linhas++;
        }

        int[][] grafo = new int[linhas][3];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0 && j == 2) {
                    grafo[i][j] = 0;
                }
                else {
                    grafo[i][j] = scan2.nextInt();
                }
            }
        }

        int v1, v2, p;
        int qtd_vertices = grafo[0][0];
        int qtd_arestas = grafo[0][1];
        int[][] matrizAdjacencias = new int[qtd_vertices+1][qtd_vertices+1];

        // Monta uma matriz de adjacência a partir da tabela
        for(int i = 1; i <= qtd_arestas; i++) {
            v1 = grafo[i][0];
            v2 = grafo[i][1];
            p = grafo[i][2];

            matrizAdjacencias[v1][v2] = p;
            matrizAdjacencias[v2][v1] = p;
        }
        for(int i = 1; i <= qtd_vertices; i++) {
            for(int j = 1; j <= qtd_vertices; j++) {
                if (matrizAdjacencias[i][j] == 0 && i != j) {
                    matrizAdjacencias[i][j] = INFINITO;
                }
            }
        }

        for(int i = 1; i <= qtd_vertices; i++) {
            dijkstra(matrizAdjacencias, qtd_vertices, i);
        }

        System.out.println("\nVertice central: " + vertice_central[0]);
    }

    static void dijkstra(int[][] matrizAdjacencias, int qtd_vertices, int vertice) {
        int u;
        int[] distancias = new int[qtd_vertices+1];
        int[] marcados = new int[qtd_vertices+1];

        for (int i = 1; i <= qtd_vertices; i++) // Inicializa todas as distâncias como infinito
            distancias[i] = INFINITO;

        distancias[vertice] = 0;

        for (int i = 1; i < qtd_vertices; i++) {
            u = minDistance(distancias, marcados, qtd_vertices);

            if (u == INFINITO)
                break;

            else {
                marcados[u] = 1; // Marca o vértice como percorrido

                for (int v = 1; v <= qtd_vertices; v++)  {
                    if (marcados[v] == 0 && matrizAdjacencias[u][v] != 0 && distancias[u] + matrizAdjacencias[u][v] < distancias[v]) {
                        distancias[v] = distancias[u] + matrizAdjacencias[u][v];
                    }
                }
            }
        }

        verificarVertice(distancias, vertice, qtd_vertices);
    }

    static int minDistance(int[] distancias, int[] marcados, int qtd_vertices) {
        int min = INFINITO, min_index = -1, v;

        for (v = 1; v <= qtd_vertices; v++)
            if (marcados[v] == 0 && distancias[v] < min) {
                min = distancias[v];
                min_index = v;
            }
        return min == INFINITO ? INFINITO : min_index;
    }

    static void verificarVertice(int[] distancias, int vertice, int qtd_vertices) {
        int soma = 0, maior_distancia = 0;

        for (int i = 1; i <= qtd_vertices; i++) {
            soma += distancias[i];
            if (distancias[i] > maior_distancia) {
                maior_distancia = distancias[i];
            }
        }

        if (soma <= vertice_central[1]) {
            if (soma < vertice_central[1]) {
                vertice_central[0] = vertice;
                vertice_central[1] = soma;
                vertice_central[2] = maior_distancia;
            }

            else if (maior_distancia < vertice_central[2]) {
                vertice_central[0] = vertice;
                vertice_central[2] = maior_distancia;
            }
        }
    }
}
