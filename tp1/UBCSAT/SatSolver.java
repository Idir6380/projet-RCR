import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SatSolver {
    private static final int MAX_LINE_LENGTH = 1000;

    public static void main(String[] args) {
        try {
            // ouvrir le fichier de la base de connaissances en mode lecture
            BufferedReader reader = new BufferedReader(new FileReader("test.cnf"));

            StringBuilder content = new StringBuilder();
            String line = reader.readLine(); // lire la premiere ligne
            String firstLine = line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            reader.close();

            // ouvrir le fichier de la base de connaissances en mode ecriture
            BufferedWriter writer = new BufferedWriter(new FileWriter("test.cnf"));

            // modifier la premiere ligne
            writer.write("p cnf 5 10\n"); // nouvelle premiere ligne avec le nombre de clauses modifie

            // copier le contenu lu du fichier
            writer.write(content.toString());

            // ajouter une nouvelle formule a la base de connaissances
            int newA = -1;
            int newB = -2;
            writer.write(String.format("%d %d 0", newA, newB));

            writer.close();

            // appeler le solveur SAT avec la base de connaissances resultante
            ProcessBuilder processBuilder = new ProcessBuilder("ubcsat", "-alg", "saps", "-i", "test.cnf", "-solve");
            Process process = processBuilder.start();

            BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String processLine;
            while ((processLine = processReader.readLine()) != null) {
                System.out.println(processLine);
            }

            // Attendre que le processus se termine
            int exitCode = process.waitFor();
            System.out.println("Processus terminé avec le code de sortie : " + exitCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}