function viewRequest(requestId) {
    $.get("Controller", {command: 'viewRequest', rentalRequestId: requestId}, function (rentalRequest) {
        if (rentalRequest.client == null){
            $("#rentalRequestError").text(rentalRequest);
            $("#rentalRequestBody").css('display', 'none');
        } else {
            $("#rentalRequestError").text();
            $("#rentalRequestBody").css('display', 'block');
            $("#client").text(rentalRequest.client.login);
            if (rentalRequest.administrator != null) {
                $("#administrator").text(rentalRequest.administrator.login);
                $("#administrator").removeClass();
            } else {
                $("#administrator").text("");
                $("#administrator").addClass("glyphicon glyphicon-refresh");
            }
            $("#seatsNumber").text(rentalRequest.seatsNumber);
            $("#checkInDate").text(rentalRequest.checkInDate);
            $("#daysNumber").text(rentalRequest.daysStayNumber);
            if (rentalRequest.fullPayment) {
                $("#requestType1").show();
                $("#requestType2").hide();
            } else {
                $("#requestType1").hide();
                $("#requestType2").show();
            }
            $("#payment").text(rentalRequest.payment);
        }
    });
}
;