let jsonMap = new Map();

function ListElement(title) {
    this.title = title;
    this.isCompleted = false;
}

function applyItemState(isCompleted, el) {
    if (isCompleted) {
        el.classList.add("checked")
    }
    else {
        el.classList.remove("checked")
    }
}


function initToDoItem(itemData) {
    var li = document.createElement("li");

    jsonMap.set(li, itemData);

    li.innerHTML = itemData.title;
    applyItemState(itemData.isCompleted, li)
    li.addEventListener("click", function updateHandler(ev) {

        var updateToDo = jsonMap.get(ev.target);
        updateToDo.isCompleted = !updateToDo.isCompleted;

        applyItemState(updateToDo.isCompleted, ev.target);

        $.ajax({
            url: "/api/update",
            method: "POST",
            data: JSON.stringify(updateToDo)
        })
    });


    var span = document.createElement("SPAN");
    span.innerHTML = "\u00D7";
    span.className = "close";
    span.addEventListener("click", function closeHandler(ev) {
        var deleteToDo = jsonMap.get(ev.target.parentElement);

        ev.stopPropagation();
        ev.preventDefault();

        var li = ev.target.parentElement;

        li.parentElement.removeChild(li);

        $.ajax({
            url: "/api/delete",
            method: "POST",
            data: JSON.stringify(deleteToDo)
        })
    })

    li.appendChild(span);


    return li;
}


function readList() {
    $.ajax({
        url: '/api/list',
        method: 'GET',
        success: function (data) {
            document.getElementById("taskInput").value = "";
            var fragment = document.createDocumentFragment();
            if (data.error === undefined) {
                for (var i = 0; i < data.data.length; i++) {
                    var jsonObj = data.data[i];

                    if (jsonObj !== undefined) {
                        fragment.appendChild(initToDoItem(jsonObj));
                    }
                }

                document.getElementById("tasksList").appendChild(fragment);
            } else {
                alert(data.error);
            }
        }
    });
}

function newElement() {

    var inputValue = document.getElementById("taskInput").value;

    if (inputValue === '') {
        alert("You must wtite something!");
        return;
    }
    document.getElementById("taskInput").value = "";

    var elementForSend = new ListElement(inputValue.toString());

    var li = initToDoItem(elementForSend);
    document.getElementById("tasksList").appendChild(li);


    $.ajax({
        method: "POST",
        url: "/api/create",
        data: JSON.stringify(elementForSend)
    }).then(function success(data) {
        jsonMap.set(li, data);
    })
}

document.addEventListener("DOMContentLoaded", readList());

