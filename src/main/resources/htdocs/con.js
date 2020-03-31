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
    onepage: "jquery.onepage-scroll.min",
  },

  // AMDに対応してないモジュールが依存するライブラリを先に読み込むよう指定する
  shim: {
    onepage: ["jquery"],
    bootstrap_toggle: ["bootstrap"]
  }
});

require([
  "jquery",
  "knockout",
  "bootstrap",
  "bootstrap_toggle",
  "onepage",
  "domReady!"
], function($, ko) {
  function ViewModel() {
    var vmdl = this;
    vmdl.gotoApp = function() {
        $(".main").moveTo(1);
    };
    vmdl.gotoRsc = function() {
        $(".main").moveTo(2);
    };
    vmdl.gotoRel = function() {
        $(".main").moveTo(3);       
    }

    vmdl.appTbl = ko.observableArray();
    vmdl.rscTbl = ko.observableArray();
    vmdl.conTbl = ko.observableArray();
  }

  ko.applyBindings(new ViewModel());

  $(".main").onepage_scroll({
    loop: true, 
  });

  // $(".main").onepage_scroll() まで、全部のページの内容が見えてしまうので、
  // onepage_scroll の初期化が終わるまで表示内容を隠しておく
  $('.main').removeClass('hidden');

//   $.ajax({
//     url: 'api/app/findAll',
//     type: 'GET',
//     dataType: 'json'
//   }).done(function(response) {
//       console.log(response);
//   }).fail(function(jqXHR, textStatus, errorThrown) {
//       console.log(jqXHR);
//       console.log(textStatus);
//       console.log(errorThrown);
//       alert('access error');
//   });

});
