package ru.kpfu.itis.asadullin.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Data
@AllArgsConstructor
public class CommentDto {
    private String text;

    private Timestamp sendingTime;

    private int likes;
    private int commentId;
    private String username;
    private int userId;
    private String profilePictureUrl;
    private int articleId;

    private boolean isLiked;

    public CommentDto(String text, Timestamp sendingTime, int likes, int commentId, int userId, String username, String profilePictureUrl, int articleId, boolean isLiked) {
        this.text = text;
        this.sendingTime = sendingTime;
        this.likes = likes;
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
        this.articleId = articleId;
        this.isLiked = isLiked;
    }

    public boolean getIsLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
