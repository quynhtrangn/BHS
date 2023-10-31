package net.javaguides.springboot.request;
import lombok.*;
import net.javaguides.springboot.models.City;
import net.javaguides.springboot.models.Market;
import net.javaguides.springboot.user.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceRequest {
    private long workplace_id;
    private User users;
    private Market market;

}
