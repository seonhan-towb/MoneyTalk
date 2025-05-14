# 💸 MoneyTalk

Jetpack Compose로 구현된 **안드로이드 가계부 앱** 프로젝트입니다.  
날짜별로 돈을 **어디서, 어떻게 썼는지** 쉽게 입력하고 확인할 수 있는 **심플한 기록형 앱**입니다.

---

## 📱 주요 기능

- 📅 **캘린더 기반 UI**에서 날짜 선택
- 🕒 날짜별 **타임테이블 방식 일정 등록 (시간 + 메모)**
- 🔻 월/년도/요일 시작일 Dropdown 선택 가능
- 🧠 상태는 **ViewModel + StateFlow**로 관리
- 🧩 **컴포저블 함수 분리 & 기능별 디렉토리 구조 적용**
- 🛠️ 앞으로 Room DB, 소비 분류/통계 기능 확장 예정

---

## 🛠 기술 스택

| 분류 | 내용 |
|------|------|
| 언어 | Kotlin 1.9.22 |
| UI 프레임워크 | Jetpack Compose (Material 3) |
| DI | Hilt |
| 아키텍처 | MVVM + StateFlow (단방향 데이터 흐름) |
| 로그 | Timber |
| 날짜 처리 | java.time.LocalDate, LocalTime |


---

## 💬 개발 노트

- `1일 1커밋` 실천 중!
- 커밋 메시지 컨벤션:  
  `feat:`, `fix:`, `refactor:`, `chore:`, `style:`, `docs:`
