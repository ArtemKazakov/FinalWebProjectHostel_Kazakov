function getRooms(page) {
    $.get("Controller", {command: 'getRooms', page: page}, function (rooms) {
       for (i in rooms){
           var result = "<tr>";
           result = result.concat("<td>"+rooms[i].number+"</td>");
           result = result.concat("<td>"+rooms[i].seatsNumber+"</td>");
           result = result.concat("<td>"+rooms[i].perdayCost+"</td>");
           result = result.concat("</tr>");
           $("#room-table").find('tbody').append(result);
       }
    });
}
;