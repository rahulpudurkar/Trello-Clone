/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.state;
import Trello.Clone.Trello.Clone.model.Task;

public class DoingState implements TaskState {

    @Override
    public void start(Task task) {
        throw new UnsupportedOperationException("Task is already in DOING state.");
    }

    @Override
    public void complete(Task task) {
        task.updateDoneTimestamp();
        task.setState(new DoneState());
    }

    @Override
    public void reopen(Task task) {
        task.updateTodoTimestamp();
        task.setState(new TodoState());
    }
}
