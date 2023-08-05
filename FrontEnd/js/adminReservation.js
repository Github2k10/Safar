let loginData = JSON.parse(localStorage.getItem("adminData"))

fetch(`http://localhost:8080/safar/reservation/all?key=${loginData.aid}`)
  .then(response => response.json())
  .then(data => {

    console.log(data)
    
    data.forEach(element => {
        document.querySelector(".UserData").innerHTML += `
        <tr>
            <td class="column1">${element.reservationID}</td>
            <td class="column2">${element.source}</td>
            <td class="column3">${element.destination}</td>
            <td class="column3">${element.status}</td>
            <td class="column4">${element.journeyDate}</td>
            <td class="column5">${element.date}</td>
            <td class="column6">${element.bookedSeat}</td>
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