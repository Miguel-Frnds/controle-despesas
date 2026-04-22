package com.miguel.expense_control.api.mapper;

import com.miguel.expense_control.api.dto.request.MemberRequestDTO;
import com.miguel.expense_control.api.dto.response.MemberResponseDTO;
import com.miguel.expense_control.domain.entity.Member;

public class MemberMapper {

    public static Member toEntity(MemberRequestDTO dto){
        Member member = new Member();
        member.setName(dto.name());
        member.setActive(true);
        return member;
    }

    public static void updateEntity(Member member, MemberRequestDTO dto){
        member.setName(dto.name());
    }

    public static MemberResponseDTO toResponseDTO(Member member){
        return new MemberResponseDTO(
                member.getId(),
                member.getName()
        );
    }
}
