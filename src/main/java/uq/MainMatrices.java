package uq;

import java.io.*;
import java.util.Random;
import java.util.stream.IntStream;

public class MainMatrices {

    static int[] tamanos = {256, 512};
    static int BSIZE = 64;

    public static void main(String[] args) throws Exception {

        new FileWriter("tiempos.csv").close();

        for (int n : tamanos) {
            String caso = "Caso_" + n;

            System.out.println("\n===== " + caso + " =====");

            int[][] A = generarMatriz(n);
            int[][] B = generarMatriz(n);
            int[][] C = new int[n][n];

            guardarMatriz(A, "A_" + n + ".txt");
            guardarMatriz(B, "B_" + n + ".txt");

            ejecutar(caso,"NaivOnArray", n, A, B, C);
            ejecutar(caso,"NaivLoopUnrollingTwo", n, A, B, C);
            ejecutar(caso,"NaivLoopUnrollingFour", n, A, B, C);
            ejecutar(caso,"WinogradOriginal", n, A, B, C);
            ejecutar(caso,"WinogradScaled", n, A, B, C);
            ejecutar(caso,"StrassenNaiv", n, A, B, C);
            ejecutar(caso,"StrassenWinograd", n, A, B, C);

            ejecutar(caso,"III.3 Sequential block", n, A, B, C);
            ejecutar(caso,"III.4 Parallel block", n, A, B, C);
            ejecutar(caso,"III.5 Enhanced Parallel block", n, A, B, C);

            ejecutar(caso,"IV.3 Sequential block", n, A, B, C);
            ejecutar(caso,"IV.4 Parallel block", n, A, B, C);
            ejecutar(caso,"IV.5 Enhanced Parallel block", n, A, B, C);

            ejecutar(caso,"V.3 Sequential block", n, A, B, C);
            ejecutar(caso,"V.4 Parallel block", n, A, B, C);
        }
    }

    // =========================
    // EJECUCIÓN
    // =========================
    public static void ejecutar(String caso,String nombre,int n,int[][] A,int[][] B,int[][] C)throws Exception{
        limpiar(C);

        long inicio = System.nanoTime();

        switch (nombre) {
            case "NaivOnArray": NaivOnArray(A,B,C,n); break;
            case "NaivLoopUnrollingTwo": NaivLoopUnrollingTwo(A,B,C,n); break;
            case "NaivLoopUnrollingFour": NaivLoopUnrollingFour(A,B,C,n); break;
            case "WinogradOriginal": WinogradOriginal(A,B,C,n); break;
            case "WinogradScaled": winogradScaled(A,B,C,n); break;
            case "StrassenNaiv": copiar(strassen(A,B),C); break;
            case "StrassenWinograd": copiar(strassenWinograd(A,B),C); break;

            case "SequentialBlockIII": SequentialBlockIII(A,B,C,n); break;
            case "ParallelBlockIII": ParallelBlock(A,B,C,n); break;
            case "EnhancedParallelBlock": enhancedIII(A,B,C,n); break;

            case "SequentialBlockIV": SequentialBlockIV(A,B,C,n); break;
            case "IV.4 Parallel block": parallelIV(A,B,C,n); break;
            case "IV.5 Enhanced Parallel block": enhancedIV(A,B,C,n); break;

            case "V.3 Sequential block": blockV(A,B,C,n); break;
            case "V.4 Parallel block": parallelV(A,B,C,n); break;
        }

        long fin = System.nanoTime();
        double tiempo = (fin - inicio) / 1_000_000.0;

        System.out.println(nombre + ": " + tiempo + " ms");
        guardarTiempo(caso,nombre,n,tiempo);
    }

    // =========================
    // GENERAR / GUARDAR
    // =========================
    static int[][] generarMatriz(int n){
        Random r=new Random();
        int[][] M=new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                M[i][j]=100000+r.nextInt(900000);
        return M;
    }

    static void guardarMatriz(int[][] M,String nombre)throws Exception{
        BufferedWriter bw=new BufferedWriter(new FileWriter(nombre));
        for(int[] f:M){
            for(int v:f) bw.write(v+" ");
            bw.newLine();
        }
        bw.close();
    }

    static void guardarTiempo(String caso,String alg,int n,double t)throws Exception{
        BufferedWriter bw=new BufferedWriter(new FileWriter("tiempos.csv",true));
        bw.write(caso+","+alg+","+n+","+t);
        bw.newLine();
        bw.close();
    }

    static void limpiar(int[][] C){
        for(int i=0;i<C.length;i++)
            for(int j=0;j<C.length;j++)
                C[i][j]=0;
    }

    // =========================
    // NAIVE
    // =========================
    static void NaivOnArray(int[][] A, int[][] B, int[][] C, int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    C[i][j]+=A[i][k]*B[k][j];
    }

    static void NaivLoopUnrollingTwo(int[][] A, int[][] B, int[][] C, int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                int sum=0,k;
                for(k=0;k<n-1;k+=2)
                    sum+=A[i][k]*B[k][j]+A[i][k+1]*B[k+1][j];
                if(k<n) sum+=A[i][k]*B[k][j];
                C[i][j]=sum;
            }
    }

    static void NaivLoopUnrollingFour(int[][] A, int[][] B, int[][] C, int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                int sum=0,k;
                for(k=0;k<n-3;k+=4)
                    sum+=A[i][k]*B[k][j]+A[i][k+1]*B[k+1][j]
                            +A[i][k+2]*B[k+2][j]+A[i][k+3]*B[k+3][j];
                for(;k<n;k++) sum+=A[i][k]*B[k][j];
                C[i][j]=sum;
            }
    }

    // =========================
    // WINOGRAD
    // =========================
    static void WinogradOriginal(int[][] A, int[][] B, int[][] C, int n){
        int[] row=new int[n],col=new int[n];

        for(int i=0;i<n;i++)
            for(int k=0;k<n/2;k++)
                row[i]+=A[i][2*k]*A[i][2*k+1];

        for(int j=0;j<n;j++)
            for(int k=0;k<n/2;k++)
                col[j]+=B[2*k][j]*B[2*k+1][j];

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                C[i][j]=-row[i]-col[j];
                for(int k=0;k<n/2;k++)
                    C[i][j]+=(A[i][2*k]+B[2*k+1][j])*
                            (A[i][2*k+1]+B[2*k][j]);
            }
    }

    static void winogradScaled(int[][] A,int[][] B,int[][] C,int n){
        WinogradOriginal(A,B,C,n);
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                C[i][j]/=2;
    }

    // =========================
    // STRASSEN
    // =========================
    static int[][] strassen(int[][] A,int[][] B){
        int n=A.length;
        int[][] C=new int[n][n];
        if(n==1){C[0][0]=A[0][0]*B[0][0];return C;}

        int s=n/2;

        int[][] A11=sub(A,0,0,s),A12=sub(A,0,s,s),
                A21=sub(A,s,0,s),A22=sub(A,s,s,s);

        int[][] B11=sub(B,0,0,s),B12=sub(B,0,s,s),
                B21=sub(B,s,0,s),B22=sub(B,s,s,s);

        int[][] M1=strassen(sum(A11,A22),sum(B11,B22));
        int[][] M2=strassen(sum(A21,A22),B11);
        int[][] M3=strassen(A11,subt(B12,B22));
        int[][] M4=strassen(A22,subt(B21,B11));
        int[][] M5=strassen(sum(A11,A12),B22);
        int[][] M6=strassen(subt(A21,A11),sum(B11,B12));
        int[][] M7=strassen(subt(A12,A22),sum(B21,B22));

        int[][] C11=sum(subt(sum(M1,M4),M5),M7);
        int[][] C12=sum(M3,M5);
        int[][] C21=sum(M2,M4);
        int[][] C22=sum(subt(sum(M1,M3),M2),M6);

        join(C,C11,0,0); join(C,C12,0,s);
        join(C,C21,s,0); join(C,C22,s,s);

        return C;
    }

    static int[][] strassenWinograd(int[][] A,int[][] B){
        return strassen(A,B);
    }

    // =========================
    // BLOQUES DIFERENCIADOS
    // =========================

    static void SequentialBlockIII(int[][] A, int[][] B, int[][] C, int n){
        for(int i1=0;i1<n;i1+=BSIZE)
            for(int j1=0;j1<n;j1+=BSIZE)
                for(int k1=0;k1<n;k1+=BSIZE)
                    for(int i=i1;i<i1+BSIZE&&i<n;i++)
                        for(int j=j1;j<j1+BSIZE&&j<n;j++)
                            for(int k=k1;k<k1+BSIZE&&k<n;k++)
                                C[i][j]+=A[i][k]*B[k][j];
    }

    static void ParallelBlock(int[][] A, int[][] B, int[][] C, int n){
        IntStream.range(0,n).parallel().forEach(i->{
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    C[i][j]+=A[i][k]*B[k][j];
        });
    }

    static void enhancedIII(int[][] A,int[][] B,int[][] C,int n){
        Thread t1=new Thread(()->{for(int i=0;i<n/2;i++)for(int j=0;j<n;j++)for(int k=0;k<n;k++)C[i][j]+=A[i][k]*B[k][j];});
        Thread t2=new Thread(()->{for(int i=n/2;i<n;i++)for(int j=0;j<n;j++)for(int k=0;k<n;k++)C[i][j]+=A[i][k]*B[k][j];});
        t1.start();t2.start();
        try{t1.join();t2.join();}catch(Exception e){}
    }

    static void SequentialBlockIV(int[][] A, int[][] B, int[][] C, int n){
        for(int i=0;i<n;i++)
            for(int k=0;k<n;k++)
                for(int j=0;j<n;j++)
                    C[i][k]+=A[i][j]*B[j][k];
    }

    static void parallelIV(int[][] A,int[][] B,int[][] C,int n){
        IntStream.range(0,n).parallel().forEach(i->{
            for(int k=0;k<n;k++)
                for(int j=0;j<n;j++)
                    C[i][k]+=A[i][j]*B[j][k];
        });
    }

    static void enhancedIV(int[][] A,int[][] B,int[][] C,int n){
        enhancedIII(A,B,C,n);
    }

    static void blockV(int[][] A,int[][] B,int[][] C,int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    C[k][i]+=A[k][j]*B[j][i];
    }

    static void parallelV(int[][] A,int[][] B,int[][] C,int n){
        IntStream.range(0,n).parallel().forEach(i->{
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    C[k][i]+=A[k][j]*B[j][i];
        });
    }

    // =========================
    // AUX
    // =========================
    static int[][] sum(int[][]A,int[][]B){
        int n=A.length;
        int[][]R=new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                R[i][j]=A[i][j]+B[i][j];
        return R;
    }

    static int[][] subt(int[][]A,int[][]B){
        int n=A.length;
        int[][]R=new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                R[i][j]=A[i][j]-B[i][j];
        return R;
    }

    static int[][] sub(int[][]A,int f,int c,int s){
        int[][]R=new int[s][s];
        for(int i=0;i<s;i++)
            for(int j=0;j<s;j++)
                R[i][j]=A[i+f][j+c];
        return R;
    }

    static void join(int[][]C,int[][]P,int f,int c){
        for(int i=0;i<P.length;i++)
            for(int j=0;j<P.length;j++)
                C[i+f][j+c]=P[i][j];
    }

    static void copiar(int[][]A,int[][]B){
        for(int i=0;i<A.length;i++)
            System.arraycopy(A[i],0,B[i],0,A.length);
    }
}