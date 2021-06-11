package com.company;

import java.util.ArrayList;
import java.util.Random;


public class SimulatedAnnealing {
    private int niter;


    public SimulatedAnnealing(int niter) {                                                          //반복횟수를 지정
        this.niter = niter;

    }

    public double solve(Problem p, double t, double a, double lower, double upper) {                //초기값 지정하지 않음
        Random r = new Random();
        double x0 = r.nextDouble() * (upper - lower) + lower;
        return solve(p, t, a, x0, lower, upper);
    }

    public double solve(Problem p, double t, double a, double x0, double lower, double upper) {     //초기값 지정됨
        Random r = new Random();
        double f0 = p.fit(x0);                                                                      //랜덤한 해를 넣어 함수값 계산
        double t0 = t;                                                                              //초기온도

        for (int i = 0; i < niter; i++) {                                                           //반복횟수만큼 repeat 루프 실행
            double kt = Math.exp(t0 / t);                                                           //kt 정하기
            if (kt > 10000)
                kt = 10000;                                                                          //kt<=500
            for (int j = 0; j < (int) kt; j++) {
                double x1 = r.nextDouble() * (upper - lower) + lower;                               //이웃한 해 랜덤으로 생성
                double f1 = p.fit(x1);                                                              //이웃한 해의 함수값

                if (p.isNeighborBetter(f0, f1)) {                                                   //이웃한 해가 우수한 해일 경우
                    x0 = x1;
                    f0 = f1;
                } else {
                    double d = Math.sqrt(Math.abs(f1 - f0));                                        //해와 이웃한 해의 값의 차이
                    double p0 = Math.exp(-d / t);                                                   //자유롭게 탐색할(좋지 않을 해를 선택할) 확률
                    if (r.nextDouble() < p0) {
                        x0 = x1;
                        f0 = f1;
                    }
                }
            }
            t *= a;                                                                                 //온도 * 냉각율
        }
        return x0;                                                                                  //최척해 반환
    }
}