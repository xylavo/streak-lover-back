# Streak Lover Backend

## 프로젝트 개요

Streak Lover Backend는 사용자의 습관 형성과 목표 달성을 위한 스트릭(연속 기록) 관리 시스템의 백엔드 API 서버입니다. 이 시스템은 사용자가 개인적인 목표나 습관을 연속으로 수행하는 것을 추적하고, 동기부여를 제공하는 핵심 기능을 담당합니다.

## 시스템 역할과 목적

### 핵심 역할
1. **습관 추적 시스템**: 사용자가 설정한 작업(습관/목표)의 일일 수행 여부를 기록하고 연속 기록을 관리
2. **동기부여 플랫폼**: 스트릭 유지를 통한 사용자의 지속적인 참여 유도
3. **알림 시스템**: 사용자가 설정한 시간에 스트릭 체크를 위한 푸시 알림 제공
4. **복구 메커니즘**: 스트릭이 끊어졌을 때 복구할 수 있는 기능 제공

### 비즈니스 로직
- 사용자는 여러 개의 작업(습관/목표)을 등록할 수 있음
- 각 작업은 일별 체크 기록을 통해 스트릭을 관리
- 스트릭이 끊어지면 무료 또는 유료 복구 옵션 제공
- 사용자 정의 알림 시간에 푸시 알림으로 스트릭 체크 유도

## 기술 아키텍처

### 백엔드 기술 스택
- **언어**: Java 17
- **프레임워크**: Spring Boot 3.3.2
- **데이터 접근**: Spring Data JPA
- **데이터베이스**: MySQL 8.0
- **빌드 도구**: Gradle
- **컨테이너화**: Docker Compose

### 외부 서비스 통합
- **Firebase Cloud Messaging**: 푸시 알림 서비스
- **Gmail SMTP**: 이메일 알림 서비스
- **Thymeleaf**: 이메일 템플릿 엔진

### 아키텍처 패턴
- **계층형 아키텍처**: Controller → Service → Repository
- **도메인 중심 설계**: User, Work, Streak 도메인 분리
- **RESTful API**: HTTP 기반 REST API 제공
- **세션 기반 인증**: HttpSession을 통한 사용자 인증

## 데이터 모델

### 핵심 엔티티
1. **User**: 사용자 정보, 알림 설정, 작업 목록 관리
2. **Work**: 사용자가 등록한 작업(습관/목표), 스트릭 상태, 복구 정보
3. **Streak**: 일별 체크 기록, 월별 스트릭 통계

### 데이터 관계
- User ↔ Work: 1:N (한 사용자가 여러 작업 보유)
- Work ↔ Streak: 1:N (한 작업의 여러 일별 기록)
- User ↔ Firebase: 1:N (한 사용자의 여러 디바이스 토큰)

## API 구조

### 사용자 관리 API (`/api/user`)
- 사용자 인증, 정보 관리, 설정 변경
- Firebase 토큰 등록으로 푸시 알림 지원

### 작업 관리 API (`/api/work`)
- 작업 CRUD, 스트릭 연장, 복구 기능
- 스트릭 상태 조회 및 관리

### 알림 시스템 API
- 이메일 API (`/api/mail`): 문의, 비밀번호 재설정
- Firebase API: 푸시 알림 토큰 관리

## 시스템 특징

### 동시성 처리
- `@EnableAsync`: 비동기 이메일 발송
- `@EnableScheduling`: 정기적인 알림 발송

### 보안
- 비밀번호 암호화
- 세션 기반 인증
- 입력 데이터 검증 (`@Valid`)

### 확장성
- 모듈화된 도메인 구조
- 설정 외부화 (환경 변수)
- Docker 컨테이너화

## 개발 환경

### 필수 환경
- Java 17
- MySQL 8.0
- Docker (선택사항)

### 환경 변수
```bash
# 데이터베이스
db.url=jdbc:mysql://localhost:3306/mydb
db.username=root
db.password=root1234!!

# 이메일
mail.password=your_gmail_app_password
```

### 실행 방법
```bash
# 데이터베이스 시작
docker-compose up -d

# 애플리케이션 실행
./gradlew bootRun
```

## 프로젝트 구조

```
src/main/java/com/example/streak/
├── user/          # 사용자 도메인 (인증, 정보 관리)
├── work/          # 작업 도메인 (습관/목표 관리)
├── streak/        # 스트릭 도메인 (연속 기록 관리)
├── email/         # 이메일 서비스
├── firebase/      # 푸시 알림 서비스
├── common/        # 공통 컴포넌트 (API, 에러 처리)
└── config/        # 설정 클래스들
```

## 핵심 비즈니스 로직

### 스트릭 관리
- 일별 체크 기록으로 연속 수행 여부 추적
- 스트릭 끊김 시 복구 메커니즘 제공
- 월별 통계로 성과 분석

### 알림 시스템
- 사용자 정의 시간에 푸시 알림
- 이메일을 통한 문의 및 비밀번호 재설정
- Firebase FCM을 통한 실시간 알림

### 동기부여 메커니즘
- 연속 기록 시각화
- 복구 시스템으로 실패에 대한 두려움 완화
- 개인화된 알림으로 지속적 참여 유도

이 시스템은 사용자의 습관 형성과 목표 달성을 지원하는 종합적인 백엔드 플랫폼으로, 기술적 안정성과 사용자 경험을 모두 고려한 설계를 제공합니다.

## 📋 프로젝트 개요

이 프로젝트는 사용자가 개인적인 목표나 습관을 연속으로 수행하는 스트릭을 관리할 수 있는 웹 애플리케이션의 백엔드입니다. 사용자는 작업을 등록하고, 매일 체크하여 연속 기록을 유지하며, 실패했을 때 복구할 수 있는 기능을 제공합니다.

## 🛠 기술 스택

- **Java 17**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
- **MySQL 8.0**
- **Firebase Admin SDK** (푸시 알림)
- **Thymeleaf** (이메일 템플릿)
- **Gradle**
- **Docker Compose**

## 🏗 프로젝트 구조

```
src/main/java/com/example/streak/
├── common/           # 공통 컴포넌트
│   ├── api/         # API 응답 형식
│   ├── error/       # 에러 코드 정의
│   └── exception/   # 예외 처리
├── config/          # 설정 클래스들
├── user/            # 사용자 관리
│   ├── controller/  # 사용자 API 컨트롤러
│   ├── db/          # 사용자 엔티티 및 리포지토리
│   ├── model/       # 사용자 요청/응답 모델
│   └── service/     # 사용자 비즈니스 로직
├── work/            # 작업 관리
│   ├── controller/  # 작업 API 컨트롤러
│   ├── db/          # 작업 엔티티 및 리포지토리
│   ├── model/       # 작업 요청/응답 모델
│   └── service/     # 작업 비즈니스 로직
├── streak/          # 스트릭 관리
│   ├── controller/  # 스트릭 API 컨트롤러
│   ├── db/          # 스트릭 엔티티 및 리포지토리
│   └── business/    # 스트릭 비즈니스 로직
├── email/           # 이메일 서비스
│   ├── controller/  # 이메일 API 컨트롤러
│   ├── db/          # 이메일 인증 엔티티
│   ├── model/       # 이메일 요청 모델
│   └── service/     # 이메일 서비스
├── firebase/        # Firebase 푸시 알림
│   ├── controller/  # Firebase API 컨트롤러
│   ├── db/          # Firebase 토큰 엔티티
│   └── service/     # Firebase 서비스
└── utils/           # 유틸리티 클래스들
```

## 🚀 주요 기능

### 사용자 관리
- 사용자 회원가입/로그인
- 사용자 정보 수정
- Firebase 토큰 관리 (푸시 알림)
- 알림 시간 설정

### 작업 관리
- 작업 등록/수정/삭제
- 작업별 스트릭 기록
- 작업 연장 기능
- 작업 복구 기능 (무료/유료)

### 스트릭 관리
- 일별 체크 기록
- 월별 스트릭 통계
- 연속 기록 추적

### 알림 시스템
- 이메일 알림 (문의, 비밀번호 재설정)
- Firebase 푸시 알림
- 사용자 정의 알림 시간

## 🗄 데이터베이스 스키마

### User (사용자)
- id: 사용자 고유 ID
- name: 사용자 이름
- password: 비밀번호
- tempPassword: 임시 비밀번호
- state: 사용자 상태
- workCount: 작업 수
- alertTime: 알림 시간

### Work (작업)
- id: 작업 고유 ID
- name: 작업 이름
- descript: 작업 설명
- orderNum: 정렬 순서
- curStreak: 현재 연속 기록
- dayWeek: 주간 목표
- state: 작업 상태
- money: 보유 금액
- repair: 복구 가능 횟수

### Streak (스트릭 기록)
- id: 스트릭 고유 ID
- checkNum: 체크 번호
- month: 월

## 🔧 환경 설정

### 필수 환경 변수
```bash
# 데이터베이스 설정
db.url=jdbc:mysql://localhost:3306/mydb
db.username=root
db.password=root1234!!

# 이메일 설정
mail.password=your_gmail_app_password
```

### Docker Compose 실행
```bash
docker-compose up -d
```

### 애플리케이션 실행
```bash
./gradlew bootRun
```

## 📡 API 엔드포인트

### 사용자 API (`/api/user`)
- `GET /work` - 사용자의 작업 목록 조회
- `GET /logout` - 로그아웃
- `GET /user` - 사용자 정보 조회
- `POST /change` - 사용자 정보 수정
- `POST /firebase-token` - Firebase 토큰 저장
- `POST /alert-time` - 알림 시간 설정

### 작업 API (`/api/work`)
- `POST /register` - 작업 등록
- `POST /extend` - 작업 연장
- `GET /{streakId}` - 스트릭 정보 조회
- `POST /delete` - 작업 삭제
- `POST /repair` - 무료 복구
- `POST /repair-buy` - 유료 복구

### 이메일 API (`/api/mail`)
- `POST /inquiry` - 문의 이메일 발송

## 🔐 인증 및 보안

- 세션 기반 인증
- 비밀번호 암호화
- 이메일 인증
- Firebase 토큰 기반 푸시 알림

## 📧 이메일 기능

- Gmail SMTP 사용
- Thymeleaf 템플릿 엔진
- 문의 이메일, 비밀번호 재설정 이메일 지원

## 🔔 알림 시스템

- Firebase Cloud Messaging (FCM)
- 사용자 정의 알림 시간
- 스트릭 체크 알림

## 🧪 테스트

```bash
./gradlew test
```

## 📝 라이선스

이 프로젝트는 개인 학습 목적으로 개발되었습니다.
