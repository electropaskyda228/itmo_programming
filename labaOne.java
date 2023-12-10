public class labaOne {
    public static void main(String[] args){
        int startNumber = 5;
        int endNumber = 17;
        int deltaNumber = 2;
        int amountNumber = (endNumber - startNumber + 1) / deltaNumber + endNumber % deltaNumber;
        long[] c = new long[amountNumber];
        for(int i=0; i<amountNumber; i++){
            c[i] = endNumber - (long) i * deltaNumber;
        }

        int amountFloat = 20;
        int rangeFloat = 17;
        int indentFloat = -15;
        float[] x = new float[amountFloat];
        for(int i=0; i<amountFloat; i++){
            x[i] = (float) Math.random() * rangeFloat + (float) indentFloat;
        }

        double[][] answer = new double[c.length][x.length];
        for(int i=0; i<c.length; i++){
            for(int j=0; j<x.length; j++){
                if(c[i] == 9){
                    answer[i][j] = Math.pow(Math.PI / (Math.pow(Math.E, Math.pow((0.75 - (double) x[j]) / (double) x[j], x[j])) - 1), 3);
                } else if(c[i] == 5 || c[i] == 11 || c[i] == 15){
                    answer[i][j] = Math.sin(Math.cos(Math.sin(x[j])));
                } else{
                    answer[i][j] = Math.asin(Math.sin(Math.tan(Math.pow(Math.atan(((double) x[j] - 6.5) / 17.0), Math.tan(x[j])))));
                }
            }
        }

        for(int i=0; i<c.length; i++){
            for(int j=0; j<x.length; j++){
                System.out.printf("%.4f\t", answer[i][j]);
            }
            System.out.println();
        }
    }
}
