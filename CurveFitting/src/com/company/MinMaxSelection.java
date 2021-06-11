package com.company;

public class MinMaxSelection {
    public double[] minmax(double[] a){

        double aMax = Double.MIN_VALUE;
        double bMax = Double.MIN_VALUE;
        double aMin = Double.MAX_VALUE;
        double bMin = Double.MAX_VALUE;

        for(int i=0;i<a.length-1; i++){
            if((a[i+1]-a[i])>aMax){aMax = (a[i+1]-a[i]);}
            if((a[i+1]-a[i])<aMin){aMin = (a[i+1]-a[i]);}
        }


        for(int i=0; i<a.length; i++){
            if(bMax<a[i]){bMax = a[i];}
            if(bMin>a[i]){bMin = a[i];}
        }

        double [] condition = {aMax,aMin,bMax,bMin};

        return condition;
    }
}
