require.config({
    // Javascript の Base Url
    baseUrl: "./js",

    // ライブラリのパスの別名を定義する
    paths: {
        jquery: "jquery-3.4.1.min",
        knockout: "knockout-3.5.1",
        // popper.js 組み込み版 bootstrap
        bootstrap: "bootstrap.bundle.min",
        bootstrap_toggle: "bootstrap4-toggle.min",
        ajax: "ajax"
    },

    // AMDに対応してないモジュールが依存するライブラリを先に読み込むよう指定する
    shim: {
        bootstrap_toggle: ["bootstrap"]
    }
});

require([
    "jquery",
    "knockout",
    "ajax",
    "bootstrap",
    "bootstrap_toggle",
    "domReady!"
], function ($, ko, ajax) {
    var params = ajax.getURLParams();
    var appId = params['app'];
    var rscId = params['rsc'];
    console.log("appId=" + appId);
    console.log("rscId=" + rscId);
    function ViewModel() {
        var vmdl = this;
        vmdl.conTbl = ko.observableArray();

        if (rscId) {
            ajax.findRscById(rscId, function(rsc){
                ajax.findContainsAppBinary(rscId, function(apps){
                    var firstEntry = true;
                    apps.forEach(function(app){
                        vmdl.conTbl.push({rsc: (firstEntry ? rsc : null), app:app});
                        firstEntry = false;
                    });
                });
            });
        } else if (appId) {
            ajax.findAppById(appId, function(app){
                ajax.findContainedResource(appId, function(rscs){
                    var firstEntry = true;
                    rscs.forEach(function(rsc){
                        vmdl.conTbl.push({rsc: rsc, app: (firstEntry ? app : null)});
                        firstEntry = false;
                    });
                });
            });
        }

        vmdl.clickRscName = function(row) {
            console.log(row);
            location.href = "con.html?rsc=" + row.rsc.id;
        };

        vmdl.clickAppName = function(row) {
            console.log(row);
            location.href = "con.html?app=" + row.app.id;
        };
    }
    ko.applyBindings(new ViewModel());
});
