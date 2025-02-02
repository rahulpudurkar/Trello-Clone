/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.service.sorting;
import Trello.Clone.Trello.Clone.model.Task;
import java.util.List;

public interface SortingStrategy {
    List<Task> sort(List<Task> tasks);
}
