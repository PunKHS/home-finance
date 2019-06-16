package ru.hf.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class ErrorResponse {

    private int status;

    private String message;

    private long timeStamp;
}
