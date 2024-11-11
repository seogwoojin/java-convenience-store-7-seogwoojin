# java-convenience-store-precourse

## 구현할 기능 목록 (v1 - 2024.11.06)

### 1. 입출력 기능

- [X] 입력값 받기
    - [X] 입력값을 받을 때, 주어진 라이브러리를 이용함
    - [X] 파일 입출력을 통해 구현용 상품 목록과 행사 목록을 가져옴
    - [X] 구매할 상품과 수량을 입력 받으며, 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분함
        - [X] 형식 검증: 입력된 값이 "[콜라-10]" 과 같이 올바른 형식을 따르는지 확인함 (대괄호, 하이픈 등)
        - [X] 구분자 검증: 쉼표로 구분된 여러 상품들이 제대로 구분되었는지 확인함
        - [X] 숫자 검증: 수량이 정수로 입력되었는지 확인함
        - [X] 빈 값 체크: 상품명이나 수량이 비어 있지 않은지 확인함
    - [X] 프로모션 추가 적용 여부를 "Y/N" 으로 받음 (양 옆 공백은 허용함)
    - [X] 프로모션 미적용 상품 결제 여부를 "Y/N" 으로 받음 (양 옆 공백은 허용함)
    - [X] 멤버십 할인 적용 여부를 "Y/N" 으로 받음 (양 옆 공백은 허용함)
    - [X] 추가 구매 여부를 "Y/N" 으로 받음 (양 옆 공백은 허용함)
    - [X] 모든 유저의 추가 요청이 끝나면 멤버십 할인 여부를 입력받음

- [ ] 결과 출력 하기
    - [ ] 매 구매 전, 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내함
        - [ ] 재고가 0이면 "재고 없음" 을 출력함
    - [ ] 프로모션 추가 적용 혜택 메시지를 출력함
    - [ ] 프로모션 미적용 정가 결제 여부 메시지를 출력함
    - [ ] 멤버십 할인 적용 여부 확인용 메시지를 출력함
    - [ ] 매 구매 이후, 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력함
    - [ ] 추가 구매 여부 확인 메시지를 출력함
    - [ ] 사용자가 잘못된 값을 입력했을 때, "[ERROR]"로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력함
    - [ ] 프로모션만 존재하는 상품은 출력 시, 프로모션이 없는 동일한 상품을 "재고 없음"으로 출력함
    - [ ] 프로모션이 존재하지 않는 상품은 출력 시, 프로모션이 없는 재고만 출력함

### 2. 상품 기능

- [X] 상품 추가
    - [X] 상품 추가 시, 상품이름, 가격, 프로모션 정보를 필요로 함
    - [X] 프로모션은 promotions.md 내의 프로모션을 가진 문자열만 유효함
    - [X] 수량은 0 이상의 정수로 제한
    - [X] 한 상품이 프로모션을 두 개 이상 가질 수 없음

- [ ] 상품 판매
    - [ ] 프로모션 적용 시, 프로모션 상품부터 선 판매함
    - [ ] 만약 프로모션 재고가 부족할 경우에는 일반 재고를 사용
    - [ ] 주문의 모든 상품을 처리할 수 있을 때만, 재고를 변경함 (트랜잭션)

### 3. 주문 기능

- [X] 구매 주문
    - [X] 주문은 상품명과 수량
    - [X] 존재하지 않는 상품을 주문할 시 주문을 다시 받음
    - [X] 멤버십 할인 여부가 필요함

- [ ] 영수증
    - [ ] 고객의 구매 내역과 할인을 요약함
    - [ ] 영수증에는 구매 상품, 수량, 가격, 증정 상품, 총구매액, 행사 할인 금액, 최종 금액이 포함됨

### 4. 할인 기능

- [X] 프로모션 혜택
    - [X] 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용
    - [X] 프로모션의 이름은 중복될 수 없음

- [ ] 멤버십 혜택
    - [ ] 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받음
    - [ ] 멤버십 할인의 최대 한도는 8,000원임
    - [ ] 프로모션 적용 후 남은 금액에 대해서만 멤버십 할인을 적용함

---

## 지난 주 피드백 사항

- 필드의 인스턴스 변수 최소화
- 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
- 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현한다.

## 신경써서 진행할 것

- InputView에서는 형식과 기본적인 유효성을 검증
- Model에서는 비즈니스 로직에 맞는 검증을 수행
- 예외처리 꼼꼼하게 진행하기
- 모델은 비지니스 로직에 집중할 수 있도록 DTO에서 유효성 검사를 진행한다.
- 모델은 DTO의 변경에 영향 받지 않도록 설계