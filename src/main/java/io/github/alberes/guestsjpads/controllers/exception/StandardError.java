package io.github.alberes.guestsjpads.controllers.exception;

import io.github.alberes.guestsjpads.controllers.dto.FieldErrorDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

    private List<FieldErrorDto> fields;

}