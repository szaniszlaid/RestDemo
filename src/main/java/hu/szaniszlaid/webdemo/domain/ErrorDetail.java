package hu.szaniszlaid.webdemo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {
    private String title;
    private String detail;
    private long timeStamp;
    private String developerMessage;
}