define(["jquery"], function(
  $
) {
    return {
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
    };
});
