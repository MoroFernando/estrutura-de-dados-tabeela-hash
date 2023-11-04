import java.util.Random;

public class App {
    public static void main(String[] args) {
        int[] tamanhos = {10, 100, 1000, 10000, 100000};
        String[] funcoesHash = {"RestoDivisao", "Multiplicacao", "Dobramento"};
        int[] conjuntosDeDados = {20000, 100000, 500000, 1000000, 5000000};

        for (int i = 0; i < tamanhos.length; i++) {
            int tamanho = tamanhos[i];

            for (int j = 0; j < funcoesHash.length; j++) {
                String funcaoHash = funcoesHash[j];
                TabelaHash tabelaHash = new TabelaHash(tamanho);
                long tempoInsercaoTotal = 0;
                long totalColisoes = 0;
                long tempoBuscaTotal = 0;
                long totalComparacoes = 0;

                for (int k = 0; k < conjuntosDeDados.length; k++) {
                    int conjuntoDeDados = conjuntosDeDados[k];
                    Registro[] dados = gerarConjuntoDados(conjuntoDeDados);
                    
                    long tempoInicioInsercao = System.nanoTime();
                    long colisoes = inserirElementos(tabelaHash, dados, funcaoHash);
                    long tempoFimInsercao = System.nanoTime();
                    tempoInsercaoTotal += (tempoFimInsercao - tempoInicioInsercao);
                    totalColisoes += colisoes;

                    long tempoInicioBusca = System.nanoTime();
                    long comparacoes = buscarElementos(tabelaHash, dados, funcaoHash);
                    long tempoFimBusca = System.nanoTime();
                    tempoBuscaTotal += (tempoFimBusca - tempoInicioBusca);
                    totalComparacoes += comparacoes;
                }

                double tempoInsercaoSegundos = (double) tempoInsercaoTotal / 1e9;
                double tempoBuscaSegundos = (double) tempoBuscaTotal / 1e9;

                System.out.println("Tamanho: " + tamanho + " / Funcao Hash: " + funcaoHash);
                System.out.println("Tempo medio de insercao: " + tempoInsercaoSegundos + " segundos");
                System.out.println("Total de colisoes: " + totalColisoes);
                System.out.println("Tempo medio de busca: " + tempoBuscaSegundos + " segundos");
                System.out.println("Total de comparacoes: " + totalComparacoes);
                System.out.println("---------------------------------");
            }
        }
    }

    private static Registro[] gerarConjuntoDados(int tamanho) {
        Registro[] dados = new Registro[tamanho];
        Random rand = new Random();
        for (int i = 0; i < tamanho; i++) {
            int codigo = 100000000 + rand.nextInt(900000000);
            dados[i] = new Registro(codigo);
        }
        return dados;
    }

    private static long inserirElementos(TabelaHash tabelaHash, Registro[] dados, String funcaoHash) {
        long colisoes = 0;
        for (Registro dado : dados) {
            int codigo = dado.getCodRegistro();
            int hash = calcularHash(codigo, funcaoHash);
            Registro existente = tabelaHash.busca(codigo);
            if (existente != null) {
                colisoes++;
            }
            tabelaHash.insere(codigo);
        }
        return colisoes;
    }

    private static long buscarElementos(TabelaHash tabelaHash, Registro[] dados, String funcaoHash) {
        long comparacoes = 0;
        for (Registro dado : dados) {
            int codigo = dado.getCodRegistro();
            tabelaHash.busca(codigo);
            comparacoes += tabelaHash.getComparacoes();
        }
        return comparacoes;
    }

    private static int calcularHash(int codigo, String funcaoHash) {
        int tamanho = 0; 
        return tamanho;
    }
}
