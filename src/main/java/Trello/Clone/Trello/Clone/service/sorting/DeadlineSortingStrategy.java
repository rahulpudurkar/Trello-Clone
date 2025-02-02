/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.service.sorting;
import Trello.Clone.Trello.Clone.model.Task;
import java.util.List;

public class DeadlineSortingStrategy implements SortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        tasks.sort((t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline()));
        return tasks;
    }
}
