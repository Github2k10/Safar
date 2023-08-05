let loginData = JSON.parse(localStorage.getItem("adminData"))

fetch(`http://localhost:8080/safar/admin/user/all?key=${loginData.aid}`)
  .then(response => response.json())
  .then(data => {

    console.log(data)
    
    data.forEach(element => {
        document.querySelector(".UserData").innerHTML += `
        <tr>
            <td class="column1">${element.userID}</td>
            <td class="column2">${element.firstName}</td>
            <td class="column3">${element.lastName}</td>
            <td class="column4">${element.mobile}</td>
            <td class="column5">${element.email}</td>
            <td class="column6">${element.totalReservation}</td>
            <td class="column7">${element.totalFeedBack}</td>
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