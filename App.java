import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }
}

public class App {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = inicializarFuncionarios();

        removerFuncionario(funcionarios, "João");

        aumentoSalario(funcionarios, BigDecimal.valueOf(0.1));

        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparFuncionariosPorFuncao(funcionarios);

        funcionariosAgrupadosPorFuncao(funcionariosPorFuncao);

        aniversariantesMeses(funcionarios, 10, 12);

        funcionarioMaisVelho(funcionarios);

        ordenarFuncionariosPorNome(funcionarios);

        funcionariosOrdenadosPorNome(funcionarios);

        totalSalarios(funcionarios);

        quantidadeSalariosMinimos(funcionarios, new BigDecimal("1212.00"));
    }

    private static List<Funcionario> inicializarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios
                .add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios
                .add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
        return funcionarios;
    }

    private static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Funcionários:");
        for (Funcionario func : funcionarios) {
            System.out.println("Nome: " + func.getNome());
            System.out.println("Data de Nascimento: " + func.getDataNascimento().format(dateFormatter));
            System.out.println("Salário: " + formatarSalario(func.getSalario()));
            System.out.println("Função: " + func.getFuncao());
            System.out.println();
        }
    }

    private static void aumentoSalario(List<Funcionario> funcionarios, BigDecimal percentual) {
        for (Funcionario func : funcionarios) {
            BigDecimal novoSalario = func.getSalario().multiply(BigDecimal.ONE.add(percentual));
            func.setSalario(novoSalario);
        }
    }

    private static Map<String, List<Funcionario>> agruparFuncionariosPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario func : funcionarios) {
            String funcao = func.getFuncao();
            funcionariosPorFuncao.computeIfAbsent(funcao, k -> new ArrayList<>()).add(func);
        }
        return funcionariosPorFuncao;
    }

    private static void funcionariosAgrupadosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        System.out.println("Funcionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario func : entry.getValue()) {
                System.out.println(" - " + func.getNome());
            }
            System.out.println();
        }
    }

    private static void aniversariantesMeses(List<Funcionario> funcionarios, int... meses) {
        System.out.println("Funcionários que fazem aniversário nos meses:");
        for (Funcionario func : funcionarios) {
            int mes = func.getDataNascimento().getMonthValue();
            for (int mesBuscado : meses) {
                if (mes == mesBuscado) {
                    System.out.println(" - " + func.getNome() + ", Mês: " + mes);
                    break;
                }
            }
        }
        System.out.println();
    }

    private static void funcionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        long idadeMaisVelho = ChronoUnit.YEARS.between(maisVelho.getDataNascimento(), LocalDate.now());
        System.out.println("Funcionário mais velho: " + maisVelho.getNome() + ", Idade: " + idadeMaisVelho);
        System.out.println();
    }

    private static void ordenarFuncionariosPorNome(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
    }

    private static void funcionariosOrdenadosPorNome(List<Funcionario> funcionarios) {
        System.out.println("Funcionários em ordem alfabética:");
        for (Funcionario func : funcionarios) {
            System.out.println(func.getNome());
        }
        System.out.println();
    }

    private static void totalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario func : funcionarios) {
            totalSalarios = totalSalarios.add(func.getSalario());
        }
        System.out.println("Total dos salários: " + formatarSalario(totalSalarios));
        System.out.println();
    }

    private static void quantidadeSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        System.out.println("Quantidade de salários mínimos por funcionário:");
        for (Funcionario func : funcionarios) {
            BigDecimal quantidadeSalariosMinimos = func.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(func.getNome() + ": " + quantidadeSalariosMinimos + " salários mínimos");
        }
    }

    private static String formatarSalario(BigDecimal salario) {
        return String.format("%,.2f", salario).replace(".", "X").replace(",", ".").replace("X", ",");
    }
}