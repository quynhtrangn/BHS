package net.javaguides.springboot.request;

import jakarta.persistence.*;
import lombok.*;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.user.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    private long follow_id;
    private User users;
    private New news;
}
