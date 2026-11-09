package com.webservice.service;

import com.webservice.dto.MemberRequest;
import com.webservice.dto.MemberResponse;
import com.webservice.entity.Member;
import com.webservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.ArrayList;

@Service
public class MemberService {
    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public MemberResponse create(MemberRequest request) {
        Member saved = repository.save(new Member(request));
        //Q7. 현재 Member에서 save()는 ID가 null이면 persist()를 호출해 INSERT로 새 데이터를 등록한다.
        // ID가 있으면 merge()로 객체의 값을 영속성 컨텍스트의 관리 객체에 반영하고, 변경된 값이 있으면 flush 시 UPDATE로 DB를 수정한다.
        return MemberResponse.from(saved);
    }

    public List<MemberResponse> findAll() {
        return repository.findAll().stream().map(MemberResponse::from).toList();
    }

    public MemberResponse findById(Long id) {
        return MemberResponse.from(findEntity(id));
    }

    public MemberResponse update(Long id, MemberRequest request) {
        Member member = findEntity(id);
        member.update(request);
        return MemberResponse.from(repository.save(member));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found: " + id);
        }
        repository.deleteById(id);
    }

    private Member findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Member not found: " + id));
    }

    // 이름으로 검색
    public List<MemberResponse> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        List<Member> members = repository.findByNameContainingIgnoreCase(keyword);
        List<MemberResponse> result = new ArrayList<>();

        for (Member member : members) {
            MemberResponse response = MemberResponse.from(member);
            result.add(response);
        }

        return result;
    }

    // 학과와 학년으로 검색
    public List<MemberResponse> searchByDepartmentAndGrade(String department, Integer grade) {
        List<Member> members = repository.findByDepartmentAndGradeGreaterThan(department, grade);
        List<MemberResponse> result = new ArrayList<>();

        for (Member member : members) {
            MemberResponse response = MemberResponse.from(member);
            result.add(response);
        }

        return result;
    }

    // 직책으로 검색하고 학년 내림차순으로 정렬
    public List<MemberResponse> searchByRole(String role) {
        List<Member> members = repository.findByRoleOrderByGradeDesc(role);
        List<MemberResponse> result = new ArrayList<>();

        for (Member member : members) {
            MemberResponse response = MemberResponse.from(member);
            result.add(response);
        }

        return result;
    }
}
