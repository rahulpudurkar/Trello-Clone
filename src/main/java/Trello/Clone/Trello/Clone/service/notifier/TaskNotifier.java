/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.service.notifier;
import org.springframework.stereotype.Service;

@Service
public class TaskNotifier implements TaskObserver {
    @Override
    public void onTaskUpdated(String taskId) {
        System.out.println("Notification: Task " + taskId + " has been updated.");
    }
}
