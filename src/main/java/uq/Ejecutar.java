package uq;

import uq.bloques.BloquesIII;
import uq.bloques.BloquesIV;
import uq.bloques.BloquesV;
import uq.clasicos.Naiv;
import uq.clasicos.Strassen;
import uq.clasicos.Winograd;

public class Ejecutar {

    static Utilidades utilidades = new Utilidades();
    static Naiv naiv = new Naiv();
    static Winograd winograd = new Winograd();
    static Strassen strassen = new Strassen();
    static BloquesIII bloquesIII = new BloquesIII();
    static BloquesIV bloquesIV = new BloquesIV();
    static BloquesV bloquesV = new BloquesV();

    public static void iniciar(String caso, String nombre, int n, int[][] A, int[][] B, int[][] C)throws Exception{


        utilidades.limpiar(C);

        long inicio = System.nanoTime();

        switch (nombre) {
            case "NaivOnArray": naiv.NaivOnArray(A,B,C,n); break;
            case "NaivLoopUnrollingTwo": naiv.NaivLoopUnrollingTwo(A,B,C,n); break;
            case "NaivLoopUnrollingFour": naiv.NaivLoopUnrollingFour(A,B,C,n); break;
            case "WinogradOriginal": winograd.WinogradOriginal(A,B,C,n); break;
            case "WinogradScaled": winograd.winogradScaled(A,B,C,n); break;
            case "StrassenNaiv": utilidades.copiar(strassen.strassen(A,B),C);break;
            case "StrassenWinograd": utilidades.copiar(strassen.strassenWinograd(A,B),C); break;
            case "SequentialBlockIII": bloquesIII.SequentialBlockIII(A,B,C,n); break;
            case "ParallelBlockIII": bloquesIII.ParallelBlockIII(A,B,C,n); break;
            case "EnhancedParallelBlockIII": bloquesIII.EnhancedParallelBlockIII(A,B,C,n); break;

            case "SequentialBlockIV": bloquesIV.SequentialBlockIV(A,B,C,n); break;
            case "ParallelBlockIV": bloquesIV.ParallelBlockIV(A,B,C,n); break;
            case "EnhancedParallelBlockIV": bloquesIV.EnhancedParallelBlockIV(A,B,C,n); break;

            case "SequentialBlockV": bloquesV.SequentialBlockV(A,B,C,n); break;
            case "ParallelBlockV": bloquesV.ParallelBlockV(A,B,C,n); break;
        }

        long fin = System.nanoTime();
        double tiempo = (fin - inicio) / 1_000_000.0;

        long check = utilidades.checksum(C);
        System.out.println(nombre + ": " + tiempo + " ms | checksum: " + check);
        utilidades.guardarTiempo(caso,nombre,n,tiempo);
        Thread.sleep(50);
    }
}
