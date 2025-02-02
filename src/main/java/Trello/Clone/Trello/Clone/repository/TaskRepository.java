/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.repository;
import Trello.Clone.Trello.Clone.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.time.Instant;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

    @Query("{ 'deadline': { $gte: ?0, $lte: ?1 } }")
    List<Task> findTasksWithDeadlineBetween(Instant start, Instant end);
}
