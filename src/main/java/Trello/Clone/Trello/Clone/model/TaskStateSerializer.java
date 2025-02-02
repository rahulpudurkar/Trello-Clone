/** Name - Rahul Manjunath Pudurkar
 SUID - 791804558
 Email - rpudurka@syr.edu
 **/

package Trello.Clone.Trello.Clone.model;
import Trello.Clone.Trello.Clone.state.TaskState;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TaskStateSerializer extends JsonSerializer<TaskState> {
    @Override
    public void serialize(TaskState value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getClass().getSimpleName().replace("State", "").toUpperCase());
    }
}
