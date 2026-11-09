package com.webservice.controller;

import com.webservice.dto.MemberRequest;
import com.webservice.dto.MemberResponse;
import com.webservice.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberRequest request) {
        MemberResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/members/" + created.id())).body(created);
    }

    @GetMapping("/search/by-department-and-grade")
    public List<MemberResponse> searchByDepartmentAndGrade(@RequestParam("department") String department, @RequestParam("grade") Integer grade) {
        List<MemberResponse> result = service.searchByDepartmentAndGrade(department, grade);
        return result;
    }

    @GetMapping("/search/by-role")
    public List<MemberResponse> searchByRole(@RequestParam("role") String role) {
        List<MemberResponse> result = service.searchByRole(role);
        return result;
    }

    @GetMapping
    public List<MemberResponse> findAll(@RequestParam(value = "keyword", required = false) String keyword) {
        List<MemberResponse> result = service.searchByName(keyword);
        return result;
    }

    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @Valid @RequestBody MemberRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
