package net.javaguides.springboot.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityRequest {
    private long city_id;
    private String city_name;
}
