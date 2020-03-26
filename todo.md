todo 24.03
- Move model related methods and object instances to MainController.
Only UI related parts should be present in MainView. MainView holds an instance of MainController,
while MainController should hold the MemberArchive instance. MemberArchive holds BonusMember collection. 

UI Structure
-
- Top: Menu and Toolbar 
    - VBox:
        - main menu
        - toolbar
            - add
            - remove
            - add points
            - run upgrades on members
- Center view: Table and Details
    - VBox
        - TableView
        - VBox
            - HBox: Edit tools
                - Button: Edit
                - Button: Points
                - Button: Save Changes
            - GridPane
                - Label, Text Field
                - Label, Text Field
                - Label, Text Field
                - Label, Text Field
                - Label, Text Field
            - HBox
                - Button: Delete
- Bottom:
    - HBox: Status Bar
        - Text: Number of members in register.
      
### Problem: Getting Grid to update automatically based on selection in Table:  
__Solution was to set detailsGrid as a listener to memberTable's selected item Observable__

We add the detailsGrid as a listener to the Observable table.

A ChangeListener object:
 ```
memberTable.valueProperty().addListener((o, ov, nv) -> {
    anonymous class here
});`
```
Detail view hooked into selected user
```
tableView.setOnMousePressed(mouseEvent -> {
    if (mouseEvent.isPrimaryButtonDown() && (mouseEvent.getClickCount() == 2)) {
        Literature selectedLiterature = tableView.getSelectionModel().getSelectedItem();
        mainController.doShowDetails(selectedLiterature);
    }
});

```
