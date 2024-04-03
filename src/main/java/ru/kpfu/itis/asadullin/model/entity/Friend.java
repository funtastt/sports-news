package ru.kpfu.itis.asadullin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "friends")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    @EmbeddedId
    private FriendId id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "user_id")
    private User friend;


}
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class FriendId implements Serializable {
    private int user;
    private int friend;
}