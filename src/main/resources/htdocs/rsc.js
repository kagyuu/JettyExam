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
    function ViewModel() {
        var vmdl = this;
        vmdl.rscTbl = ko.observableArray();
        vmdl.clickName = function(entry) {
            ajax.findRscByName(entry.directory, entry.name, function (response) {
                console.log("success");
                vmdl.rscTbl.removeAll();
                response.forEach(function (entry) {
                    vmdl.rscTbl.push(entry);
                    $('#' + entry.id).bootstrapToggle();
                });
            });    
        };
        
        vmdl.clickRelation = function(entry) {
            location.href = "con.html?rsc=" + entry.id;
        };
        
        vmdl.changeStatus = function(entry) {
            // TODO: イベント実装
            console.log(entry);
        }
        
        vmdl.clickDelete = function(entry) {
            // TODO: イベント実装
            console.log(entry);
        }
        
        ajax.findRscLatest(function (response) {
            response.forEach(function (entry) {
                vmdl.rscTbl.push(entry);
                // Bootstrap の toggle で checkbox を wrap しているので、knockout.js からイベントを取れない
                $('#' + entry.id).bootstrapToggle().change(function() {
                    entry.enabled = $(this).prop('checked');
                    vmdl.changeStatus(entry);
                });
            });
        });
    }
    ko.applyBindings(new ViewModel());    
});
