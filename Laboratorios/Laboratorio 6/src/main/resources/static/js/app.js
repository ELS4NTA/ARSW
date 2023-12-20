// Angie Mojica
// Daniel Santanilla
var useMockData = false;
var api = useMockData ? apimock : apiclient;

var app = (function () {
  var _author = "";
  var _blueprints = [];

  return {
    changeAuthor: function (author) {
      _author = author;
    },

    getBlueprintsByName: function (name) {
      var author = $("#search-author").val();
      api.getBlueprintsByNameAndAuthor(author, name, function (blueprint) {
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
      });
    },

    getBlueprints: function () {
      var author = $("#search-author").val();
      api.getBlueprintsByAuthor(author, function (data) {
        _blueprints = data.map(function (blueprint) {
          return { name: blueprint.name, npoints: blueprint.points.length };
        });

        var $authorName = $("#author-name");
        $authorName.text(author + "'s blueprints:");

        var $table = $("#blueprints-table");
        $table.find("tbody").empty();
        _blueprints.forEach(function (blueprint) {
          var $row = $("<tr>");
          $row.append($("<td>").text(blueprint.name));
          $row.append($("<td>").text(blueprint.npoints));
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
          return count + blueprint.npoints;
        }, 0);

        $("#total-user-points").text("Total user points: " + totalPoints);
      });
    },

    
  };
})();
