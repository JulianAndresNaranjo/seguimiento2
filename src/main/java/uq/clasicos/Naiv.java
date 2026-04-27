package uq.clasicos;

public class Naiv {

    public void NaivOnArray(int[][] A,int[][] B,int[][] C,int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    C[i][j]+=A[i][k]*B[k][j];
    }

    public void NaivLoopUnrollingTwo(int[][] A,int[][] B,int[][] C,int n){
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                int sum=0,k;
                for(k=0;k<n-1;k+=2)
                    sum+=A[i][k]*B[k][j]+A[i][k+1]*B[k+1][j];
                if(k<n) sum+=A[i][k]*B[k][j];
                C[i][j]=sum;
            }
    }

    public void NaivLoopUnrollingFour(int[][] A,int[][] B,int[][] C,int n){
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
}