package net.javaguides.springboot.request;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldRequest {
    private long field_id;
    private String fieldName;
}
