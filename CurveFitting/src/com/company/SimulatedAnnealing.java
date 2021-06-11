package com.company;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    private int niter;

    public SimulatedAnnealing(int niter) {                                                          //반복횟수를 지정
        this.niter = niter;
    }

    public double[] solve(Curvefitting c, double t, double a, double alower, double aupper,double blower, double bupper) {                //초기값 지정하지 않음
        Random r = new Random();
        double a0 = r.nextDouble() * (aupper - alower) + alower;
        double b0 = r.nextDouble() * 200 - 100;
        return solve(c, t, a, a0, b0, alower, aupper,blower,bupper);
    }

    public double[] solve(Curvefitting c, double t, double a, double a0, double b0, double alower, double aupper,double blower,double bupper) {     //초기값 지정됨
        Random r = new Random();
        double f0 = c.fit(a0, b0);                                                                      //랜덤한 해를 넣어 함수값 계산

        double t0 = t;                                                                              //초기온도

        for (int i = 0; i < niter; i++) {                                                           //반복횟수만큼 repeat 루프 실행
            double kt = Math.exp(t0 / t);                                                           //kt 정하기
            if (kt > 100000)
                kt = 100000;                                                                          //kt<=500
            for (int j = 0; j < (int) kt; j++) {
                double a1 = r.nextDouble() * (aupper - alower) + alower;                               //이웃한 해 랜덤으로 생성
                double b1 = r.nextDouble() * (bupper-blower) + blower;
                double f1 = c.fit(a1, b1);                                                              //이웃한 해의 함수값

                if (c.isNeighborBetter(f0, f1)) {                                                   //이웃한 해가 우수한 해일 경우
                    a0 = a1;
                    b0 = b1;
                    f0 = f1;
                } else {
                    double d = Math.sqrt(Math.abs(f1 - f0));                                        //해와 이웃한 해의 값의 차이
                    double p0 = Math.exp(-d / t);                                                   //자유롭게 탐색할(좋지 않을 해를 선택할) 확률
                    if (r.nextDouble() < p0) {
                        a0 = a1;
                        b0 = b1;
                        f0 = f1;
                    }
                }
            }
            t *= a;                                                                                 //온도 * 냉각율
        }
        double root[] = new double[2];
        root[0] = a0;
        root[1] = b0;

        return root;                                                                                  //최척해 반환
    }
}