package uq;

public class MainMatrices {

    static int[] tamanos = {512, 1024};
    static GeneradorMatrices generador = new GeneradorMatrices();
    static Utilidades utilidades = new Utilidades();

    public static void main(String[] args) throws Exception {

        utilidades.limpiarCSV();

        for (int n : tamanos) {
            String caso = "Caso_" + n;

            System.out.println("\n===== " + caso + " =====");

            int[][] A = generador.generarMatriz(n);
            int[][] B = generador.generarMatriz(n);
            int[][] C = new int[n][n];

            Ejecutar.iniciar(caso,"NaivOnArray", n, A, B, C);
            Ejecutar.iniciar(caso,"NaivLoopUnrollingTwo", n, A, B, C);
            Ejecutar.iniciar(caso,"NaivLoopUnrollingFour", n, A, B, C);
            Ejecutar.iniciar(caso,"WinogradOriginal", n, A, B, C);
            Ejecutar.iniciar(caso,"WinogradScaled", n, A, B, C);
            Ejecutar.iniciar(caso,"StrassenNaiv", n, A, B, C);
            Ejecutar.iniciar(caso,"StrassenWinograd", n, A, B, C);

            Ejecutar.iniciar(caso,"SequentialBlockIII", n, A, B, C);
            Ejecutar.iniciar(caso,"ParallelBlockIII", n, A, B, C);
            Ejecutar.iniciar(caso,"EnhancedParallelBlockIII", n, A, B, C);

            Ejecutar.iniciar(caso,"SequentialBlockIV", n, A, B, C);
            Ejecutar.iniciar(caso,"ParallelBlockIV", n, A, B, C);
            Ejecutar.iniciar(caso,"EnhancedParallelBlockIV", n, A, B, C);

            Ejecutar.iniciar(caso,"SequentialBlockV", n, A, B, C);
            Ejecutar.iniciar(caso,"ParallelBlockV", n, A, B, C);
        }
    }
}