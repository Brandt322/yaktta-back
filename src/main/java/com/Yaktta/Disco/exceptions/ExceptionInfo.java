package com.Yaktta.Disco.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionInfo {

  @JsonProperty("timestamp")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  @JsonProperty("code")
  private Integer statusCode;

  @JsonProperty("status")
  private String statusName;

  @JsonProperty("message")
  private String message;

  @JsonProperty("path")
  private String uriRequested;

}
