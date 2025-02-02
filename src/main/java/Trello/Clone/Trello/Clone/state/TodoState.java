/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.state;
import Trello.Clone.Trello.Clone.model.Task;

public class TodoState implements TaskState {

    @Override
    public void start(Task task) {
        task.updateDoingTimestamp();
        task.setState(new DoingState());
    }

    @Override
    public void complete(Task task) {
        throw new UnsupportedOperationException("Cannot directly complete a task from TODO state.");
    }

    @Override
    public void reopen(Task task) {
        throw new UnsupportedOperationException("Task is already in TODO state.");
    }
}
