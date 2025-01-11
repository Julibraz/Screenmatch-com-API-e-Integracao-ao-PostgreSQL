package br.com.alura.Screenmatch.principal;

import br.com.alura.Screenmatch.model.Categoria;
import br.com.alura.Screenmatch.model.DadosSerie;
import br.com.alura.Screenmatch.model.DadosTemporada;
import br.com.alura.Screenmatch.model.Episodio;
import br.com.alura.Screenmatch.model.Serie;
import br.com.alura.Screenmatch.repository.SerieRepository;
import br.com.alura.Screenmatch.service.ConsumoAPI;
import br.com.alura.Screenmatch.service.ConverteDados;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {
    private Scanner scan;
    private ConsumoAPI consumo;
    private ConverteDados conversor;
    private final String ENDERECO;
    private final String API_KEY;
    private List<DadosSerie> dadosSeries;
    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieBusca;

    public Principal(SerieRepository repositorio) {
        this.scan = new Scanner(System.in);
        this.consumo = new ConsumoAPI();
        this.conversor = new ConverteDados();
        this.ENDERECO = "http://www.omdbapi.com/?t=";
        this.API_KEY = System.getenv("OMDB_APIKEY");
        this.dadosSeries = new ArrayList();
        this.series = new ArrayList();
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        int opcao = -1;

        while(opcao != 0) {
            String menu = "1 - Buscar séries\n2 - Buscar episódios\n3 - Listar séries buscadas\n4 - Buscar série por titulo\n5 - Buscar serie por ator\n6 - Top 5 séries\n7 - Buscar séries por categoria\n8 - Filtrar séries\n9 - Buscar episódio por trecho\n10 - Top 5 episodios por série\n11 - Buscar episodios a partir de uma data\n0 - Sair\n";
            System.out.println(menu);
            opcao = this.scan.nextInt();
            this.scan.nextLine();
            switch (opcao) {
                case 0:
                    System.out.println("Saindo...");
                    break;
                case 1:
                    this.buscarSerieWeb();
                    break;
                case 2:
                    this.buscarEpisodioPorSerie();
                    break;
                case 3:
                    this.listarSeriesBuscadas();
                    break;
                case 4:
                    this.buscarSeriePorTitulo();
                    break;
                case 5:
                    this.buscarSeriePorAtor();
                    break;
                case 6:
                    this.buscarTop5Series();
                    break;
                case 7:
                    this.buscarSeriePorCategoria();
                    break;
                case 8:
                    this.filtrarSeries();
                    break;
                case 9:
                    this.buscarEpisodioPorTrecho();
                    break;
                case 10:
                    this.topEpisodiosPorSerie();
                    break;
                case 11:
                    this.buscarEpisodiosApartirDeData();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }

    }

    private void buscarSerieWeb() {
        DadosSerie dados = this.getDadosSerie();
        Serie serie = new Serie(dados);
        this.repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = this.scan.nextLine();
        ConsumoAPI var10000 = this.consumo;
        String var10001 = nomeSerie.replace(" ", "+");
        String json = var10000.obterDados("http://www.omdbapi.com/?t=" + var10001 + "&apikey=" + this.API_KEY);
        DadosSerie dados = (DadosSerie)this.conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        this.listarSeriesBuscadas();
        System.out.print("\nEscolha a serie: ");
        String nomeSerie = this.scan.nextLine();
        System.out.println();
        Optional<Serie> serie = this.repositorio.findByTituloContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()) {
            Serie serieEncontrada = (Serie)serie.get();
            List<DadosTemporada> temporadas = new ArrayList();

            for(int i = 1; i <= serieEncontrada.getTotalTemporadas(); ++i) {
                String json = this.consumo.obterDados("http://www.omdbapi.com/?t=" + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + "&apikey=" + this.API_KEY);
                DadosTemporada dadosTemporada = (DadosTemporada)this.conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            PrintStream var10001 = System.out;
            Objects.requireNonNull(var10001);
            temporadas.forEach(var10001::println);
            List<Episodio> episodios = (List)temporadas.stream().flatMap((d) -> d.episodios().stream().map((e) -> new Episodio(d.numero(), e))).collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            this.repositorio.save(serieEncontrada);
        } else {
            System.out.println("Serie nao encontrada");
        }

    }

    private void listarSeriesBuscadas() {
        this.series = this.repositorio.findAll();
        Stream var10000 = this.series.stream().sorted(Comparator.comparing(Serie::getGenero));
        PrintStream var10001 = System.out;
        Objects.requireNonNull(var10001);
        var10000.forEach(var10001::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println();
        System.out.print("Digite o nome da série que deseja buscar: ");
        String nomeSerie = this.scan.nextLine();
        this.serieBusca = this.repositorio.findByTituloContainingIgnoreCase(nomeSerie);
        System.out.println();
        if (this.serieBusca.isPresent()) {
            System.out.println("Dados da série: " + String.valueOf(this.serieBusca.get()));
        } else {
            System.out.println("Serie nao encontrada");
        }

        System.out.println();
    }

    private void buscarSeriePorAtor() {
        System.out.print("Informe o nome do ator: ");
        String nomeAtor = this.scan.nextLine();
        System.out.print("Avaliações a partir de: ");
        double avaliacao = this.scan.nextDouble();
        List<Serie> seriesEncontradas = this.repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("\nSeries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach((s) -> {
            PrintStream var10000 = System.out;
            String var10001 = s.getTitulo();
            var10000.println(var10001 + " avaliaçao: " + s.getAvaliacao());
        });
        System.out.println();
    }

    private void buscarTop5Series() {
        List<Serie> topSeries = this.repositorio.findTop5ByOrderByAvaliacaoDesc();
        System.out.println();
        topSeries.forEach((s) -> {
            PrintStream var10000 = System.out;
            String var10001 = s.getTitulo();
            var10000.println(var10001 + " avaliaçao: " + s.getAvaliacao());
        });
        System.out.println();
    }

    private void buscarSeriePorCategoria() {
        System.out.print("Informe a categoria que deseja buscar: ");
        String nomeCategoria = this.scan.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeCategoria);
        List<Serie> seriesPorCategoria = this.repositorio.findByGenero(categoria);
        System.out.println("\nSéries de " + nomeCategoria + ": ");
        PrintStream var10001 = System.out;
        Objects.requireNonNull(var10001);
        seriesPorCategoria.forEach(var10001::println);
        System.out.println();
    }

    private void filtrarSeries() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        int totalTemporadas = this.scan.nextInt();
        this.scan.nextLine();
        System.out.println("Com avaliação a partir de que valor? ");
        double avaliacao = this.scan.nextDouble();
        this.scan.nextLine();
        List<Serie> filtroSeries = this.repositorio.seriesPortemporadaEAvaliacao(totalTemporadas, avaliacao);
        System.out.println("\n*** Séries filtradas ***");
        filtroSeries.forEach((s) -> {
            PrintStream var10000 = System.out;
            String var10001 = s.getTitulo();
            var10000.println(var10001 + "  - avaliação: " + s.getAvaliacao());
        });
        System.out.println();
    }

    private void buscarEpisodioPorTrecho() {
        System.out.print("Informe o trecho do episodio: ");
        String trechoEpisodio = this.scan.nextLine();
        List<Episodio> episodiosEncontrados = this.repositorio.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrados.forEach((e) -> System.out.printf("Série: %s Temporada %s - Episodio %s - %s\n", e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie() {
        this.buscarSeriePorTitulo();
        if (this.serieBusca.isPresent()) {
            Serie serie = (Serie)this.serieBusca.get();
            List<Episodio> topEpisodios = this.repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach((e) -> System.out.printf("Série: %s Temporada %s - Episodio %s - %s. Avaliação: %s\n", e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
            System.out.println();
        }

    }

    private void buscarEpisodiosApartirDeData() {
        this.buscarSeriePorTitulo();
        if (this.serieBusca.isPresent()) {
            Serie serie = (Serie)this.serieBusca.get();
            System.out.println("Informe o ano limite de lançamento: ");
            int anoLancamento = this.scan.nextInt();
            this.scan.nextLine();
            List<Episodio> episodiosAno = this.repositorio.episodioPorSerieAno(serie, anoLancamento);
            PrintStream var10001 = System.out;
            Objects.requireNonNull(var10001);
            episodiosAno.forEach(var10001::println);
        }

    }
}
