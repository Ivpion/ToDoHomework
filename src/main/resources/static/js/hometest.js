/**
 *
 * @type {Map<HTMLElement,TodoModel>}
 */
let jsonMap = new Map();

let pagination;

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
        }).then(invalidResp);
    });


    var span = document.createElement("SPAN");
    span.innerHTML = "\u00D7";
    span.className = "closed";
    span.id = "close";
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
        }).then(invalidResp);
    })

    li.appendChild(span);


    return li;
}

function newElement() {

    var inputValue = document.getElementById("taskInput").value;

    if (inputValue === '') {
        alert("You must write something!");
        return;
    }
    document.getElementById("taskInput").value = "";

    var elementForSend = new ListElement(inputValue.toString());


    $.ajax({
        method: "POST",
        url: "/api/create",
        data: JSON.stringify(elementForSend)
    }).then(function success(resp) {
        if(!resp.error) {
            pagination.pagination("refresh");

            // var li = initToDoItem(elementForSend);
            // document.getElementById("tasksList").appendChild(li);
            //
            // jsonMap.set(li, resp.data);
        }
        else {
            // todo show error
        }
    })
}

function createList(data) {
    document.getElementById("taskInput").value = "";
    var fragment = document.createDocumentFragment();
    if (data.error === undefined) {
        for (var i = 0; i < data.length; i++) {
            var jsonObj = data[i];

            if (jsonObj !== undefined) {
                fragment.appendChild(initToDoItem(jsonObj));
            }
        }
        $("#tasksList").empty().append(fragment);
    } else {
        alert(data.error);
    }
}

function initPagination() {
    pagination = $('#paginator').pagination({
        dataSource: '/api/list', //[1, 2, 3, 4, 5, 6, 7, 195],
        pageSize: $('#perPageCount').val(),
        alias: {
            pageNumber: 'page',
            pageSize: 'count'
        },
        locator: function () {
            // find data and return
            return 'data.items';
        },
        // totalNumber: 120,
        totalNumberLocator: function (response) {
            // {
            //      data: {
            //          items: [ {...}, ....],
            //          total: 24
            //      },
            // console.log(response);
            // var total = 5 * 10; // total item count
            return response.data.total;
        },
        callback: function (data, pagination) {
            // template method of yourself
            // var html = template(data);
            // dataContainer.html(html);
            console.log(data);
            console.log(pagination);
            console.log(pagination.pageNumber);
            createList(data);
        }
    });
}

function invalidResp(resp) {
    if(resp.error){
        alert(resp.error)
        location.reload();
    }
}

document.addEventListener("DOMContentLoaded", initPagination);
     $('#perPageCount').on("change", function () {
        initPagination();
    })






