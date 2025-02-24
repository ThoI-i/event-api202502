
package com.study.event.domain;

import lombok.Builder;

@Builder
public record SignupRequest(
        String email,
        String password
) {


}