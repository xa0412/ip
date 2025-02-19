# XanChatBot User Guide

![XanChatBot](/docs/Ui.png)

This is Xan, your personalized task manager help to keep track of various thing!

## Adding todo

Tell Xan to add a todo task

Example: `todo read book`

Xan will add the todo to your task list and respond accordingly.

```
Got it. I've added this task:
[T][] read book
Now you have 1 tasks in the list.
```

## Adding deadlines

Tell Xan to add a deadline task

Example: `deadline return book /by 2019-12-25`

Xan will add the deadline to your task list and respond accordingly.

```
Got it. I've added this task:
[D][] return book (by: Dec 25 2019)
Now you have 2 tasks in the list.
```

## Adding events

Tell Xan to add an event task

Example: `event project meeting /from Mon 2pm /to 4pm`

Xan will add the event to your task list and respond accordingly.

```
Got it. I've added this task:
[E][] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
```

## Listing all tasks

Tell Xan to list all tasks

Example: `list`

```
Here are the tasks in your list:
1. [T][] read book
2. [D][] return book (by: Dec 25 2019)
3. [E][] project meeting (from: Mon 2pm to: 4pm)
```

## Deleting tasks

Tell Xan to delete a task

Example: `delete 1`

Xan will delete the task 1 from your task list and respond accordingly.

```
Noted. I've removed this task:
1. [T][] read book
Now you have 2 tasks in the list.
```

## Mark/Unmark tasks as done

Tell Xan to mark/unmark a task as done

Example: `mark/unmark 1`

```
Nice! I've marked this task as done:
1. [D][X] return book (by: Dec 25 2019)

Ok, I've unmarked this task as not done:
1. [D][] return book (by: Dec 25 2019)
```

## Searching for tasks

Tell Xan to search for tasks

Example: `search book`

```
Here are the matching tasks in your list:
1. [D][] return book (by: Dec 25 2019)
```

## Help command

Tell Xan to show commands

Example: `help`

```
Here are the commands you can use:
Enter the following commands to get started:
    - list: to show all the tasks in your list.
    - todo: tasks without any date/time attached to it.
            (e.g. todo visit new theme park)
    - deadline: tasks that need to be done before a specific date.
            (e.g. deadline return book /by 2019-12-25)
    - event: tasks that start at a specific date/time and end at a specific time.
            (e.g. event project meeting /from Mon 2pm /to 4pm)
    - delete: to delete a task. (e.g. delete 1)
    - mark: to mark a task as completed. (e.g. mark 1)
    - unmark: to unmark a task as not completed. (e.g. unmark 1)
    - search: to search for a task. (e.g. search book)
    - help: to show the list of commands available.
    - bye: to exit the chatbot.
What can I do for you?