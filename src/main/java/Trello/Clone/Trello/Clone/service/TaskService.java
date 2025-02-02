/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.service;
import Trello.Clone.Trello.Clone.model.Task;
import Trello.Clone.Trello.Clone.model.Comment;
import Trello.Clone.Trello.Clone.model.User;
import Trello.Clone.Trello.Clone.repository.TaskRepository;
import Trello.Clone.Trello.Clone.repository.UserRepository;
import Trello.Clone.Trello.Clone.service.factory.TaskFactory;
import Trello.Clone.Trello.Clone.service.notifier.TaskNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Trello.Clone.Trello.Clone.service.sorting.SortingStrategy;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskNotifier taskNotifier;

    public void initializeDefaultUsers() {
        if (userRepository.count() == 0) {
            List<User> defaultUsers = Arrays.asList(
                    new User("John", "john@example.com"),
                    new User("Paul", "paul@example.com"),
                    new User("George", "george@example.com"),
                    new User("Ringo", "ringo@example.com")
            );
            userRepository.saveAll(defaultUsers);
        }
    }

    public Task createTask(String description, String assignedToEmail, String deadline, int priority, int complexity) {
        Task task = TaskFactory.createTask(description, assignedToEmail, deadline, priority, complexity);
        return taskRepository.save(task);
    }

    private Task updateTaskState(String taskId, java.util.function.Consumer<Task> stateTransitionAction) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            stateTransitionAction.accept(task);
            taskRepository.save(task);
            return task;
        }
        return null;
    }

    public Task startTask(String taskId) {
        return updateTaskState(taskId, Task::start);
    }

    public Task completeTask(String taskId) {
        return updateTaskState(taskId, Task::complete);
    }

    public Task reopenTask(String taskId) {
        return updateTaskState(taskId, Task::reopen);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
        taskNotifier.onTaskUpdated(task.getId());
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(String taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    public boolean addCommentToTask(String taskId, String commentText, String author) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            Comment newComment = new Comment(commentText, author);
            task.addComment(newComment);
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    public boolean deleteTask(String taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }

    public List<Task> getSortedTasks(SortingStrategy strategy) {
        List<Task> tasks = taskRepository.findAll();
        return strategy.sort(tasks);
    }

    public Map<String, Long> getTaskMetrics() {
        List<Task> tasks = taskRepository.findAll();
        long totalTodoToDoing = 0, totalDoingToDone = 0, totalCreationToCompletion = 0;
        int todoToDoingCount = 0, doingToDoneCount = 0, totalCompletedTasks = 0;

        for (Task task : tasks) {
            if (task.getDoingEnteredAt() != null && task.getTodoEnteredAt() != null) {
                totalTodoToDoing += java.time.Duration.between(task.getTodoEnteredAt(), task.getDoingEnteredAt()).toMinutes();
                todoToDoingCount++;
            }
            if (task.getDoneEnteredAt() != null && task.getDoingEnteredAt() != null) {
                totalDoingToDone += java.time.Duration.between(task.getDoingEnteredAt(), task.getDoneEnteredAt()).toMinutes();
                doingToDoneCount++;
            }
            if (task.getCompletedAt() != null) {
                totalCreationToCompletion += java.time.Duration.between(task.getCreatedAt(), task.getCompletedAt()).toMinutes();
                totalCompletedTasks++;
            }
        }

        Map<String, Long> metrics = new HashMap<>();
        metrics.put("averageTodoToDoingTime", todoToDoingCount > 0 ? totalTodoToDoing / todoToDoingCount : 0);
        metrics.put("averageDoingToDoneTime", doingToDoneCount > 0 ? totalDoingToDone / doingToDoneCount : 0);
        metrics.put("averageCreationToCompletionTime", totalCompletedTasks > 0 ? totalCreationToCompletion / totalCompletedTasks : 0);

        return metrics;
    }

    public long getAverageTimeToStart() {
        List<Task> tasks = taskRepository.findAll();
        long totalTimeToStart = 0;
        int count = 0;

        for (Task task : tasks) {
            if (task.getTodoEnteredAt() != null && task.getDoingEnteredAt() != null) {
                totalTimeToStart += java.time.Duration.between(task.getTodoEnteredAt(), task.getDoingEnteredAt()).toMinutes();
                count++;
            }
        }
        return count > 0 ? totalTimeToStart / count : 0;
    }

    public Map<String, Long> getEngineerUtilization() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getAssignedToEmail() != null)
                .collect(Collectors.groupingBy(Task::getAssignedToEmail, Collectors.counting()));
    }
}
