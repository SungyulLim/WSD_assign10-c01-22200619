package com.webservice.repository;

import com.webservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 단일 조건
    List<Member> findByNameContainingIgnoreCase(String keyword);

    // 복합 조건
    List<Member> findByDepartmentAndGradeGreaterThan(
            String department, Integer grade);

    // 검색 및 정렬
    List<Member> findByRoleOrderByGradeDesc(String role);
}
/*
Q3. JpaRepository<Entity, Long>에서 두 타입은 각각 무엇을 의미하는가?
=> Entity는 Repository가 관리하는 엔티티 클래스이고, 두 번째 타입 Long은 그 엔티티의 기본키 타입이다

Q4. JpaRepository에서 기본 제공하는 CRUD 메서드를 5개 이상 찾아 메서드명과 역할을 주석으로 작성하세요.

- save() : 새로운 엔티티를 등록하거나 기존 엔티티의 상태를 저장.
- findAll() : 해당 엔티티의 모든 데이터를 조회.
- findById(id) : Primary Key(ID)로 단건 조회.
- deleteById() : 지정한 ID를 가진 엔티티를 삭제.
- count(): 전체 엔티티 개수를 반환.

Q5. findById()의 반환 타입이 Optional인 이유는 무엇인가?

- 조회한 ID에 해당하는 데이터가 존재하지 않을 수 있기 때문이다.
데이터가 있으면 엔티티를 담은 Optional을 반환하고, 없으면 빈 Optional을 반환한다.
이를 통해 개발자가 조회 결과가 없는 경우를 명시적으로 처리할 수 있다.

Q6. JpaRepository에서 기본 제공하는 메서드와 개발자가 직접 작성하는 Query Method의 차이는 무엇인가?
- 기본 제공 메서드는 ID로 찾거나 저장,삭제하는 공통 기본 기능을 자동으로 제공하는 것이고,
쿼리 메서드는 개발자가 원하는 조건으로 검색하기 위해 직접 만드는 기능이다. 따라서 조건을 다르게 검색하고 싶으면 쿼리 매서드를 직접 작성해서 사용 가능하다.
*/
