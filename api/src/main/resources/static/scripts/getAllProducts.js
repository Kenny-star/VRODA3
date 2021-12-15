function callService() {
    var json;
    uri = "/api/gateway/products";

    var xhr = new XMLHttpRequest();
    xhr.responseType = "json";
    xhr.onreadystatechange = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            json = xhr.response;
            //console.log(xhr.responseText);
            loadData(json);
        }
    };

    xhr.open("GET", uri, true);

    xhr.send(null);
}

async function loadData(json) {
    var selector = "results";

    //call the jsonToTable Function
    jsonToTable(json, selector);

    function jsonToTable(json, selector) {
        //begin function

        //array to hold the html for the table
        var html = [];

        //add the opening table and tablebody tags
        html.push("<table>\n<tbody>");

        //begin adding the table headers
        html.push("<tr>");

        html.push("<th>" + "Product ID" + "</td>");
        html.push("<th>" + "Category ID" + "</td>");
        html.push("<th>" + "Quantity" + "</td>");
        html.push("<th>" + "Title" + "</td>");
        html.push("<th>" + "Price" + "</td>");
        html.push("<th>" + "Decription" + "</td>");


        html.push("</tr>");

        console.log(json); //add the closing table and table body tags

        //loop through the array of objects
        json.forEach(function (item) {
            //begin forEach

            //add the opening table row tag
            html.push("<tr>");

            //loop though each of the objects properties
            for (var key in item) {
                //begin for in loop

                //append the table data containing the objects property value
                html.push("<td>" + item[key] + "</td>");
            } //end for in loop

            //add the closing table row tag
            html.push("</tr>");
        }); //end forEach

        html.push("<table>\n</tbody>");

        //testing display of results
        document.getElementById(selector).innerHTML = html.join("");
    } //end function
}

callService();