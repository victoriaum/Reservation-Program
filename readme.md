# 레포트 예약 프로그램
<br>

### 프로젝트 개요
> 대학교에서 사용될 레포트 예약 프로그램으로 2022년 2월 배포했습니다. 현재 추가 유지보수를 하면서 운영중에 있습니다.
> 운영상 비용문제로 AWS EC2 배포에서 카페24 Tomcat JSP 웹호스팅 배포로 변경한 상태입니다.
<br>

> 학생, 선생님이 따로 로그인이 가능하며 원하는 시간에 레포트 검사요청을 할 수 있습니다.
> 프로젝트 자체는 웹 프로젝트이나 별도 어플리케이션 다운 없이 핸드폰으로 접속하는 사용자들이 많을 것으로 고려되어 화면 구성 비율은 핸드폰이나 패드에서도 확인이 가능하도록 반응형으로 만들었습니다.
<br>

### 프로젝트 계획이유
> DB를 이용한 토이프로젝트 구상 중 지인 요청으로 진행하게 되었습니다. 
> 그리고 최근 토이프로젝트와 달리 다른 기술들을 적용해보고 싶어 도전했습니다. 처음 적용해본 기술들은 다음과 같습니다.
1. thymeleaf를 이용한 레이아웃 템플릿 구성
2. AWS RDS 사용
3. mariaDB, JPA, JPQL

<br>

### 프로젝트 개발환경
+ OS: Window10
+ IDE: IntelliJ IDEX 2021.1.2 x64
+ Language: Java 8, HTML5,CSS3, JS
+ Middleware: Tomcat9, Apache2
+ Framework: Spring Gradle
+ DB: MariaDB Server 10.6.5


### 프로젝트 현재 배포환경
+ OS: Linux 3.10.x (카페24 Tomcat JSP)
+ DB: MariaDB 10.1.x
+ Middleware: Tomcat 8.0.x (jsp 2.3/Servlet 3.1)


### 프로젝트 이전 배포환경
+ OS: AWS EC2 Ubuntu 20.04
+ DB: AWS RDS MariaDB 10.5.1
