package com.supermercado.market.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    ADMIN(1L),
    CLIENTE(2L);

    private final Long id;
}