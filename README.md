# 2026 신입 Back-End 개발자 코딩 과제 - 간단한 CMS REST API

Spring Boot 기반의 간단한 CMS API 서버입니다.
회원가입, 로그인(JWT), 콘텐츠 CRUD, 접근 권한 기능을 제공합니다.

## Spec

- Java 25
- Spring Boot 4
- Spring Security
- JPA
- JWT
- H2 (db)
- Lombok

## 데이터 모델

### Contents

| 컬럼명                | 이름  | 설명          | 데이터 타입                      | 비고 |
|--------------------|-----|-------------|-----------------------------|----|
| id                 | 아이디 | 고유 아이디      | bigint primary key not null |    |
| title              | 제목  | contents 제목 | varchar(100) not null       |    |
| description        | 내용  | contents 내용 | text                        |    |
| view_count         | 조회수 | 조회수         | bigint not null             |    |
| created_date       | 생성일 | 생성한 날짜      | timestamp                   |    |
| created_by         | 생성자 | 생성한 사용자     | varchar(50) not null        |    |
| last_modified_date | 수정일 | 마지막 수정일     | timestamp                   |    |
| last_modified_by   | 수정자 | 마지막 수정한 사용자 | varchar(50)                 |    |

### Users
| 컬럼명                | 이름   | 설명          | 데이터 타입                       | 비고 |
|--------------------|------|-------------|------------------------------|----|
| id                 | 아이디  | 고유 아이디      | bigint primary key not null  |    |
| email              | 이메일  | 사용자 email   | varchar(100) not null unique |    |
| password           | 비밀번호 | 사용자 비밀번호    | varchar(255) not null        |    |
| name               | 이름   | 사용자 이름      | varchar(50) not null         |    |
| role               | 역활   | 사용자 역활      | varchar(20) not null         |    |

## 구현 기능

### 로그인

#### API 명세서
| 기능 | Method | URL |
|-----|------|-----|
| 회원가입 | POST | /auth/signup |
| 로그인 | POST | /auth/login |

#### 회원가입

- **Method** : POST
- **URL** : `/auth/signup`

##### Request
```json
{
  "email": "test@test.com",
  "password": "1234",
  "name": "tester"
}
```

##### Response
```json
{
  "id": 2,
  "email": "test@test.com",
  "name": "tester",
  "role": "USER"
}
```
* id가 2가 나오는 건 프로그램 실행 시 admin계정을 생성하기 때문입니다.

#### 로그인

- **Method** : POST
- **URL** : `/auth/login`

##### Request
```json
{
  "email": "test@test.com",
  "password": "1234"
}
```

##### Response
```json
{
  "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3NzI5Njk4NzQsImV4cCI6MTc3Mjk3MzQ3NH0.oQDaKpgrfDi_e1m60kzpATgP0NC_FWdreGbB9IYgiTatXpHXAfB9sYCUPnH9WIst"
}
```

### 콘텐츠 관련 CRUD

#### API 명세서
| 기능 | Method | URL |
|-----|------|-----|
| 콘텐츠 생성 | POST | /contents |
| 콘텐츠 목록 조회 | GET | /contents |
| 콘텐츠 상세 조회 | GET | /contents/{id} |
| 콘텐츠 수정 | PUT | /contents/{id} |
| 콘텐츠 삭제 | DELETE | /contents/{id} |

#### 콘텐츠 생성

- **Method** : POST
- **URL** : `/contents`

##### Header

- Authorization: Bearer {accessToken}

##### Request
```json
{
  "title": "Test Title",
  "description": "Test description"
}
```

##### Response
```json
{
  "createdBy": "2",
  "createdDate": "2026-03-08T20:38:07.4412475",
  "description": "Test description",
  "id": 1,
  "lastModifiedBy": null,
  "lastModifiedDate": null,
  "title": "Test Title",
  "viewCount": 0
}
```

#### 콘텐츠 목록 조회

- **Method** : GET
- **URL** : `/contents`

##### Response
```json
{
  "content": [
    {
      "createdBy": "2",
      "createdDate": "2026-03-08T20:38:07.441248",
      "description": "Test description",
      "id": 1,
      "lastModifiedBy": null,
      "lastModifiedDate": null,
      "title": "Test Title",
      "viewCount": 0
    }
  ],
  "first": true,
  "last": true,
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

#### 콘텐츠 상세 조회

- **Method** : GET
- **URL** : `/contents/{id}`

##### Response
```json
{
  "createdBy": "2",
  "createdDate": "2026-03-08T17:15:43.119131",
  "description": "Test description",
  "id": 1,
  "lastModifiedBy": null,
  "lastModifiedDate": null,
  "title": "Test Title",
  "viewCount": 1
}
```

#### 콘텐츠 수정

- **Method** : PUT
- **URL** : `/contents/{id}`

##### Header

- Authorization: Bearer {accessToken}

##### Request
```json
{
  "title": "Update title",
  "description": "Update description"
}
```

##### Response
```json
{
  "createdBy": "2",
  "createdDate": "2026-03-08T20:29:36.40381",
  "description": "Update description",
  "id": 1,
  "lastModifiedBy": "1",
  "lastModifiedDate": "2026-03-08T20:29:55.3911919",
  "title": "Update title",
  "viewCount": 0
}
```

#### 콘텐츠 삭제

- **Method** : DELETE
- **URL** : `/contents/{id}`

##### Header

- Authorization: Bearer {accessToken}

### 접근 권한

- Token 내에 사용자의 역활 정보를 저장
- 콘텐츠 수정, 삭제를 사용할 때 해당 Token에서 사용자의 id와 역활을 확인
- 콘텐츠를 올린 본인 혹은 사용자의 역활이 "admin"인 경우 수정 및 삭제 가능

## AI

- Chat GPT
  - 오류 로그 및 검증, 개선 방향 확인
  - 프로그램 기본 뼈대 및 구현










