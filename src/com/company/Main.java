package com.company;

public class Main {
    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(700);
        Problem p = new Problem() {
            @Override
            public double fit(double x) {
                return 10*x*x*x*x - 10*x*x*x-100*x*x+80*x+50;
                // x=0.38907 , f(x)=65.628
            }

            @Override
            public boolean isNeighborBetter(double f0, double f1) {
                return f0 < f1;                     //최대값을 찾을지. 최소값을 찾을지 결정
            }
        };
        double x = sa.solve(p, 100, 0.99, -3, 3);
        System.out.println(x);
        System.out.println(p.fit(x));
    }
}