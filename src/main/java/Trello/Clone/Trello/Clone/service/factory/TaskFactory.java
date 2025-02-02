/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.service.factory;
import Trello.Clone.Trello.Clone.model.Task;

public class TaskFactory {
    public static Task createTask(String description, String assignedToEmail, String deadline, int priority, int complexity) {
        return new Task(description, assignedToEmail, deadline, priority, complexity);
    }
}
