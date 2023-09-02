package duke;

import duke.util.TaskList;
import duke.util.Ui;
import duke.util.Parser;
import duke.util.Storage;

import java.io.IOException;

public class Duke {

    private Storage storage;
    private TaskList taskList;
    private Parser parser;
    private Ui ui;
    public static boolean isDone = false;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskList = new TaskList(storage.load());
            parser = new Parser();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void run() {
        ui.showWelcome();
        while (!isDone) {
            parser.parseUserInput(ui.getUserInput(), taskList, ui, storage);
        }
    }

    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }
}