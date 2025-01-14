package duke;

import duke.task.Task;
import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The Duke class represents the TaskMate bot that allows users to
 * manage tasks by adding, deleting, marking and un-marking tasks.
 *
 * @author Win Sheng
 * @since 3 September 2023
 */
public class Duke {
    private static Storage storage;
    private static TaskList taskList;
    private static Parser parser;
    private static Ui ui;
    public static boolean isDone = false;

    /**
     * Constructs a new instance of the task bot with the specified file path.
     */
    public Duke() {
        storage = new Storage("./data/duke.txt");
        ui = new Ui();
        try {
            taskList = new TaskList(storage.load());
            parser = new Parser(taskList, ui, storage);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Returns the UI associated with this Duke instance.
     *
     * @return ui The Ui.
     */
    public Ui getUi() {
        return ui;
    }

    public static String getResponse(String userInput) {
        return parser.parseUserInput(userInput);
    }

    /**
     * Returns a list of tasks that are due in 3 days.
     *
     * @return A list of tasks due in 3 days.
     */
    public List<Task> getTasksDueSoon() {
        LocalDateTime now = LocalDateTime.now();

        List<Task> dueTasks = new ArrayList<>();
        for (Task task: taskList.getAllTasks()) {
            if (task.getDueDate() != null) {
                LocalDateTime dueDate = task.getDueDate();
                if (dueDate.isEqual(now) || dueDate.isBefore(now.plusDays(3))) {
                    dueTasks.add(task);
                }
            }
        }
        return dueTasks;
    }
}