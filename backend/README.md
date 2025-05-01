This readme will handle everything related to backend: database modeling

I - Database relational model

This is a first version of the relational model. It might change as we add features.

![Database Relational Model](https://raw.githubusercontent.com/nicolasyazman/teamtaskmanager/main/backend/pictures/database_relational_model_taskmanager.png)

Explanation:

**Users**: The accounts of the users of this application.
**Projects**: The boards owned by the users.
**Tasks**: The tasks created by users and belonging to the project.
**Task_labels**: Reusable labels (for example: "urgent") per project.
**Task_assignments**: Defines which task has been assigned to which user.
**Task_label_mapping**: Attributes a reusable label (for example: "urgent") to a task.

