/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone;
import Trello.Clone.Trello.Clone.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TrelloCloneApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TrelloCloneApplication.class, args);

		TaskService taskService = context.getBean(TaskService.class);
		taskService.initializeDefaultUsers();
	}
}
