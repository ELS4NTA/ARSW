// Angie Mojica
// Daniel Santanilla

var apiclient = (function () {
  var dataCallBack = [];
  return {
    getBlueprintsByAuthor: function (authname, callback) {
      callback(JSON.parse($.ajax({ url: "/blueprints/" + authname, async: false }).responseText))
    },

    getBlueprintsByNameAndAuthor: function (authname, bpname, callback) {
      callback(JSON.parse($.ajax({ url: "/blueprints/" + authname + "/" + bpname, async: false }).responseText))
    },
  };
})();
