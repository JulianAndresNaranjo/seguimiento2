package uq.bloques;

import java.util.stream.IntStream;

public class BloquesV {

    public void SequentialBlockV(int[][] A, int[][] B, int[][] C, int n){
        for(int j=0;j<n;j++)
            for(int k=0;k<n;k++)
                for(int i=0;i<n;i++)
                    C[i][j] += A[i][k] * B[k][j];
    }

    public void ParallelBlockV(int[][] A, int[][] B, int[][] C, int n){
        IntStream.range(0,n).parallel().forEach(j -> {
            for(int k=0;k<n;k++)
                for(int i=0;i<n;i++)
                    C[i][j] += A[i][k] * B[k][j];
        });
    }
}