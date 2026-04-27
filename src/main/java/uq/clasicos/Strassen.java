package uq.clasicos;

import uq.Aux;

public class Strassen {

    static Aux aux = new Aux();

    public int[][] strassen(int[][] A,int[][] B){
        int n=A.length;
        int[][] C=new int[n][n];
        if(n==1){C[0][0]=A[0][0]*B[0][0];return C;}

        int s=n/2;

        int[][] A11=aux.sub(A,0,0,s),A12=aux.sub(A,0,s,s),
                A21=aux.sub(A,s,0,s),A22=aux.sub(A,s,s,s);

        int[][] B11=aux.sub(B,0,0,s),B12=aux.sub(B,0,s,s),
                B21=aux.sub(B,s,0,s),B22=aux.sub(B,s,s,s);

        int[][] M1=strassen(aux.sum(A11,A22),aux.sum(B11,B22));
        int[][] M2=strassen(aux.sum(A21,A22),B11);
        int[][] M3=strassen(A11,aux.subt(B12,B22));
        int[][] M4=strassen(A22,aux.subt(B21,B11));
        int[][] M5=strassen(aux.sum(A11,A12),B22);
        int[][] M6=strassen(aux.subt(A21,A11),aux.sum(B11,B12));
        int[][] M7=strassen(aux.subt(A12,A22),aux.sum(B21,B22));

        int[][] C11=aux.sum(aux.subt(aux.sum(M1,M4),M5),M7);
        int[][] C12=aux.sum(M3,M5);
        int[][] C21=aux.sum(M2,M4);
        int[][] C22=aux.sum(aux.subt(aux.sum(M1,M3),M2),M6);

        aux.join(C,C11,0,0); aux.join(C,C12,0,s);
        aux.join(C,C21,s,0); aux.join(C,C22,s,s);

        return C;
    }

    public int[][] strassenWinograd(int[][] A,int[][] B){
        return strassen(A,B);
    }
}
