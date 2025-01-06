import java.lang.Math;

public class Main{

    static double calc(int z, double x){
        return switch (z){
            case 10 -> Math.cos(Math.sin(Math.pow((1-x)/x,x)));    //  случай для z = 10
            case 2, 4, 11, 12, 13, 14, 15 -> Math.pow((Math.asin(Math.pow((x+1)/2 * Math.E + 1,2)) * (0.5 + Math.tan(x) * (Math.cos(x) + 0.25))), 3);    //  случай для z ∈ {2, 4, 11, 12, 13, 14, 15}
            default -> Math.pow((Math.sin(Math.cos(Math.cos(x))) - 1) / Math.PI, 2);    //  случай для остальных значений z
        };
    }

    static void output(double[][] z1){
        for (int i = 0; i < z1.length; i++){    //  вывод двумерного массива z1 на экран
            for (int j = 0; j < z1[i].length; j++){
                System.out.format("%8.5f", z1[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        int[] z = new int[14];
        double[] x = new double[20];
        double[][] z1 = new double[14][20];   //  инициализация требуемых массивов
        for (int i = 0; i < 14; i++){    //  заполнение массива z целыми числами от 15 до 2
            z[i] = 15 - i;
        }
        for (int i = 0; i < x.length; i++){    //  заполнение массива x случайными числами из промежутка [-9; 11]
            x[i] = Math.random() * 20 - 9;
        }
        for (int i = 0; i < 14; i++){    //  заполнение массива z1 в соответствии с ТЗ
            for (int j = 0; j < 20; j++){
                z1[i][j] = calc(z[i], x[j]);
            }
        }
        output(z1);
    }
}