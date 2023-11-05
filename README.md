![header](https://capsule-render.vercel.app/api?type=waving&height=150&text=Project_ChattingApp&fontAlign=60&fontAlignY=50&color=gradient)


## 개요
* 채팅 애플리케이션(Chatting Application)

---
## 주요 기능
* 전체 채팅
* 유저와의 귓속말 채팅

---
## 제작 및 실행 환경
* JDK : Java SE 11
* IDE : Eclipse 및 IntelliJ IDEA

---
## 사용 API
* Java 내장 AWT API

---
## 실행법
* 서버 구동
  * 소스폴더(src 폴더) 내의 chat.server 패키지 내의 EzenTalkServer.java 실행
* 클라이언트가 사용할 어플리케이션 구동
  * 소스폴더(src 폴더) 내의 chat.client 패키지 내의 ArsenalTalkApp.java 실행

---
## 실행 시 주의사항
* 이클립스 : 프로젝트 실행환경 -> VM arguments 에 -Dfile.encoding=MS949 입력 -> 적용
* 인텔리제이 : 프로젝트 실행 구성 -> 어플리케이션 구성 추가 -> 실행 옵션에서 VM 옵션 추가 -> VM 옵션 란에 -Dfile.encoding=MS949 입력 -> 적용

---
## 소스 내 패키지
* chat.client : 채팅 어플리케이션 클라이언트 부분 구현
* chat.protocol : 통신을 위한 프로토콜 구현
* chat.server : 채팅 어플리케이션 서버 부분 구현
* chat.handler : 채팅 어플리케이션의 원활한 사용을 위한 기타 유틸리티

---
## 기능 및 설명
* 접속
  * 닉네임 입력 > 엔터 또는 연결 버튼 클릭
* 채팅
  * 좌측 하단에서 모두에게 선택 > 메세지 입력 > 엔터 또는 보내기 클릭
* 특정 사용자에게 귓속말 전송
  * 좌측 하단에서 원하는 사용자 선택 > 메세지 입력 > 엔터 또는 보내기 클릭
* 종료
  * 우측 상단에서 연결 종료 버튼 클릭 또는 채팅 창 종료

---
## 구현 기록

* 서버 구현 및 전체 채팅만 가능
* 현재 접속 중인 사용자 목록 출력
* 전체 채팅 뿐 아니라 특정 사용자에게 귓속말 가능한 기능 추가
* 전체 채팅과 귓속말(송수신) 분리해서 모두 메세지 창에 출력

