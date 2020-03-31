require(["jquery"], function(
  $
) {
    return {

        get : function(url) {
            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json'
              }).done(function(response) {
              }).fail(function(jqXHR, textStatus, errorThrown) {
              });
        }


    }
});
