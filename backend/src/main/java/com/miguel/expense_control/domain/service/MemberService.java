package com.miguel.expense_control.domain.service;

import com.miguel.expense_control.api.dto.request.MemberRequestDTO;
import com.miguel.expense_control.api.dto.response.MemberResponseDTO;
import com.miguel.expense_control.domain.entity.Member;
import com.miguel.expense_control.domain.exception.*;
import com.miguel.expense_control.api.mapper.MemberMapper;
import com.miguel.expense_control.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<MemberResponseDTO> findMembers(Boolean active){
        if(active == null){
            return memberRepository.findAll().stream()
                    .map(MemberMapper::toResponseDTO)
                    .toList();
        }

        return memberRepository.findByActive(active).stream()
                .map(MemberMapper::toResponseDTO)
                .toList();
    }

    public MemberResponseDTO createMember(MemberRequestDTO dto){
        if(memberRepository.existsByNameAndActiveTrue(dto.name())){
            throw new NameAlreadyExistsException(dto.name());
        }

        if(memberRepository.existsByNameAndActiveFalse(dto.name())){
            throw new MemberIsInactiveException(dto.name());
        }

        Member member = MemberMapper.toEntity(dto);

        return MemberMapper.toResponseDTO(memberRepository.save(member));
    }

    public MemberResponseDTO updateMember(Long id, MemberRequestDTO dto){
        if(memberRepository.existsByNameAndActiveTrueAndIdNot(dto.name(), id)){
            throw new NameAlreadyExistsException(dto.name());
        }

        Member foundMember = memberRepository.findByIdAndActiveTrue(id)
                .orElseThrow(MemberNotFoundException::new);

        MemberMapper.updateEntity(foundMember, dto);
        return MemberMapper.toResponseDTO(memberRepository.save(foundMember));
    }

    public void deactivateMember(Long id){
        Member foundMember = getById(id);
        if(!foundMember.isActive()) {
            throw new MemberIsAlreadyDeactivate();
        }
        foundMember.setActive(false);
        memberRepository.save(foundMember);
    }

    public void activateMember(Long id){
        Member foundMember = getById(id);
        if(foundMember.isActive()) {
            throw new MemberIsAlreadyActivate();
        }
        foundMember.setActive(true);
        memberRepository.save(foundMember);
    }

    public Member getById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }
}
