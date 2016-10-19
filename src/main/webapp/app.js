function App() {
	const appId = "#app";
	const appDiv = $(appId);

	function getActions(text) {
		let actions = $("<ul></ul>");

		let action = $("<li></li>");
        let bt = $("<button title=\"delete todo\" class=\"less\" data-text=\"" + text+ "\">-</button>");
		action.append(bt);

        bt.click(function (ev) {
            ev.preventDefault();
            let row = actions.parent().parent();
            row.remove();
            $.ajax('/todo/'+(bt.data().text), {
                'method': 'DELETE',
                'success': function (res, st) {
                    if (st === 'success') {
                        alert("Done!");
                    } else {
                        console.dir(res);
                    }
                },
                'error': function() {
                    console.log("error");
                    console.dir(arguments);
                }
            });
        });

		actions.append(action);

		let td = $("<td></td>");
		td.width(1);
		td.append(actions);

		return td
	}

	function TodoList() {
		this.table = $("<table></table>");
		this.head = $("<thead></thead>");
		this.body = $("<tbody></tbody>");

		let tr = $("<tr></tr>");

		let th = $("<th></th>");

		th.html("My TODO list");
        th.append($("<button class=\"plus\">+</button>"));

		th.attr("colspan", 3);

		tr.append(th);

		this.head.append(tr);

		this.table.append(this.head);
		this.table.append(this.body);
		appDiv.append(this.table);

		this.bindEventHandlers();
	}

	TodoList.prototype.addRow = function (todo) {
		let row = $("<tr></tr>");

		let td = $("<td></td>");

        td.width(1);

		let checkbox = $("<input type=\"checkbox\" />");

		switch (todo.status) {
			case "CREATED":
			    let _diff = ((new Date())-(new Date(parseInt(todo.created, 10))));
			    if (isNaN(_diff)) {
			        _diff = parseInt(todo.created, 10);
			    }
				checkbox.attr("title", "Created " + _diff);

				td.append(checkbox);

                checkbox.click(function (ev) {
                    let _status = checkbox.data();
                    if (_status.done) {
                        checkbox.data({'done': undefined});
                    } else {
                        checkbox.data({'done': (new Date()).getTime()});
                    }
                    checkbox.parent().next().toggleClass('tododone');
                });

				break;

			case "DONE":
				checkbox.attr("checked", true);
				checkbox.attr("disabled", true);
				checkbox.attr("title", "Done " + (new Date(parseInt(todo.done, 10))));

				td.append(checkbox);
				break;
		}

	    row.append(td);

		td = $("<td></td>");

		td.html(todo.text);

	    row.append(td);

        if (todo.status == 'DONE') {
            td.addClass('tododone');
        }

	    row.append(getActions(todo.text));

		this.body.append(row);
	}

	TodoList.prototype.fetch = function () {
		$.ajax('/todo', {
			"dataType": "json",
			"success": function(data, status) {
				if (status === 'success') {
				    data.todoList.forEach(todo => {
				        App.TodoList.addRow(todo)
				    });
				}
			},
			"error": function() {
				console.log("error");
				console.dir(arguments);
			}
		});
	}

    function createTodo (text) {
        return {status: 'CREATED',
                created: (new Date()).getTime(),
                text: text};
    }

    function addTodoHandler (ev) {
        ev.preventDefault();
        if ($('#todo_text').val() != '') {
            let todo = createTodo($('#todo_text').val());
            App.TodoList.addRow(todo);
            $('#todo_text').val('');
            App.TodoList.textInput.css({'top': -100, 'left': 0});
            App.TodoList.textInput.hide();
            $.ajax('/todo', {
                'method': "PUT",
                'dataType': 'json',
                'data': JSON.stringify(todo),
                'contentType': 'application/json',
                'success': function(req, stat) {
                    if (stat === 'success') {
                        console.dir(req);
                    } else {
                        alert(req);
                        console.log(req);
                    }
                },
                'error': function(err, stat) {
                    console.log(stat);
                    console.dir(err);
                }
            });
        }
    }

    TodoList.prototype.showText = function () {
        if (!this.textInput) {
            this.textInput = $('<span class="hide"><input type="text" id="todo_text" placeholder="Insert a task and pres Add" value=""/>&nbsp;<button>Add</button></span>');
            this.textInput.css({'top': -100, 'left': 0, 'position': 'absolute'});

            $('body').append(this.textInput);

            this.textInput.find('button').click(addTodoHandler);
            this.textInput.find('input').keypress(function(ev){
                if (ev.which == 13) {
                    return addTodoHandler(ev);
                }
            });
        }

        let pos = $('.plus').position();
        pos.left = pos.left - this.textInput.width();
        pos.top += 10;

        this.textInput.css({'top': pos.top, 'left': pos.left});
        this.textInput.show();
    }

    TodoList.prototype.bindEventHandlers = function () {
        $('.plus').click(function (ev) {
            ev.preventDefault();
           App.TodoList.showText();
        });
    }

	App.TodoList = new TodoList();
}