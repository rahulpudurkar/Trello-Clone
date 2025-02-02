/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.state;
import Trello.Clone.Trello.Clone.model.Task;

public interface TaskState {
    void start(Task task);
    void complete(Task task);
    void reopen(Task task);
}
