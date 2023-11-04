public class TabelaHash {
    private Registro[] tabela;
    private int tamanho;
    private int quantidadeElementos;
    private int totalColisoes;
    private int totalComparacoes;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.quantidadeElementos = 0;
        this.totalColisoes = 0;
        this.totalComparacoes = 0;
    }

    public int hash(int cod_registro) {
        return cod_registro % tamanho;
    }

    public void insere(int cod_registro) {
        int hash = hash(cod_registro);
        int colisoes = 0;

        if (quantidadeElementos >= tamanho / 2) {
            rehash();
            hash = hash(cod_registro);
        }

        Registro novoRegistro = new Registro(cod_registro);

        if (tabela[hash] == null) {
            tabela[hash] = novoRegistro;
        } else {
            int novoHash = (hash + 1) % tamanho;
            while (tabela[novoHash] != null) {
                novoHash = (novoHash + 1) % tamanho;
                colisoes++;
            }
            tabela[novoHash] = novoRegistro;
        }

        quantidadeElementos++;
        totalColisoes += colisoes;
    }

    public Registro busca(int cod_registro) {
        int hash = hash(cod_registro);
        int comparacoes = 0;

        while (tabela[hash] != null) {
            comparacoes++;
            if (tabela[hash].getCodRegistro() == cod_registro) {
                totalComparacoes += comparacoes;
                return tabela[hash];
            }
            hash = (hash + 1) % tamanho;
        }

        totalComparacoes += comparacoes;
        return null;
    }

    public void remove(int cod_registro) {
        int hash = hash(cod_registro);

        while (tabela[hash] != null) {
            if (tabela[hash].getCodRegistro() == cod_registro) {
                tabela[hash] = null;
                quantidadeElementos--;
                return;
            }
            hash = (hash + 1) % tamanho;
        }
    }

    private void rehash() {
        int novoTamanho = tamanho * 2;
        Registro[] novaTabela = new Registro[novoTamanho];

        for (Registro registro : tabela) {
            if (registro != null) {
                int novoHash = hash(registro.getCodRegistro()) % novoTamanho;
                while (novaTabela[novoHash] != null) {
                    novoHash = (novoHash + 1) % novoTamanho;
                }
                novaTabela[novoHash] = registro;
            }
        }

        tabela = novaTabela;
        tamanho = novoTamanho;
    }

    public int getColisoes() {
        return totalColisoes;
    }

    public int getComparacoes() {
        return totalComparacoes;
    }
}
