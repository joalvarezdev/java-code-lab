package com.joalvarez.shared.data.dto.general;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseDTO(
        String status,
        String result,
        JsonNode error,
        JsonNode data,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp
) {

    public static final List<Integer> RESPONSE_OK = List.of(
            HttpStatus.OK.value(), HttpStatus.CREATED.value(), HttpStatus.ACCEPTED.value(),
            HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), HttpStatus.NO_CONTENT.value(),
            HttpStatus.RESET_CONTENT.value(), HttpStatus.PARTIAL_CONTENT.value(), HttpStatus.MULTI_STATUS.value(),
            HttpStatus.ALREADY_REPORTED.value(), HttpStatus.IM_USED.value()
    );

    public ResponseDTO(String status, String result, JsonNode data) {
        this(
                status,
                result,
                RESPONSE_OK.contains(Integer.parseInt(status)) ? null : data,
                RESPONSE_OK.contains(Integer.parseInt(status)) ? data: null,
                LocalDateTime.now()
        );
    }

}