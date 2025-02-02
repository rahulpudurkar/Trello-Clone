/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.controller;
import Trello.Clone.Trello.Clone.model.Comment;
import Trello.Clone.Trello.Clone.model.Task;
import Trello.Clone.Trello.Clone.state.DoingState;
import Trello.Clone.Trello.Clone.state.DoneState;
import Trello.Clone.Trello.Clone.state.TaskState;
import Trello.Clone.Trello.Clone.service.TaskService;
import Trello.Clone.Trello.Clone.service.sorting.SortingStrategy;
import Trello.Clone.Trello.Clone.service.sorting.DeadlineSortingStrategy;
import Trello.Clone.Trello.Clone.service.sorting.PrioritySortingStrategy;
import Trello.Clone.Trello.Clone.state.TodoState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateRequest request) {
        Task task = taskService.createTask(
                request.getDescription(),
                request.getAssignedToEmail(),
                request.getDeadline(),
                request.getPriority(),
                request.getComplexity()
        );

        if (request.getComments() != null) {
            for (Comment comment : request.getComments()) {
                task.addComment(comment);
            }
        }

        taskService.updateTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{taskId}/updateState")
    public ResponseEntity<?> updateTaskState(@PathVariable String taskId, @RequestParam String newState) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }

        try {
            TaskState taskState;
            switch (newState.toUpperCase()) {
                case "TODO":
                    taskState = new TodoState();
                    break;
                case "DOING":
                    taskState = new DoingState();
                    break;
                case "DONE":
                    taskState = new DoneState();
                    break;
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid state provided");
            }

            task.setState(taskState);
            taskService.updateTask(task);
            return ResponseEntity.ok(task);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task state");
        }
    }

    @PutMapping("/{taskId}/update")
    public ResponseEntity<Task> updateTask(
            @PathVariable String taskId,
            @RequestBody Task updatedTaskData
    ) {
        Task existingTask = taskService.getTaskById(taskId);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (updatedTaskData.getDescription() != null) existingTask.setDescription(updatedTaskData.getDescription());
        if (updatedTaskData.getDeadline() != null) existingTask.setDeadline(updatedTaskData.getDeadline());
        if (updatedTaskData.getPriority() != 0) existingTask.setPriority(updatedTaskData.getPriority());
        if (updatedTaskData.getComments() != null) {
            existingTask.getComments().clear();
            existingTask.getComments().addAll(updatedTaskData.getComments());
        }

        taskService.updateTask(existingTask);
        return ResponseEntity.ok(existingTask);
    }


    @PostMapping("/{taskId}/addComment")
    public ResponseEntity<String> addComment(
            @PathVariable String taskId,
            @RequestParam String commentText,
            @RequestParam String author) {

        boolean success = taskService.addCommentToTask(taskId, commentText, author);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
    }


    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<String> deleteTask(@PathVariable String taskId) {
        boolean success = taskService.deleteTask(taskId);
        if (success) {
            return ResponseEntity.ok("Task deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/sortByDeadline")
    public ResponseEntity<List<Task>> sortByDeadline() {
        SortingStrategy strategy = new DeadlineSortingStrategy();
        List<Task> sortedTasks = taskService.getSortedTasks(strategy);
        return ResponseEntity.ok(sortedTasks);
    }

    @GetMapping("/sortByPriority")
    public ResponseEntity<List<Task>> sortByPriority() {
        SortingStrategy strategy = new PrioritySortingStrategy();
        List<Task> sortedTasks = taskService.getSortedTasks(strategy);
        return ResponseEntity.ok(sortedTasks);
    }

    @GetMapping("/engineerUtilization")
    public ResponseEntity<Map<String, Long>> getEngineerUtilization() {
        Map<String, Long> utilization = taskService.getEngineerUtilization();
        return ResponseEntity.ok(utilization);
    }

    @GetMapping("/taskMetrics")
    public ResponseEntity<Map<String, Long>> getTaskMetrics() {
        Map<String, Long> metrics = taskService.getTaskMetrics();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/averageTimeToStart")
    public ResponseEntity<Long> getAverageTimeToStart() {
        long averageTimeToStart = taskService.getAverageTimeToStart();
        return ResponseEntity.ok(averageTimeToStart);
    }
}
