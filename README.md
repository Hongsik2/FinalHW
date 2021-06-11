# 모의 담금질 기법(Simulated Annealing)

## 설명

- 모의 담금질 기법이란, 높은 온도에서 액체 상태인 물질이 온도가 점차 낮아지면서 결정체로 변하는 과정을 모방한 해 탐색 알고리즘이다. 
- 용융 상태에서는 물질의 분자가 자유로이 움직이는데 이를 모방하여, 해를 탐색하는 과정도 특정한 패턴 없이 이루어진다.
- 그러나 온도가 점점 낮아지면 분자의 움직임이 점점 줄어들어 결정체가 되는데, 해 탐색 과정도 이와 유사하게 점점 더 규칙적인 방식으로 이루어진다.
- 단순이 해가 지역 최적값을 향해 가기만 한다면 지역 최적해에 갇힐 수 있기 때문에 여기서는 확률을 도입하여 이러한 문제점을 해결하고 전역 최적해를 찾을 수 있도록 한다.

## 인자와 변수, 상관관계

### 인자와 변수

- 초기 온도 : **T**	(결과값을 구할 수 있을 정도로 충분히 큰 값)

- 냉각율 : **a** (일반적으로 a를 0.99에 가까운 수로 선택하여, **T**가 천천히 감소되도록 조절한다.)

- 해의 범위 : **lower, upper**

- 초기해 : **X0**

- *repeat-loop* 반복 횟수 : **niter**

- *for-loop* 반복 횟수 : **Kt** (현재 온도에서 for-loop가 반복되는 횟수, T와 반비례)

- 해를 자유롭게 탐색할 (*좋지 않은 해를 선택할*) 확률 : **p**

  

### 인자 간의 상관관계

#### 반복 횟수

##### repeat-loop

- 초기 온도와 냉각율, *repeat-loop* 반복 횟수의 상관 관계는 다음과 같이 나타낼 수 있다.

$$
T=T_{0}e^{niter\left(a-1\right)} ≈ 0(T_{0} = 초기온도,T=현재온도)
$$

![niter 그래프]()

- 왜냐하면 온도가 0에 수렴해야 안정화된 해를 찾을 수 있기 때문이다.
- 보다 정확한 해를 구하려면 :
  1. 냉각율을 1에 가깝게 하여 온도의 변화량을 작게 한다
  2. 반복 횟수를 더 크게 해서 보다 정확한 해를 구한다.

- 보다 빠른 연산을 하려면 : 
  1. 냉각율을 작게 하여 온도의 변화량을 크게 만든다.
  2. 반복 횟수를 줄여 연산의 수를 줄인다.

- 더 이상 우수한 해를 찾지 못하거나, **niter** 값의 초과 여부로 종료 조건을 판단한다.

##### for-loop

- Kt 는 현재 온도(T)에서 for-loop가 수행되는 횟수인데, 일반적으로 T가 작아질수록 Kt가 커지도록 조절한다.

#### 해를 자유롭게 탐색할 확률

- 해를 자유롭게 탐색할 확률 **p** 와 현재 해와 이웃하는 해 사이의 거리 **d**, 온도 **T**와의 상관관계는 다음과 같다.

$$
p = e^{\left(-\frac{d}{T}\right)}
$$

- 온도 **T**가 높을 때에는 **p** 가 커서 해를 자유롭게 탐색할 수 있지만 온도 **T**가 낮을 때에는 나쁜 이웃해가 현재 해가 되지 못하도록 한다.
- 현재 해와 이웃하는 해의 거리가 멀(값의 차이가 큰) 경우에는 확률을 줄인다. 그렇지 않으면 랜덤으로 탐색하게 되는 결과를 낳기 때문이다.
- **이 값 때문에 온도 T를 충분히 낮추어 주어야 정확한 결과값이 나올 수 있다.**
- 이 값을 임의로 너무 작게 설정하게 된다면(빠른 시간 내에 결과값을 도출해야 하는 경우) 지역해에 빠질 수 있다.



## 4차 함수의 최적해 찾기

### 초기 설정

- 주어진 구간 내에서 함수의 최솟값이나 최댓값을 구할 수 있는 코드를 구현.
- 여기에서 사용한 함수는 다음과 같다.

$$
f\left(x\right)=10x^{4}-10x^{3}-100x^{2}+80x+50
$$

- x의 범위는 -3에서 3까지로 했다.
- T의 값은 100, a의 값은 0.99, 초기해는 랜덤으로 정했다.

![niter결정]()

- niter 값은 온도가 약 0.1도 까지 내려가게 되는 값인 700으로 했다.
- Kt의 값은 T가 작아지면 커지게 설정했다.

### 실행 결과

- (-3<x<3)구간에서 이 함수에서의 최댓값은 x = 0.38907 에서 65.628의 값을 갖는다.
- 구현한 코드의 실행 결과는 다음과 같다.

| 최적해 | x                   | f(x)              |
| ------ | ------------------- | ----------------- |
| 1회차  | 0.3894597815193306  | 65.62822693549518 |
| 2회차  | 0.39726842927394657 | 65.6213545412819  |
| 3회차  | 0.382890417714961   | 65.62431991821207 |
| 4회차  | 0.3850036848309024  | 65.62654333506156 |
| 5회차  | 0.384613171876099   | 65.62620153939869 |

- 초기 조건을 잘못 설정한 경우에는 실용적인 결과값이 나오지 않았다.
- 처음에는 랜덤한 값들이 나오지만 회차를 반복하며 온도가 낮아지면 일정한 규칙을 가지고 전역 해로 결과가 수렴하는 것을 볼 수 있다.

![실행결과 그래프]()



## Curve Fitting

- 사용된 데이터 : 용돈의 잔액
- curve fitting을 위한 모델 : 선형 모델(ax + b)

### 인자의 선택

- 각 구간별 기울기 >= 전체 기울기 이므로 선형 그래프의 기울기의 최소, 최대 조건을 각 구간의 기울기의 최대, 최소값으로 설정하였다.

- 전체 그래프의 최소, 최대값 사이에 b 값이 있으므로 b값의 최소, 최대 조건을 전체 그래프의 최소, 최대값으로 하였다. 
- 초기온도 **T**는 200으로 하였으며, 반복횟수는 최종온도가 약 0.001이 되는 1200으로 설정하였다.
- 냉각율 **a**은 0.99로 설정하였다.
- for-loop의 반복 횟수인 Kt는 100000 으로 설정해 주었다.(상한선을 지정해 주지 않으면 메모리 공간 부족으로 프로그램이 정상 작동하지 않는다.)

![커브피팅 niter 값 결정]()

### 실행 결과

- 초기 데이터는 다음과 같은 값으로 넣어주었다.

![커브피팅 초기 데이터]()

- 실행 결과는 다음과 같았다.

| 선형 그래프의 | 기울기(a)         | y절편(b)           |
| ------------- | ----------------- | ------------------ |
| 1회차         | 80310.71135496488 | 2176418.7959228684 |
| 2회차         | 80135.02591871005 | 2173573.6073116516 |
| 3회차         | 80757.03542196518 | 2172726.9149009185 |
| 4회차         | 80757.42392379232 | 2172398.510740992  |
| 5회차         | 80337.28414606256 | 2180125.0231421    |

![커브피팅 실행 결과]()

#### 정상 작동 검증

- 위에서 살펴본 해가 제대로 나온 해인지 검증하기 위해 단순화된 데이터를 가지고 실행시켜 보았다.
- 초기 데이터는 다음과 같이 주었다.
- 