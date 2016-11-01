function App() {
	const appId = "#app";
	const appDiv = $(appId);

	 function getActions(text) {
		let actions = $("<ul></ul>");

		let action = $("<li></li>");
        let bt = $("<button title=\"delete todo\" class=\"less\" data-text=\"" + text + "\">-</button>");
		action.append(bt);

        bt.click(function (ev) {
            ev.preventDefault();
            let row = actions.parent().parent();
            row.remove();
            let text = $(this).data().text;

            deleteTodo(text, function() {
               alert("Done!\nTodo " + text + " has been delete.");
            });
        });

		actions.append(action);

		let td = $("<td></td>");
		td.width(1);
		td.append(actions);

		return td
	}

    function updateTodo(todo) {
        $.ajax({
            'url': '/todo',
            'method': 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
             },
            'data': JSON.stringify(todo),
            'success': function (req, sts) {
                if (sts == 'success') {
                   console.log('update ' + req);
                }
            },
            'error': function (err) {
                console.dir(err);
            }
        });
    }

	function fetch() {
		$.ajax('/todo', {
			"dataType": "json",
			"success": function(data, status) {
				if (status === 'success') {
				    App.TodoList.addTodoList(data.todoList);
				}
			},
			"error": function() {
				console.log("error");
				console.dir(arguments);
			}
		});
	}

    function addTodo(todo) {
        $.ajax('/todo', {
            'method': "PUT",
            'dataType': 'json',
            'data': JSON.stringify(todo),
            'contentType': 'application/json',
            'success': function(req, stat) {
                if (stat === 'success') {
                    // console.dir(req);
                } else {
                    // alert(req);
                    // console.log(req);
                }
            },
            'error': function(err, stat) {
                console.log(stat);
                console.dir(err);
            }
        });
    }

    function fetchOne(todo, cbk) {
        $.ajax({
            'url': '/todo/' + todo.text,
            'method': 'GET',
            'success': function (req, sts) {
                if (sts === 'success') {
                    cbk(req);
                } else {
                   // something here
                }
            },
            "error": function (err) {
                console.log(err);
            }
        });
    }

    function deleteTodo(text, cbk) {
        $.ajax('/todo/'+(text), {
            'method': 'DELETE',
            'success': function (res, st) {
                if (st === 'success') {
                    cbk();
                } else {
                   // console.dir(res);
                }
            },
            'error': function() {
                console.log("error");
                console.dir(arguments);
            }
        })
    }

	function updateTodoStatus(ev) {
	    let checkbox = $(this);
        let _status = checkbox.data();
        if (_status.done) {
            checkbox.data('done', undefined);
        } else {
            checkbox.data('done', (new Date()).getTime());
        }
        let todo = {
            'id': checkbox.data('id'),
            'status': (checkbox.data('done') ? 'DONE' : 'CREATED'),
            'done': checkbox.data('done')
        };
        checkbox.parent().next().toggleClass('tododone');
        if (todo.id === undefined) {
            fetchOne(todo, function(req){
                checkbox.data('id', req.id);
                todo.id = req.id;
                updateTodo(todo);
            });
        } else {
            updateTodo(todo);
        }
    }

    function createTodo(text) {
        return {status: 'CREATED',
                created: (new Date()).getTime(),
                text: text};
    }

    function addTodoHandler(ev) {
        ev.preventDefault();
        if ($('#todo_text').val() != '') {
            let todo = createTodo($('#todo_text').val());
            App.TodoList.addRow(todo, true);
            $('#todo_text').val('');
            App.TodoList.textInput.css({'top': -100, 'left': 0});
            App.TodoList.textInput.hide();
            addTodo(todo);
        }
    }

	function TodoList() {
		this.table = appDiv.find('table');
		this.head = this.table.find('thead');
		this.body = this.table.find('tbody');

		this.bindEventHandlers();
		setTimeout(fetch, 10);
	}

	TodoList.prototype.addRow = function(todo, before) {
		let row = $("<tr></tr>");

		let td = $("<td></td>");

        td.width(1);

		let checkbox = $("<input type=\"checkbox\" />");

        if (todo.id) {
            checkbox.data('id', todo.id)
        }

		switch (todo.status) {
			case 'CREATED':
			    let _diff = ((new Date())-(new Date(parseInt(todo.created, 10))));
			    if (isNaN(_diff)) {
			        _diff = parseInt(todo.created, 10);
			    }
				checkbox.attr('title', 'Created ' + _diff);

				td.append(checkbox);

                checkbox.click(updateTodoStatus);

				break;

			case 'DONE':
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

        if (before) {
            before = this.body.children().length > 0;
        }

        if (!before) {
           this.body.append(row);
        } else {
           row.insertBefore(this.body.children()[0]);
        }
	}

    TodoList.prototype.addTodoList = function (todoList) {
        todoList.forEach(App.TodoList.addRow.bind(App.TodoList));
    }

    TodoList.prototype.showText = function() {
        if (!this.textInput) {
            this.textInput = $('span.hide');

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
        this.textInput.find('input').focus();
    }

    TodoList.prototype.showAddText = function(ev) {
        ev.preventDefault();
        this.showText();
    }

    TodoList.prototype.bindEventHandlers = function () {
        $('.plus').click(this.showAddText.bind(this));
    }

	App.TodoList = new TodoList();
}