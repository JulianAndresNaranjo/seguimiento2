package uq.bloques;

import java.util.stream.IntStream;

public class BloquesIV {

    public void SequentialBlockIV(int[][] A, int[][] B, int[][] C, int n){
        for(int i=0;i<n;i++)
            for(int k=0;k<n;k++)
                for(int j=0;j<n;j++)
                    C[i][j] += A[i][k] * B[k][j];
    }

    public void ParallelBlockIV(int[][] A, int[][] B, int[][] C, int n){
        IntStream.range(0,n).parallel().forEach(i -> {
            for(int k=0;k<n;k++)
                for(int j=0;j<n;j++)
                    C[i][j] += A[i][k] * B[k][j];
        });
    }

    public void EnhancedParallelBlockIV(int[][] A, int[][] B, int[][] C, int n){
        int mid = n / 2;

        Thread t1 = new Thread(() -> {
            for(int i=0;i<mid;i++)
                for(int k=0;k<n;k++)
                    for(int j=0;j<n;j++)
                        C[i][j] += A[i][k] * B[k][j];
        });

        Thread t2 = new Thread(() -> {
            for(int i=mid;i<n;i++)
                for(int k=0;k<n;k++)
                    for(int j=0;j<n;j++)
                        C[i][j] += A[i][k] * B[k][j];
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch(Exception e){}
    }
}