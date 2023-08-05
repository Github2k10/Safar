let loginData = JSON.parse(localStorage.getItem("adminData"))

fetch(`http://localhost:8080/safar/feedback/all`)
  .then(response => response.json())
  .then(data => {
    
    data.forEach(element => {
        document.querySelector(".UserData").innerHTML += `
        <tr>
            <td class="column1">${element.feedBackId}</td>
            <td class="column2">${element.userId}</td>
            <td class="column3">${element.busId}</td>
            <td class="column4">${element.driverRating}</td>
            <td class="column5">${element.serviceRating}</td>
            <td class="column6">${element.overallRating}</td>
            <td class="column7">${element.feedbackDateTime.slice(0, 10)}</td>
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