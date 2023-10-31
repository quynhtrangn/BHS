package net.javaguides.springboot.request;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.models.New;
import net.javaguides.springboot.user.User;

@Getter
@Setter
@Builder
public class FollowRequest {
    private long follow_id;
    private User users;
    private New news;
}
