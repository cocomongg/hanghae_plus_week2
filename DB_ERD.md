# 1. ERD
![스크린샷 2024-10-04 오전 3 39 07](https://github.com/user-attachments/assets/d5df8992-04ff-4013-9aa2-df061d616138)
<br/>
<br/>

# 2. 테이블 관계
- `lecture_apply_history` 와 `mebmer`, `lecture`, `lecture_option`은 N:1 관계로 설정했습니다.
- `lecture` 와 `lecture_option`은 1:N 관계로 설정했습니다.
  - 확장 가능성을 열어두고, 하나의 특강에 여러 옵션(강의 시간 등)이 생길 수 있는 상황을 가정했습니다.
<br/>

# 3. 테이블 컬럼
### `lecture`
- `lecture_id` : pk
- `title` : 특강 제목
- `description` : 특강 관련 설명 혹은 내용 등
- `lecturer_name` : 특강 강사 이름
- `created_at` : DB에 실제 삽입된 일시
- `updated_at` : 업데이트 일시
<br/>

### `lecture_option`
- `lecture_option_id` : pk
- `lecture_id` : lecture테이블을 참조하는 fk
- `max_apply_count` : 최대 신청 인원 수
- `current_apply_count` : 현재 신청 인원 수
- `apply_start_date` : 신청 시작일
- `apply_end_date` : 신청 종료일
- `lecture_start_at` : 강의 시작일시
- `lecture_end_at` : 강의 종료일시
- `created_at`: DB에 실제 삽입된 일시
- `updated_at`: 업데이트 일시
<br/>

### `lecture_apply_history`
- `lecture_apply_history_id` : pk
- `lecture_id`: lecture테이블을 참조하는 fk
- `member_id`: member테이블을 참조하는 fk
- `success`: 특강 신청에 대한 성공과 실패를 구분하는 컬럼
- `applied_at`: 특강 신청 일시
- `created_at`: DB에 실제 삽입된 일시
<br/>

### `member`
- `id` : pk
- `name`: 이름
- `created_at`: DB에 실제 삽입된 일시
- `updated_at`: 업데이트 일시
<br/>

# 4. 고려해볼 점
- 강사 테이블 생성
  - 강사 테이블을 따로 생성해서 lecture와 참조 관계를 맺으면 강사 이름을 변경할 때 강사 테이블만 변경할 수 있는 등 확장성이 있다고 생각합니다.
- 특강 신청 인원 수 관련 테이블 생성
  - 특강 신청 인원 수에 대한 테이블을 분리하여 해당 테이블의 record에만 Lock을 걸면 성능이 조금 올라갈 수 있지 않을까? 라는 생각을 했습니다.
  - 결국 lock을 걸어야 하는 이유는 명확한데 지금 테이블 구조 상으로는 다른 컬럼까지 lock으로 잠기는게 맞는가에 대한 의문이 생겼습니다.   

