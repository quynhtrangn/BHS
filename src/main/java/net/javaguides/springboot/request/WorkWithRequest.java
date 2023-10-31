package net.javaguides.springboot.request;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.user.User;

@Getter
@Setter
@Builder
public class WorkWithRequest {
    private long workwith_id;
    private User users;
    private Field field;
}
