package ru.hf.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private long timeStamp;
}
