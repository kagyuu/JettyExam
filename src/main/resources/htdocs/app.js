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
    console.log(ajax);
    function ViewModel() {
        var vmdl = this;
        vmdl.appTbl = ko.observableArray();
        vmdl.clickName = function(entry) {
            ajax.findAppByName(entry.name, function (response) {
                vmdl.appTbl.removeAll();
                response.forEach(function (entry) {
                    vmdl.appTbl.push(entry);
                    $('#' + entry.id).bootstrapToggle();
                });
            });
        };
        
        vmdl.clickRelation = function(entry) {
            location.href = "con.html?app=" + entry.id;
        };
        
        ajax.findAppLatest(function (response) {
            response.forEach(function(entry){
                vmdl.appTbl.push(entry);
                $('#' + entry.id).bootstrapToggle();
            });
        });    
    }
    ko.applyBindings(new ViewModel());    
});
