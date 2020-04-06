define(["jquery"], function(
  $
) {
    return {
        getURLParams : function() {
            // IE を考慮しなくていいなら URL#searchParams を使ったほうがいい
            var params = {};
            var query = window.location.href.split("?")[1];
            if(query) {
                var kvPairAry = query.split('&');
                kvPairAry.forEach(function(kvPair,i){
                    var kv = kvPair.split('=');
                    params[kv[0]] = kv[1];
                });
            }
            return params;
        },
        findRscById : function(id, onSuccess) {
            $.ajax({
                url: 'api/rsc/findById/' + id,
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findContainsAppBinary : function(id, onSuccess) {
            $.ajax({
                url: 'api/rsc/findContainsAppBinary/' + id,
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findRscByName : function(directory, name, onSuccess) {
            $.ajax({
                url: 'api/rsc/findByName/' + directory + '/' + name,
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findRscLatest :  function(onSuccess) {
            $.ajax({
                url: 'api/rsc/names',
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findAppByName : function(name, onSuccess) {
            $.ajax({
                url: 'api/app/findByName/' + name,
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findAppById : function(id, onSuccess) {
            $.ajax({
                url: 'api/app/findById/' + id,
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findAppLatest :  function(onSuccess) {
            $.ajax({
                url: 'api/app/names',
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        },
        findContainedResource : function(id, onSuccess) {
            $.ajax({
                url: 'api/app/findContainedResource/' + id,
                type: 'GET',
                dataType: 'json'
            }).done(function (response) {
                onSuccess(response);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
                alert('access error');
            });
        }
    };
});
