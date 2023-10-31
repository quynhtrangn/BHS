package net.javaguides.springboot.request;

import lombok.*;
import net.javaguides.springboot.models.City;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketRequest {
    private long market_id;
    private String market_name;
    private String address;
    private City city;
}
