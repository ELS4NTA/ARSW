// Angie Mojica
// Daniel Santanilla

var apiclient = (function () {
  var getBlueprintsA = function (authname, callback) {
    var getPromise = $.ajax({
      url: "/blueprints/" + authname,
      type: "GET",
      contentType: "application/json",
      dataType: "json",
      success: function (result) {
        callback(result);
      },
    });
    return getPromise;
  };

  var getBlueprintsNA = function (authname, bpname, callback) {
    var getPromiseName = $.ajax({
      url: "/blueprints/" + authname + "/" + bpname,
      type: "GET",
      contentType: "application/json",
      dataType: "json",
      success: function (result) {
        callback(result);
      },
    });
    return getPromiseName;
  };

  var putBlueprint = function (blueprint, authname, bpname) {
    var putPromise = $.ajax({
      url: "/blueprints/" + authname + "/" + bpname,
      type: "PUT",
      data: JSON.stringify(blueprint),
      contentType: "application/json",
    });

    putPromise.then(
      function () {
        alert("The blueprint was saved correctly");
      },
      function () {
        alert("The blueprint could not be saved");
      }
    );

    return putPromise;
  };

  var postBlueprint = function (blueprint) {
    var postPromise = $.ajax({
      url: "/blueprints/",
      type: "POST",
      data: JSON.stringify(blueprint),
      contentType: "application/json",
    });

    postPromise.then(
      function () {
        alert("The blueprint was created correctly");
      },
      function () {
        alert("The blueprint could not be created");
      }
    );

    return postPromise;
  };

  var deleteBlueprint = function (blueprint, authname, bpname) {
    var deletePromise = $.ajax({
      url: "/blueprints/" + authname + "/" + bpname,
      type: "DELETE",
      data: JSON.stringify(blueprint),
      contentType: "application/json",
    });

    deletePromise.then(
      function () {
        alert("The blueprint was deleted correctly");
      },
      function () {
        alert("The blueprint could not be deleted");
      }
    );

    return deletePromise;
  };

  return {
    getBlueprintsByAuthor: function (authname, callback) {
      getBlueprintsA(authname, callback);
    },

    getBlueprintsByNameAndAuthor: function (authname, bpname, callback) {
      getBlueprintsNA(authname, bpname, callback);
    },

    putBlueprintByAuthor: function (blueprint, authname, bpname) {
      return putBlueprint(blueprint, authname, bpname);
    },

    postBlueprintApp: function (blueprint) {
      return postBlueprint(blueprint);
    },

    deleteBlueprintByNameAndAuthor: function (blueprint, authname, bpname) {
      return deleteBlueprint(blueprint, authname, bpname);
    },
  };
})();
