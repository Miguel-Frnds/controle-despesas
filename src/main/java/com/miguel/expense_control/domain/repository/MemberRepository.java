package com.miguel.expense_control.domain.repository;

import com.miguel.expense_control.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNameAndActiveTrue(String name);
    boolean existsByNameAndActiveTrueAndIdNot(String name, Long id);
    boolean existsByNameAndActiveFalse(String name);
    List<Member> findByActiveTrue();
    Optional<Member> findByIdAndActiveTrue(Long id);
}
