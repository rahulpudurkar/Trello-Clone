/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.model;
import java.time.LocalDateTime;

public class Comment {
    private String commentText;
    private String author;
    private LocalDateTime createdAt;

    public Comment(String commentText, String author) {
        this.commentText = commentText;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

