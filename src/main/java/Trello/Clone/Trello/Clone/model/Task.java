/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.model;
import Trello.Clone.Trello.Clone.state.TaskState;
import Trello.Clone.Trello.Clone.state.TodoState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private String id;
    private String description;
    private String assignedToEmail;
    private String deadline;
    private int priority;
    private int complexity;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime todoEnteredAt;
    private LocalDateTime doingEnteredAt;
    private LocalDateTime doneEnteredAt;
    private List<Comment> comments = new ArrayList<>();

    @JsonSerialize(using = TaskStateSerializer.class)
    private TaskState state;

    public Task(String description, String assignedToEmail, String deadline, int priority, int complexity) {
        this.description = description;
        this.assignedToEmail = assignedToEmail;
        this.deadline = deadline;
        this.priority = priority;
        this.complexity = complexity;
        this.createdAt = LocalDateTime.now();
        this.todoEnteredAt = this.createdAt;
        this.updatedAt = LocalDateTime.now();
        this.state = new TodoState();
    }

    public void setState(TaskState state) {
        this.state = state;
        this.updatedAt = LocalDateTime.now();
    }

    public TaskState getState() {
        return state;
    }

    public void start() {
        state.start(this);
    }

    public void complete() {
        state.complete(this);
    }

    public void reopen() {
        state.reopen(this);
    }

    public void updateTodoTimestamp() {
        this.todoEnteredAt = LocalDateTime.now();
    }

    public void updateDoingTimestamp() {
        this.doingEnteredAt = LocalDateTime.now();
        if (this.startedAt == null) {
            this.startedAt = this.doingEnteredAt;
        }
    }

    public void updateDoneTimestamp() {
        this.doneEnteredAt = LocalDateTime.now();
        this.completedAt = this.doneEnteredAt;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAssignedToEmail() { return assignedToEmail; }
    public void setAssignedToEmail(String assignedToEmail) { this.assignedToEmail = assignedToEmail; }
    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public int getComplexity() { return complexity; }
    public void setComplexity(int complexity) { this.complexity = complexity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getTodoEnteredAt() { return todoEnteredAt; }
    public LocalDateTime getDoingEnteredAt() { return doingEnteredAt; }
    public LocalDateTime getDoneEnteredAt() { return doneEnteredAt; }
    public List<Comment> getComments() { return comments; }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        this.updatedAt = LocalDateTime.now();
    }
}
