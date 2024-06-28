import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
    public static void main(String[] args) {
        String pessoasCsv = "src/Pessoas.csv";
        String enderecosCsv = "src/Enderecos.csv";
        String outputCsv = "src/PessoasComEnderecos.csv";

        Map<Integer, String> pessoas = new HashMap<>();
        Map<Integer, List<String>> enderecos = new HashMap<>();

        try (BufferedReader brPessoas = new BufferedReader(new FileReader(pessoasCsv))) {
            String linha;
            while ((linha = brPessoas.readLine()) != null) {
                String[] dados = linha.split(";");
                int id = Integer.parseInt(dados[0].trim());
                String nome = dados[1].trim();
                pessoas.put(id, nome);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader brEnderecos = new BufferedReader(new FileReader(enderecosCsv))) {
            String linha;
            while ((linha = brEnderecos.readLine()) != null) {
                String[] dados = linha.split(";");
                String rua = dados[0].trim();
                String cidade = dados[1].trim();
                int idPessoa = Integer.parseInt(dados[2].trim());

                if (!enderecos.containsKey(idPessoa)) {
                    enderecos.put(idPessoa, new ArrayList<>());
                }
                enderecos.get(idPessoa).add(rua + ", " + cidade);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputCsv))) {
            writer.write("Nome;Endereço\n");
            for (Map.Entry<Integer, String> pessoa : pessoas.entrySet()) {
                int id = pessoa.getKey();
                String nome = pessoa.getValue();
                List<String> enderecosPessoa = enderecos.get(id);

                if (enderecosPessoa != null) {
                    for (String endereco : enderecosPessoa) {
                        writer.write(nome + ";" + endereco + "\n");
                    }
                } else {
                    writer.write(nome + ";Sem endereço\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Arquivo PessoasComEnderecos.csv gerado com sucesso.");
    }
}
