import java.io.FileWriter;
import java.io.IOException;

public class EjecutadorCsv {
    public static void main(String[] args) {
        final int pasoEstudiantes = 100;
        final int maxEstudiantes = 100;
        final int evaluaciones = 10;
        final int repeticiones = 10;

        try (FileWriter csv = new FileWriter("Resultados_Experimento.csv")) {
            csv.write("CantidadEstudiantes;"
                    + "TiempoVarianzaEstudiantesNoOptimizado;"
                    + "TiempoMaximoEvaluacionNoOptimizado;"
                    + "TiempoPromedioEvaluacionNoOptimizado;"
                    + "TiempoVarianzaEvaluacionNoOptimizado;"
                    + "TiempoPromedioEstudianteNoOptimizado;"
                    + "TiempoPromediosEstudiantesNoOptimizado;"
                    + "TiempoPromedioEvaluacionesNoOptimizado;"
                    + "TiempoPromediosEstudiantesOptimizado;"
                    + "TiempoPromedioEvaluacionesOptimizado;"
                    + "TamanoDatosBits\n");

            long[] tVarEstNoOpt = new long[maxEstudiantes + 1];
            long[] tMaxEvalNoOpt = new long[maxEstudiantes + 1];
            long[] tPromEvalNoOpt = new long[maxEstudiantes + 1];
            long[] tVarEvalNoOpt = new long[maxEstudiantes + 1];
            long[] tPromEstNoOpt = new long[maxEstudiantes + 1];
            long[] tPromEstudiantesNoOpt = new long[maxEstudiantes + 1];
            long[] tPromEvalNoOptTotal = new long[maxEstudiantes + 1];

            long[] tPromEstOpt = new long[maxEstudiantes + 1];
            long[] tPromEvalOpt = new long[maxEstudiantes + 1];


            AnalizadorDeNotas warmUp = new AnalizadorDeNotas(100, evaluaciones);
            warmUp.calcularVarianzaEstudiantes();
            warmUp.encontrarMaximo(0);
            warmUp.calcularPromedioEvaluacion(0);
            warmUp.calcularVarianzaEvaluacion(0);
            warmUp.calcularPromedioEstudiante(0);
            warmUp.calcularPromediosEstudiantes();
            warmUp.calcularPromedioEvaluaciones(warmUp.getEvaluaciones());
            warmUp.calcularPromediosEstudiantesOptimizado();
            warmUp.calcularPromedioEvaluacionesOptimizado();

            for (int rep = 0; rep < repeticiones; rep++) {
                for (int i = 1; i <= maxEstudiantes; i++) {
                    int n = i * pasoEstudiantes;
                    AnalizadorDeNotas analizador = new AnalizadorDeNotas(n, evaluaciones);

                    long inicio, fin;

                    // Métodos no optimizados
                    inicio = System.nanoTime();
                    analizador.calcularVarianzaEstudiantes();
                    fin = System.nanoTime();
                    tVarEstNoOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.encontrarMaximo(0);
                    fin = System.nanoTime();
                    tMaxEvalNoOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.calcularPromedioEvaluacion(0);
                    fin = System.nanoTime();
                    tPromEvalNoOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.calcularVarianzaEvaluacion(0);
                    fin = System.nanoTime();
                    tVarEvalNoOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.calcularPromedioEstudiante(0);
                    fin = System.nanoTime();
                    tPromEstNoOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.calcularPromediosEstudiantes();
                    fin = System.nanoTime();
                    tPromEstudiantesNoOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.calcularPromedioEvaluaciones(analizador.getEvaluaciones());
                    fin = System.nanoTime();
                    tPromEvalNoOptTotal[i] += (fin - inicio);

                    // Métodos optimizados
                    inicio = System.nanoTime();
                    analizador.calcularPromediosEstudiantesOptimizado();
                    fin = System.nanoTime();
                    tPromEstOpt[i] += (fin - inicio);

                    inicio = System.nanoTime();
                    analizador.calcularPromedioEvaluacionesOptimizado();
                    fin = System.nanoTime();
                    tPromEvalOpt[i] += (fin - inicio);
                }
            }

            for (int i = 1; i <= maxEstudiantes; i++) {
                int n = i * pasoEstudiantes;
                long tamanoBits = n * evaluaciones * 64L;

                csv.write(n + ";"
                        + (tVarEstNoOpt[i]/repeticiones) + ";"
                        + (tMaxEvalNoOpt[i]/repeticiones) + ";"
                        + (tPromEvalNoOpt[i]/repeticiones) + ";"
                        + (tVarEvalNoOpt[i]/repeticiones) + ";"
                        + (tPromEstNoOpt[i]/repeticiones) + ";"
                        + (tPromEstudiantesNoOpt[i]/repeticiones) + ";"
                        + (tPromEvalNoOptTotal[i]/repeticiones) + ";"
                        + (tPromEstOpt[i]/repeticiones) + ";"
                        + (tPromEvalOpt[i]/repeticiones) + ";"
                        + tamanoBits + "\n");
            }

            System.out.println("CSV generado: Resultados_Experimento.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
