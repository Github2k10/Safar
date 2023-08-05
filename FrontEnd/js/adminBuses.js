let loginData = JSON.parse(localStorage.getItem("adminData"))

fetch(`http://localhost:8080/safar/bus/all`)
  .then(response => response.json())
  .then(data => {

    console.log(data)
    
    data.forEach(element => {
        document.querySelector(".UserData").innerHTML += `
        <tr>
            <td class="column1">${element.busId}</td>
            <td class="column2">${element.busName}</td>
            <td class="column3">${element.driverName}</td>
            <td class="column4">${element.routeFrom}</td>
            <td class="column5">${element.routeTo}</td>
            <td class="column5">${element.busJourneyDate}</td>
            <td class="column6">${element.departureTime}</td>
            <td class="column6">${element.availableSeats}</td>
            <td class="column7">${element.fare}</td>
            <td id="btn" class="column8">
                <i class="fi fi-rs-trash"></i>
            </td>
        </tr>
        `
    });
  })
  .catch(error => {
    console.log('Error:', error);
  });