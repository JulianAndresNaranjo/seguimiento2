package uq.clasicos;

import uq.Apoyov;

public class Strassen {

    static Apoyov apoyov = new Apoyov();

    public int[][] strassen(int[][] A,int[][] B){
        int n=A.length;
        int[][] C=new int[n][n];
        if(n==1){C[0][0]=A[0][0]*B[0][0];return C;}

        int s=n/2;

        int[][] A11= apoyov.sub(A,0,0,s),A12= apoyov.sub(A,0,s,s),
                A21= apoyov.sub(A,s,0,s),A22= apoyov.sub(A,s,s,s);

        int[][] B11= apoyov.sub(B,0,0,s),B12= apoyov.sub(B,0,s,s),
                B21= apoyov.sub(B,s,0,s),B22= apoyov.sub(B,s,s,s);

        int[][] M1=strassen(apoyov.sum(A11,A22), apoyov.sum(B11,B22));
        int[][] M2=strassen(apoyov.sum(A21,A22),B11);
        int[][] M3=strassen(A11, apoyov.subt(B12,B22));
        int[][] M4=strassen(A22, apoyov.subt(B21,B11));
        int[][] M5=strassen(apoyov.sum(A11,A12),B22);
        int[][] M6=strassen(apoyov.subt(A21,A11), apoyov.sum(B11,B12));
        int[][] M7=strassen(apoyov.subt(A12,A22), apoyov.sum(B21,B22));

        int[][] C11= apoyov.sum(apoyov.subt(apoyov.sum(M1,M4),M5),M7);
        int[][] C12= apoyov.sum(M3,M5);
        int[][] C21= apoyov.sum(M2,M4);
        int[][] C22= apoyov.sum(apoyov.subt(apoyov.sum(M1,M3),M2),M6);

        apoyov.join(C,C11,0,0); apoyov.join(C,C12,0,s);
        apoyov.join(C,C21,s,0); apoyov.join(C,C22,s,s);

        return C;
    }

    public int[][] strassenWinograd(int[][] A,int[][] B){
        return strassen(A,B);
    }
}
