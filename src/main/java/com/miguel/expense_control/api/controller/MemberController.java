package com.miguel.expense_control.api.controller;

import com.miguel.expense_control.api.dto.request.MemberRequestDTO;
import com.miguel.expense_control.api.dto.response.MemberResponseDTO;
import com.miguel.expense_control.domain.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> findAll(){
        List<MemberResponseDTO> members = memberService.findAll();
        return ResponseEntity.ok().body(members);
    }

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@Valid @RequestBody MemberRequestDTO dto){
        MemberResponseDTO member = memberService.createMember(dto);
        return ResponseEntity.status(201).body(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO dto){
        MemberResponseDTO updateMember = memberService.updateMember(id, dto);
        return ResponseEntity.ok().body(updateMember);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateMember(@PathVariable Long id){
        memberService.deactivateMember(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateMember(@PathVariable Long id){
        memberService.activateMember(id);
        return ResponseEntity.noContent().build();
    }
}
