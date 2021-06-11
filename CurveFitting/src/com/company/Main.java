package com.company;

public class Main {
    public static void main(String[] args) {

        double[] money = {20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200};
        //주어진 데이터

        SimulatedAnnealing sa = new SimulatedAnnealing(1200);
        MinMaxSelection mm = new MinMaxSelection();
        Curvefitting c = new Curvefitting() {
            @Override
            public double fit(double a, double b) {

                double diff[] = new double[money.length];
                for (int i = 0; i < money.length; i++) {
                    diff[i] = Math.abs(money[i] - (a * i + b));
                }
                double diffsum = 0;
                for (int i = 0; i < money.length; i++) {
                    diffsum += diff[i];
                }
                return diffsum;
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 > f1;
            }
        };

        double a;                               //선형 그래프의 기울기 값
        double b;                               //선형 그래프의 y절편 값
        double alower;                          //데이터 안에서의 최소 기울기 값
        double aupper;                          //데이터 안에서의 최대 기울기 값
        double blower;                          //데이터 안에서의 최대값
        double bupper;                          //데이터 안에서의 최소값

        double[] condition = mm.minmax(money);  //기울기와 y절편의 최대, 최소값을 정해주는 함수

        aupper = condition[0];
        alower = condition[1];
        bupper = condition[2];
        blower = condition[3];

        double root[] = sa.solve(c, 200, 0.99, alower, aupper, blower, bupper);

        a = root[0];
        b = root[1];
        System.out.println(a);
        System.out.println(b);
        System.out.println(c.fit(a, b));
    }
}
