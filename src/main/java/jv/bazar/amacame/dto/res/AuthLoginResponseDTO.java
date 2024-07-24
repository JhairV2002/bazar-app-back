package jv.bazar.amacame.dto.res;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthLoginResponseDTO(String username, String message, boolean status) {
}
