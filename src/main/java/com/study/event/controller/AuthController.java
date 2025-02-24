package com.study.event.controller;

import com.study.event.domain.eventUser.dto.request.SignupRequest;
import com.study.event.service.EventUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final EventUserService eventUserService;

    // email 중복확인 API 생성
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(String email) {
        boolean isDuplicate = eventUserService.checkEmailDuplicate(email);
        String message = isDuplicate ? "이메일이 중복되었습니다." : "사용 가능한 이메일입니다.";

        return ResponseEntity.ok().body(Map.of(
                "isDuplicate", isDuplicate,
                "message", message
        ));
    }

    // 인증 코드 검증 API
    @GetMapping("/code")
    public ResponseEntity<?> verifyCode(String email, String code) {
        log.info("{}'s verify code is [ {} ]", email, code);

        boolean isMatch = eventUserService.isMatchCode(email, code);

        log.info("code matches? - {}", isMatch);

        return ResponseEntity.ok().body(Map.of(
                "isMatch", isMatch
        ));
    }

    // 회원가입 마무리 요청
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody SignupRequest dto) {

        log.info("save request user info - {}", dto);

        eventUserService.confirmSignup(dto);

        return ResponseEntity.ok().body(Map.of(
                "message", "회원가입이 완료되었습니다."
        ));
    }
}