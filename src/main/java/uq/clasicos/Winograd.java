package uq.clasicos;

public class Winograd {

    public void WinogradOriginal(int[][] A,int[][] B,int[][] C,int n){
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

    public void winogradScaled(int[][] A,int[][] B,int[][] C,int n){
        WinogradOriginal(A,B,C,n);
    }
}