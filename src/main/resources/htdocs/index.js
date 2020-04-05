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
  },

  // AMDに対応してないモジュールが依存するライブラリを先に読み込むよう指定する
  shim: {
    bootstrap_toggle: ["bootstrap"]
  }
});

require([
  "jquery",
  "knockout",
  "bootstrap",
  "bootstrap_toggle",
  "domReady!"
], function($, ko) {
  function ViewModel() {
    var vmdl = this;
  }
  ko.applyBindings(new ViewModel());
});
