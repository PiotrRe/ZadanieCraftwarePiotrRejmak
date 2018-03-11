$("#inputData").validate({
    rules: {
        firstname: "required",
        lastname: "required",
        startdate: "required",
        enddate: "required",
        placenumber: "required",
        phonenumber: "required",
    },

    messages: {
        firstname: "Proszę podać Imię.",
        lastname: "Proszę podać Nazwisko.",
        startdate: "Proszę podać datę startu.",
        enddate: "Proszę podać datę zakończenia.",
        placenumber: "Proszę podać poprawny numer miejsca parkingowego.",
        phonenumber: "Proszę podać poprawnie numer telefonu."
    }
});


$("#endDate").focusout(function () {
    var startDate = document.getElementById("startDate").value;
    var endDate = document.getElementById("endDate").value;
    $("#submitButton").prop("disabled",false);
    if (Date.parse(endDate) < Date.parse(startDate)) {
        $("#submitButton").prop("disabled",true);
        alert("Proszę wprowadzić datę późniejszą lub odpowiadającą początkowej");
        }
});


$(document).ready(function () {
    $(document).on("click", "#submitButton", function () {
        if (!$("#inputData").valid()) {
            return false;
        } else {
            sendAjax();
        }
    });
});

function sendAjax() {
    var parkingData = {
        "firstname": $('#firstName').val(),
        "lastname": $('#lastName').val(),
        "phonenumber": $('#phoneNumber').val(),
        "startdate": $('#startDate').val(),
        "enddate": $('#endDate').val(),
        "placenumber": $('#placeNumber').val()
    }

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/ParkingServlet",
        dataType: "json",
        data: JSON.stringify(parkingData),
        contentType: "application/json; charset = utf-8",

        success: function () {
            alert("Rezerwacja została pomyślnie dodana.");
        },
        error: function (error) {
            alert(error.responseJSON.message);
        }
    });
}
