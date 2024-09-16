import java.util.*;
import java.lang.Math;

public class Main{
    public static void main(String[] args){
        Random rand = new Random();
        int[] w = new int[15];
        double[] x = new double[16];
        double[][] r = new double[8][16];
        for (int i = 0; i < 15; i++){
            w[i] = 19 - i;
        }
        for (int i = 0; i < x.length; i++){
            x[i] = rand.nextDouble(-6,15);
        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 16; j++){
                switch (w[i]){
                    case 11: r[i][j] = Math.cos(Math.pow((0.5 / Math.exp(x[j])), Math.pow((0.5 - x[j]),x[j])));
                    case 5: case 15: case 17: case 19: r[i][j] = Math.asin(Math.pow(Math.sin(x[j]),2));
                    default: r[i][j] = Math.atan(Math.exp(Math.cbrt(-1 * Math.exp(Math.pow(Math.cbrt(x[j])/2, 3)))));
                }
            }
        }
        for (int i = 0; i < r.length; i++){
            for (int j = 0; j < r[i].length; j++){
                System.out.format("%.4f", r[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}