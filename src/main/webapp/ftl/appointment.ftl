<#include "base.ftl">
<#macro title>Appointment</#macro>
<#macro content>
    <div id="registrationForm" class="registration-form">
        <h3>Make an appointment</h3>
        <form action="/appointment" method="POST" id="appointmentForm">
            <div class="form-group">
                <label for="phone_number">Phone number:</label>
                <input type="text" class="form-control"
                       id="phone_number" name="phone_number"
                       placeholder="Enter your phone number"
                       required>
            </div>

            <div class="form-group">
                <label for="date">Date:</label>
                <input type="date" class="form-control"
                       id="date" name="date" required>
            </div>

            <div class="form-group">
                <label for="time">Time:</label>
                <input type="time" class="form-control"
                       id="time" name="time" required>
            </div>

            <div class="form-group">
                <label for="master">Choose your master:</label>
                <select id="master" name="master" class="form-control" required>
                    <#list masters as m>
                        <option value="${m.masterId}">${m.name}</option>
                    </#list>
                </select>
            </div>

            <div class="form-group">
                <label for="service">Choose your service:</label>
                <select id="service" name="service" class="form-control" required>
                    <#list services as s>
                        <option value="${s.serviceId}">${s.name}</option>
                    </#list>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Apply</button>
        </form>
    </div>

    <script>
        $("#master").change(function (event) {
            var masterId = $("#master").val()

            $.ajax({
                type: "POST",
                url: "/appointment",
                data: {
                    "action" : "master",
                    "masterId": masterId,
                },
                dataType: "json",
                success: function (response) {
                    $("#service").empty()
                    $.each(response, function (index, service) {
                        $("#service").append('<option value="' + service.serviceId + '">' + service.name + '</option>');
                    });
                },
                error: function (error) {
                    alert('error!')
                    console.error("Error:", error);
                }
            });
        })


        $("#appointmentForm").submit(function (event) {
            event.preventDefault();

            var phone = $("#phone_number").val()
            var date = $("#date").val()
            var time = $("#time").val()
            var masterId = $("#master").val()
            var serviceId = $("#service").val()

            $.ajax({
                type: "POST",
                url: "/appointment",
                data: {
                    "action": "apply",
                    "phone": phone,
                    "date": date,
                    "time": time,
                    "masterId": masterId,
                    "serviceId": serviceId
                },
                dataType: "json",
                success: function (resp) {
                    alert(resp.message)

                },
                error: function (error) {
                    alert('error!')
                    console.error("Error:", error);
                }
            });
        });

    </script>
</#macro>
