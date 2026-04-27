package uq;

import java.io.*;

public class Utilidades {

    public void limpiar(int[][] C){
        for(int i=0;i<C.length;i++)
            for(int j=0;j<C.length;j++)
                C[i][j]=0;
    }

    public long checksum(int[][] C){
        long sum=0;
        for(int i=0;i<C.length;i++)
            for(int j=0;j<C.length;j++)
                sum+=C[i][j];
        return sum;
    }

    public void copiar(int[][] A,int[][] B){
        for(int i=0;i<A.length;i++)
            System.arraycopy(A[i],0,B[i],0,A.length);
    }

    public void limpiarCSV() throws Exception{
        new FileWriter("tiempos.csv").close();
    }

    public void guardarTiempo(String caso,String alg,int n,double t)throws Exception{
        BufferedWriter bw=new BufferedWriter(new FileWriter("tiempos.csv",true));
        bw.write(caso+","+alg+","+n+","+t);
        bw.newLine();
        bw.close();
    }
}