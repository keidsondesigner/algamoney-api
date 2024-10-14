package com.keidson.algamoney_api.dto;

import java.util.Set;

public record ResponseDTO(String email, String token, Set<Long> permissoes) { }
