package com.webservice.entity;

import com.webservice.dto.MemberRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Q1. Member를 JPA가 관리하는 Entity로 지정
@Entity
@Table(name = "members") // 실제 테이블의 이름을 명시, Q2. Member 클래스가 DB의 members 테이블과 연결되고, 객체 하나가 테이블의 행 하나에 연결됨.
public class Member {
    @Id // Q1. id 필드가 엔티티를 구분하는 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Q1. 기본키 값을 자동 생성. 현재 strategy = GenerationType.IDENTITY를 통해서 자동 증가
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false)
    private Integer grade;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 50)
    private String role;

    protected Member() { } // JPA uses the no-args constructor.

    public Member(MemberRequest request) {
        this.name = request.name();
        this.studentId = request.studentId();
        this.department = request.department();
        this.grade = request.grade();
        this.email = request.email();
        this.role = request.role();
    }

    public void update(MemberRequest request) {
        this.name = request.name();
        this.studentId = request.studentId();
        this.department = request.department();
        this.grade = request.grade();
        this.email = request.email();
        this.role = request.role();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public String getDepartment() { return department; }
    public Integer getGrade() { return grade; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
