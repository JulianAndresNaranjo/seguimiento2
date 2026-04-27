package uq.bloques;

import java.util.stream.IntStream;

public class BloquesIII {
    static int BSIZE = 64;

    public void SequentialBlockIII(int[][] A,int[][] B,int[][] C,int n){
        for(int i1=0;i1<n;i1+=BSIZE)
            for(int j1=0;j1<n;j1+=BSIZE)
                for(int k1=0;k1<n;k1+=BSIZE)
                    for(int i=i1;i<i1+BSIZE&&i<n;i++)
                        for(int j=j1;j<j1+BSIZE&&j<n;j++)
                            for(int k=k1;k<k1+BSIZE&&k<n;k++)
                                C[i][j]+=A[i][k]*B[k][j];
    }

    public void ParallelBlockIII(int[][] A,int[][] B,int[][] C,int n){
        IntStream.range(0,n).parallel().forEach(i->{
            for(int j=0;j<n;j++)
                for(int k=0;k<n;k++)
                    C[i][j]+=A[i][k]*B[k][j];
        });
    }

    public void EnhancedParallelBlockIII(int[][] A,int[][] B,int[][] C,int n){
        Thread t1=new Thread(()->{
            for(int i=0;i<n/2;i++)
                for(int j=0;j<n;j++)
                    for(int k=0;k<n;k++)
                        C[i][j]+=A[i][k]*B[k][j];
        });

        Thread t2=new Thread(()->{
            for(int i=n/2;i<n;i++)
                for(int j=0;j<n;j++)
                    for(int k=0;k<n;k++)
                        C[i][j]+=A[i][k]*B[k][j];
        });

        t1.start(); t2.start();
        try{t1.join(); t2.join();}catch(Exception e){}
    }
}