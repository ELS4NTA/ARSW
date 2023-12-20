// Angie Mojica
// Daniel Santanilla
var useMockData = false;
var api = useMockData ? apimock : apiclient;

var app = (function () {
  var _author = "";
  var _currentBlueprint = {};
  var _blueprints = [];
  var _newBlueprint = undefined;

  var _changeAuthor = function (author) {
    _author = author;
  };

  var _draw = function (blueprint) {
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    var points = blueprint.points;
    ctx.beginPath();
    ctx.moveTo(points[0].x, points[0].y);
    for (var i = 1; i < points.length; i++) {
      ctx.lineTo(points[i].x, points[i].y);
    }
    ctx.stroke();
    $("#name-blueprint").text("Current blueprint: " + blueprint.name);
  };

  var getOffset = function (obj) {
    var offsetLeft = 0;
    var offsetTop = 0;
    do {
      if (!isNaN(obj.offsetLeft)) {
        offsetLeft += obj.offsetLeft;
      }
      if (!isNaN(obj.offsetTop)) {
        offsetTop += obj.offsetTop;
      }
    } while ((obj = obj.offsetParent));
    return { left: offsetLeft, top: offsetTop };
  };

  var addNewPoint = function (event) {
    var canvas = document.getElementById("canvas"),
      context = canvas.getContext("2d");
    var offset = getOffset(canvas);
    var newX = event.pageX - offset.left;
    var newY = event.pageY - offset.top;

    var blueprintToSearch = $("#name-blueprint").text().split(": ")[1];
    if (blueprintToSearch !== undefined) {
      _currentBlueprint = _blueprints.find(function (blueprint) {
        return blueprint.name === blueprintToSearch;
      });
      _currentBlueprint.points.push({ x: newX, y: newY });
      _draw(_currentBlueprint);
    }
  };

  var _loadDataTable = function (data) {
    _blueprints = data.map(function (blueprint) {
      return { name: blueprint.name, points: blueprint.points };
    });

    var $authorName = $("#author-name");
    $authorName.text(_author + "'s blueprints:");

    var $table = $("#blueprints-table");
    $table.find("tbody").empty();
    _blueprints.forEach(function (blueprint) {
      var $row = $("<tr>");
      $row.append($("<td>").text(blueprint.name));
      $row.append($("<td>").text(blueprint.points.length));
      var button = document.createElement("button");
      button.innerHTML = "Open";
      button.className = "btn btn-info px-3";
      button.addEventListener("click", function () {
        app.getBlueprintsByName(blueprint.name);
      });
      $row.append($("<td>").append(button));
      $table.append($row);
    });

    var totalPoints = _blueprints.reduce(function (count, blueprint) {
      return count + blueprint.points.length;
    }, 0);

    $("#total-user-points").text("Total user points: " + totalPoints);
  };

  var _createNewBlueprint = function (nameBlueprint) {
    _newBlueprint = { author: _author, points: [], name: nameBlueprint };
    _blueprints.push(_newBlueprint);
  };

  return {
    getBlueprintsByName: function (name) {
      api.getBlueprintsByNameAndAuthor(_author, name, _draw);
    },

    getBlueprints: function () {
      _changeAuthor($("#search-author").val());
      api.getBlueprintsByAuthor(_author, _loadDataTable);
    },

    addListenerToCanvas: function () {
      var canvas = document.getElementById("canvas");
      if (window.PointerEvent) {
        canvas.addEventListener("pointerdown", addNewPoint, false);
      } else {
        canvas.addEventListener("mousedown", addNewPoint, false);
      }
    },

    saveAndUpdate: function () {
      if (_newBlueprint !== undefined) {
        api
          .postBlueprintApp(_currentBlueprint)
          .then(() => api.getBlueprintsByAuthor(_author, _loadDataTable));
        _newBlueprint = undefined;
      } else {
        api
          .putBlueprintByAuthor(
            _currentBlueprint,
            _author,
            _currentBlueprint.name
          )
          .then(() => api.getBlueprintsByAuthor(_author, _loadDataTable));
      }
    },

    createBlueprint: function () {
      var nameBlueprint = $("#new-name").val();
      if (!nameBlueprint) {
        alert("Please enter a name for the new blueprint");
        return;
      }
      var canvas = document.getElementById("canvas");
      var ctx = canvas.getContext("2d");
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      _createNewBlueprint(nameBlueprint);
      $("#name-blueprint").text("Current blueprint: " + _newBlueprint.name);
      $("#new-name").val("");
    },

    delete: function () {
      var canvas = document.getElementById("canvas");
      var ctx = canvas.getContext("2d");
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      var blueprintToSearch = $("#name-blueprint").text().split(": ")[1];
      if (blueprintToSearch !== undefined) {
        _currentBlueprint = _blueprints.find(function (blueprint) {
          return blueprint.name === blueprintToSearch;
        });
      }
      api
        .deleteBlueprintByNameAndAuthor(
          _currentBlueprint,
          _author,
          _currentBlueprint.name
        )
        .then(() => api.getBlueprintsByAuthor(_author, _loadDataTable));
    },
  };
})();
