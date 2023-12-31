package net.javaguides.springboot.request;
import lombok.*;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.user.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkWithRequest {
    private long workwith_id;
    private User users;
    private Field field;
}
