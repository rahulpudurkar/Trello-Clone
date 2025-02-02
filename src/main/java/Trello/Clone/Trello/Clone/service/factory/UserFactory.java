/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.service.factory;
import Trello.Clone.Trello.Clone.model.User;

public class UserFactory {

    public static User createUser(String name, String email) {
        return new User(name, email);
    }
}
