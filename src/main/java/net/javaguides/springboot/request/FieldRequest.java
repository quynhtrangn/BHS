package net.javaguides.springboot.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldRequest {
    private long field_id;
    private String fieldName;

}
