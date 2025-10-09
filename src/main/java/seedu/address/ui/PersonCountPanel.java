package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

public class PersonCountPanel extends UiPart<Region> {
    private static final String FXML = "PersonCountPanel.fxml";
    private static final String PERSON_COUNT_MESSAGE =  "You currently have %d contact(s).";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label personCountText;

    public PersonCountPanel(ObservableList<Person> personList) {
        super(FXML);
        personCountText.textProperty().bind(
                Bindings.size(personList)
                        .asString(PERSON_COUNT_MESSAGE)
        );
    }
}
