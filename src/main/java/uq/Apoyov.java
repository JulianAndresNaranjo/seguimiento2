package uq;

public class Apoyov {

    public int[][] sum(int[][]A,int[][]B){
        int n=A.length;
        int[][]R=new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                R[i][j]=A[i][j]+B[i][j];
        return R;
    }

    public int[][] subt(int[][]A,int[][]B){
        int n=A.length;
        int[][]R=new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                R[i][j]=A[i][j]-B[i][j];
        return R;
    }

    public int[][] sub(int[][]A,int f,int c,int s){
        int[][]R=new int[s][s];
        for(int i=0;i<s;i++)
            for(int j=0;j<s;j++)
                R[i][j]=A[i+f][j+c];
        return R;
    }

    public void join(int[][]C,int[][]P,int f,int c){
        for(int i=0;i<P.length;i++)
            for(int j=0;j<P.length;j++)
                C[i+f][j+c]=P[i][j];
    }
}
