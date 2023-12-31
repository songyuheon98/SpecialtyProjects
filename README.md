---

![](https://velog.velcdn.com/images/songyuheon/post/0576f746-236b-434a-811d-f1720e97fac8/image.png)

---


## 프로젝트 소개 
---

* 로그인한 사용자들에게 프로젝트 관리를 위한 칸반 보드 기반의 트렐로 서비스를 제공하는 웹 플랫폼
---
### 프로젝트 구성원

* 팀장 : 송유헌
* 팀원 : 최수경
* 팀원 : 강다온

---

---
## 프로젝트 기간 
* 2023/09/15 ~ 2023/09/27
---
## 커뮤니케이션

* GitHub
* Slack
* Notion

---
## **프로젝트 기술 스택**

---

### **1. Application**
  
  - **프론트엔드** 
   HTML5, CSS3, Thymeleaf
   
  
   - **백엔드** 
   Java, Gradle, Spring Boot, Spring Security, JPA

### **2. Test**
   - JUnit5, Postman, Mockito

### **3. Database**
   - AWS-RDS ( MySQL ), H2-DB( Test )

### **4. Infra**
   - GitHub Actions, AWS ( EC2 + RDS + Route53 )

---
## 아키텍처

---

![](https://velog.velcdn.com/images/songyuheon/post/f7629801-4cce-4d78-a6ed-d328c746d06e/image.png)


---

## CI / CD
---

![](https://velog.velcdn.com/images/songyuheon/post/9de75d00-4458-472c-95b3-ef9057a32995/image.png)


---

## ERD 
---

![](https://velog.velcdn.com/images/songyuheon/post/282acb83-11a4-4acf-989d-18f43f507c67/image.png)


---
## 핵심 기능
---
1. 사용자 관리 기능 [ 로그인 ( Spring Security 사용 ), 회원가입, 회원탈퇴, 로그아웃 ]
2. 컨텐츠 관리 기능 [ 보드 & 컬럼 & 카드 ( 생성, 조회, 수정, 삭제 기능 ) ]
3. 로드 밸런스를 이용한 부하 분산 기능
4. HTTPS를 이용한 보안 기능

---
## 테스트 
---
![](https://velog.velcdn.com/images/songyuheon/post/17f7fe08-0a3a-4164-a462-06cfc9f43e93/image.png)

---
## 트러블 슈팅
---

- [**SpringBoot에서 Controller 인증 에러 트러블 슈팅 (Error 403, 401)**](https://velog.io/@songyuheon/SpringBoot%EC%97%90%EC%84%9C-Controller-%EC%9D%B8%EC%A6%9D-%EC%97%90%EB%9F%AC-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-Error-403-401)
- [**컨텍스트 오염 문제 (Context Pollution)**](https://velog.io/@songyuheon/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)
- [**Java Mocking Static Method**](https://velog.io/@songyuheon/Java-Mocking-Static-Method)
- [**NoSuchElementException**](https://velog.io/@songyuheon/NoSuchElementException)
- [**Mockito Argument Matcher 오류 해결 방법**](https://velog.io/@songyuheon/Mockito-Argument-Matcher-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95)
- [**Spring Mockito when() 메서드 바디 비어있는 문제 해결**](https://velog.io/@songyuheon/Spring-Mockito-when-%EB%A9%94%EC%84%9C%EB%93%9C-%EB%B0%94%EB%94%94-%EB%B9%84%EC%96%B4%EC%9E%88%EB%8A%94-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)
- [**JPA와 @Transactional: 문제 해결하기 (AssertionFailedError)**](https://velog.io/@songyuheon/JPA%EC%99%80-Transactional-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0-AssertionFailedError)
- [**Test 코드 작성 및 GitHub Action에 적용을 위한 트러블 슈팅**](https://velog.io/@songyuheon/Test-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1-%EB%B0%8F-GitHub-Action%EC%97%90-%EC%A0%81%EC%9A%A9%EC%9D%84-%EC%9C%84%ED%95%9C-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)
- [**Lombok - @Builder와 @NoArgsConstructor 함께 사용시 발생하는 오류와 해결법**](https://velog.io/@songyuheon/Lombok-Builder%EC%99%80-NoArgsConstructor-%ED%95%A8%EA%BB%98-%EC%82%AC%EC%9A%A9%EC%8B%9C-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-%EC%98%A4%EB%A5%98%EC%99%80-%ED%95%B4%EA%B2%B0%EB%B2%95)
- [**GitHub Action -> EC2 배포 [SpringBoot]**](https://velog.io/@songyuheon/GitHub-Action-EC2-%EB%B0%B0%ED%8F%AC)
- [**CI/CD 적용 (GitHub Action)**](https://velog.io/@songyuheon/CICD-%EC%A0%81%EC%9A%A9-GitHub-Action)
- [**GitHub Actions 워크플로우 오류 해결**](https://velog.io/@songyuheon/GitHub-Actions-%EC%9B%8C%ED%81%AC%ED%94%8C%EB%A1%9C%EC%9A%B0-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0)
- [**Thymeleaf 템플릿 에러와 GitHub Actions를 사용한 해결 방법**](https://velog.io/@songyuheon/Thymeleaf-%ED%85%9C%ED%94%8C%EB%A6%BF-%EC%97%90%EB%9F%AC%EC%99%80-GitHub-Actions%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%9C-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95-adxo903t)

---


