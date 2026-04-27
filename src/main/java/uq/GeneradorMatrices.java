package uq;

import java.util.Random;

public class GeneradorMatrices {

    public int[][] generarMatriz(int n){
        Random r = new Random();
        int[][] M = new int[n][n];

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                M[i][j]=100000+r.nextInt(900000);

        return M;
    }


}