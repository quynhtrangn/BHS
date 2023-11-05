package net.javaguides.springboot.request;

import lombok.*;
import net.javaguides.springboot.models.Field;
import net.javaguides.springboot.user.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareRequest {
    private long care_id;
    private User users;
    private User userCared;
}
