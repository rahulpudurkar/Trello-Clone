/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.state;
import Trello.Clone.Trello.Clone.model.Task;

public class DoneState implements TaskState {

    @Override
    public void start(Task task) {
        throw new UnsupportedOperationException("Cannot start a task that is already completed.");
    }

    @Override
    public void complete(Task task) {
        throw new UnsupportedOperationException("Task is already completed.");
    }

    @Override
    public void reopen(Task task) {
        task.updateDoingTimestamp();
        task.setState(new DoingState());
    }
}
